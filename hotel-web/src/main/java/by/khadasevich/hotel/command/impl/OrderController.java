package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.dto.OrderDto;
import by.khadasevich.hotel.dto.RoomTypeDto;
import by.khadasevich.hotel.dto.mappers.OrderDtoMapper;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.User;
import by.khadasevich.hotel.services.OrderService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class OrderController implements Controller {
    private OrderService orderService = SingletonBuilder.getInstanceImpl(OrderService.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        // del after debug
//        System.out.println("start OrderController.execute");

        // check valid request param
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        Date currentDate = new Date((new java.util.Date()).getTime());
        Date arrivalDate = req.getParameter("arrivalDate") != null
                ? Date.valueOf(req.getParameter("arrivalDate"))
                : currentDate;
        Date eventsDate = req.getParameter("eventsDate") != null
                ? Date.valueOf(req.getParameter("eventsDate"))
                : currentDate;

        boolean wrongDate = false;
        if (arrivalDate.before(currentDate) || eventsDate.before(currentDate)
                || eventsDate.before(arrivalDate) || arrivalDate.equals(eventsDate)) {
            wrongDate = true;
            if (req.getParameter("arrivalDate") != null && req.getParameter("eventsDate") != null) {
                req.setAttribute("orderErrorMsg", "Invalid Data");

                // del after debug
//                System.out.println("OrderController.execute set order.errorMsg= "
//                        + req.getAttribute("orderErrorMsg"));
            }

        }

        if (wrongDate) {
            // del after debug
//            System.out.println("OrderController.execute: WrongDate=true," +
//                    " refer forward to MAIN_PAGE");

            // WrongDate, refer to try again. MAIN_PAGE
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);

            return;
        }

        boolean isRoomTypeDto = req.getSession().getAttribute("roomTypeDto") != null;
        if (! isRoomTypeDto) {
            // del after debug
//            System.out.println("OrderController.execute: isRoomTypeDto=false," +
//                    " refer forward to /frontController?command=hotels");

            // don't chosen RoomTypeDto, refer to /frontController?command=hotels
            req.getRequestDispatcher("/frontController?command=hotels").forward(req, resp);

            return;
        }

        // make order, save in DB
        User user = (User) req.getSession().getAttribute("user");
        RoomTypeDto roomTypeDto = (RoomTypeDto) req.getSession().getAttribute("roomTypeDto");
        Order order = orderService.make( roomTypeDto.getRoomTypeId(), arrivalDate, eventsDate);
        order.setDateProcessing(currentDate);

        long periodDays = order.getArrivalDate().toLocalDate()
                               .until(order.getEventsDate().toLocalDate(), ChronoUnit.DAYS);
        order.setTotal(roomTypeDto.getPrice().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).longValue() *
                periodDays);
        order = orderService.save(order, user.getId());
        OrderDto orderDto = SingletonBuilder.getInstanceImpl(OrderDtoMapper.class).map(order);
        //save orderDto in session
        req.getSession().setAttribute("orderDto", orderDto);

        // refer to OrderShow, to orders/user/showOrder.jsp
        // del after debug
//        System.out.println("OrderController.execute: Order made and refer to: " +
//                 "/frontController?command=OrderShow");

        req.getRequestDispatcher( "/frontController?command=OrderShow").forward(req, resp);

    }
}
