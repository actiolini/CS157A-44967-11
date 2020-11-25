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
    request.setAttribute("email", session.getAttribute("signinEmail"));
    request.setAttribute("message", session.getAttribute("signinMessage"));
    session.removeAttribute("signinEmail");
    session.removeAttribute("signinMessage");
%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Buddy | Sign In</title>
</head>

<body onload="refillSignIn('${email}')">
    <h2>Sign In</h2>
    <form action="SignIn" method="POST">
        <input id="email" type="text" name="email" placeholder="Enter email"><br>
        <input type="password" name="password" placeholder="Enter password"><br>
        <input type="submit" value="Sign In">
    </form>
    <p id="signInError">${message}</p>
    <script src="./js/functions.js"></script>
</body>

</html>