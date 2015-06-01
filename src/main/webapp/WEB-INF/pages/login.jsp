<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Somesha
  Date: 31/05/2015
  Time: 7:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Authentcation page</title>
</head>
<body>

<form:form method = "POST" action = "/authenticate">
  <label>User Name:<input type = "text" name = "username"/></label><br/>
  <label>Password:<input type = "text" name = "password"/></label><br/>
  <input type="submit" value="Enter"/>
</form:form>

</body>
</html>
