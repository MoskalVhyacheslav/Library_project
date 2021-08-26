package commands.user;

import DAO.DAOFactory;
import DAO.OrderDAO;
import DAO.UserDAO;
import commands.Command;
import entity.Book;
import entity.Order;
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

public class UserOrdersCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(UserOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
        String page;
        List<Order> orderList;
        List<Book> bookList;

        try{
            User user = (User)request.getSession().getAttribute("user");

            OrderDAO orderDAO = DAOFactory.getOrderDAO();
            orderList = (orderDAO.selectOrderByUser(user.getLogin())).getKey();
            bookList = (orderDAO.selectOrderByUser(user.getLogin())).getValue();

            UserDAO userDAO  = DAOFactory.getUserDAO();
            User userForRequest = userDAO.findUserByLogin(user.getLogin());

            request.getSession().setAttribute("user",user);
            request.setAttribute("userOrders", orderList);
            request.getSession().setAttribute("userBooks", bookList);
            page = Path.USER_PAGE;
        }catch (DAOException exception){
            LOGGER.error(CommandMessages.ERR_COMMAND_GET_BOOKS_FOR_USER);
            throw  new LogicException(CommandMessages.ERR_COMMAND_GET_BOOKS_FOR_USER,exception);
        }
        return page;
    }
}
