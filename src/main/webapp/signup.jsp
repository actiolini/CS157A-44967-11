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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/style.css">
    <title>Movie Buddy | Sign Up</title>
</head>

<body style="height: 100%; display: flex; flex-direction: column;">
    <div style="flex: 1 0 auto;">
        <!-- Navigation bar -->
        <jsp:include page="/navbar.jsp" />

        <!-- Page Content -->
        <div class="container">
            <h1 class="display-3 text-center">Sign Up</h1>
            <hr>
            <div class="row">
                <div class="col"></div>
                <div class="col-6">
                    <form id="signUpForm" action="SignUp" method="POST" onsubmit="return validate(this)">
                        <div class="form-group">
                            <label>Name</label><br>
                            <input class="inputbox" type="text" name="userName" placeholder="Enter your name"
                                onkeyup="checkName(this, 'userNameError')" value="${userName}">
                            <br>
                            <span id="userNameError" class="errormessage">${userNameError}</span>
                        </div>
                        <div class="form-group">
                            <label>Email</label><br>
                            <input class="inputbox" type="text" name="email" placeholder="Enter email"
                                onkeyup="checkEmail(this, 'emailError')" value="${email}">
                            <br>
                            <span id="emailError" class="errormessage">${emailError}</span>
                        </div>
                        <div class="form-group">
                            <label>Password</label><br>
                            <input class="inputbox" type="password" name="password" placeholder="Enter password"
                                onkeyup="checkPassword(this, 'passwordError')">
                            <br>
                            <span id="passwordError" class="errormessage">${passwordError}</span>
                        </div>
                        <div class="form-group">
                            <label>Confirm Password</label><br>
                            <input class="inputbox" type="password" name="rePassword" placeholder="Re-enter password"
                                onkeyup="checkRePassword('signUpForm', 'rePasswordError')">
                            <br>
                            <span id="rePasswordError" class="errormessage">${rePasswordError}</span>
                        </div>
                        <div class="text-center">
                            <input type="submit" class="btn btn-primary" value="Sign Up">
                        </div>
                    </form>
                    <a href="./signin.jsp">Already have an account? Sign in here</a>
                </div>
                <div class="col"></div>
            </div>
        </div>
    </div>
    <div style="flex-shrink: 0;">
        <hr>
        <p class="text-center">CS157A-Section01-Team11&copy;2020</p>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>

    <script src="./JS/functions.js"></script>
    <script src="./JS/validation.js"></script>
</body>

</html>