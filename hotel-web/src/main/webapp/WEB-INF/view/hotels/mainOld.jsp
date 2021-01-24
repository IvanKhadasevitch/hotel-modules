<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>


<div style="font-size: large">
    <c:if test="${not empty requestScope.message}">INFO : ${requestScope.message}</c:if> <br/>
</div>
<div>
    <div class="container-fluid; t">
        <%--<div style="font-size: large; color: blue" class="col-md-12"><fmt:message bundle="${i18n}" key="hotels.title"/></div>--%>
        <div class="col-md-12; tableHeader"><fmt:message bundle="${i18n}" key="hotels.title"/></div>
        <%--<table class="table table-striped table-hover table-condensed">--%>
        <%--<table id="hotelsTable" class="table table-striped table-hover table-condensed tablesorter">--%>
        <table id="hotelsTable" class="tablesorter">
            <thead>
            <tr>
                <%--<th class="col-md-4; gumHeader"><fmt:message bundle="${i18n}" key="hotels.name"/></th>--%>
                <th><fmt:message bundle="${i18n}" key="hotels.name"/></th>
                <%--<div class="col-md-8">--%>
                <th class="col-md-2; gumHeader"><fmt:message bundle="${i18n}" key="hotels.roomTypeName"/></th>
                <th class="col-md-1; gumHeader"><fmt:message bundle="${i18n}" key="hotels.seats"/></th>
                <th class="col-md-2; gumHeader"><fmt:message bundle="${i18n}" key="hotels.price"/></th>
                <th class="col-md-1; gumHeader"><fmt:message bundle="${i18n}" key="hotels.currency"/></th>
                <th class="col-md-2; gumHeader"><fmt:message bundle="${i18n}" key="hotels.toOrder"/></th>
                <%--</div>--%>
            </tr>
            </thead>
            <form action="frontController?command=hotels" method="post">
                <c:forEach var="product" items="${sessionScope.roomTypeDtoList}" varStatus="status">
                    <tbody>
                    <tr class="info">
                        <td class="col-md-4; gumRowLeft">${product.hotelName}</td>
                        <div class="col-md-8">
                            <td class="col-md-2; gumRowCenter">${product.roomTypeName}</td>
                            <td class="col-md-1; gumRowCenter">${product.seats}</td>
                            <td class="col-md-2; gumRowRight"><fmt:formatNumber value="${product.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                            <td class="col-md-1; gumRowLeft">${product.currency}</td>
                            <td class="col-md-2; gumRowCenter"><input type="submit" name="roomTypeDtoId" title="<fmt:message bundle="${i18n}" key="hotels.toOrder"/>" value="${product.roomTypeId}" /></td>
                        </div>
                    </tr>
                    </tbody>
                </c:forEach>
            </form>
        </table>
    </div>
</div>
