<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>

<head>
    <title>Movie Buddy | error</title>
    <style>
        .container {
            width: 60%;
            margin: auto;
            position: relative;
        }

        .textbox {
            position: absolute;
            right: 0;
            bottom: 0;
        }

        .errMsg {
            text-align: center;
            font-size: 40px;
        }
    </style>
</head>

<body>
    <div class='container'>
        <img style='width: 50%;' src='https://bit.ly/2MVsZU8' alt='saitama'>
        <div class='textbox'>
            <p class='errMsg'>Oops!!!</p>
            It seems like something just went wrong.<br>
            Please <a href="home.jsp">click here</a> and try again.
        </div>
    </div>
</body>

</html>