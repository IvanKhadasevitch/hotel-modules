package by.khadasevich.hotel.command.impl;

import by.khadasevich.hotel.command.Controller;
import by.khadasevich.hotel.entities.User;
import by.khadasevich.hotel.services.UserService;
import by.khadasevich.hotel.singleton.SingletonBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginUserController implements Controller {
    private UserService userService = SingletonBuilder.getInstanceImpl(UserService.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        // del after debug !!!
//        System.out.println("Start LoginUserController");


        String email = req.getParameter("email");
//        String password = req.getParameter("password");
        if (email == null
//                || password==null
                ) {
            // put errorMsg in response Header
            resp.setHeader("errorMsg", "Invalid Login or Password");

            // refer to main page
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            return;
        }

        if (! userService.validateEmail(email)) {
            // invalid  email refer to try again userRegistration
            // put errorMsg in response Header & request
            req.setAttribute("errorMsg", "Try again. Invalid email: " + email);

            // refer to main page
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            return;
        }

        User user = userService.get(email);
        if (user != null
//                && user.getPassword().equals(Encoder.encode(password))
                ) {
//        if (user != null && password.equals(user.getPassword())) {
            // save user in session
            req.getSession().setAttribute("user", user);
            // del admin from session
            req.getSession().setAttribute("admin", null);

            // refer to booking - делать ордера!!!
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath+ "/frontController?command=orders");
        } else {
            // No email in DB, refer to try again userRegistration
            // put errorMsg in response Header & request
            resp.setHeader("errorMsg", "Invalid Login or Password");
            req.setAttribute("errorMsg", "Try again. Invalid email: " + email);

            // refer to main page
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
        }
    }

}
