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
import java.util.List;

public class FindBooksCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FindBooksCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
        String page;
        List<Book> bookList;
        Book book;
        try {
            String title = new String(request.getParameter("title")
                    .getBytes("ISO-8859-1"), "UTF-8");
            String author = new String(request.getParameter("author")
                    .getBytes("ISO-8859-1"), "UTF-8");

            BookDAO bookDAO = DAOFactory.getBookDAO();

            if (!title.isEmpty() && !author.isEmpty()) {
                book = bookDAO.selectByTitleAndAuthor(title, author);
                request.getSession().setAttribute("findBooks", book);
            }
            else if (!title.isEmpty()) {
                bookList = bookDAO.selectByTitle(title);
                request.getSession().setAttribute("findBooks", bookList);
            }
            else if (!author.isEmpty()) {
                bookList = bookDAO.selectByAuthor(author);
                request.getSession().setAttribute("findBooks", bookList);
            }

            page = Path.FIND_BOOK_PAGE;
        } catch (DAOException exception) {
            LOGGER.error(CommandMessages.ERR_COMMAND_FIND_BOOK);
            throw new LogicException(CommandMessages.ERR_COMMAND_FIND_BOOK, exception);
        }
        return page;
    }
}
