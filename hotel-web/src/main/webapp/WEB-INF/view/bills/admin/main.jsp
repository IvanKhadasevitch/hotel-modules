<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<div style="font-size: large">
    <c:if test="${not empty requestScope.message}">INFO : ${requestScope.message}</c:if> <br/>
</div>

<div class="container-fluid">
    <div class="col-md-12 tableHeader"><fmt:message bundle="${i18n}" key="bills.title"/></div>
    <form action="frontController?command=billsAdmin" method = "POST">
        <input type="radio" name="billStatus" value="UNPAID"> <fmt:message bundle="${i18n}" key="bills.statusUnPaid"/>
        <input type="radio" name="billStatus" value="PAID"> <fmt:message bundle="${i18n}" key="bills.statusPaid"/>
        <input type="submit" value="<fmt:message bundle="${i18n}" key="bills.submit"/>">
    </form>

    <table class="table table-striped table-hover table-condensed">
        <tr>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="billsAdmin.id"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="bills.status"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="billsAdmin.orderId"/></th>
            <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="billsAdmin.guestName"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="bills.roomType"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="bills.roomNumber"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="bills.arrivalDate"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="bills.eventsDate"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="bills.price"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="bills.total"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="bills.currency"/></th>
        </tr>
        <form action="frontController?command=billPay" method="post">
            <c:forEach var="billDto" items="${requestScope.billDtoList}" varStatus="status">
                <tr class="info gumRow">
                    <td class="col-md-1 Center">${billDto.id}</td>
                    <td class="col-md-1 Center">${billDto.status}</td>
                    <td class="col-md-1 Center">${billDto.orderId}</td>
                    <td class="col-md-2 Left">${billDto.guestName}</td>
                    <td class="col-md-1 Left">${billDto.roomTypeName}</td>
                    <td class="col-md-1 Center">${billDto.roomNumber}</td>
                    <td class="col-md-1 Center">${billDto.arrivalDate}</td>
                    <td class="col-md-1 Center">${billDto.eventsDate}</td>
                    <td class="col-md-1 Right"><fmt:formatNumber value="${billDto.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                    <td class="col-md-2 Right"><fmt:formatNumber value="${billDto.total}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                    <td class="col-md-1 Left">${billDto.currency}</td>
                </tr>
            </c:forEach>
        </form>
    </table>
</div>
