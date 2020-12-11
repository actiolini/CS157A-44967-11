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
    request.setAttribute("theatreNameUpload", session.getAttribute("theatreNameUpload"));
    request.setAttribute("theatreAddressUpload", session.getAttribute("theatreAddressUpload"));
    request.setAttribute("theatreCityUpload", session.getAttribute("theatreCityUpload"));
    request.setAttribute("theatreStateUpload", session.getAttribute("theatreStateUpload"));
    request.setAttribute("theatreCountryUpload", session.getAttribute("theatreCountryUpload"));
    request.setAttribute("theatreZipUpload", session.getAttribute("theatreZipUpload"));
    session.removeAttribute("errorMessage");
    session.removeAttribute("theatreNameUpload");
    session.removeAttribute("theatreAddressUpload");
    session.removeAttribute("theatreCityUpload");
    session.removeAttribute("theatreStateUpload");
    session.removeAttribute("theatreCountryUpload");
    session.removeAttribute("theatreZipUpload");
%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/style.css">
    <title>Movie Buddy | Manage Theatre</title>
</head>

<body style="height: 100%; display: flex; flex-direction: column;"
    onload="loadSelectedOption('default', 'state', '${theatreStateUpload}')">
    <div style="flex: 1 0 auto;">
        <!-- Navigation bar -->
        <jsp:include page="/navbar.jsp" />

        <!-- Page Content -->
        <div class="container">
            <h3>Theatre</h3>
            <hr>
            <h1 class="display-3 text-center">Upload Theatre Information</h1>
            <hr>
            <a class="inputAsLink" href="./managetheatre.jsp">&#8249;
                <span>Back</span>
            </a>
            <div class="row">
                <div class="col"></div>
                <div class="col-6">
                    <p class="text-center errormessage" id="errorMessage">${errorMessage}</p>
                    <form id="uploadTheatreForm" action="TheatreCreate" method="POST"
                        onsubmit="return validateTheatreForm(this)">
                        <div class="form-group">
                            <label>Theatre Name</label><span class="errormessage">*</span><br>
                            <input class="inputbox" name="theatreName" type="text" placeholder="Buddy###"
                                value="${theatreNameUpload}" />
                        </div>
                        <div class="form-group">
                            <label>Address</label><span class="errormessage">*</span><br>
                            <input class="inputbox" name="address" type="text" placeholder="1234 Main St"
                                value="${theatreAddressUpload}" />
                        </div>
                        <div class="form-group">
                            <label>City</label><span class="errormessage">*</span><br>
                            <input class="inputbox" name="city" type="text" placeholder="San Francisco"
                                value="${theatreCityUpload}" />
                        </div>
                        <div class="form-group">
                            <label>State</label><span class="errormessage">*</span><br>
                            <select class="inputbox" id="state" name="state">
                                <option id="default" hidden selected value="none">Select a State</option>
                                <option value="AL">Alabama</option>
                                <option value="AK">Alaska</option>
                                <option value="AZ">Arizona</option>
                                <option value="AR">Arkansas</option>
                                <option value="CA">California</option>
                                <option value="CO">Colorado</option>
                                <option value="CT">Connecticut</option>
                                <option value="DE">Delaware</option>
                                <option value="DC">District Of Columbia</option>
                                <option value="FL">Florida</option>
                                <option value="GA">Georgia</option>
                                <option value="HI">Hawaii</option>
                                <option value="ID">Idaho</option>
                                <option value="IL">Illinois</option>
                                <option value="IN">Indiana</option>
                                <option value="IA">Iowa</option>
                                <option value="KS">Kansas</option>
                                <option value="KY">Kentucky</option>
                                <option value="LA">Louisiana</option>
                                <option value="ME">Maine</option>
                                <option value="MD">Maryland</option>
                                <option value="MA">Massachusetts</option>
                                <option value="MI">Michigan</option>
                                <option value="MN">Minnesota</option>
                                <option value="MS">Mississippi</option>
                                <option value="MO">Missouri</option>
                                <option value="MT">Montana</option>
                                <option value="NE">Nebraska</option>
                                <option value="NV">Nevada</option>
                                <option value="NH">New Hampshire</option>
                                <option value="NJ">New Jersey</option>
                                <option value="NM">New Mexico</option>
                                <option value="NY">New York</option>
                                <option value="NC">North Carolina</option>
                                <option value="ND">North Dakota</option>
                                <option value="OH">Ohio</option>
                                <option value="OK">Oklahoma</option>
                                <option value="OR">Oregon</option>
                                <option value="PA">Pennsylvania</option>
                                <option value="RI">Rhode Island</option>
                                <option value="SC">South Carolina</option>
                                <option value="SD">South Dakota</option>
                                <option value="TN">Tennessee</option>
                                <option value="TX">Texas</option>
                                <option value="UT">Utah</option>
                                <option value="VT">Vermont</option>
                                <option value="VA">Virginia</option>
                                <option value="WA">Washington</option>
                                <option value="WV">West Virginia</option>
                                <option value="WI">Wisconsin</option>
                                <option value="WY">Wyoming</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Country</label><span class="errormessage">*</span><br>
                            <input class="inputbox" name="country" type="text" placeholder="USA"
                                value="${theatreCountryUpload}" />
                        </div>
                        <div class="form-group">
                            <label>Zip Code</label><span class="errormessage">*</span><br>
                            <input class="inputbox" name="zip" type="text" placeholder="12345"
                                value="${theatreZipUpload}" />
                        </div>
                        <div class="text-center">
                            <input type="submit" class="btn btn-outline-info" value="Upload">
                        </div>
                    </form>
                </div>
                <div class="col"></div>
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