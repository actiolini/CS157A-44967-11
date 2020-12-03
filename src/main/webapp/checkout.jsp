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
    request.setAttribute("signedOut", "");
    request.setAttribute("signedIn", "hidden");
    if(session.getAttribute("email") != null && session.getAttribute("currentSession").equals(Passwords.applySHA256(session.getId() + request.getRemoteAddr()))){
        request.setAttribute("signedOut", "hidden");
        request.setAttribute("signedIn", "");
        request.setAttribute("userName", session.getAttribute("userName"));
        request.setAttribute("zip", session.getAttribute("zip"));
        request.setAttribute("guestemail", "hidden");
        if(session.getAttribute("staffId") != null && (session.getAttribute("role").equals("admin") || session.getAttribute("role").equals("manager"))){
            request.setAttribute("isProvider", "");
        }
    }
%>

<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

    <title>Movie Buddy - Check Out</title>
</head>

<body style="height: 100%; display: flex; flex-direction: column;">
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
            <h1 class="display-3 text-center">Check Out</h1>
            <hr>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Movie</th>
                        <th scope="col">Time</th>
                        <th scope="col">Seat</th>
                        <th scope="col">Price</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>movie name</td>
                        <td>11:00pm, 12/01/2020</td>
                        <td>A1,B1</td>
                        <td>$10</td>
                    </tr>
                </tbody>
            </table>
            <hr>
            <p>Use buddy points?</p>
            <form class="form-inline">
                <label for="inputPassword">Current buddy points: <strong>50</strong></label>
                <button type="submit" class="btn btn-warning mx-2">Use buddy points</button>
            </form>
            <hr>
            <p class="text-right"><strong>Total: $20.00</strong></p>
            <form>
                <div id="paymentInfo">
                    <h1 class="display-4">Payment information</h1>
                    <div ${guestemail} class="form-group">
                        <label for="inputCardHolderName">Email</label>
                        <input type="text" class="form-control" id="inputEmail" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="inputCardHolderName">Card Holder Name</label>
                        <input type="text" class="form-control" id="inputCardHolderName" placeholder="">
                    </div>
                    <div class="form-group">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputCardNumber">Card number</label>
                            <input type="text" class="form-control" id="inputCardNumber" placeholder="">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputCVS">CVS</label>
                            <input type="password" class="form-control" id="inputCVS">
                        </div>
                    </div>
                </div>
                <br>
                <div id="billingAddr">
                    <h1 class="display-4">Billing Address</h1>
                    <div class="form-group">
                        <label for="inputShippingAddress">Address</label>
                        <input type="text" class="form-control" id="inputShippingAddress" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="inputShippingAddress2">Address 2</label>
                        <input type="text" class="form-control" id="inputShippingAddress2"
                            placeholder="Apartment, studio, or floor">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputShippingCity">City</label>
                            <input type="text" class="form-control" id="inputShippingCity">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="inputShippingState">State</label>
                            <select id="inputShippingState" class="form-control">
                                <option>Choose...</option>
                                <option>CA</option>
                            </select>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="inputShippingZip">Zip</label>
                            <input type="text" class="form-control" id="inputShippingZip">
                        </div>
                    </div>
                </div>
                <br>
                <div class="text-center"><button type="submit" class="btn btn-primary">submit payment</button></div>
            </form>
        </div>
    </div>

    <div style="flex-shrink: 0;">
        <br>
        <p class="text-center">CS157A-Section01-Team11©2020</p>
    </div>

    <!-- Optional JavaScript -->
    <!-- Option 1: jQuery and Bootstrap Bundle (includes Popper) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>
</body>

</html>