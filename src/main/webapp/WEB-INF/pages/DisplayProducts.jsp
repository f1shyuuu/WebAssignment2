<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            $(".searchButton").click(function(e){
                e.preventDefault();
                $(".category").load($(this).parent().attr("action")+'/'+$('#tag').val());
            });
        });
    </script>


    <title>Catalogue page</title>
</head>
<br>
<div style="text-align:right"><a href="/logout" >Log Out</a></div>
<div style="text-align:left"><a href="/back" >Back</a></div>
<h1>Photo store</h1>

<div class="search">
    <form:form  method="post" action="list/category">
        Please enter the tag: <input id="tag" name="tag" type="text"/> <br/>
        <input class="searchButton" type="submit" value="Submit">
    </form:form>
</div>

<h2>Product List</h2>
<div class="category">
<%@ include file="Category_Partial.jsp"%>
</div>
<div id="cart">
<%@ include file="Cart_Partial.jsp"%>
</div>
</body>
</html>
