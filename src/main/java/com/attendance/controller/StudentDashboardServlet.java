package com.attendance.controller;

import com.attendance.DAO.StudentDashboardDAO;
import com.attendance.model.StudentBean;
import com.attendance.model.SubjectBean;
import com.attendance.model.AttendanceSummaryBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class StudentDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("rollNo") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        System.out.println("In student dashboard servlet");
        String rollNo = (String) session.getAttribute("rollNo");

        // Fetch student details
        StudentBean student = StudentDashboardDAO.getStudentDetails(rollNo);

        // Fetch student subjects
        List<SubjectBean> subjects = StudentDashboardDAO.getStudentSubjects(rollNo);

        // Fetch attendance summary
        List<AttendanceSummaryBean> attendanceSummary = StudentDashboardDAO.getAttendanceSummary(rollNo);

        // Set attributes for JSP
        request.setAttribute("student", student);
        request.setAttribute("subjects", subjects);
        request.setAttribute("attendanceSummary", attendanceSummary);

        // Forward to JSP
        System.out.println("now going to student dashboard");
        request.getRequestDispatcher("student_dashboard.jsp").forward(request, response);
    }
}
