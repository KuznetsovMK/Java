<%@ page import="java.util.List" %>
<%@ page import="com.gb.entity.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<title> Products</title>
</head>
<body>
    <h2>Products</h2>
    <ul>
    <% for (Product value : (List<Product>) request.getAttribute("product")) { %>
    <li> id: <%=value.getId()%>; title: <%=value.getTitle()%>; cost: <%=value.getCost()%>
    <% } %>
    </ul>
    <hr>
</body>
</html>