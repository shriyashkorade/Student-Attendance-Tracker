<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Select Elective</title>
    <link rel="stylesheet" href="select_elective.css">
</head>
<body>
    <div class="container">
        <h2>Select Your Elective Subject</h2>

        <form action="elective_selection" method="post">
            <div class="electives-list">
                <c:forEach var="subject" items="${electives}">
                    <div class="elective-item">
                        <input type="radio" id="subject${subject.subjectId}" name="electiveSubjectId" value="${subject.subjectId}" required>
                        <label for="subject${subject.subjectId}">${subject.subjectName}</label>
                    </div>
                </c:forEach>
            </div>

            <button type="submit">Save Elective</button>
        </form>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
    </div>
</body>
</html>
