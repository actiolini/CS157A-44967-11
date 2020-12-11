<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="moviebuddy.util.Passwords" %>
<jsp:include page="" />
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

<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

    <title>Movie Buddy - Profile</title>
</head>

<body style="height: 100%; display: flex; flex-direction: column;">
    <div style="flex: 1 0 auto;">
        <!-- Navigation bar -->
        <jsp:include page="/navbar.jsp" />

        <!-- Page Content -->
        <div class="container">
            <h1 class="display-3 text-center">Customer Portal</h1>
            <hr>
            <br>
            <div class="row">
                <div class="col"></div>
                <div class="col-8">
                    <div class="alert alert-success" role="alert">
                        Welcome <strong>username</strong>,
                    </div>
                    <p><strong>Email: </strong>xxx@xxx.com</p>
                    <form class="form-inline">
                        <label for="inputPassword"><strong>Password: </strong></label>
                        <input type="password" class="form-control mx-2" id="inputPassword" placeholder="Password">
                        <button type="submit" class="btn btn-warning">Change Password</button>
                    </form>
                    <br>
                    <hr>
                    <p><strong>Membership Status: </strong>No</p>
                    <form class="form-inline">
                        <button type="submit" class="btn btn-success">Join the Club</button>
                    </form>
                    <br>
                    <p><strong>Membership Status: </strong>Yes</p>
                    <p><strong>Buddy Points: </strong>50</p>
                    <p><strong>Expire Date: </strong>12/01/2020</p>
                </div>
                <div class="col"></div>
            </div>
            <hr>
            <div>
                <h1 class="display-4 text-center">Purchase History</h1>
                <c:forEach items="${receiptList}" var="receipt">
                    <p><strong>Order# ${receipt.getReceiptID()}</strong></p>
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
                            <c:forEach items="${receipt.getTicket()}" var="ticket">
                                <tr>
                                    <td>${receipt.getMovieName()}</td>
                                    <td>${receipt.getShowTime()}, ${receipt.getShowDate()}</td>
                                    <td>${ticket.getSeatNumber()}</td>
                                    <td>${receipt.getTotalPrice()/receipt.getQuantity()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <hr>
                    <p class="text-right"><strong>Total: ${receipt.getTotalPrice()}</strong></p>
                    <hr>
                </c:forEach>
            </div>
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