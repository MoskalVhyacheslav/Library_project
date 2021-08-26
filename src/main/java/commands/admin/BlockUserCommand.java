package commands.admin;

import DAO.DAOFactory;
import DAO.UserDAO;
import commands.Command;
import entity.User;
import exceptions.DAOException;
import exceptions.LogicException;
import messages.CommandMessages;
import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BlockUserCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(BlockUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
        String page;
        List<User> userList;

        try {
            String login= new String(request.getParameter("login")
                    .getBytes("ISO-8859-1"), "UTF-8");
            ServletContext context = request.getSession().getServletContext();
            userList = (List<User>)context.getAttribute("blockedUsers");

            UserDAO userDAO = DAOFactory.getUserDAO();
            User user =userDAO.findUserByLogin(login);
            userDAO.blockUser(login);

            userList.add(user);
            context.setAttribute("blockedUsers",userList);
            page = Path.ALL_USERS;
        } catch (DAOException exception) {
            LOGGER.error(CommandMessages.ERR_COMMAND_BLOCK_USER);
            throw new LogicException(CommandMessages.ERR_COMMAND_BLOCK_USER,exception);
        }
        return page;
    }
}
