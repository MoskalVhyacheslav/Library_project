package filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class CharsetFilter implements Filter {
    private static final Logger LOGGER =Logger.getLogger(CharsetFilter.class);
    private FilterConfig filterConfig = null;

    public void init(FilterConfig config)
            throws ServletException {
        this.filterConfig = config;
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain next)
            throws IOException, ServletException {

        String encoding = request.getCharacterEncoding();
        System.out.println(encoding);

        if (!"UTF-8".equalsIgnoreCase(encoding))
            response.setCharacterEncoding("UTF-8");

        next.doFilter(request, response);
    }

    public void destroy() {
    }
}
