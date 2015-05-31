<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Somesha
  Date: 30/05/2015
  Time: 6:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%--<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style_new.css"/>"  media="screen" />--%>

    <title>Admin Page</title>


</head>
<body>
<h1>Admin Page</h1>

<c:forEach var="orders" items="${orderList}">
    <ul>
        <li>
        <div>Order details </div>
        Order ID:<c:out value="${orders.order.id}"/> <br>
        User:<c:out value="${orders.order.user}"/> <br>
        Destination:<c:out value="${orders.order.destination}"/> <br>
        Order Status:<c:out value="${orders.order.status}"/>

    <form:form action="admin/changeStatus" method="post">
        <input type="text" name="orderId" value="${orders.order.id}" style="display: none;  ">
        <input type="submit" value="Change Status"><br>
    </form:form>
        Shipping Fee:<c:out value="${orders.order.shipfee}"/> <br>
        Total:<c:out value="${orders.order.total}"/> <br>

        <c:forEach var="cartItem" items="${orders.cartItems}">
            <li><div>Item:<c:out value="${cartItem.product.title}"/><br>
                Quantity:<c:out value="${cartItem.quantity}"/><br></div></li>
        </c:forEach>

        </li>

    </ul>
    <br><br>
</c:forEach>



</body>
</html>
