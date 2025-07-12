package com.attendance.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.attendance.model.AttendanceSummaryBean;
import com.attendance.model.DBConnection;
import com.attendance.model.StudentBean;
import com.attendance.model.TeacherBean;
import com.attendance.model.SubjectBean;

public class TeacherDashboardDAO {

    public static TeacherBean getTeacherDetails(int teacherId) throws SQLException {
        TeacherBean teacher = null;
        String sql = "SELECT * FROM teachers WHERE teacher_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    teacher = new TeacherBean();
                    teacher.setTeacherId(rs.getInt("teacher_id"));
                    teacher.setTeacherName(rs.getString("name"));
                    teacher.setTeacherEmail(rs.getString("email"));
                    teacher.setTeacherUsername(rs.getString("username"));
                }
            }
        }
        return teacher;
    }

    public static SubjectBean getAssignedSubject(int teacherId) throws SQLException {
        SubjectBean subject = null;
        String sql = "SELECT * FROM subjects WHERE teacher_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    subject = new SubjectBean();
                    subject.setSubjectId(rs.getInt("subject_id"));
                    subject.setSubjectName(rs.getString("subject_name"));
                    subject.setSubjectType(rs.getString("subject_type"));
                    subject.setTeacherId(teacherId);
                }
            }
        }
        return subject;
    }

    public static List<StudentBean> getStudentsForSubject(int subjectId, String subjectType) throws SQLException {
        List<StudentBean> students = new ArrayList<>();
        String sql;

        if ("core".equalsIgnoreCase(subjectType)) {
            sql = "SELECT * FROM students";
        } else {
            sql = "SELECT s.* FROM students s JOIN student_electives se ON s.roll_no = se.roll_no WHERE se.subject_id = ?";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (!"core".equalsIgnoreCase(subjectType)) {
                stmt.setInt(1, subjectId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StudentBean student = new StudentBean();
                    student.setUserId(rs.getInt("user_id"));
                    student.setRollNo(rs.getString("roll_no"));
                    student.setFullName(rs.getString("full_name"));
                    students.add(student);
                }
            }
        }

        return students;
    }


    public static void markAttendance(List<String> rollNos, int subjectId, Date date, byte lectureNo, String status) throws SQLException {
        String sql = "INSERT INTO attendance (roll_no, subject_id, date, lecture_no, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (String rollNo : rollNos) {
                stmt.setString(1, rollNo);
                stmt.setInt(2, subjectId);
                stmt.setDate(3, date);
                stmt.setByte(4, lectureNo);
                stmt.setString(5, status);
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }
    public void saveAttendanceRecord(String rollNo, int subjectId, java.time.LocalDate date, int lectureNo, boolean present) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM attendance WHERE roll_no = ? AND subject_id = ? AND date = ?";
        String insertSql = "INSERT INTO attendance (roll_no, subject_id, lecture_no, date, status) VALUES (?, ?, ?, ?, ?)";
        String updateSql = "UPDATE attendance SET status = ?, lecture_no = ? WHERE roll_no = ? AND subject_id = ? AND date = ?";

        String statusStr = present ? "Present" : "Absent";

        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, rollNo);
                checkStmt.setInt(2, subjectId);
                checkStmt.setDate(3, java.sql.Date.valueOf(date));

                try (ResultSet rs = checkStmt.executeQuery()) {
                    rs.next();
                    int count = rs.getInt(1);

                    if (count == 0) {
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setString(1, rollNo);
                            insertStmt.setInt(2, subjectId);
                            insertStmt.setInt(3, lectureNo);
                            insertStmt.setDate(4, java.sql.Date.valueOf(date));
                            insertStmt.setString(5, statusStr);
                            insertStmt.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setString(1, statusStr);
                            updateStmt.setInt(2, lectureNo);
                            updateStmt.setString(3, rollNo);
                            updateStmt.setInt(4, subjectId);
                            updateStmt.setDate(5, java.sql.Date.valueOf(date));
                            updateStmt.executeUpdate();
                        }
                    }
                }
            }
        }
    }
    
    public List<AttendanceSummaryBean> getAttendanceSummary(int subjectId) throws SQLException {
        List<AttendanceSummaryBean> summaryList = new ArrayList<>();

        String sql = "SELECT s.roll_no, s.full_name, " +
                     "COUNT(a.attendance_id) AS totalLectures, " +
                     "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) AS lecturesAttended " +
                     "FROM students s " +
                     "LEFT JOIN attendance a ON s.roll_no = a.roll_no AND a.subject_id = ? " +
                     "GROUP BY s.roll_no, s.full_name " +
                     "ORDER BY s.roll_no";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subjectId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AttendanceSummaryBean summary = new AttendanceSummaryBean();
                    summary.setRollNo(rs.getString("roll_no"));
                    summary.setFullName(rs.getString("full_name"));
                    summary.setTotalLectures(rs.getInt("totalLectures"));
                    summary.setLecturesAttended(rs.getInt("lecturesAttended"));
                    summary.calculatePercentage(); // method to calculate attendance %
                    summaryList.add(summary);
                }
            }
        }

        return summaryList;
    }
    
    public static int getTeacherIdByLoginId(String loginId) throws SQLException {
        String sql = "SELECT teacher_id FROM teachers WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loginId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("teacher_id");
            }
        }
        throw new SQLException("Teacher not found for login ID: " + loginId);
    }

}
