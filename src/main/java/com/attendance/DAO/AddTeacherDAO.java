package com.attendance.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.attendance.model.DBConnection;
import com.attendance.model.TeacherBean;

public class AddTeacherDAO {

    // Method to add a teacher to the database
    public boolean addTeacher(TeacherBean teacher) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean isAdded = false;

        try {
            // Get the database connection
            con = DBConnection.getConnection(); // Make sure to have a DatabaseConnection utility
            String sql = "INSERT INTO teachers (name, email, username, password) VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, teacher.getTeacherName());
            stmt.setString(2, teacher.getTeacherEmail());
            stmt.setString(3, teacher.getTeacherUsername());
            stmt.setString(4, teacher.getTeacherPassword());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database resources
        	closeConnection(con, stmt);
        }

        return isAdded;
    }
    private void closeConnection(Connection con, PreparedStatement stmt) {
        try {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


