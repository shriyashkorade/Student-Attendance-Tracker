package com.attendance.controller;

import com.attendance.DAO.StudentDashboardDAO;
import com.attendance.DAO.StudentElectiveDAO;
import com.attendance.model.SubjectBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ElectiveSelectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("ElectiveSelectionServlet: doGet() called");
    	// Show elective subjects to student
        List<SubjectBean> electives = StudentElectiveDAO.getElectiveSubjects();
        request.setAttribute("electives", electives);
        request.getRequestDispatcher("select_elective.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("rollNo") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String rollNo = (String) session.getAttribute("rollNo");
        int subjectId = Integer.parseInt(request.getParameter("electiveSubjectId"));

        boolean saved = StudentElectiveDAO.saveStudentElective(rollNo, subjectId);

        if (saved) {
            response.sendRedirect("student_dashboard"); // servlet mapping for StudentDashboardServlet
        } else {
            request.setAttribute("error", "Failed to save elective. Please try again.");
            doGet(request, response);
        }
    }
}
