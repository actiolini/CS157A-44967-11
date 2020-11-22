<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Buddy | Sign In</title>
</head>

<body onload="refillSignIn('${email}')">
    <h2>Sign In</h2>
    <form action="SignIn" method="POST">
        <input id="email" type="text" name="email" placeholder="Enter email"><br>
        <input type="password" name="password" placeholder="Enter password"><br>
        <input type="submit" value="Sign In">
    </form>
    <p id="signInError">${message}</p>
    <script src="./JS/functions.js"></script>
</body>

</html>