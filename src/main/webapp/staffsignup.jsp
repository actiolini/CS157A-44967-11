<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="moviebuddy.util.Passwords" %>
<jsp:include page="/TheatreGet" />
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

    request.setAttribute("signupStaffRole", session.getAttribute("signupStaffRole"));
    request.setAttribute("signupStaffTheatreLocation", session.getAttribute("signupStaffTheatreLocation"));
    request.setAttribute("signupStaffUserName", session.getAttribute("signupStaffUserName"));
    request.setAttribute("signupStaffEmail", session.getAttribute("signupStaffEmail"));
    request.setAttribute("roleError", session.getAttribute("roleError"));
    request.setAttribute("theatreLocationError", session.getAttribute("theatreLocationError"));
    request.setAttribute("userNameError", session.getAttribute("userNameError"));
    request.setAttribute("emailError", session.getAttribute("emailError"));
    request.setAttribute("passwordError", session.getAttribute("passwordError"));
    request.setAttribute("errorMessage", session.getAttribute("errorMessage"));
    session.removeAttribute("signupStaffRole");
    session.removeAttribute("signupStaffTheatreLocation");
    session.removeAttribute("signupStaffUserName");
    session.removeAttribute("signupStaffEmail");
    session.removeAttribute("roleError");
    session.removeAttribute("theatreLocationError");
    session.removeAttribute("userNameError");
    session.removeAttribute("emailError");
    session.removeAttribute("passwordError");
    session.removeAttribute("errorMessage");
%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/style.css">
    <title>Movie Buddy | Mangage Staff</title>
</head>

<body style="height: 100%; display: flex; flex-direction: column;"
    onload="loadSelectedOption('defaultRole', 'role', '${signupStaffRole}'); loadSelectedOption('defaultLocation', 'theatreLocation', '${signupStaffTheatreLocation}');">
    <div style="flex: 1 0 auto;">
        <!-- Navigation bar -->
        <jsp:include page="/navbar.jsp" />

        <!-- Page Content -->
        <div class="container">
            <h1 class="display-3 text-center">Create Faculty Account</h1>
            <hr>
            <p class="text-center errormessage" id="errorMessage">${errorMessage}</p>
            <div class="row">
                <div class="col-lg"></div>
                <div class="col-lg">
                    <div class="card">
                        <div class="card-body">
                            <form id="signUpForm" action="SignUpStaff" method="POST"
                                onsubmit="return validateStaffSignUp(this)">
                                <div class="form-group">
                                    <label>Role</label><br>
                                    <select id="role" class="inputbox" name="role" form="signUpForm"
                                        onchange="checkRole(this, 'roleError', 'theatreLocationInput')">
                                        <option id="defaultRole" hidden value="none">Select a role</option>
                                        <c:if test="${isAdmin}">
                                            <option value="admin">Admin</option>
                                            <option value="manager">Manager</option>
                                        </c:if>
                                        <option value="faculty">Faculty</option>
                                    </select>
                                    <span id="roleError" class="errormessage">${roleError}</span>
                                </div>
                                <div class="form-group" id="theatreLocationInput">
                                    <label>Theatre Location</label><br>
                                    <select id="theatreLocation" class="inputbox" name="theatreLocation"
                                        form="signUpForm" onchange="checkTheatreLocation(this, 'theatreLocationError')">
                                        <option id="defaultLocation" hidden value="none">Select a theatre location
                                        </option>
                                        <c:forEach items="${theatreList}" var="theatre">
                                            <option value="${theatre.getId()}">${theatre.getTheatreName()}</option>
                                        </c:forEach>
                                    </select>
                                    <span id="theatreLocationError" class="errormessage">${theatreLocationError}</span>
                                </div>
                                <div class="form-group">
                                    <label>Name</label><br>
                                    <input class="inputbox" type="text" name="userName" placeholder="Enter your name"
                                        onkeyup="checkName(this, 'userNameError')" value="${signupStaffUserName}">
                                    <br>
                                    <span id="userNameError" class="errormessage">${userNameError}</span>
                                </div>
                                <div class="form-group">
                                    <label>Email</label><br>
                                    <input name="email" class="inputbox" type="text" placeholder="Enter email"
                                        onkeyup="checkEmail(this, 'emailError')" value="${signupStaffEmail}">
                                    <br>
                                    <span id="emailError" class="errormessage">${emailError}</span>
                                </div>
                                <div class="form-group">
                                    <label>Password</label><br>
                                    <input name="password" class="inputbox" type="password" placeholder="Enter password"
                                        onkeyup="checkPassword(this, 'passwordError')">
                                    <br>
                                    <span id="passwordError" class="errormessage">${passwordError}</span>
                                </div>
                                <div class="text-center">
                                    <input type="submit" class="btn btn-primary" value="Create Account">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-lg"></div>
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