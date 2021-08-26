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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetUsersCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetUsersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
        String page;
        List<User> userList;

        try{
            UserDAO userDAO = DAOFactory.getUserDAO();
            userList = userDAO.selectUsers();

            request.setAttribute("users",userList);
            page = Path.ALL_USERS;
        }catch (DAOException exception){
            LOGGER.error(CommandMessages.ERR_COMMAND_GET_USERS);
            throw new LogicException(CommandMessages.ERR_COMMAND_GET_USERS,exception);
        }
        return page;
    }
}
