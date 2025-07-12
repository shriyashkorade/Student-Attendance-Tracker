<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Teacher Dashboard</title>
    <link rel="stylesheet" href="teacher_dashboard.css">
</head>
<body>

<div class="container">
    <h1>Teacher Dashboard</h1>
	<c:if test="${not empty sessionScope.successMessage}">
    <div class="success-message">${sessionScope.successMessage}</div>
    <c:remove var="successMessage" scope="session" />
	</c:if>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <c:if test="${not empty teacher}">
        <section class="teacher-info">
            <h2>${teacher.teacherName}</h2>
            <p><strong>Email:</strong> ${teacher.teacherEmail}</p>
        </section>
    </c:if>

    <c:if test="${not empty subject}">
        <section class="subject-info">
            <h3>Assigned Subject</h3>
            <p><strong>Subject Name:</strong> ${subject.subjectName}</p>
            <p><strong>Type:</strong> ${subject.subjectType}</p>
             <!-- Button for attendance summary -->
        <form action="teacherAttendanceSummary" method="get" style="margin-top: 15px;">
            <button type="submit">
                View Attendance Summary
            </button>
        </form>
        </section>
    </c:if>

    <c:if test="${not empty students}">
        <section class="students-list">
            <h3>Student List</h3>
            <table>
                <thead>
                    <tr>
                        <th>Roll No</th>
                        <th>Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="student" items="${students}">
                        <tr>
                            <td>${student.rollNo}</td>
                            <td>${student.fullName}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>

        <section class="attendance-form">
            <h3>Mark Attendance</h3>
            <form action="mark_attendance" method="post">
                <label for="attendanceDate">Date:</label>
                <input type="date" id="attendanceDate" name="attendanceDate" required>
                <br><br>
				<label for="lectureNo">Lecture Number:</label>
    			<input type="number" id="lectureNo" name="lectureNo" min="1" value="1" required>
				<br><br>
                <table>
                    <thead>
                        <tr>
                            <th>Roll No</th>
                            <th>Name</th>
                            <th>Present</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td>${student.rollNo}</td>
                                <td>${student.fullName}</td>
                                <td><input type="checkbox" name="presentStudentIds" value="${student.userId}"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br>
                <input type="submit" value="Submit Attendance" class="btn-submit">
            </form>
        </section>
    </c:if>

    <c:if test="${empty students}">
        <p>No students found for the assigned subject.</p>
    </c:if>
</div>

</body>
</html>
