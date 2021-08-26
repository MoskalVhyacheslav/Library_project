package commands.user;

import DAO.BookDAO;
import DAO.DAOFactory;
import DAO.OrderDAO;
import commands.Command;
import entity.Book;
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

public class AddOrderCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddOrderCommand.class);

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

            List<Book> userBooks = (List<Book>) request.getSession()
                    .getAttribute("userBooks");

            BookDAO bookDAO = DAOFactory.getBookDAO();
            Book book = bookDAO.selectByTitleAndAuthor(title,author);

            OrderDAO orderDAO = DAOFactory.getOrderDAO();
            orderDAO.insertOrder
                    (user.getLogin(), book.getTitle(), book.getAuthor(), book.getCreditDays());

            userBooks.add(book);
            request.getSession().setAttribute("userBooks",userBooks);
            page = Path.ALL_ORDERS;
        } catch (DAOException exception) {
            LOGGER.error(CommandMessages.ERR_COMMAND_ADD_BOOK_FOR_USER);
            throw new LogicException(CommandMessages.ERR_COMMAND_ADD_BOOK_FOR_USER, exception);
        }
        return page;
    }
}
