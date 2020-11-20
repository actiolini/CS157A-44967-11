<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Movie Buddy | Home</title>
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
            aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand" href="./home">Movie Buddy</a>
        <div class="collapse navbar-collapse" id="navbarToggler">
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                        Menu
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="#">Releasing movies</a>
                        <a class="dropdown-item" href="#">Future Release</a>
                        <a class="dropdown-item" href="#">Top rated movies</a>
                        <a class="dropdown-item" href="#">Most popular movies</a>
                    </div>
                </li>
                <li class="nav-item">
                    <form class="form-inline my-2 my-lg-0">
                        <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                    </form>
                </li>
            </ul>
            <a class="nav-link " href="#">Sign In / Register</a>
        </div>
    </nav>
    <div class="container">
        <!-- <h1 class="display-3">Releasing now</h1>
        <div class="">
            <div id="carouselIndicators" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselIndicators" data-slide-to="1"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="images/movie1.jpg" class="d-block w-100" alt="movie1">
                        <div class="carousel-caption d-none d-md-block">
                            <h5>Movie 1</h5>
                            <p>Movie 1 Description</p>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="images/movie2.jpg" class="d-block w-100" alt="movie2">
                        <div class="carousel-caption d-none d-md-block">
                            <h5>Movie 2</h5>
                            <p>Movie 2 Description</p>
                        </div>
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
        <hr> -->
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
                                    <c:set var="count" value="${0}"/>
                                    <c:forEach items="${movie.getSchedule()}" var="schedule">
                                        <c:set var="dateId" value="${movie.getId()}-${schedule.getShowDate()}" />
                                        <c:if test="${count < 1}">
                                            <a class="nav-link active" id="${dateId}-tab" data-toggle="tab" href="#${dateId}" role="tab" aria-controls="${dateId}"
                                                aria-selected="true">${schedule.getFormattedDate()}</a>
                                        </c:if>
                                        <c:if test="${count >= 1}">
                                            <a class="nav-link" id="${dateId}-tab" data-toggle="tab" href="#${dateId}" role="tab" aria-controls="${dateId}"
                                                aria-selected="false">${schedule.getFormattedDate()}</a>
                                        </c:if>
                                        <c:set var="count" value ="${count+1}"/>
                                    </c:forEach>
                                </div>
                            </nav>
                            <div class="tab-content" id="nav-tabContent${movie.getId()}">
                                <!-- Load Time -->
                                <c:set var="count" value="${0}"/>
                                <c:forEach items="${movie.getSchedule()}" var="schedule">
                                    <c:set var="dateId" value="${movie.getId()}-${schedule.getShowDate()}"/>
                                    <c:if test="${count < 1}">
                                        <div class="tab-pane fade show active" id="${dateId}" role="tabpanel" aria-labelledby="${dateId}-tab">
                                            <br>
                                            <div class="container">
                                                <c:forEach items="${schedule.getShowTimes()}" var="time">
                                                    <a href="#" class="card-link">${time}</a>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        
                                    </c:if>
                                    <c:if test="${count >= 1}">
                                        <div class="tab-pane fade" id="${dateId}" role="tabpanel" aria-labelledby="${dateId}-tab">
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
    <br>
    <div class="card">
        <div class="card-body">
            Some Footer
        </div>
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>

</html>