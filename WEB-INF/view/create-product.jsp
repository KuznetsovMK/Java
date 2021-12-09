<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <title>Product</title>
</head>
<body>
<form:form action="create" modelAttribute="product">
    <form:hidden path="id" value="${product.id}"/>
    Name: <form:input path="name"/>
    <br>
    Cost: <form:input path="cost"/>
    <br>
    <input type="submit" value="Submit">
</form:form>
</body>
</html>