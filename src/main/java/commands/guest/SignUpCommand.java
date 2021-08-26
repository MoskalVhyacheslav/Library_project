package commands.guest;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignUpCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SignUpCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {

        String page;
        String login;
        String email;
        String password;

        try {
            login = new String(request.getParameter("login")
                    .getBytes("ISO-8859-1"), "UTF-8");
            email = new String(request.getParameter("email")
                    .getBytes("ISO-8859-1"), "UTF-8");
            password = new String(request.getParameter("password")
                    .getBytes("ISO-8859-1"), "UTF-8");

            UserDAO userDAO = DAOFactory.getUserDAO();
            userDAO.insertNewAccount(login, email,password);

            User user = userDAO.findUserByLogin(login);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            session.setAttribute("userRole","user");
            page = Path.COMMAND_USER_PAGE;
        } catch (DAOException exception) {
            LOGGER.error(CommandMessages.ERR_CANNOT_SIGN_UP_WITH_SAME);
            throw new LogicException(CommandMessages.ERR_CANNOT_SIGN_UP_WITH_SAME, exception);
        }
        return page;
    }

}
