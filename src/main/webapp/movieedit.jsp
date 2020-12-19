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
    
    if(session.getAttribute("email") == null || !session.getAttribute("currentSession").equals(Passwords.applySHA256(session.getId() + request.getRemoteAddr())) || session.getAttribute("staffId") == null || !(session.getAttribute("role").equals("admin"))){
        response.sendRedirect("home.jsp");
    }

    request.setAttribute("errorMessage", session.getAttribute("errorMessage"));
    session.removeAttribute("errorMessage");
%>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/style.css">
    <title>Movie Buddy | Manage Movie</title>
</head>

<body>
    <!-- Navigation bar -->
    <jsp:include page="/navbar.jsp" />
    <div style="min-height: 60px;"></div>
    <div id="custom-scroll">
        <div class="main">
            <!-- Page Content -->
            <div class="container">
                <h3>Movie</h3>
                <hr>
                <h1 class="display-3 text-center">Update Movie Information</h1>
                <hr>
                <a class="inputAsLink" href="./managemovie.jsp">&#8249;
                    <span>Back</span>
                </a>
                <div class="row">
                    <div class="col"></div>
                    <div class="col-6">
                        <p class="text-center errormessage" id="errorMessage">${errorMessage}</p>
                        <form id="editMovieForm" action="MovieEdit" method="POST" enctype="multipart/form-data"
                            onsubmit="return validateMovieForm(this)">
                            <div class="form-group">
                                <input type="hidden" name="action" value="save" />
                            </div>
                            <div class="form-group">
                                <input type="hidden" name="movieId" value="${movieIdEdit}" />
                            </div>
                            <div class="form-group">
                                <label>Title</label><br>
                                <input class="inputbox" name="title" type="text" placeholder="Enter title"
                                    value="${movieTitleEdit}" />
                            </div>
                            <div class="form-group">
                                <label>Release Date</label><br>
                                <input class="inputbox" name="releaseDate" type="date" value="${movieReleaseDateEdit}" />
                            </div>
                            <div class="form-group">
                                <label>Duration</label><br>
                                <input class="inputbox" name="duration" type="text" placeholder="Enter duration in minutes"
                                    value="${movieDurationEdit}" />
                            </div>
                            <div class="form-group">
                                <label>Trailer Source</label><br>
                                <input class="inputbox" name="trailer" type="text" placeholder="Enter trailer source..."
                                    value="${movieTrailerEdit}" />
                            </div>
                            <div class="form-group">
                                <label>Poster</label><br>
                                <input class="inputbox" name="poster" type="file" />
                            </div>
                            <div class="form-group">
                                <label>Description</label><br>
                                <textarea class="inputbox" name="description" cols="60" rows="5" maxlength="1000"
                                    placeholder="Enter movie description...">${movieDescriptionEdit}</textarea>
                            </div>
                        </form>
                        <form id="cancelMovieForm" action="MovieEdit" method="POST">
                            <div class="form-group">
                                <input type="hidden" name="action" value="cancel" />
                            </div>
                        </form>
                        <div class="text-center">
                            <div class="button">
                                <input form="editMovieForm" type="submit" class="btn btn-outline-info" value="Save">
                            </div>
                            <div class="button">
                                <input form="cancelMovieForm" type="submit" class="btn btn-outline-info" value="Cancel" />
                            </div>
                        </div>
                    </div>
                    <div class="col"></div>
                </div>
            </div>
        </div>
        <div class="footer">
            <hr>
            <p class="text-center">CS157A-Section01-Team11&copy;2020</p>
        </div>
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