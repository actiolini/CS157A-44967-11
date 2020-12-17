<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="moviebuddy.util.Passwords" %>
<jsp:include page="/TheatreGet" />
<jsp:include page="/StaffGet" />
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
    <title>Movie Buddy | Manage Staff</title>
</head>

<body style="height: 100%; display: flex; flex-direction: column;"
    onload="loadSelectedOption('defaultLocation', 'selectTheatreOption', '${selectTheatreId}');">
    <div style=" flex: 1 0 auto;">
        <!-- Navigation bar -->
        <jsp:include page="/navbar.jsp" />

        <!-- Page Content -->
        <div class="container">
            <h3>Theatre: ${staffTheatreName}</h3>
            <hr>
            <div class="row">
                <div class="col"></div>
                <div class="col-6 text-center">
                    <a href="./staffsignup.jsp">
                        <button type="button" class="btn btn-outline-info">Create Faculty Account</button>
                    </a>
                </div>
                <div class="col"></div>
            </div>
            <hr>
            <c:if test="${isAdmin}">
                <form id="selectTheatreForm" action="StaffGet" method="POST">
                    <div class="form-group">
                        <label>Theatre: </label>
                        <select id="selectTheatreOption" name="selectTheatreOption" form="selectTheatreForm"
                            onchange="submitOnChange('selectTheatreForm')">
                            <option id="defaultLocation" hidden value="none">Select a theatre location
                            </option>
                            <c:forEach items="${theatreList}" var="theatre">
                                <option value="${theatre.getId()}">${theatre.getTheatreName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
                <hr>
            </c:if>
            <p class="text-center errormessage" id="errorMessage">${errorMessage}</p>
            <table>
                <tr>
                    <th>Staff Id</th>
                    <th>Name</th>
                    <th>Role</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
                <c:if test="${isAdmin}">
                    <c:forEach items="${adminUserList}" var="admin">
                        <tr>
                            <td>${admin.getStaffId()}</td>
                            <td>${admin.getUserName()}</td>
                            <td>${admin.getRole()}</td>
                            <td>${admin.getEmail()}</td>
                            <td>N/A</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:forEach items="${staffUserList}" var="staff">
                    <tr>
                        <td>${staff.getStaffId()}</td>
                        <td>${staff.getUserName()}</td>
                        <td>${staff.getRole()}</td>
                        <td>${staff.getEmail()}</td>
                        <c:if test="${staff.getRole().equals('manager') && !isAdmin}">
                            <td>N/A</td>
                        </c:if>
                        <c:if test="${staff.getRole().equals('faculty') || isAdmin}">
                            <td>
                                <div class="container">
                                    <form action="StaffDelete" method="POST" class="button">
                                        <input type="hidden" name="staffId" value="${staff.getStaffId()}" />
                                        <input type="submit" class="btn btn-outline-info" value="Delete" />
                                    </form>
                                </div>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
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