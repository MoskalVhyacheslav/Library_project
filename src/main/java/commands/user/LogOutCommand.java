package commands.user;

import commands.Command;
import exceptions.LogicException;
import listeners.SessionListener;
import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {

        HttpSession session = request.getSession(false);
        if (SessionListener.find(session.getId())!=null) {
            session.invalidate();
        }

        LOGGER.debug("Command finished");
        return Path.HOMEPAGE_PAGE;
    }
}
