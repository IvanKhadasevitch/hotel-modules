package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderShowController implements Controller {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // del after debug
//        System.out.println("start: OrderShowController");

        // show order, refer to MAIN_PAGE
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);

    }
}
