<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="admin_dashboard.css">
</head>
<body>
	<div class="page-wrapper">
    <%@ include file="header.jsp" %>

    <div class="container">
        <c:choose>
            <c:when test="${sessionScope.role == 'admin'}">
                <h2>Admin Dashboard</h2>
                <div class="menu-grid">
                    <a href="add_teacher.jsp" class="menu-card">
                        <h3>Add Teacher</h3>
                        <p>Create a new teacher account and profile.</p>
                    </a>
                    <a href="assign_subject.jsp" class="menu-card">
                        <h3>Assign Subject</h3>
                        <p>Assign subjects to teachers for the semester.</p>
                    </a>
                    <a href="add_subject.jsp" class="menu-card">
                        <h3>Add Subject</h3>
                        <p>Provide details of a new subject to be taught.</p>
                    </a>
                    <a href="view_attendance.jsp" class="menu-card">
                        <h3>View Attendance</h3>
                        <p>Check attendance records for students and classes.</p>
                    </a>
                    <!-- Add more cards here as needed -->
                </div>
            </c:when>
            <c:otherwise>
                <div class="login-register">
                    <h3>Please log in to access the dashboard</h3>
                    <a href="login.jsp">Login</a> | <a href="register.jsp">Register</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <%@ include file="footer.jsp" %>
    </div>
</body>
</html>
