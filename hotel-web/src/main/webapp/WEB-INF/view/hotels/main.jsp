<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<script src="https://www.w3schools.com/lib/w3.js"></script>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>


<div style="font-size: large">
    <c:if test="${not empty requestScope.message}">INFO : ${requestScope.message}</c:if> <br/>
</div>
<div>
    <div class="container-fluid; t">
        <div class="col-md-12 tableHeader"><fmt:message bundle="${i18n}" key="hotels.title"/></div>
        <%--<table class="table table-striped table-hover table-condensed">--%>
        <table id="hotelsTable" class="table table-striped table-hover table-condensed">
            <tr>
                <%--<th class="col-md-4; gumHeader"><fmt:message bundle="${i18n}" key="hotels.name"/></th>--%>
                <th onclick="w3.sortHTML('#hotelsTable','.item', 'td:nth-child(1)')" class="col-md-4 gumHeader"><fmt:message bundle="${i18n}" key="hotels.name"/></th>

                <%--<div class="col-md-8">--%>
                    <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="hotels.roomTypeName"/></th>
                    <%--<th onclick="w3.sortHTML('#hotelsTable','.item', 'td:nth-child(2)')" class="col-md-2; gumHeader"><fmt:message bundle="${i18n}" key="hotels.roomTypeName"/></th>--%>
                    <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="hotels.seats"/></th>
                    <%--<th onclick="w3.sortHTML('#hotelsTable','.item', 'td:nth-child(3)')" class="col-md-1; gumHeader"><fmt:message bundle="${i18n}" key="hotels.seats"/></th>--%>
                    <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="hotels.price"/></th>
                    <%--<th onclick="w3.sortHTML('#hotelsTable','.item', 'td:nth-child(4)')" class="col-md-2; gumHeader"><fmt:message bundle="${i18n}" key="hotels.price"/></th>--%>
                    <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="hotels.currency"/></th>
                    <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="hotels.toOrder"/> №</th>
                <%--</div>--%>
            </tr>
            <form action="frontController?command=hotels" method="post">
                <c:forEach var="product" items="${sessionScope.roomTypeDtoList}" varStatus="status">
                    <%--<tr class="info">--%>
                    <tr class="item gumRow">
                        <td class="col-md-4 Left">${product.hotelName}</td>
                        <%--<div class="col-md-8">--%>
                            <%--<td class="col-md-2; gumRowCenter">${product.roomTypeName}</td>--%>
                            <td class="col-md-2 Center">${product.roomTypeName}</td>
                            <td class="col-md-1 Center">${product.seats}</td>
                            <td class="col-md-2 Right"><fmt:formatNumber value="${product.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/></td>
                            <td class="col-md-1 Left">${product.currency}</td>
                            <td class="col-md-2 Center">
                                <input type="submit" style="color:gainsboro; background-color:gainsboro" name="roomTypeDtoId" title="<fmt:message bundle="${i18n}" key="hotels.toOrder"/>" value="${product.roomTypeId}" />
                            </td>
                        <%--</div>--%>
                    </tr>
                </c:forEach>
            </form>
        </table>
    </div>
</div>




