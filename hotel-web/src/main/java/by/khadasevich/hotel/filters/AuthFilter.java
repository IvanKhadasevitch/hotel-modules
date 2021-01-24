package by.khadasevich.hotel.filters;


import by.khadasevich.hotel.command.enums.CommandType;
import by.khadasevich.hotel.handlers.RequestHandler;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/frontController"})
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        //del after debug
//        System.out.println("start AuthFilter.doFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        CommandType type = RequestHandler.getCommand(req);

        //del after debug
//        System.out.println("AuthFilter.doFilter: CommandType= " + type);

        if (CommandType.ORDERS.equals(type) || CommandType.ORDERS_LOOK.equals(type)  || CommandType.ORDERS_SHOW.equals(type)
                || CommandType.BILLS.equals(type) || CommandType.BILL_PAY.equals(type)) {
            String contextPath = req.getContextPath();
            HttpSession session = req.getSession();

            if((session.getAttribute("user") == null)) {
                //del after debug !!!
//                System.out.println("AuthFilter.doFilter: LogOff user refer to LogIn. user="
//                        + session.getAttribute("user"));

                // LogOff user refer to LogIn
                res.sendRedirect(contextPath + "/frontController?command=LoginUser");
                return;
            }
        }
        if (CommandType.ORDERS_LOOK_ADMIN.equals(type) || CommandType.ORDERS_APPROVE.equals(type)
                || CommandType.BILLS_ADMIN.equals(type)) {
            String contextPath = req.getContextPath();
            HttpSession session = req.getSession();

            if((session.getAttribute("admin") == null)) {
                //del after debug !!!
//                System.out.println("AuthFilter.doFilter: LogOff admin refer to LogIn. admin="
//                        + session.getAttribute("admin"));

                // LogOff admin refer to LogIn
                res.sendRedirect(contextPath + "/frontController?command=LoginAdmin");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
