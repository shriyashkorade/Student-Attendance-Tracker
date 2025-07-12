package com.attendance.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.attendance.model.SubjectBean;
import com.attendance.DAO.AddSubjectDAO;

import java.io.IOException;

public class AddSubjectServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String subjectName = request.getParameter("subject_name");
        String subjectType = request.getParameter("subject_type");

        SubjectBean subject = new SubjectBean();
        subject.setSubjectName(subjectName);
        subject.setSubjectType(subjectType);

        AddSubjectDAO dao = new AddSubjectDAO();
        boolean isAdded = dao.addSubject(subject);

        if (isAdded) {
            request.setAttribute("message", "Subject added successfully.");
            request.setAttribute("msgClass", "success");
        } else {
            request.setAttribute("message", "Failed to add subject.");
            request.setAttribute("msgClass", "error");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("add_subject.jsp");
        dispatcher.forward(request, response);
    }
}
