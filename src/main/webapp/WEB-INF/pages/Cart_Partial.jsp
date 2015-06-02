<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="address" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $(document).ready(function(){
            $("#checkoutButton").click(function(e){
                e.preventDefault();
                $("#cart").load($(this).parent().attr("action")+'/'+$('#address').val()+'/'+$('#orderId').val());
            });
            $(".add").click(function(e){
                e.preventDefault();
                $("#cart").load($(this).attr("href"));
            });
            $(".remove").click(function(e){
                e.preventDefault();
                $("#cart").load($(this).attr("href"));
            });
        });
    });
</script>
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
    <a href="list/add/${cartItem.product.productId}" class="add">Add</a>
    <a href="list/remove/${cartItem.product.productId}" class="remove">Delete</a> <br>

</c:forEach>

<c:if test="${isSuccess == 1}" var="succeeded"/>
<tfoot>
  <td>Product Total price :</td>
  <td> ${cart.total}</td>
</tfoot>
<c:if test="${succeeded}">
    <tfoot>
        <td>Shipping Cost:</td>
        <td> ${shippingCost}</td>
    </tfoot>
    <tfoot>
        <td>Total Cost:</td>
        <td> ${finalTotal}</td>
    </tfoot>
</c:if>
</table>


<c:if test="${isSuccess == 1}" var="succeeded"/>
<c:if test="${not succeeded}">
        <div class="container">
            <form:form class="form-group form" name="input" method="POST" action="list/checkout">
                <c:if test="${not empty orderId}">
                    <input type="text" name="orderId" id="orderId" value="${orderId}" style="display: none"/>
                </c:if>
                <c:if test="${empty orderId}">
                    <input type="text" name="orderId" id="orderId" value="-1" style="display: none"/>
                </c:if>
                Please enter the address:address: <input class="form-control" type="text" id="address" name="address"/> <br/>
                <input class="form-control" type="submit" id="checkoutButton" value="Checkout"/>
            </form:form>
        </div>
</c:if>
<c:if test="${succeeded}">
            <label>Order Succesfully placed!</label>
</c:if>



</body>
</html>
