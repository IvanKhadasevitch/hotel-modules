package by.khadasevich.hotel.filters;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@WebFilter(filterName = "encodingFilter", urlPatterns = "/*",
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8")})
public class EncodingFilter implements Filter {

    private String encoding = "UTF-8";

    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding(encoding);

        response.setContentType("text/html; charset=" + encoding);
        response.setCharacterEncoding(encoding);
        ((HttpServletResponse)response).setHeader("Content-Language", "UTF-8");

        // del after debug !!!
//        System.out.printf("EncodingFilter.doFilter set request.getCharacterEncoding=%s \n", request.getCharacterEncoding());
//        System.out.printf("EncodingFilter.doFilter     request.getContentType=%s  \n", request.getContentType());
//
//        System.out.printf("EncodingFilter.doFilter set response.getCharacterEncoding=%s  \n", response.getCharacterEncoding());
//        System.out.printf("EncodingFilter.doFilter set response.getContentType=%s  \n", response.getContentType());


        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null && !"".equals(encodingParam)) {
            encoding = encodingParam;
        }
    }

    public void destroy() {
    }
}