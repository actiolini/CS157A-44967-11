<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="moviebuddy.util.Passwords" %>
<jsp:include page="/GetMovie" />
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
    <title>Movie Buddy | Manage Movie</title>
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

        .button {
            display: inline-block;
        }
    </style>
</head>

<body style="height: 100%; display: flex; flex-direction: column;">
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
                <form action="SignOut" method="POST">
                    <input class="submitLink" type="submit" value="Sign Out">
                </form>
            </div>
        </nav>

        <div class="container">
            <hr>
            <div class="row">
                <div class="col"></div>
                <div class="col-6 text-center">
                    <a href="./movieupload.jsp">
                        <button type="button" class="btn btn-outline-info">Upload Movie</button>
                    </a>
                </div>
                <div class="col"></div>
            </div>
            <hr>

            <c:forEach items="${movies}" var="movie">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col col-lg-5">
                                <div class="text-center">
                                    <img src=${movie.getPoster()} class="rounded mx-auto w-100" alt="poster">
                                </div>
                            </div>
                            <div class="col">
                                <ul class="list-inline">
                                    <li class="list-inline-item">
                                        <h1>${movie.getTitle()}</h1>
                                    </li>
                                    <li class="list-inline-item">
                                        <p class="">Length: ${movie.getDuration()} minutes</p>
                                    </li>
                                </ul>
                                <hr>
                                <h3>Trailer</h3>
                                <div class="embed-responsive embed-responsive-16by9">
                                    <!-- ${movie.getTrailer()} -->
                                </div>
                                <hr>
                                <h3>Description</h3>
                                <p>${movie.getDescription()}</p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col">
                                <div class="container">
                                    <form action="EditMovie" method="POST" class="button">
                                        <input type="hidden" name="movieId" value=${movie.getId()} />
                                        <input type="submit" class="btn btn-primary" value="Edit" />
                                    </form>
                                    <form action="DeleteMovie" method="POST" class="button">
                                        <input type="hidden" name="movieId" value=${movie.getId()} />
                                        <input type="submit" class="btn btn-primary" value="Delete" />
                                    </form>
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

    <script src="./JS/functions.js"></script>
    <script src="./JS/validation.js"></script>
</body>

</html>