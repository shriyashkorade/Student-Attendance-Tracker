<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="student_dashboard.css">
</head>
<body>
    <div class="container">
        <h1>${student.fullName}</h1>

        <section class="subjects-section">
            <h2>Your Subjects</h2>
            <ul>
                <c:forEach var="subject" items="${subjects}">
                    <li>${subject.subjectName}</li>
                </c:forEach>
            </ul>
        </section>

        <section class="attendance-summary-section">
            <h2>Attendance Summary</h2>
            <table>
                <thead>
                    <tr>
                        <th>Subject</th>
                        <th>Total Lectures</th>
                        <th>Lectures Attended</th>
                        <th>Attendance %</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="summary" items="${attendanceSummary}">
                        <tr>
                            <td>${summary.subjectName}</td>
                            <td>${summary.totalLectures}</td>
                            <td>${summary.lecturesAttended}</td>
                            <td>${summary.percentage}%</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
    </div>
</body>
</html>
