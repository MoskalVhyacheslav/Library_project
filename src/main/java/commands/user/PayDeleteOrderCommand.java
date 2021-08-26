package commands.user;

import DAO.DAOFactory;
import DAO.OrderDAO;
import DAO.UserDAO;
import commands.Command;
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

public class PayDeleteOrderCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(PayDeleteOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
        String page;

        try {
            User user = (User) request.getSession().getAttribute("user");
            String title = new String(request.getParameter("title")
                    .getBytes("ISO-8859-1"), "UTF-8");
            String author = new String(request.getParameter("author")
                    .getBytes("ISO-8859-1"), "UTF-8");

            OrderDAO orderDAO = DAOFactory.getOrderDAO();
            Order order = orderDAO.selectOrderByBookAndUser(user.getLogin(), title, author);

            if (order.getCredit() > 0) {
                UserDAO userDAO = DAOFactory.getUserDAO();
                double balance = userDAO.selectUserBalance(user.getLogin());
                double credit = order.getCredit();

                if (balance < credit) {
                    page = Path.NOT_ENOUGH_MONEY;
                    return page;
                } else {
                    user.setBalance(balance -=credit);
                    userDAO.updateUserBalance(user.getLogin(), balance);
                    userDAO.updateUserCredit(user.getLogin(),credit);
                }
            }

            orderDAO.deleteOrder(user.getLogin(), order.getBook().getTitle()
                    ,order.getBook().getAuthor());

            page = Path.COMMAND_USER_PAGE;
        } catch (DAOException exception) {
            LOGGER.error(CommandMessages.ERR_COMMAND_PAY_DELETE_ORDER);
            throw new LogicException(CommandMessages.ERR_COMMAND_PAY_DELETE_ORDER, exception);
        }
        return page;
    }
}

