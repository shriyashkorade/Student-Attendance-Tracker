package com.attendance.controller;

import com.attendance.DAO.TeacherDashboardDAO;
import com.attendance.model.AttendanceSummaryBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacherAttendanceSummary")
public class TeacherAttendanceSummaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("teacherId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int teacherId = (Integer) session.getAttribute("teacherId");

        try {
            TeacherDashboardDAO dao = new TeacherDashboardDAO();
            var subject = dao.getAssignedSubject(teacherId);

            if (subject == null) {
                req.setAttribute("errorMessage", "No assigned subject found.");
                req.getRequestDispatcher("teacher_dashboard.jsp").forward(req, resp);
                return;
            }

            List<AttendanceSummaryBean> summaryList = dao.getAttendanceSummary(subject.getSubjectId());

            req.setAttribute("attendanceSummary", summaryList);
            req.setAttribute("subjectName", subject.getSubjectName());
            req.getRequestDispatcher("teacher_attendance_summary.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Database error: " + e.getMessage());
            req.getRequestDispatcher("teacher_dashboard.jsp").forward(req, resp);
        }
    }
}
