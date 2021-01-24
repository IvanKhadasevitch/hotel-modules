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
import java.sql.Date;

public class RegistrationController implements Controller {
    private final UserService userService
            = SingletonBuilder.getInstanceImpl(UserService.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        final String DEFAULT = "NotDefine";

        // del after debug !!!
//        System.out.printf("Start RegistrationController.execute req.getCharacterEncoding=%s \n",
//                req.getCharacterEncoding());
//        System.out.printf("RegistrationController.execute     request.getContentType=%s  \n", req.getContentType());

        String userName = req.getParameter("name") == null
                ? DEFAULT
                : new String(req.getParameter("name")
                                .getBytes("ISO-8859-1"),"UTF-8");
        String userSurName = req.getParameter("surName") == null
                ? DEFAULT
                : new String(req.getParameter("surName")
                                .getBytes("ISO-8859-1"),"UTF-8");
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        Date userBirthDate = req.getParameter("birthDate") == null
                ? new Date((new java.util.Date()).getTime())
                : Date.valueOf(req.getParameter("birthDate")) ;
        String userEmail = req.getParameter("email");

        // del after debug !!!
//        System.out.printf("RegistrationController.execute userName=%s userSurName=%s userBirthDate=%s" +
//                        " userEmail=%s \n", userName, userSurName, userBirthDate, userEmail);

        if (userEmail == null) {
            // first time in Registration form,
            // put errorMsg in response Header
            resp.setHeader("errorMsg", "Invalid Email or Password");

            // refer to main page
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            return;
        }

        //save param in request
        req.setAttribute("userName", userName);
        req.setAttribute("userSurName", userSurName);
        req.setAttribute("userBirthDate", userBirthDate);
        req.setAttribute("userEmail", userEmail);

        if (! userService.validateEmail(userEmail)) {
            // invalid  email refer to try again userRegistration
            // put errorMsg in response Header & request
            resp.setHeader("errorMsg", "Invalid Email or Password");
            req.setAttribute("errorMsg", "Try again. Invalid email: " + userEmail);

            // refer to main page
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            return;
        }

        if (userService.existWithEmail(userEmail)) {
            // del after debug !!!
//            System.out.printf("RegistrationController email=%s exist \n", userEmail);

            // email already exist, put errorMsg in response Header & request
            resp.setHeader("errorMsg", "Invalid Email or Password");
            req.setAttribute("errorMsg", "already exist email: " + userEmail);

            // refer to main page
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
        } else {
            // del after debug !!!
//            System.out.printf("RegistrationController save user in DB and session email=%s \n", userEmail);

            // save user in DB and session
            User user = userService.make(userName, userSurName, userBirthDate, userEmail);

            // del wen user will be use password !!!!!!!!!!
            user.setPassword(DEFAULT);

            // del after debug !!!
//            System.out.printf("RegistrationController.execute, userService.existWithEmail=false, req.getCharacterEncoding=%s \n",
//                    req.getCharacterEncoding());

            user = userService.save(user);
            req.getSession().setAttribute("user", user);
            // del admin from session
            req.getSession().setAttribute("admin", null);

            // refer to booking - делать ордера!!!
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath+ "/frontController?command=orders");
        }
    }
}
