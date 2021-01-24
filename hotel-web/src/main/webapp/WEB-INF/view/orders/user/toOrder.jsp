<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<c:if test="${not empty requestScope.orderErrorMsg}">
    <div class="error"><fmt:message bundle="${i18n}" key="orders.errorMsg"/></div>
</c:if>


<div class="container-fluid">
    <table class="table table-hover table-condensed">
        <tr>
           <td class="col-sm-3 Right"><strong><fmt:message bundle="${i18n}" key="orders.hotelName"/>: </strong></td>
           <td class="col-sm-9 Left"><strong>${sessionScope.roomTypeDto.hotelName}</strong></td>
        </tr>
        <tr>
           <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.roomTypeName"/>: </td>
           <td class="col-sm-9 Left">${sessionScope.roomTypeDto.roomTypeName}</td>
        </tr>
        <tr>
            <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.seats"/>: </td>
            <td class="col-sm-9 Left">${sessionScope.roomTypeDto.seats}</td>
        </tr>
        <tr>
            <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.price"/>: </td>
            <td class="col-sm-9 Left"><fmt:formatNumber value="${sessionScope.roomTypeDto.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/> ${sessionScope.roomTypeDto.currency}</td>
        </tr>
    </table>

    <form class="form-inline" action="frontController?command=orders" method="post">
        <div class="form-group">
            <label for="arrivalDate"><fmt:message bundle="${i18n}" key="orders.arrivalDate"/>:</label>
            <input type="date" class="form-control" id="arrivalDate" required name="arrivalDate">
        </div>
        <div class="form-group">
            <label for="eventsDate"><fmt:message bundle="${i18n}" key="orders.eventsDate"/>:</label>
            <input type="date" class="form-control" id="eventsDate" required name="eventsDate">
        </div>
        <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="orders.submit"/></button>
    </form>


    <div style="text-align: center">
        <a href="frontController?command=hotels" class="buttonRefer"><fmt:message bundle="${i18n}" key="orders.choiceHotel"/></a>
        <a href="frontController?command=ordersLook" class="buttonRefer"><fmt:message bundle="${i18n}" key="orders.lookOrders"/></a>
    </div>
</div>