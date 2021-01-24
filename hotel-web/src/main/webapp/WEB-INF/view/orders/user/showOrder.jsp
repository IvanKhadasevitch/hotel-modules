<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<table class="table table-hover table-condensed">
    <tr>
        <td class="col-sm-3 Right"><strong><fmt:message bundle="${i18n}" key="orders.hotelName"/>: </strong></td>
        <td class="col-sm-9 Left"><strong>${sessionScope.orderDto.hotelName}</strong></td>
    </tr>
    <tr>
        <td class="col-sm-3 Right"><strong><fmt:message bundle="${i18n}" key="ordersApprove.guestName"/>: </strong></td>
        <td class="col-sm-9 Left"><strong>${sessionScope.orderDto.guestName}</strong></td>
    </tr>
    <tr>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.id"/>: </td>
        <td class="col-sm-9 Left">${sessionScope.orderDto.id}</td>
    </tr>
    <tr>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="ordersLook.status"/>: </td>
        <td class="col-sm-9 Left">${sessionScope.orderDto.status}</td>
    </tr>
    <tr>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.roomTypeName"/>: </td>
        <td class="col-sm-9 Left">${sessionScope.orderDto.roomTypeName}</td>
    </tr>
    <tr>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.seats"/>: </td>
        <td class="col-sm-9 Left">${sessionScope.orderDto.seats}</td>
    </tr>
    <tr>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.arrivalDate"/>: </td>
        <td class="col-sm-9 Left">${sessionScope.orderDto.arrivalDate}</td>
    </tr>
    <tr>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.eventsDate"/>: </td>
        <td class="col-sm-9 Left">${sessionScope.orderDto.eventsDate}</td>
    </tr>
    <tr>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.price"/>: </td>
        <td class="col-sm-9 Left"><fmt:formatNumber value="${sessionScope.orderDto.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/> ${sessionScope.orderDto.currency}</td>
    </tr>
    <tr>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="ordersLook.total"/>: </td>
        <td class="col-sm-9 Left"><fmt:formatNumber value="${sessionScope.orderDto.total}" minFractionDigits="2" maxFractionDigits="2" type="number"/> ${sessionScope.orderDto.currency}</td>
    </tr>
</table>

<div style="text-align: center">
    <a href="frontController?command=hotels" class="buttonRefer"><fmt:message bundle="${i18n}" key="orders.choiceHotel"/></a>
    <a href="frontController?command=Orders" class="buttonRefer"><fmt:message bundle="${i18n}" key="orderShow.anotherOrder"/></a>
    <a href="frontController?command=ordersLook" class="buttonRefer"><fmt:message bundle="${i18n}" key="orders.lookOrders"/></a>
</div>