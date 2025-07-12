<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Student Registration - Attendance Management System</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>

<body>

    <jsp:include page="header.jsp" />

    <!-- Gradient background only here -->
    <div class="content-background">
        <div class="form-container">
            <h2>Student Registration</h2>

            <c:if test="${param.error != null}">
                <div class="error-msg">${param.error}</div>
            </c:if>
            <c:if test="${param.success != null}">
                <div class="success-msg">${param.success}</div>
            </c:if>

            <form action="RegisterStudentServlet" method="post" onsubmit="return validateForm()">
                <div class="form-group">
                    <label for="full_name">Full Name:</label>
                    <input type="text" name="full_name" id="full_name" required placeholder="Enter your full name">
                </div>

                <div class="form-group">
                    <label for="roll_no">Roll Number:</label>
                    <input type="text" name="roll_no" id="roll_no" required value="RMC24MC0">
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" name="password" id="password" required placeholder="Create a password">
                </div>

                <div class="form-group">
                    <label for="confirm_password">Confirm Password:</label>
                    <input type="password" name="confirm_password" id="confirm_password" required placeholder="Confirm your password">
                </div>

                <div class="form-group">
                    <button type="submit" class="btn">Register</button>
                </div>
            </form>

            <div class="login-link">
                <p>Already have an account? <a href="login.jsp">Login here</a></p>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />

    <script>
        function validateForm() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirm_password").value;
            if (password !== confirmPassword) {
                alert("Passwords do not match.");
                return false;
            }
            return true;
        }
    </script>

</body>

</html>
