<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="moviebuddy.util.Passwords" %>
<jsp:include page="/Home" />
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
    request.setAttribute("signedOut", "");
    request.setAttribute("signedIn", "hidden");
    if(session.getAttribute("email") != null && session.getAttribute("currentSession").equals(Passwords.applySHA256(session.getId() + request.getRemoteAddr()))){
        request.setAttribute("signedOut", "hidden");
        request.setAttribute("signedIn", "");
        request.setAttribute("userName", session.getAttribute("userName"));
        request.setAttribute("zip", session.getAttribute("zip"));
        if(session.getAttribute("staffId") != null && (session.getAttribute("role").equals("admin") || session.getAttribute("role").equals("manager"))){
            request.setAttribute("isProvider", "");
        }
    }
%>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <title>Movie Buddy | Home</title>
    <style>
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

<body style="height: 100%; display: flex; flex-direction: column;">
    <div style="flex: 1 0 auto;">
        <!-- Navigation bar -->
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
                    <li class="nav-item active">
                        <form class="form-inline my-2 my-lg-0">
                            <label for="theatreName" class="mx-2 navbar-brand">Theatre Name</label>
                            <input class="form-control mr-sm-2" type="search" placeholder="Zipcode">
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Enter</button>
                        </form>
                    </li>
                </ul>
                <a ${signedOut} class="nav-link" href="./signin.jsp">Sign In</a>
                <a ${signedOut} class="nav-link" href="./signup.jsp">Sign Up</a>
                <form action="" method="POST">
                    <input class="submitLink" ${signedIn} type="submit" value="${userName}">
                </form>
                <form action="SignOut" method="POST">
                    <input class="submitLink" ${signedIn} type="submit" value="Sign Out">
                </form>
            </div>
        </nav>

        <!-- Movie Schedules -->
        <div class="container">
            <h1 class="display-4">Showtimes</h1>
            <c:forEach items="${movies}" var="movie">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col">
                                <div class="text-center">
                                    <img src="images/movie1.jpg" class="rounded mx-auto w-75" alt="...">
                                </div>
                            </div>
                            <div class="col">
                                <h1>${movie.getTitle()}</h1>
                                <hr>
                                <br>
                                <p>Length: ${movie.getDuration()} minutes</p>
                                <a href="#" class="card-link">Details</a>
                                <a href="#" class="card-link">Trailer</a>
                                <hr>
                                <h3>Description</h3>
                                <p>${movie.getDescription()}</p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col">
                                <nav>
                                    <div class="nav nav-tabs" id="nav-tab${movie.getId()}" role="tablist">
                                        <!-- Load Date -->
                                        <c:set var="count" value="${0}" />
                                        <c:forEach items="${movie.getSchedule()}" var="schedule">
                                            <c:set var="dateId" value="${movie.getId()}-${schedule.getShowDate()}" />
                                            <c:if test="${count < 1}">
                                                <a class="nav-link active" id="${dateId}-tab" data-toggle="tab"
                                                    href="#${dateId}" role="tab" aria-controls="${dateId}"
                                                    aria-selected="true">${schedule.getFormattedDate()}</a>
                                            </c:if>
                                            <c:if test="${count >= 1}">
                                                <a class="nav-link" id="${dateId}-tab" data-toggle="tab"
                                                    href="#${dateId}" role="tab" aria-controls="${dateId}"
                                                    aria-selected="false">${schedule.getFormattedDate()}</a>
                                            </c:if>
                                            <c:set var="count" value="${count+1}" />
                                        </c:forEach>
                                    </div>
                                </nav>
                                <div class="tab-content" id="nav-tabContent${movie.getId()}">
                                    <!-- Load Time -->
                                    <c:set var="count" value="${0}" />
                                    <c:forEach items="${movie.getSchedule()}" var="schedule">
                                        <c:set var="dateId" value="${movie.getId()}-${schedule.getShowDate()}" />
                                        <c:if test="${count < 1}">
                                            <div class="tab-pane fade show active" id="${dateId}" role="tabpanel"
                                                aria-labelledby="${dateId}-tab">
                                                <br>
                                                <div class="container">
                                                    <c:forEach items="${schedule.getShowTimes()}" var="time">
                                                        <a href="#" class="card-link">${time}</a>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${count >= 1}">
                                            <div class="tab-pane fade" id="${dateId}" role="tabpanel"
                                                aria-labelledby="${dateId}-tab">
                                                <br>
                                                <div class="container">
                                                    <c:forEach items="${schedule.getShowTimes()}" var="time">
                                                        <a href="#" class="card-link">${time}</a>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:set var="count" value="${count+1}" />
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
            </c:forEach>
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
</body>

</html>