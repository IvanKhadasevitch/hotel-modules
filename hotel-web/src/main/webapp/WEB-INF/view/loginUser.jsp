<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<c:if test="${not empty requestScope.errorMsg}">
    <div class="error"><fmt:message bundle="${i18n}" key="loginUser.errorMsg"/></div>
</c:if>

<form class="form-horizontal" action="frontController?command=loginUser" method="post">
    <div class="form-group">
        <label class="control-label col-sm-5" for="email"><fmt:message bundle="${i18n}" key="loginUser.email"/>:</label>
        <div class="col-sm-3">
            <input type="email" required maxlength="64" class="form-control" id="email" name="email" placeholder="<fmt:message bundle="${i18n}" key="loginUser.emailEnter"/>">
        </div>
    </div>
        <%--<b><fmt:message bundle="${i18n}" key="loginUser.password"/></b>--%>
        <%--<input type="password" name="password" maxlength="20"/><br/>--%>
    <div class="form-group">
       <div class="col-sm-offset-5 col-sm-3">
          <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="loginUser.submit"/></button>
       </div>
    </div>
</form>
<a href="frontController?command=Registration" class="col-sm-offset-5 col-sm-3 buttonRefer"><fmt:message bundle="${i18n}" key="registration.refer"/></a>

