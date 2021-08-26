package commands.user;

import DAO.DAOFactory;
import DAO.UserDAO;
import commands.Command;
import entity.User;
import exceptions.DAOException;
import exceptions.LogicException;
import messages.CommandMessages;
import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(UpdateUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, LogicException {
        String page;

        try {
            String email = new String(request.getParameter("email")
                    .getBytes("ISO-8859-1"), "UTF-8");
            User user = (User) request.getSession().getAttribute("user");

            UserDAO userDAO = DAOFactory.getUserDAO();
            userDAO.updateUserMail(user.getLogin(), email);

            user.setMail(email);
            request.getSession().setAttribute("user",user);
            page = Path.COMMAND_USER_PAGE;
        } catch (DAOException exception) {
            LOGGER.error(CommandMessages.ERR_COMMAND_UPDATE_USER);
            throw new LogicException(CommandMessages.ERR_COMMAND_UPDATE_USER, exception);
        }
        return page;
    }
}
