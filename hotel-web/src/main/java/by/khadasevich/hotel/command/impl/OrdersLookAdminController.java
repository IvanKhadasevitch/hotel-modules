package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.dto.OrderDto;
import by.khadasevich.hotel.dto.mappers.OrderDtoMapper;
import by.khadasevich.hotel.entities.Hotel;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.enums.OrderStatusType;
import by.khadasevich.hotel.services.OrderService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersLookAdminController implements Controller {
    private OrderService orderService = SingletonBuilder.getInstanceImpl(OrderService.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // del after debug !!!
//        System.out.println("start OrdersLookAdminController");

        // check valid order status, if invalid then  OrderStatusType.NEW
        String orderStatusStr = req.getParameter("orderStatus") != null
                ? req.getParameter("orderStatus")
                : null;
        OrderStatusType orderStatus;
        try {
            orderStatus = OrderStatusType.valueOf(orderStatusStr);
        } catch (NullPointerException | IllegalArgumentException e) {
            orderStatus = OrderStatusType.NEW;
        }

        // take Orders with status = orderStatus from DB, & convert to orderDtoList
        Hotel hotel = (Hotel) req.getSession().getAttribute("hotel");
        List<Order> orderList = orderService.getAllForStatus(orderStatus);
        OrderDtoMapper orderDtoMapper = SingletonBuilder.getInstanceImpl(OrderDtoMapper.class);
        List<OrderDto> orderDtoList = orderList.stream()
                                               .map(orderDtoMapper::map)
                                               .collect(Collectors.toList());

        // filter hotel, take only for hotel where admin  response
        orderDtoList = orderDtoList.stream()
                                   .filter(p -> p.getHotelId() == hotel.getId())
                                   .collect(Collectors.toList());

        // save orderDtoList in request
        req.setAttribute("orderDtoList", orderDtoList);

        //if orderType NEW let orders approve
        req.setAttribute("isNewOrder", OrderStatusType.NEW.equals(orderStatus));

        // refer to MAIN_PAGE
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }
}
