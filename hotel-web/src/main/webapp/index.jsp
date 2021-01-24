<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<html>
  <head>
    <title>Hotel</title>
  </head>
  <body>
  <c:out value="This is index.jsp: ${sessionScope.admin.name}"/>
  <jsp:forward page="/frontController?command=hotels"/>
  </body>
</html>
