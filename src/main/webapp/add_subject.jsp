<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Subject</title>
    <link rel="stylesheet" href="admin_dashboard.css">
    <style>
    	.message-error {
    background-color: #e6ffed;
    border: 1px solid #2ecc71;
    color: #2ecc71;
    padding: 10px;
    margin-bottom: 15px;
    border-radius: 5px;
}

.message-success {
    background-color: #ffe6e6;
    border: 1px solid #e74c3c;
    color: #e74c3c;
    padding: 10px;
    margin-bottom: 15px;
    border-radius: 5px;
}
    	
    </style>
</head>
<body>
    <div class="page-wrapper">
        <%@ include file="header.jsp" %>

        <div class="container">
           <a href="admin_dashboard.jsp"> <h2>Add Subject</h2> </a>
            <c:if test="${not empty message}">
        <div class="${messageType == 'success' ? 'message-success' : 'message-error'}">
            ${message}
        </div>
    </c:if>
            
            <form action="AddSubjectServlet" method="post">
                <div class="form-field">
                    <label for="subject_name">Subject Name:</label>
                    <input type="text" name="subject_name" id="subject_name" required>
                </div>

                <div class="form-field">
                    <label for="subject_type">Subject Type:</label>
                    <select name="subject_type" id="subject_type" required>
                        <option value="core">Core</option>
                        <option value="elective">Elective</option>
                    </select>
                </div>

                <button type="submit">Add Subject</button>
            </form>
        </div>

        <%@ include file="footer.jsp" %>
    </div>
</body>
</html>
