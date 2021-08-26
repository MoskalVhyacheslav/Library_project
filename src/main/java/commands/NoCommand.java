package commands;

import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(NoCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
        LOGGER.error("Set the request attribute: errorMessage --> " + errorMessage);

        LOGGER.debug("Command finished");
        return Path.ERROR_PAGE;
    }
}
