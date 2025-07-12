<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.attendance.model.AssignTeacherBean" %>
<%@ page import="com.attendance.model.TeacherBean" %>
<%@ page import="com.attendance.model.SubjectBean" %>
<%@ page import="com.attendance.DAO.AssignTeacherDAO" %>
<%@ page import="java.util.List" %>

<%
    // Instantiate the DAO and fetch data
    AssignTeacherDAO dao = new AssignTeacherDAO();
    List<TeacherBean> teacherList = dao.getAllTeachers();
    List<SubjectBean> unassignedSubjects = dao.getUnassignedSubjects();

    // Set the lists in the request scope for use in the JSP
    request.setAttribute("teacherList", teacherList);
    request.setAttribute("unassignedSubjects", unassignedSubjects);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Subject</title>
    <link rel="stylesheet" type="text/css" href="admin_dashboard.css">
    <style>
    	.message-success  {
    background-color: #e6ffed;
    border: 1px solid #2ecc71;
    color: #2ecc71;
    padding: 10px;
    margin-bottom: 15px;
    border-radius: 5px;
}


.message-error{
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
    <%@ include file="header.jsp" %>

    <div class="container">
        <a href="admin_dashboard.jsp"><h2>Assign Subject to Teacher</h2> </a>
        <c:if test="${not empty message}">
        <div class="${messageType == 'success' ? 'message-success' : 'message-error'}">
            ${message}
        </div>
    	</c:if>
        
        <form action="assign_subject" method="post">
    <div class="form-field">
        <label for="teacher_id">Select Teacher:</label>
        <select name="teacher_id" id="teacher_id" required>
            <option value="">-- Select Teacher --</option>
            <c:forEach var="teacher" items="${teacherList}">
                <option value="${teacher.teacherId}">${teacher.teacherName}</option>
            </c:forEach>
        </select>
    </div>

    <div class="form-field">
        <label for="subject_id">Select Subject:</label>
        <select name="subject_id" id="subject_id" required>
            <option value="">-- Select Subject --</option>
            <c:forEach var="subject" items="${unassignedSubjects}">
                <option value="${subject.subjectId}">${subject.subjectName}</option>
            </c:forEach>
        </select>
    </div>

    <button type="submit">Assign Subject</button>
</form>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>
