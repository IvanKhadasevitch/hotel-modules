package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutController implements Controller {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        //del after debug!
//        System.out.println("start LogoutController, refer to: " + MAIN_PAGE);

        // invalidate this session,  then unbinds any objects bound to it. Refer to MAIN_PAGE
        req.getSession().invalidate();
        req.getRequestDispatcher("/frontController?command=hotels").forward(req, resp);
//        req.getServletContext().getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }
}
