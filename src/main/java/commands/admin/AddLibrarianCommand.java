package commands.admin;

import DAO.DAOFactory;
import DAO.UserDAO;
import commands.Command;
import exceptions.DAOException;
import exceptions.LogicException;
import messages.CommandMessages;
import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddLibrarianCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddLibrarianCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, LogicException {
        String page;

        try{
            String login =  new String(request.getParameter("login")
                    .getBytes("ISO-8859-1"), "UTF-8");
            String  password = new String(request.getParameter("password")
                    .getBytes("ISO-8859-1"), "UTF-8");
            String mail = new String(request.getParameter("email")
                    .getBytes("ISO-8859-1"), "UTF-8");

            UserDAO userDAO = DAOFactory.getUserDAO();
            userDAO.insertNewLibrarian(login,password,mail);

            page = Path.COMMAND_ALL_LIBRARIANS;
        }catch (DAOException exception){
            LOGGER.error(CommandMessages.ERR_COMMAND_ADD_LIBRARIAN);
            throw new LogicException(CommandMessages.ERR_COMMAND_ADD_LIBRARIAN,exception);
        }
        return page;
    }
}
