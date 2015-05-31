<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: guichi
  Date: 31/05/15
  Time: 3:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
  <script type="text/javascript">
    $(document).ready(function(){
      $(".add").click(function(e){
        e.preventDefault();
        $("#cart").load($(this).attr("href"));
      });
    });
  </script>
</head>
<body>
<c:forEach var="product" items="${productList}">
  <a href="list/add/${product.productId}" class="add">Add to cart</a>
  <a href="list/remove/${product.productId}" class="add">Remove from cart</a> <br>

  Product Id: <c:out value="${product.productId}"/> <br>
  Title: <c:out value="${product.title}"/> <br>
  Description: <c:out value="${product.description}"/><br>
  Price: <c:out value="${product.price}"/><br>
  Photo: <img src="${product.imageUrl}"/>
  <br><br><br>
</c:forEach>
</body>
</html>
