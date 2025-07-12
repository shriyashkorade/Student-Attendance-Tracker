<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<html>
<head>
    <title>Attendance Summary - ${subjectName}</title>
    <style>
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
        }
        th, td {
            border: 1px solid #333;
            padding: 8px 12px;
            text-align: center;
        }
        th {
            background-color: #444;
            color: white;
        }
        .percentage-low {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Attendance Summary for Subject: <strong>${subjectName}</strong></h2>

    <c:if test="${not empty attendanceSummary}">
        <table>
            <thead>
                <tr>
                    <th>Roll No</th>
                    <th>Student Name</th>
                    <th>Total Lectures</th>
                    <th>Lectures Attended</th>
                    <th>Attendance %</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="summary" items="${attendanceSummary}">
                    <tr>
                        <td>${summary.rollNo}</td>
                        <td>${summary.fullName}</td>
                        <td>${summary.totalLectures}</td>
                        <td>${summary.lecturesAttended}</td>
                        <td>
    <c:choose>
        <c:when test="${summary.percentage lt 75}">
            <span class="percentage-low">
                <fmt:formatNumber value="${summary.percentage}" type="number" minFractionDigits="2" maxFractionDigits="2" />%
            </span>
        </c:when>
        <c:otherwise>
            <fmt:formatNumber value="${summary.percentage}" type="number" minFractionDigits="2" maxFractionDigits="2" />%
        </c:otherwise>
    </c:choose>
</td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty attendanceSummary}">
        <p style="text-align:center;">No attendance records found.</p>
    </c:if>

    <div style="text-align:center;">
<a href="${pageContext.request.contextPath}/teacher_dashboard">Back to Dashboard</a>

    </div>
</body>
</html>
