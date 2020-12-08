<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="moviebuddy.util.Passwords" %>
<jsp:include page="/TicketPriceGet" />
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

    request.setAttribute("ticketPriceStartTimeUpload", session.getAttribute("ticketPriceStartTimeUpload"));
    request.setAttribute("ticketPricePriceUpload", session.getAttribute("ticketPricePriceUpload"));
    request.setAttribute("errorMessage", session.getAttribute("errorMessage"));
    session.removeAttribute("ticketPriceStartTimeUpload");
    session.removeAttribute("ticketPricePriceUpload");
    session.removeAttribute("errorMessage");
%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/style.css">
    <title>Movie Buddy | Ticket Price</title>
</head>

<body style="height: 100%; display: flex; flex-direction: column;">
    <div style="flex: 1 0 auto;">
        <!-- Navigation bar -->
        <jsp:include page="/navbar.jsp" />

        <!-- Page Content -->
        <div class="container">
            <h3>Theatre: ${ticketPriceTheatreName}</h3>
            <hr>
            <a class="inputAsLink" href="./managetheatre.jsp">&#8249;
                <span>Back</span>
            </a>
            <h1 class="display-3 text-center">Ticket Price</h1>
            <hr>
            <p class="text-center errormessage" id="errorMessage">${errorMessage}</p>
            <div class="row">
                <div class="col"></div>
                <div class="col-5">
                    <table>
                        <tr>
                            <th>Start time</th>
                            <th>Price</th>
                            <th></th>
                        </tr>
                        <tr>
                            <td>
                                <input form="addTicketPriceForm" style="width: 80px;" name="startTime" type="time"
                                    value="${ticketPriceStartTimeUpload}" />
                            </td>
                            <td>
                                <input form="addTicketPriceForm" style="width: 80px;" name="price" type="number" min="0"
                                    step="0.01" ; placeholder="11.50" value="${ticketPricePriceUpload}" />
                            </td>
                            <td>
                                <form id="addTicketPriceForm" action="TicketPriceAdd" method="POST" class="button"
                                    onsubmit="return validateTicketPriceForm(this)">
                                    <input type="hidden" name="theatreId" value="${ticketPriceTheatreId}" />
                                    <input type="submit" class="btn btn-outline-info" value="Add" />
                                </form>
                            </td>
                        </tr>
                        <c:forEach items="${ticketPriceList}" var="ticketPrice">
                            <tr>
                                <td>${ticketPrice.getStartTime()}</td>
                                <td>$${ticketPrice.displayPrice()}</td>
                                <td>
                                    <form action="TicketPriceDelete" method="POST" class="button">
                                        <input type="hidden" name="theatreId" value="${ticketPriceTheatreId}" />
                                        <input type="hidden" name="startTime" value="${ticketPrice.getStartTime()}" />
                                        <input type="submit" class="btn btn-outline-info" value="Delete" />
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="col"></div>
            </div>
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