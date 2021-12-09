<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <title>Product</title>
</head>
<body>
<h2>Product name: ${product.name}</h2>
<h3>Product cost: ${product.cost}</h3>
<br>
<c:url var="deleteUrl" value="/product/delete">
    <c:param name="id" value="${product.id}"/>
</c:url>
<a href="${deleteUrl}" >Delete</a>
<br>
<c:url var="editUrl" value="/product/edit">
    <c:param name="id" value="${product.id}"/>
</c:url>
<a href="${editUrl}">Edit</a>
</body>
</html>