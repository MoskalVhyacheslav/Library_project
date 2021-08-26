package commands.book;

import DAO.BookDAO;
import DAO.DAOFactory;
import commands.Command;
import entity.Book;
import exceptions.DAOException;
import exceptions.LogicException;
import messages.CommandMessages;
import org.apache.log4j.Logger;
import web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class UpdateBookCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(UpdateBookCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, LogicException {
        String page;

        try {
            Book book = (Book)request.getSession().getAttribute("updateBook");
            String edition = new String(request.getParameter("edition")
                    .getBytes("ISO-8859-1"), "UTF-8");

            LocalDate date = LocalDate.parse(request.getParameter("edition-date"));
            int amount = Integer.parseInt(request.getParameter("amount"));
            int creditDays = Integer.parseInt(request.getParameter("credit-days"));

            BookDAO bookDAO = DAOFactory.getBookDAO();
            bookDAO.updateBook(book.getTitle(), book.getAuthor(),
                    edition, date, amount, creditDays);

            page = Path.ALL_BOOKS;
        } catch (DAOException exception) {
            LOGGER.error(CommandMessages.ERR_COMMAND_UPDATE_BOOK_FOR_USER);
            throw new LogicException(CommandMessages.ERR_COMMAND_UPDATE_BOOK_FOR_USER);
        }
        return page;
    }
}
