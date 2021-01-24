<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>


<c:if test="${not empty requestScope.orderFreeRoomErrorMsg}">
    <div class="error"><fmt:message bundle="${i18n}" key="ordersApprove.errorMsgFreeRoom"/></div>
</c:if>
<c:if test="${not empty requestScope.orderIdErrorMsg}">
    <div class="error"><fmt:message bundle="${i18n}" key="ordersApprove.errorMsgId"/></div>
</c:if>
<c:if test="${not empty requestScope.orderDeclineErrorMsg}">
    <div class="error"><fmt:message bundle="${i18n}" key="ordersApprove.errorMsgDecline"/></div>
</c:if>
<c:if test="${not empty requestScope.orderApproveMsg}">
    <div class="approve"><fmt:message bundle="${i18n}" key="ordersApprove.approveMsg"/>: ${requestScope.order.id}</div>
</c:if>


<div class="container-fluid">
    <h4><fmt:message bundle="${i18n}" key="ordersApprove.hotelName"/>: ${sessionScope.hotel.name}</h4>
    <form class="form-inline" action="frontController?command=OrdersLookAdmin" method = "POST">
        <label class="radio-inline"><input type="radio" name="orderStatus"  value="NEW"> <fmt:message bundle="${i18n}" key="ordersApprove.statusNew"/></label>
        <label class="radio-inline"><input type="radio" name="orderStatus"  value="APPROVED"> <fmt:message bundle="${i18n}" key="ordersApprove.statusApprove"/></label>
        <label class="radio-inline"><input type="radio" name="orderStatus"  value="DECLINE"> <fmt:message bundle="${i18n}" key="ordersApprove.statusDecline"/></label>
        <label class="radio-inline"><input type="radio" name="orderStatus"  value="PAID"> <fmt:message bundle="${i18n}" key="ordersApprove.statusPaid"/></label>
        <div class="form-group">
            <button type="submit" class="btn btn-default"><fmt:message bundle="${i18n}" key="ordersApprove.statusSubmit"/></button>
        </div>
    </form>
    <c:if test="${requestScope.isNewOrder}">
       <form class="form-inline" action="frontController?command=OrdersApprove" method = "POST">
           <div class="form-group">
               <label for="orderId"><fmt:message bundle="${i18n}" key="ordersApprove.enterOrderId"/>:</label>
               <input type="number" name="orderId" required class="form-control" id="orderId"
                      max="${requestScope.orderDtoList[requestScope.orderDtoList.size() - 1].id}"
                      min="${requestScope.orderDtoList[0].id}" value="">
           </div>
           <div class="form-group">
               <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="ordersApprove.takeFreeRoom"/></button>
           </div>
           <div class="form-group">
               <label for="decline"><fmt:message bundle="${i18n}" key="ordersApprove.toDecline"/> <span class="glyphicon glyphicon-arrow-right"></span></label>
               <button id="decline" type="submit" class="btn btn-warning" name="decline" value="Decline"><fmt:message bundle="${i18n}" key="ordersApprove.decline"/></button>
           </div>
       </form>
    </c:if>

    <table class="table table-striped table-hover table-condensed">
        <tr>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.id"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.status"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.roomType"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.roomNumber"/></th>
            <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="ordersApprove.guestName"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.arrivalDate"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.eventsDate"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.price"/></th>
            <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.total"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="ordersLook.сurrency"/></th>
        </tr>
        <c:forEach var="orderDto" items="${requestScope.orderDtoList}" varStatus="status">
            <c:choose>
                <c:when test="${orderDto.expired eq false}">
                   <tr class="info gumRow">
                      <td class="col-md-1 Right">${orderDto.id}</td>
                      <td class="col-md-1 Center">${orderDto.status}</td>
                      <td class="col-md-1 Left">${orderDto.roomTypeName}</td>
                      <td class="col-md-1 Center">${orderDto.roomNumber}</td>
                      <td class="col-md-2 Left">${orderDto.guestName}</td>
                      <td class="col-md-1 Center">${orderDto.arrivalDate}</td>
                      <td class="col-md-1 Center">${orderDto.eventsDate}</td>
                      <td class="col-md-1 Right"><fmt:formatNumber value="${orderDto.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                      <td class="col-md-2 Right"><fmt:formatNumber value="${orderDto.total}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                      <td class="col-md-1 Left">${orderDto.currency}</td>
                   </tr>
                </c:when>
                <c:otherwise>
                   <tr class="info gumRow Expired">
                       <td class="col-md-1 Right">${orderDto.id}</td>
                       <td class="col-md-1 Center">${orderDto.status}</td>
                       <td class="col-md-1 Left">${orderDto.roomTypeName}</td>
                       <td class="col-md-1 Center">${orderDto.roomNumber}</td>
                       <td class="col-md-2 Left">${orderDto.guestName}</td>
                       <td class="col-md-1 Center">${orderDto.arrivalDate}</td>
                       <td class="col-md-1 Center">${orderDto.eventsDate}</td>
                       <td class="col-md-1 Right"><fmt:formatNumber value="${orderDto.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                       <td class="col-md-2 Right"><fmt:formatNumber value="${orderDto.total}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                       <td class="col-md-1 Left">${orderDto.currency}</td>
                   </tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </table>
</div>

