package filters;

import entity.User;
import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebFilter({""})
public class UserFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(UserFilter.class);
    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        LOGGER.trace("UserFilter is Init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        filterChain.doFilter(servletRequest,servletResponse);
        checkAccess(servletRequest, servletResponse);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!"user".equals(request.getSession().getAttribute("userRole"))) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(Path.INCORRECT_ACCESS);
        } else
            filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.trace("UserFilter is Destroyed!");
    }

    private void checkAccess(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        ServletContext context = req.getSession().getServletContext();

        List<User> blockedUsers = (List<User>) context.getAttribute("blockedUsers");
        User user = (User) servletRequest.getAttribute("user");

        if (blockedUsers.contains(user)) {
            user.setBlocked(true);
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(Path.USER_BLOCKED);
        }
    }
}
