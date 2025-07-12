package com.attendance.controller;

import com.attendance.DAO.TeacherDashboardDAO;
import com.attendance.model.StudentBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@WebServlet("/mark_attendance")
public class MarkAttendanceServlet extends HttpServlet {

    private TeacherDashboardDAO dao = new TeacherDashboardDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("teacherId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int teacherId = (Integer) session.getAttribute("teacherId");
        String attendanceDateStr = req.getParameter("attendanceDate");
        String lectureNoStr = req.getParameter("lectureNo");
        String[] presentStudentIdsArr = req.getParameterValues("presentStudentIds");

        if (attendanceDateStr == null || attendanceDateStr.isEmpty()) {
            req.setAttribute("errorMessage", "Attendance date is required.");
            req.getRequestDispatcher("teacher_dashboard").forward(req, resp);
            return;
        }

        LocalDate attendanceDate;
        try {
            attendanceDate = LocalDate.parse(attendanceDateStr);
        } catch (DateTimeParseException e) {
            req.setAttribute("errorMessage", "Invalid date format.");
            req.getRequestDispatcher("teacher_dashboard").forward(req, resp);
            return;
        }

        int lectureNo = 1; // default
        try {
            lectureNo = Integer.parseInt(lectureNoStr);
            if (lectureNo < 1) {
                throw new NumberFormatException("Lecture number must be >= 1");
            }
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Invalid lecture number.");
            req.getRequestDispatcher("teacher_dashboard").forward(req, resp);
            return;
        }

        try {
            TeacherDashboardDAO dao = new TeacherDashboardDAO();
            var subject = dao.getAssignedSubject(teacherId);

            if (subject == null) {
                req.setAttribute("errorMessage", "No assigned subject found.");
                req.getRequestDispatcher("teacher_dashboard").forward(req, resp);
                return;
            }

            var students = dao.getStudentsForSubject(subject.getSubjectId(), subject.getSubjectType());

            Set<Integer> presentStudentIds = new HashSet<>();
            if (presentStudentIdsArr != null) {
                for (String idStr : presentStudentIdsArr) {
                    presentStudentIds.add(Integer.parseInt(idStr));
                }
            }

            for (StudentBean student : students) {
                boolean present = presentStudentIds.contains(student.getUserId());
                dao.saveAttendanceRecord(student.getRollNo(), subject.getSubjectId(), attendanceDate, lectureNo, present);
            }

            session.setAttribute("successMessage", "Attendance recorded successfully for " + attendanceDateStr);
            resp.sendRedirect("teacher_dashboard");

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Database error: " + e.getMessage());
            req.getRequestDispatcher("teacher_dashboard").forward(req, resp);
        }
    }

}
