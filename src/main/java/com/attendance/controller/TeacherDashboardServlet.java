package com.attendance.controller;

import com.attendance.DAO.TeacherDashboardDAO;
import com.attendance.model.TeacherBean;
import com.attendance.model.SubjectBean;
import com.attendance.model.StudentBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacher_dashboard")
public class TeacherDashboardServlet extends HttpServlet {

    private TeacherDashboardDAO dao = new TeacherDashboardDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get current session; don't create new if none exists
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("teacherId") == null) {
            // Not logged in or session expired, redirect to login page
            resp.sendRedirect("login.jsp");
            return;
        }

        // Retrieve teacher ID from session
        int teacherId = (Integer) session.getAttribute("teacherId");
        try {
        // Fetch teacher details
        TeacherBean teacher = dao.getTeacherDetails(teacherId);

        // Fetch assigned subject
        SubjectBean subject = dao.getAssignedSubject(teacherId);

        // Fetch list of students assigned to this subject
        List<StudentBean> students = null;
        if (subject != null) {
        	System.out.println("Subject Type: " + subject.getSubjectType());
            students = dao.getStudentsForSubject(subject.getSubjectId(),subject.getSubjectType());
        }

        // Set attributes to request scope for JSP
        req.setAttribute("teacher", teacher);
        req.setAttribute("subject", subject);
        req.setAttribute("students", students);

        // Forward to JSP page to display dashboard
        System.out.println("Students size: " + (students != null ? students.size() : "null"));
        req.getRequestDispatcher("teacher_dashboard.jsp").forward(req, resp);
        }catch (SQLException e) {
            e.printStackTrace();
            // Optionally forward to an error page or display a message
            req.setAttribute("errorMessage", "Database error: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
