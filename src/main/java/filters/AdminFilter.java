package filters;

import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/jsp/AllLibrarians.jsp","/jsp/AllOrders.jsp","/jsp/AllUsers.jsp",
        "/jsp/UpdateBook.jsp","/jsp/AddBook.jsp","/jsp/AddLibrarian.jsp"})
public class AdminFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AdminFilter.class);
    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig  = filterConfig;
        LOGGER.trace("Admin Filter Init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest)servletRequest;

        LOGGER.trace("User Role -> "+request.getSession().getAttribute("userRole"));

        if(!"admin".equals(request.getSession().getAttribute("userRole"))){
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(Path.INCORRECT_ACCESS);
        }
        else
            filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
        LOGGER.trace("Admin Filter Destroyed!");
    }

}
