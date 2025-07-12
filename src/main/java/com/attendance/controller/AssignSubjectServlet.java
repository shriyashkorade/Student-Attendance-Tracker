package com.attendance.controller;

import com.attendance.DAO.AssignTeacherDAO;
import com.attendance.model.AssignTeacherBean;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

public class AssignSubjectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int teacherId = Integer.parseInt(request.getParameter("teacher_id"));
        int subjectId = Integer.parseInt(request.getParameter("subject_id"));

        // Use AssignTeacherBean here
        AssignTeacherBean assignBean = new AssignTeacherBean();
        assignBean.setTeacherId(teacherId);
        assignBean.setSubjectId(subjectId);

        AssignTeacherDAO dao = new AssignTeacherDAO();
        boolean success = dao.assignSubjectToTeacher(assignBean);

        if (success) {
            request.setAttribute("message", "Subject assigned successfully.");
            request.setAttribute("messageType", "success");
        } else {
            request.setAttribute("message", "Failed to assign subject. Try again.");
            request.setAttribute("messageType", "error");
        }

        request.getRequestDispatcher("assign_subject.jsp").forward(request, response);
    }
}
