package com.attendance.DAO;

import java.sql.*;
import com.attendance.model.DBConnection;
import com.attendance.model.LoginBean;

public class LoginDAO {

    public static String validateAndGetUserId(LoginBean bean) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String userId = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "";

            // Choose the SQL query based on the role
            switch (bean.getRole()) {
                case "student":
                    sql = "SELECT user_id FROM students WHERE roll_no = ? AND password = ?";
                    break;
                case "teacher":
                    sql = "SELECT teacher_id FROM teachers WHERE username = ? AND password = ?";
                    break;
                case "admin":
                    sql = "SELECT admin_id FROM admin WHERE username = ? AND password = ?";
                    break;
                default:
                    return null; // Invalid role
            }

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, bean.getLoginId());
            stmt.setString(2, bean.getPassword());

            rs = stmt.executeQuery();

            // If user is found, get the actual user ID based on the role
            if (rs.next()) {
                if ("student".equals(bean.getRole())) {
                    userId = rs.getString("user_id"); // For student, extract user_id
                } else if ("teacher".equals(bean.getRole())) {
                    userId = rs.getString("teacher_id"); // For teacher, extract teacher_id
                } else if ("admin".equals(bean.getRole())) {
                    userId = rs.getString("admin_id"); // For admin, extract admin_id
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return userId; // Return the user ID if credentials are valid, otherwise null
    }
}
