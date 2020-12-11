<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="moviebuddy.util.Passwords" %>
<jsp:include page="/MovieGet" />
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
    
    if(session.getAttribute("email") == null || !session.getAttribute("currentSession").equals(Passwords.applySHA256(session.getId() + request.getRemoteAddr())) || session.getAttribute("staffId") == null || !(session.getAttribute("role").equals("admin") || session.getAttribute("role").equals("manager"))){
        response.sendRedirect("home.jsp");
    }

    request.setAttribute("errorMessage", session.getAttribute("errorMessage"));
    session.removeAttribute("errorMessage");
%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/style.css">
    <title>Movie Buddy | Manage Movie</title>
</head>

<body style="height: 100%; display: flex; flex-direction: column;">
    <div style="flex: 1 0 auto;">
        <!-- Navigation bar -->
        <jsp:include page="/navbar.jsp" />

        <!-- Page Content -->
        <div class="container">
            <h3>Movie</h3>
            <hr>
            <c:if test="${isAdmin}">
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
                <p class="text-center errormessage" id="errorMessage">${errorMessage}</p>
            </c:if>
            <c:forEach items="${movieList}" var="movie">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col">
                                <h1>${movie.getTitle()}</h1>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col col-lg-5">
                                <div class="text-center">
                                    <img src=${movie.getPoster()} class="rounded mx-auto w-100" alt="poster">
                                </div>
                            </div>
                            <div class="col">
                                <ul class="list-inline">
                                    <p><b>Length:</b> ${movie.getDuration()} minutes</p>
                                    <p><b>Release Date:</b> ${movie.displayReleaseDate()}</p>
                                </ul>
                                <hr>
                                <h3>Trailer</h3>
                                <div class="embed-responsive embed-responsive-16by9">
                                    <iframe width="907" height="510" src="${movie.getTrailer()}" frameborder="0"
                                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                        allowfullscreen></iframe>
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
                                    <form action="ScheduleGet" method="POST" class="button">
                                        <input type="hidden" name="movieId" value=${movie.getId()} />
                                        <input type="submit" class="btn btn-outline-info" value="Schedule" />
                                    </form>
                                    <c:if test="${isAdmin}">
                                        <form action="MovieLoadEdit" method="POST" class="button">
                                            <input type="hidden" name="movieId" value=${movie.getId()} />
                                            <input type="submit" class="btn btn-outline-info" value="Edit" />
                                        </form>
                                        <form action="MovieDelete" method="POST" class="button">
                                            <input type="hidden" name="movieId" value=${movie.getId()} />
                                            <input type="submit" class="btn btn-outline-info" value="Delete" />
                                        </form>
                                    </c:if>
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