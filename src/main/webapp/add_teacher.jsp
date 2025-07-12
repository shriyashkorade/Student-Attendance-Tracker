<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Teacher</title>
    <link rel="stylesheet" type="text/css" href="admin_dashboard.css">
    <style>
    .alert-success {
        color: green;
        background-color: #ddffdd;
        padding: 10px;
        border: 1px solid green;
        margin-bottom: 15px;
        border-radius: 4px;
        font-weight: bold;
    }

    .alert-error {
        color: red;
        background-color: #ffe0e0;
        padding: 10px;
        border: 1px solid red;
        margin-bottom: 15px;
        border-radius: 4px;
        font-weight: bold;
    }
</style>
    
</head>
<body>
	<%@ include file="header.jsp" %>
	
    <div class="container">
	<a href="admin_dashboard.jsp">
    <h2>Add Teacher</h2>
	</a>        
        <div class="dashboard-item">
        <c:if test="${not empty message}">
    <div class="${status == 'success' ? 'alert-success' : 'alert-error'}">
        ${message}
    </div>
</c:if>
            <form action="add_teacher" method="post">
                <div class="form-field">
                    <label for="teacher_name">Teacher Name:</label>
                    <input type="text" name="teacher_name" id="teacher_name" required>
                </div>

                <div class="form-field">
                    <label for="teacher_email">Email:</label>
                    <input type="email" name="teacher_email" id="teacher_email" required>
                </div>

                <div class="form-field">
                    <label for="teacher_username">Username:</label>
                    <input type="text" name="teacher_username" id="teacher_username" required>
                </div>

                <div class="form-field">
                    <label for="teacher_password">Password:</label>
                    <input type="password" name="teacher_password" id="teacher_password" required>
                </div>

                <button type="submit">Add Teacher</button>
            </form>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
</body>
</html>
