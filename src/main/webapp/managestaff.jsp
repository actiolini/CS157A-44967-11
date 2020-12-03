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
    request.setAttribute("isProvider", "hidden");
    request.setAttribute("isAdmin", "hidden");
    if(session.getAttribute("staffId") != null && (session.getAttribute("role").equals("admin") || session.getAttribute("role").equals("manager")) && session.getAttribute("currentSession").equals(Passwords.applySHA256(session.getId() + request.getRemoteAddr()))){
        request.setAttribute("isProvider", "");
        if(session.getAttribute("role").equals("admin")){
            request.setAttribute("isAdmin", "");
        }
    }else{
        response.sendRedirect("home.jsp");
    }
%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Buddy | Create Faculty Account</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <style>
        .inputbox {
            width: 100%;
        }

        .errormessage {
            color: red;
        }

        .submitLink {
            background-color: transparent;
            border: none;
            color: #007bff;
            cursor: pointer;
        }

        .submitLink:hover {
            color: #0056b3;
        }

        .submitLink:focus {
            outline: none;
        }
    </style>
</head>

<body style="height: 100%; display: flex; flex-direction: column;"
    onload="refillSignUp('${signupUserName}', '${signupEmail}')">
    <div style="flex: 1 0 auto;">
        <nav id="movieBuddyNavBar" class="navbar navbar-expand-lg navbar-light bg-light">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
                aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand" href="./home.jsp">Movie Buddy</a>
            <div class="collapse navbar-collapse" id="navbarToggler">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li ${isProvider} class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                            Manage
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" href="./managetheatre.jsp">Theatre</a>
                            <a class="dropdown-item" href="./managemovie.jsp">Movie</a>
                            <a class="dropdown-item" href="./manageschedule.jsp">Schedule</a>
                            <a class="dropdown-item" href="./managestaff.jsp">Staff</a>
                        </div>
                    </li>
                </ul>
                <form action="" method="POST">
                    <input class="submitLink" type="submit" value="${userName}">
                </form>
                <form action="./SignOut" method="POST">
                    <input class="submitLink" type="submit" value="Sign Out">
                </form>
            </div>
        </nav>

        <div class="container">
            <a href="./staffsignup.jsp">Create Faculty Account</a>
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