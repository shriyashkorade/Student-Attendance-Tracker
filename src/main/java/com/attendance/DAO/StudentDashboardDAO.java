package com.attendance.DAO;

import com.attendance.model.DBConnection;
import com.attendance.model.StudentBean;
import com.attendance.model.SubjectBean;
import com.attendance.model.AttendanceRecordBean;
import com.attendance.model.AttendanceSummaryBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDashboardDAO {

    // 1. Get student details by roll number
    public static StudentBean getStudentDetails(String rollNo) {
        StudentBean student = null;
        String sql = "SELECT * FROM students WHERE roll_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rollNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                student = new StudentBean();
                student.setUserId(rs.getInt("user_id"));
                student.setRollNo(rs.getString("roll_no"));
                student.setFullName(rs.getString("full_name"));
                student.setPassword(rs.getString("password")); // optional
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    // 2. Get all subjects (core + selected elective) for a student
    public static List<SubjectBean> getStudentSubjects(String rollNo) {
        List<SubjectBean> subjects = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            // Fetch core subjects
            String coreSql = "SELECT * FROM subjects WHERE subject_type = 'core'";
            PreparedStatement corePs = conn.prepareStatement(coreSql);
            ResultSet coreRs = corePs.executeQuery();
            while (coreRs.next()) {
                SubjectBean sb = new SubjectBean();
                sb.setSubjectId(coreRs.getInt("subject_id"));
                sb.setSubjectName(coreRs.getString("subject_name"));
                subjects.add(sb);
            }

            // Fetch elective selected by the student
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

    // 3. Get detailed attendance records for a student
    public static List<AttendanceRecordBean> getAttendanceRecords(String rollNo) {
        List<AttendanceRecordBean> list = new ArrayList<>();
        String sql = "SELECT a.date, a.lecture_no, s.subject_name, a.status " +
                     "FROM attendance a " +
                     "JOIN subjects s ON a.subject_id = s.subject_id " +
                     "WHERE a.roll_no = ? " +
                     "ORDER BY a.date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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

    // 4. Get attendance summary per subject
    public static List<AttendanceSummaryBean> getAttendanceSummary(String rollNo) {
        List<AttendanceSummaryBean> list = new ArrayList<>();
        String sql = "SELECT s.subject_name, " +
                     "COUNT(a.attendance_id) AS totalLectures, " +
                     "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) AS lecturesAttended " +
                     "FROM attendance a " +
                     "JOIN subjects s ON a.subject_id = s.subject_id " +
                     "WHERE a.roll_no = ? " +
                     "GROUP BY s.subject_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rollNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AttendanceSummaryBean summary = new AttendanceSummaryBean();
                summary.setSubjectName(rs.getString("subject_name"));
                summary.setTotalLectures(rs.getInt("totalLectures"));
                summary.setLecturesAttended(rs.getInt("lecturesAttended"));
                double percentage = (summary.getTotalLectures() == 0) ? 0.0 :
                        (summary.getLecturesAttended() * 100.0) / summary.getTotalLectures();
                summary.setPercentage(percentage);
                list.add(summary);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static String getRollNoByUserId(int userId) {
        String rollNo = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT roll_no FROM students WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rollNo = rs.getString("roll_no");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rollNo;
    }

}
