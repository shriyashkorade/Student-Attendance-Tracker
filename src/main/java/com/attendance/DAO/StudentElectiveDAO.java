package com.attendance.DAO;

import com.attendance.model.AttendanceRecordBean;
import com.attendance.model.AttendanceSummaryBean;
import com.attendance.model.DBConnection;
import com.attendance.model.StudentBean;
import com.attendance.model.SubjectBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentElectiveDAO {
	//To get attendance summary
	public static List<AttendanceSummaryBean> getAttendanceSummary(String rollNo) {
	    List<AttendanceSummaryBean> list = new ArrayList<>();
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "SELECT s.subject_name, COUNT(a.attendance_id) AS totalLectures, " +
	                     "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) AS lecturesAttended " +
	                     "FROM attendance a JOIN subjects s ON a.subject_id = s.subject_id " +
	                     "WHERE a.roll_no = ? GROUP BY s.subject_name";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, rollNo);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            AttendanceSummaryBean summary = new AttendanceSummaryBean();
	            summary.setSubjectName(rs.getString("subject_name"));
	            summary.setTotalLectures(rs.getInt("totalLectures"));
	            summary.setLecturesAttended(rs.getInt("lecturesAttended"));
	            int total = rs.getInt("totalLectures");
	            int attended = rs.getInt("lecturesAttended");
	            summary.setPercentage(total == 0 ? 0 : ((double) attended / total) * 100);
	            list.add(summary);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	//To get attendance record 
	public static List<AttendanceRecordBean> getAttendanceRecords(String rollNo) {
	    List<AttendanceRecordBean> list = new ArrayList<>();
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "SELECT a.date, a.lecture_no, s.subject_name, a.status " +
	                     "FROM attendance a JOIN subjects s ON a.subject_id = s.subject_id " +
	                     "WHERE a.roll_no = ? ORDER BY a.date DESC";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, rollNo);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            AttendanceRecordBean record = new AttendanceRecordBean();
	            record.setDate(rs.getDate("date"));
	            record.setLectureNo(rs.getInt("lecture_no"));
	            record.setSubjectName(rs.getString("subject_name"));
	            record.setStatus(rs.getString("status"));
	            list.add(record);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	//To get all details of student
	public static StudentBean getStudentDetails(String rollNo) {
	    StudentBean student = null;
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "SELECT * FROM students WHERE roll_no = ?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, rollNo);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            student = new StudentBean();
	            student.setUserId(rs.getInt("user_id"));
	            student.setRollNo(rs.getString("roll_no"));
	            student.setFullName(rs.getString("full_name"));
	            // Add more fields if you want
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return student;
	}

	
	public static List<SubjectBean> getStudentSubjects(String rollNo) {
	    List<SubjectBean> subjects = new ArrayList<>();
	    try (Connection conn = DBConnection.getConnection()) {
	        // Core subjects
	        String coreSql = "SELECT subject_id, subject_name FROM subjects WHERE subject_type = 'core'";
	        PreparedStatement corePs = conn.prepareStatement(coreSql);
	        ResultSet coreRs = corePs.executeQuery();
	        while (coreRs.next()) {
	            SubjectBean sb = new SubjectBean();
	            sb.setSubjectId(coreRs.getInt("subject_id"));
	            sb.setSubjectName(coreRs.getString("subject_name"));
	            subjects.add(sb);
	        }
	        // Elective selected by student
	        String electiveSql = "SELECT s.subject_id, s.subject_name FROM subjects s " +
	                             "JOIN student_electives se ON s.subject_id = se.subject_id " +
	                             "WHERE se.roll_no = ?";
	        PreparedStatement electivePs = conn.prepareStatement(electiveSql);
	        electivePs.setString(1, rollNo);
	        ResultSet electiveRs = electivePs.executeQuery();
	        while (electiveRs.next()) {
	            SubjectBean sb = new SubjectBean();
	            sb.setSubjectId(electiveRs.getInt("subject_id"));
	            sb.setSubjectName(electiveRs.getString("subject_name"));
	            subjects.add(sb);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return subjects;
	}

    // Get all elective subjects
    public static List<SubjectBean> getElectiveSubjects() {
        List<SubjectBean> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT subject_id, subject_name FROM subjects WHERE subject_type = 'elective'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SubjectBean sb = new SubjectBean();
                sb.setSubjectId(rs.getInt("subject_id"));
                sb.setSubjectName(rs.getString("subject_name"));
                list.add(sb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Check if a student has already selected an elective
    public static boolean hasSelectedElective(String rollNo) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM student_electives WHERE roll_no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rollNo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Save student's elective selection
    public static boolean saveStudentElective(String rollNo, int subjectId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO student_electives (roll_no, subject_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rollNo);
            ps.setInt(2, subjectId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
