<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<div class="container-fluid">
    <div class="col-md-12; tableHeader"><fmt:message bundle="${i18n}" key="ordersLook.title"/></div>
    <table class="table table-striped table-hover table-condensed">
        <tr>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.id"/></th>
            <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.hotelName"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.roomType"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.roomNumber"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.price"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.status"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.arrivalDate"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.eventsDate"/></th>
            <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.total"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.сurrency"/></th>
        </tr>
        <c:forEach var="orderDto" items="${requestScope.orderDtoList}" varStatus="status">
            <tr class="info gumRow">
                <td class="col-md-1 Right">${orderDto.id}</td>
                <td class="col-md-2 Left">${orderDto.hotelName}</td>
                <td class="col-md-1 Left">${orderDto.roomTypeName}</td>
                <td class="col-md-1 Center">${orderDto.roomNumber}</td>
                <td class="col-md-1 Right"><fmt:formatNumber value="${orderDto.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                <td class="col-md-1 Center">${orderDto.status}</td>
                <td class="col-md-1 Center">${orderDto.arrivalDate}</td>
                <td class="col-md-1 Center">${orderDto.eventsDate}</td>
                <td class="col-md-2 Right"><fmt:formatNumber value="${orderDto.total}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                <td class="col-md-1 Left">${orderDto.currency}</td>
            </tr>
        </c:forEach>
    </table>
</div>
