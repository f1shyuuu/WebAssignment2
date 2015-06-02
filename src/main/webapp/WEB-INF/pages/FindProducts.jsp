<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: fish
  Date: 15/5/27
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Photo Shop</title>

</head>
<body>
<%--<div class="container">--%>
    <%--<form:form class="form-group form" name="input" method="post" action="list">--%>
        <%--Please enter the tag: <input name="tag" class="form-control" type="text"/> <br/>--%>

        <%--<input class="form-control" type="submit" value="Submit">--%>
    <%--</form:form>--%>
<%--</div>--%>

<h1 style="color: #5cb85c"> <a href="/list">Go to category</a> </h1>

<a href="/logout">Log Out</a>
<h1>Here is your order history</h1>
<c:forEach var="o" items="${userOrders}">
    <a href="/edit/${o.order.id}">Edit Item</a>
    <h3>Order summary:<br></h3>
    <b>
    Status : <c:out value="${o.order.status}"></c:out>
    Destination : <c:out value="${o.order.destination}"></c:out>
    Shipping fee : <c:out value="${o.order.shipfee}"></c:out>
    Total fee : <c:out value="${o.order.total}"></c:out>
    </b>
    <h3><br>Order details :</h3>
    <c:forEach var="c" items="${o.cartItems}">
        Title :<c:out value="${c.product.title}"></c:out>
        Price : <c:out value="${c.product.price}"></c:out>
        Quantity : <c:out value="${c.quantity}"></c:out>
        <br>
    </c:forEach>


</c:forEach>

</body>
</html>
