package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.entities.Admin;
import by.khadasevich.hotel.entities.Bill;
import by.khadasevich.hotel.entities.Order;
import by.khadasevich.hotel.entities.RoomType;
import org.apache.commons.lang3.math.NumberUtils;
import by.khadasevich.hotel.services.OrderService;
import by.khadasevich.hotel.services.RoomTypeService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrdersApproveController implements Controller {
    private OrderService orderService = SingletonBuilder.getInstanceImpl(OrderService.class);
    private RoomTypeService roomTypeService = SingletonBuilder.getInstanceImpl(RoomTypeService.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // del after debug !!!
//        System.out.println("start OrdersApproveController");

        // check in data
        final long DEFAULT_ID = -1L;
        final String DECLINE_ORDER_EN = "Decline";
        final String DECLINE_ORDER_RU = "Отклонить";
        final String DEFAULT = "NotDefine";

        // check decline
        String declineStr = req.getParameter("decline") == null
                ? DEFAULT
                : new String(req.getParameter("decline").getBytes("ISO-8859-1"),"UTF-8");
        boolean isDecline = DECLINE_ORDER_EN.equals(declineStr) || DECLINE_ORDER_RU.equals(declineStr);

        // take orderId from request
        long orderId = NumberUtils.toLong(req.getParameter("orderId"), DEFAULT_ID);

        // check isValidOrderId
        Order order = orderService.get(orderId);
        long hotelId = req.getSession().getAttribute("admin") == null
                ? DEFAULT_ID
                : ((Admin) req.getSession().getAttribute("admin")).getHotelId();
        long roomTypeId = order == null
                ? DEFAULT_ID
                : order.getRoomTypeId();
        RoomType roomType = roomTypeService.get(roomTypeId);
        boolean isValidOrderId = order != null &&
                roomType != null && hotelId == roomType.getHotelId() ;

        // invalid OrderId tray again, refer to adminLookOrders
        if (!isValidOrderId) {
            // del after debug !!!
//            System.out.println("OrdersApproveController: invalid OrderId");

            //save message in request
            req.setAttribute("orderIdErrorMsg", "Invalid order id");

            // refer to adminLookOrders
            req.getRequestDispatcher("/frontController?command=OrdersLookAdmin").forward(req, resp);

            return;
        }

        long adminId = req.getSession().getAttribute("admin") == null
                ? DEFAULT_ID
                : ((Admin) req.getSession().getAttribute("admin")).getId();

        // Decline Order
        if (isDecline) {
            // try decline order in DB
            if (!orderService.decline(orderId, adminId)) {
                // del after debug !!!
//                System.out.println("OrdersApproveController: can't decline order");

                // can't decline order, save message in request
                req.getSession().setAttribute("orderDeclineErrorMsg", "");
            }
            // refer to adminLookOrders
            req.getRequestDispatcher("/frontController?command=OrdersLookAdmin").forward(req, resp);

            return;
        }

        // if Not one didn't work -> Approve order
        Bill bill = orderService.approve(orderId,adminId);

        if (bill == null) {
            // del after debug !!!
//            System.out.println("OrdersApproveController: can't approve Order & make Bill");

            // can't approve Order & make Bill,save message in request
            req.setAttribute("orderFreeRoomErrorMsg", "Can't approve order and make bill");
        } else {
            // del after debug !!!
//            System.out.println("OrdersApproveController: Order approved & Bill created");

            // Order approved & Bill created, order  save in request
            req.setAttribute("order", order);

            //save message in request
            req.setAttribute("orderApproveMsg", "Bill dane. Approved order with id");
        }

        //  refer to adminLookOrders, Continue to approve orders
        req.getRequestDispatcher("/frontController?command=OrdersLookAdmin").forward(req, resp);
    }
}
