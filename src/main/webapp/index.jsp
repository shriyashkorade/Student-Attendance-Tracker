<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Attendance Management System</title>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to bottom, #00c6ff, #007bff);
            color: white;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            align-items: center;
            justify-content: center;
        }
        .container {
            background-color: rgba(255, 255, 255, 0.1);
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
            text-align: center;
        }
        h1 {
            margin-bottom: 20px;
            font-size: 2.5em;
        }
        .btn-group a {
            display: inline-block;
            margin: 10px;
            padding: 12px 25px;
            background-color: white;
            color: #007bff;
            border: none;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            transition: all 0.3s ease;
        }
        .btn-group a:hover {
            background-color: #0056b3;
            color: white;
        }
        footer {
            margin-top: 40px;
            font-size: 0.9em;
            opacity: 0.8;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to the Attendance Management System</h1>
        <div class="btn-group">
            <a href="login.jsp">Login</a>
            <a href="register.jsp">Register</a>
        </div>
    </div>
    <footer>&copy; 2025 Attendance System. All rights reserved.</footer>
</body>
</html>
