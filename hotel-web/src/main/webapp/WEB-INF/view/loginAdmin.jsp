<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<c:if test="${not empty requestScope.errorMsg}">
    <div class="error"><fmt:message bundle="${i18n}" key="loginAdmin.errorMsg"/></div>
</c:if>

<form class="form-horizontal" action="frontController?command=loginAdmin" method="post">
    <div class="input-group col-sm-offset-4 col-sm-4">
        <span class="input-group-addon"><i class="glyphicon glyphicon-sunglasses"></i></span>
        <input type="number" required  class="form-control" name="adminId"
               placeholder="<fmt:message bundle="${i18n}" key="loginAdmin.id"/>"
               value="${requestScope.adminId}"
               min="1">
    </div>
    <div class="input-group col-sm-offset-4 col-sm-4">
        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
        <input type="text" required  class="form-control" name="adminName"  maxlength="30"
               placeholder="<fmt:message bundle="${i18n}" key="loginAdmin.name"/>"
               value="${requestScope.adminName}">
    </div>
    <div class="input-group col-sm-offset-4 col-sm-4">
        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
        <input type="password" required  class="form-control" name="adminPass"
               placeholder="<fmt:message bundle="${i18n}" key="loginAdmin.password"/>">
    </div>
    <div class="input-group col-sm-offset-4 col-sm-1">
      <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="loginAdmin.submit"/></button>
    </div>
</form>