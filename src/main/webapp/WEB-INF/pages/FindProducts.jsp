<%@ taglib prefix="c" uri="http://www.springframework.org/tags/form" %>
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
    <div class="container">
    <form:form class="form-group form" name="input" method="post" action="list">
      Please enter the tag: <input name="tag" class="form-control" type="text" /> <br/>

    <input class="form-control" type="submit" value="Submit">
    </form:form>
    </div>

</body>
</html>
