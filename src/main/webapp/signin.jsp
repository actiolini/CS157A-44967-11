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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>

<body style="height: 100%; display: flex; flex-direction: column;" onload="refillSignIn('${email}')">
    <div style="flex: 1 0 auto;">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
                aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand" href="#">Movie Buddy</a>

            <div class="collapse navbar-collapse" id="navbarToggler">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item active">
                        <form class="form-inline my-2 my-lg-0">
                            <label for="theatreName" class="mx-2 navbar-brand">Theatre Name</label>
                            <input class="form-control mr-sm-2" type="search" placeholder="Zipcode">
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Enter</button>
                        </form>
                    </li>
                </ul>
                <a class="nav-link" href="#">Sign In / Register</a>
            </div>
        </nav>

        <div class="container">
            <h1 class="display-3 text-center">Sign In</h1>
            <hr>
            <br>
            <div class="row">
                <div class="col-sm"></div>
                <div class="col-sm">
                    <div class="card">
                        <div class="card-body ">
                            <form action="SignIn" method="POST">
                                <div class="form-group ">
                                    <label for="inputEmail">Email address</label>
                                    <input id="email" type="text" name="email" placeholder="Enter email"><br>
                                </div>
                                <div class="form-group">
                                    <label for="inputPassword">Password</label>
                                    <input type="password" name="password" placeholder="Enter password"><br>
                                </div>
                                <div class="text-center">
                                    <input type="submit" class="btn btn-primary" value="Sign In">
                                </div>
                            </form>
                            <p id="signInError">${message}</p>
                        </div>
                    </div>
                </div>
                <div class="col-sm"></div>
            </div>
        </div>
    </div>
    <div style="flex-shrink: 0;">
        <br>
        <p class="text-center">CS157A-Section01-Team11©2020</p>
    </div>
    <script src="./js/functions.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>
</body>

</html>