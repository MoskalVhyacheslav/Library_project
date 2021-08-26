package commands.librarian;

import DAO.DAOFactory;
import DAO.OrderDAO;
import commands.Command;
import entity.Order;
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

public class GetOrdersCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
        String page;
        List<Order> orders;

        try {
            OrderDAO orderDAO = DAOFactory.getOrderDAO();
            orders = orderDAO.selectAll();

            request.setAttribute("orders", orders);
            page = Path.ALL_ORDERS;
        } catch (DAOException exception) {
            LOGGER.error(CommandMessages.ERR_COMMAND_GET_ORDERS);
            throw new LogicException(CommandMessages.ERR_COMMAND_GET_ORDERS, exception);
        }
        return page;
    }
}
