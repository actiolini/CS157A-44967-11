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
    <form action="SignUp" method="POST">
        <input type="text" name="username" placeholder="Enter your name"><br>
        <input type="text" name="email" placeholder="Enter email"><br>
        <input type="password" name="password" placeholder="Enter password"><br>
        <input type="password" name="re-password" placeholder="Re-enter password"><br>
        <input type="submit" value="Sign Up">
    </form>
    <p>${message}</p>
</body>

</html>