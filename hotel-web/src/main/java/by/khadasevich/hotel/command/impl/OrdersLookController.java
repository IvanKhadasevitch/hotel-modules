package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.dto.OrderDto;
import by.khadasevich.hotel.dto.mappers.OrderDtoMapper;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.User;
import by.khadasevich.hotel.services.OrderService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OrdersLookController implements Controller {
    private OrderService orderService = SingletonBuilder.getInstanceImpl(OrderService.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // del after debug
//        System.out.println("start OrdersLookController.execute");

        // for  LogOn user get orderList from DB & convert to orderDtoList
        User user = req.getSession().getAttribute("user") != null
                ? (User) req.getSession().getAttribute("user")
                : new User();
        List<Order> orderList = orderService.getAllForUserId(user.getId());
        List<OrderDto> orderDtoList = new LinkedList<>();
        OrderDtoMapper orderDtoMapper = SingletonBuilder.getInstanceImpl(OrderDtoMapper.class);
        for (Order order : orderList ) {
            orderDtoList.add(orderDtoMapper.map(order));
        }

        // save orderDtoList in request
        req.setAttribute("orderDtoList", orderDtoList);

        // look ordersList
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }
}
