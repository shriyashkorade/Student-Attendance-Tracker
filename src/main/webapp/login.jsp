<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Attendance Management System</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>

<body>

    <jsp:include page="header.jsp" />

    <div class="content-background">
        <div class="login-container">
            <h2>Login to Attendance Management System</h2>

            <c:if test="${param.error != null}">
                <div class="error-msg">
                    ${param.error}
                </div>
            </c:if>

            <form action="LoginAMS" method="POST">
                <div class="form-group">
                    <label for="login_id">Login ID (Roll No or Username):</label>
                    <input type="text" id="login_id" name="login_id" required placeholder="Enter your Roll No or Username">
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required placeholder="Enter your Password">
                </div>

                <div class="form-group">
                    <label for="role">Login as:</label>
                    <select name="role" id="role" required>
                        <option value="admin">Admin</option>
                        <option value="teacher">Teacher</option>
                        <option value="student">Student</option>
                    </select>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn">Login</button>
                </div>
            </form>

            <div class="signup-link">
                <p>Don't have an account? <a href="register.jsp">Sign Up</a></p>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />

</body>

</html>
