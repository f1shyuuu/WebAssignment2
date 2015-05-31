<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: fish
  Date: 15/5/28
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h2>Items in cart</h2>


<%--<h1>Cart: ${cart.items}</h1>--%>
<table>
<c:forEach var="cartItem" items="${cart.items}">

    <tr>
      <td>product : ${cartItem.product.title}</td>
      <td>quantity: ${cartItem.quantity}</td>
      <td>unit price : ${cartItem.product.price}</td>
    </tr>
    <%--<a href="list/add/${cartItem.product.productId}" class="add">Add</a>--%>
    <%--<a href="list/remove/${cartItem.product.productId}" class="remove">Delete</a> <br>--%>

</c:forEach>
<tfoot>
  <td>Total price :</td>
  <td> ${cart.total}</td>
</tfoot>

</table>

<%--Incomplete Checkout Function!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--%>
<div class="container">
    <form:form class="form-group form" name="input" method="POST" action="cart/checkout">
        Please enter the address: <input class="form-control" type="text" name="address"/> <br/>
        <input class="form-control" type="submit" id="checkoutButton" value="Checkout"/>
    </form:form>
</div>


</body>
</html>
