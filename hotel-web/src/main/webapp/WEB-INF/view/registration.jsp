<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<c:if test="${not empty requestScope.errorMsg}">
    <div class="error"><fmt:message bundle="${i18n}" key="registration.errorMsg"/></div>
</c:if>

<form class="form-horizontal" action="frontController?command=Registration" method="post">
    <div class="form-group">
        <label class="control-label col-sm-5" for="name"><fmt:message bundle="${i18n}" key="registration.name"/>:</label>
        <div class="col-sm-4">
            <input type="text" required maxlength="20" class="form-control" id="name" name="name"
                   placeholder="<fmt:message bundle="${i18n}" key="registration.nameEnter"/>"
                   value="${requestScope.userName}">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-5" for="surName"><fmt:message bundle="${i18n}" key="registration.surname"/>:</label>
        <div class="col-sm-4">
            <input type="text" required maxlength="30" class="form-control" id="surName" name="surName"
                   placeholder="<fmt:message bundle="${i18n}" key="registration.surnameEnter"/>"
                   value="${requestScope.userSurName}">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-5" for="birthDate"><fmt:message bundle="${i18n}" key="registration.birthDate"/>:</label>
        <div class="col-sm-4">
            <input type="date" required class="form-control" id="birthDate" name="birthDate"
                   value="${requestScope.userBirthDate}">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-5" for="email"><fmt:message bundle="${i18n}" key="registration.email"/>:</label>
        <div class="col-sm-4">
            <input type="email" required maxlength="64" class="form-control" id="email" name="email"
                   placeholder="<fmt:message bundle="${i18n}" key="registration.emailEnter"/>"
                   value="${requestScope.userEmail}">
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-5 col-sm-1">
            <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="loginUser.submit"/></button>
        </div>
    </div>
</form>



