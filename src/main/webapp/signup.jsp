<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Buddy | Sign Up</title>
</head>

<body onload="refillSignUp('${userName}', '${email}')">
    <h2>Sign Up</h2>
    <form id="signUpForm" action="SignUp" method="POST" onsubmit="return validate(this)">
        <input id="userName" type="text" name="userName" placeholder="Enter your name"
            onkeyup="checkName(this, 'userNameError')">
        <span id="userNameError">${userNameError}</span><br>

        <input id="email" type="text" name="email" placeholder="Enter email" onkeyup="checkEmail(this, 'emailError')">
        <span id="emailError">${emailError}</span><br>

        <input type="password" name="password" placeholder="Enter password"
            onkeyup="checkPassword(this, 'passwordError')">
        <span id="passwordError">${passwordError}</span><br>

        <input type="password" name="rePassword" placeholder="Re-enter password"
            onkeyup="checkRePassword('signUpForm', 'rePasswordError')">
        <span id="rePasswordError">${rePasswordError}</span><br>

        <input type="submit" value="Sign Up">
    </form>
    <script src="./JS/validation.js"></script>
    <script src="./JS/functions.js"></script>
</body>

</html>