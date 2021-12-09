<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <title>Products</title>
</head>
<body>
<h1>Products List</h1>
<ul>
 <jsp:useBean id="products" type="java.util.List"     scope="request"/>
    <c:forEach var="product" items="${products}">
        <c:url var="viewUrl" value="/product/ + ${product.id}">
        </c:url>
        <li>
            <a href="${viewUrl}">View</a>
            <br>
            Product name: ${product.name}
            <br>
            Product cost: ${product.cost}
            <br>
        </li>
    </c:forEach>
</ul>
<br>
<c:url var="createUrl" value="/product/create">
</c:url>
<a href="${createUrl}">Create</a>
</body>
</html>