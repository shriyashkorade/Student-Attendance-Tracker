package com.attendance.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;
import com.attendance.DAO.AddTeacherDAO;
import com.attendance.model.TeacherBean;

public class AddTeacherServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        TeacherBean teacher = new TeacherBean();
        teacher.setTeacherName(request.getParameter("teacher_name"));
        teacher.setTeacherEmail(request.getParameter("teacher_email"));
        teacher.setTeacherUsername(request.getParameter("teacher_username"));
        teacher.setTeacherPassword(request.getParameter("teacher_password"));

        AddTeacherDAO teacherDAO = new AddTeacherDAO();
        boolean isAdded = teacherDAO.addTeacher(teacher);

        if (isAdded) {
            request.setAttribute("message", "Teacher added successfully!");
            request.setAttribute("status", "success");
        } else {
            request.setAttribute("message", "Failed to add teacher.");
            request.setAttribute("status", "error");
        }

        // Forward back to add_teacher.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("add_teacher.jsp");
        dispatcher.forward(request, response);
    }
}
