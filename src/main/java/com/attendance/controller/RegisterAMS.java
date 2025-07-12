package com.attendance.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.attendance.model.DBConnection;
import com.attendance.model.RegisterBean;

public class RegisterAMS extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create RegisterBean instance and set properties from request parameters
        RegisterBean registerBean = new RegisterBean();
        registerBean.setFullName(request.getParameter("full_name"));
        registerBean.setRollNo(request.getParameter("roll_no"));
        registerBean.setPassword(request.getParameter("password"));
        registerBean.setConfirmPassword(request.getParameter("confirm_password"));

        // Validate if the passwords match using the bean method
        if (!registerBean.isPasswordMatching()) {
            response.sendRedirect("register.jsp?error=Passwords do not match");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Get a connection to the database
            conn = DBConnection.getConnection();

            // SQL query for inserting a new user (assuming 'users' table with relevant fields)
            String sql = "INSERT INTO students (roll_no, password, full_name) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, registerBean.getRollNo());
            stmt.setString(2, registerBean.getPassword());
            stmt.setString(3, registerBean.getFullName());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                response.sendRedirect("register.jsp?success=Registration successful. You can now log in.");
            } else {
                response.sendRedirect("register.jsp?error=Registration failed. Try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=Server error during registration.");
        } finally {
            // Clean up database resources
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
