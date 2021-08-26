package listeners;

import db.ConnectionPool;
import entity.User;
import messages.DAOMessages;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class ApplicationListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(ApplicationListener.class);
    private List<User> blockedUsers = new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("blockedUsers", blockedUsers);
        initLog4J(servletContext);
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        LOG.trace("ServletContext destroyed");
    }


    private void initLog4J(ServletContext servletContext) {
        LOG.trace("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
            LOG.debug("Log4j has been initialized");
        } catch (Exception exception) {
            LOG.trace(DAOMessages.ERR_WITH_LOG4J);
        }
        LOG.trace("Log4J initialization finished");
    }

}
