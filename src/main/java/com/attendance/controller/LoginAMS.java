package com.attendance.controller;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.attendance.model.LoginBean;
import com.attendance.DAO.LoginDAO;
import com.attendance.DAO.StudentElectiveDAO;

public class LoginAMS extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("In LoginAMS");
        // 1. Collect login form data
        String loginId = request.getParameter("login_id");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // 2. Wrap in LoginBean
        LoginBean loginBean = new LoginBean();
        loginBean.setLoginId(loginId);
        loginBean.setPassword(password);
        loginBean.setRole(role);

        // 3. Validate basic input
        if (!loginBean.isValid()) {
            response.sendRedirect("login.jsp?error=Please+fill+in+all+fields");
            return;
        }

        try {
            // 4. Validate credentials and get actual user ID (user_id, teacher_id, admin_id)
            String userId = LoginDAO.validateAndGetUserId(loginBean);

            if (userId != null) {
                HttpSession session = request.getSession();
                session.setAttribute("role", role);
                session.setAttribute("loggedIn", true);

                switch (role) {
                    case "admin":
                        session.setAttribute("adminId", Integer.parseInt(userId));
                        response.sendRedirect("admin_dashboard.jsp");
                        break;

                    case "teacher":
                        session.setAttribute("teacherId", Integer.parseInt(userId));
                        response.sendRedirect("teacher_dashboard");
                        break;

                    case "student":
                        // For student, loginId is roll_no, userId is user_id
                        session.setAttribute("user_id", Integer.parseInt(userId));
                        session.setAttribute("rollNo", loginId);

                        boolean hasElective = StudentElectiveDAO.hasSelectedElective(loginId);
                        if (hasElective) {
                        	System.out.println("Redirecting to student dashboard servlet");
                        	response.sendRedirect(request.getContextPath() + "/student_dashboard");
                        } else {
                        	System.out.println("Redirecting to elective");
                        	response.sendRedirect(request.getContextPath() + "/elective_selection");
                        }
                        break;

                    default:
                        response.sendRedirect("login.jsp?error=Invalid+role");
                }
            } else {
                response.sendRedirect("login.jsp?error=Invalid+credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Server+error");
        }
    }
}
