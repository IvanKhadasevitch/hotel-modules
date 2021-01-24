package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.entities.Admin;
import by.khadasevich.hotel.entities.Hotel;
import org.apache.commons.lang3.math.NumberUtils;
import by.khadasevich.hotel.services.AdminService;
import by.khadasevich.hotel.services.HotelService;
import by.khadasevich.hotel.singleton.SingletonBuilder;
import by.khadasevich.hotel.auth.Encoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAdminController implements Controller {
    private AdminService adminService = SingletonBuilder.getInstanceImpl(AdminService.class);
    private HotelService hotelService = SingletonBuilder.getInstanceImpl(HotelService.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // del after debug
//        System.out.println("start LoginAdminController");

        final String DEFAULT = "NotDefine";

        long adminId = NumberUtils.toLong(req.getParameter("adminId"), -1L);
        String adminName = req.getParameter("adminName") == null
                ? DEFAULT
                : new String(req.getParameter("adminName").getBytes("ISO-8859-1"),"UTF-8");
        String adminPass = req.getParameter("adminPass") == null
                ? DEFAULT
                : new String(req.getParameter("adminPass").getBytes("ISO-8859-1"),"UTF-8");

        //validate parameters
        Admin admin = adminService.get(adminId);
        boolean validParam = admin != null
                && admin.getId() == adminId
                && adminName.equals(admin.getName())
//                && adminPass.equals(admin.getPassword());
                && admin.getPassword().equals(Encoder.encode(adminPass));

        if (validParam) {
            // valid parameters set adminLogOn in session
            Hotel hotel = hotelService.get(admin.getHotelId());
            req.getSession().setAttribute("admin", admin);
            req.getSession().setAttribute("hotel", hotel);
            req.getSession().setAttribute("user", null);

            // refer to admin work "/frontController?command=OrdersLookAdmin"
            req.getRequestDispatcher("/frontController?command=OrdersLookAdmin").forward(req, resp);
        } else {
            // invalid  parameters
            if (adminId != -1L || !DEFAULT.equalsIgnoreCase(adminName) || !DEFAULT.equalsIgnoreCase(adminPass)) {
                //save param in request
                req.setAttribute("adminId", adminId);
                req.setAttribute("adminName", adminName);
                req.setAttribute("errorMsg", "Invalid parameters, try again.");
            }
            //refer to try again login MAIN_PAGE
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        }
    }
}
