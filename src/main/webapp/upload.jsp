<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">

<body>
    <form action="upload" method="post" enctype="multipart/form-data">
        Select File:<input type="file" name="uploadfile" /><br/>
        <input type="submit" value="upload" />
    </form>
    <p>${message}</p>
</body>

</html>