<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="moviebuddy.util.Passwords" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies

    session = request.getSession();
    if (session.getAttribute("sessionId") == null) {
        session.setAttribute("sessionId", Passwords.applySHA256(session.getId()));
    }
    if (session.getAttribute("count") == null) {
        session.setAttribute("count", 0);
    } else {
        int count = (int) session.getAttribute("count");
        session.setAttribute("count", count + 1);
    }
    if(session.getAttribute("email") != null && session.getAttribute("currentSession").equals(Passwords.applySHA256(session.getId() + request.getRemoteAddr()))){
        response.sendRedirect("home.jsp");
    }
    request.setAttribute("userName", session.getAttribute("signupUserName"));
    request.setAttribute("email", session.getAttribute("signupEmail"));
    request.setAttribute("userNameError", session.getAttribute("userNameError"));
    request.setAttribute("emailError", session.getAttribute("emailError"));
    request.setAttribute("passwordError", session.getAttribute("passwordError"));
    request.setAttribute("rePasswordError", session.getAttribute("rePasswordError"));
    session.removeAttribute("signupUserName");
    session.removeAttribute("signupEmail");
    session.removeAttribute("userNameError");
    session.removeAttribute("emailError");
    session.removeAttribute("passwordError");
    session.removeAttribute("rePasswordError");
%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Buddy | Sign Up</title>
</head>

<body onload="refillSignUp('${userName}', '${email}')">
    <h2>Sign Up</h2>
    <form id="signUpForm" action="SignUp" method="POST" onsubmit="return validate(this)">
        <input id="userName" type="text" name="userName" placeholder="Enter your name"
            onkeyup="checkName(this, 'userNameError')">
        <span id="userNameError">${userNameError}</span><br>

        <input id="email" type="text" name="email" placeholder="Enter email" onkeyup="checkEmail(this, 'emailError')">
        <span id="emailError">${emailError}</span><br>

        <input type="password" name="password" placeholder="Enter password"
            onkeyup="checkPassword(this, 'passwordError')">
        <span id="passwordError">${passwordError}</span><br>

        <input type="password" name="rePassword" placeholder="Re-enter password"
            onkeyup="checkRePassword('signUpForm', 'rePasswordError')">
        <span id="rePasswordError">${rePasswordError}</span><br>

        <input type="submit" value="Sign Up">
    </form>
    <script src="./js/validation.js"></script>
    <script src="./js/functions.js"></script>
</body>

</html>