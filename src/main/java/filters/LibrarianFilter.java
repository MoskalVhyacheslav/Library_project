package filters;


import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebListener("/jsp/AllOrders.jsp")
public class LibrarianFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(LibrarianFilter.class);
    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        LOGGER.trace("Librarian Filter Init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest)servletRequest;

        if(!"librarian".equals(request.getSession().getAttribute("userRole"))){
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(Path.INCORRECT_ACCESS);
        }
        else
            filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.trace("Librarian Filter Destroyed!");
    }
}
