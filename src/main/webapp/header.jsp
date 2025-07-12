<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" type="text/css" href="header-footer.css">


<div class="header">
<p style="color: red;">HEADER LOADED</p>
    <nav>
    <p>Session loggedIn: ${sessionScope.loggedIn}</p>
    
        <ul>
            <!-- Check if user is logged in -->
            <c:if test="${sessionScope.loggedIn == null || sessionScope.loggedIn == false}">
                <li><a href="login.jsp">Login</a></li>
                <li><a href="register.jsp">Register</a></li>
            </c:if>

            <!-- Show other menu items (for logged-in users) -->
            <c:if test="${sessionScope.loggedIn != null && sessionScope.loggedIn == true}">
                <li><a href="about.jsp">About</a></li>
                <li><a href="logout.jsp">Logout</a></li>
            </c:if>
        </ul>
    </nav>
</div>
