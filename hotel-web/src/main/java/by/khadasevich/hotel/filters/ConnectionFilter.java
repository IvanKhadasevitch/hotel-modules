package by.khadasevich.hotel.filters;

import by.khadasevich.hotel.db.ConnectionManager;
import by.khadasevich.hotel.db.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(urlPatterns = {"/frontController"})
public class ConnectionFilter implements Filter {
    private static Logger logger = LogManager.getLogger(ConnectionFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (ConnectionManager.tl.get() != null) {
            try {
                ConnectionManager.tl.get().close();
                ConnectionManager.tl.set(null);
            } catch (SQLException e) {

            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
