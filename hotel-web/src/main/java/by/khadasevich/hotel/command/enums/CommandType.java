package by.khadasevich.hotel.command.enums;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.command.impl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandType {
    LOGIN("loginUser.jsp", "LoginUser", "loginUser.title",
            new LoginUserController()),
    LOGIN_ADMIN("loginAdmin.jsp", "LoginAdmin", "loginAdmin.title",
            new LoginAdminController()),
    LOGOUT(Controller.MAIN_PAGE, "LogoutUser", "logoutUser.title",
            new LogoutController()),
    LOGOUT_ADMIN(Controller.MAIN_PAGE, "LogoutAdmin", "logoutAdmin.title",
            new LogoutController()),
    REGISTRATION("registration.jsp", "Registration", "registration.title",
            new RegistrationController()),
    ORDERS("orders/user/toOrder.jsp", "Orders", "orders.title",
            new OrderController()),
    ORDERS_LOOK("orders/user/main.jsp", "OrdersLook", "ordersLook.title",
            new OrdersLookController()),
    ORDERS_SHOW("orders/user/showOrder.jsp", "OrderShow", "orderShow.title",
            new OrderShowController()),
    ORDERS_LOOK_ADMIN("orders/admin/approve.jsp", "OrdersLookAdmin",
            "ordersLook.title", new OrdersLookAdminController()),
    ORDERS_APPROVE("orders/admin/approve.jsp", "OrdersApprove",
            "ordersApprove.title", new OrdersApproveController()),
    BILLS("bills/main.jsp", "Bills", "bills.title",
            new BillController()),
    BILL_PAY("bills/main.jsp", "BillPay", "bills.title",
            new BillPayController()),
    BILLS_ADMIN("bills/admin/main.jsp", "BillsAdmin", "billsAdmin.title",
            new BillAdminController()),
    HOTELS("hotels/main.jsp", "Hotels", "hotels.title",
        new HotelsController());

    private String pagePath;
    private String pageName;
    private String i18nKey;
    private Controller controller;

    public static CommandType getByPageName(String page) {
        //del after debug
//        System.out.println(String.format("Start CommandType.getByPageName page=%s ",page));

        for (CommandType type : CommandType.values()) {
            if (type.pageName.equalsIgnoreCase(page)) {

                //del after debug
//                System.out.println("CommandType.getByPageName return type.pagePath= " + type.pagePath);

                return type;
            }
        }
// If nothing is found, return to the start page: hotelStart

        //del after debug
//        System.out.println("CommandType.getByPageName return type.pagePath= " + HOTELS);

        return HOTELS;
    }
}
