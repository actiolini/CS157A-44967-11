<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Buddy | Sign Up</title>
</head>

<body>
    <h2>Sign Up</h2>
    <form id="sign_up_form" action="SignUp" method="POST" onsubmit="return validate(this)">
        <input type="text" name="username" placeholder="Enter your name" onkeyup="checkName(this, 'username_error')">
        <span id="username_error"></span><br>

        <input type="text" name="email" placeholder="Enter email" onkeyup="checkEmail(this, 'email_error')">
        <span id="email_error"></span><br>

        <input type="password" name="password" placeholder="Enter password"
            onkeyup="checkPassword(this, 'password_error')">
        <span id="password_error"></span><br>

        <input type="password" name="re_password" placeholder="Re-enter password"
            onkeyup="checkRePassword('sign_up_form', 're_password_error')">
        <span id="re_password_error"></span><br>

        <input type="submit" value="Sign Up">
    </form>
    <p>${message}</p>
    <script src="auth.js"></script>
</body>

</html>