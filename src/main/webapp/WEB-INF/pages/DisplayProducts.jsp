<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: fish
  Date: 15/5/27
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%--<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style_new.css"/>"  media="screen" />--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $(".add").click(function(e){
                e.preventDefault();
                $("#cart").load($(this).attr("href"));
            });
        })

        $(document).ready(function(){
            $(".remove").click(function(e){
                e.preventDefault();
                $("#cart").load($(this).attr("href"));
            });
        })
    </script>
    <title>Catalogue page</title>
</head>
<br>

<h1>Photo store</h1>

<h2>Product List</h2>

<c:forEach var="product" items="${productList}">
    <a href="list/add/${product.productId}" class="add">Add to cart</a>
    <a href="list/remove/${product.productId}" class="remove">Remove from cart</a> <br>

    Product Id: <c:out value="${product.productId}"/> <br>
    Title: <c:out value="${product.title}"/> <br>
    Description: <c:out value="${product.description}"/><br>
    Price: <c:out value="${product.price}"/><br>
    Photo: <img src="${product.imageUrl}"/>
    <br><br><br>
</c:forEach>

<div id="cart">
<jsp:include page="Cart_Partial.jsp"/>
</div>
</body>
</html>
