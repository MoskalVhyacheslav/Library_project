package commands.guest;

import DAO.DAOFactory;
import DAO.UserDAO;
import commands.Command;
import entity.Role;
import entity.User;
import exceptions.DAOException;
import exceptions.LogicException;
import messages.CommandMessages;
import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogInCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogInCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
        String page = Path.ERROR_PAGE;

        try {
            String login = new String(request.getParameter("login")
                    .getBytes("ISO-8859-1"), "UTF-8");
            String password = new String(request.getParameter("password")
                    .getBytes("ISO-8859-1"), "UTF-8");

            UserDAO userDAO = DAOFactory.getUserDAO();
            User authorizeUser = userDAO.authorizeAccount(login, password);

            if (authorizeUser == null) {
                LOGGER.trace("User don't authorizate!");
                throw new LogicException("Can not find user with such login/password");
            } else if (authorizeUser.isBlocked()==true)
                return Path.USER_BLOCKED;

            Role userRole = Role.getRole(authorizeUser);
            HttpSession session = request.getSession();

            if (userRole.equals(Role.USER)) {
                session.setAttribute("user", authorizeUser);
                session.setAttribute("userRole", Role.getRole(authorizeUser).toString().toLowerCase());
                page = Path.COMMAND_USER_PAGE;
            } else if (userRole.equals(Role.LIBRARIAN)) {
                session.setAttribute("user", authorizeUser);
                session.setAttribute("userRole", Role.getRole(authorizeUser).toString().toLowerCase());
                page = Path.COMMAND_ALL_ORDERS;
            } else if (userRole.equals(Role.ADMIN)) {
                session.setAttribute("user", authorizeUser);
                session.setAttribute("userRole", Role.getRole(authorizeUser).toString().toLowerCase());
                page = Path.COMMAND_ALL_USERS;
            }

            return page;
        } catch (DAOException exception) {
            LOGGER.error(CommandMessages.ERR_COMMAND_LOG_IN);
            throw new LogicException(CommandMessages.ERR_COMMAND_LOG_IN, exception);
        }
    }
}
