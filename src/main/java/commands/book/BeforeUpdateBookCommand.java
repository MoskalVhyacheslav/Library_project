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

public class BeforeUpdateBookCommand implements Command {
    private static  final Logger LOGGER = Logger.getLogger(BeforeUpdateBookCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
        String page;

        try{
            String title = new String(request.getParameter("title")
                    .getBytes("ISO-8859-1"), "UTF-8");
            String author = new String(request.getParameter("author")
                    .getBytes("ISO-8859-1"), "UTF-8");

            BookDAO bookDAO = DAOFactory.getBookDAO();
            Book book = bookDAO.selectByTitleAndAuthor(title,author);
            request.getSession().setAttribute("updateBook",book);

            page= Path.UPDATE_BOOK;
        }catch (DAOException exception){
            LOGGER.error(CommandMessages.ERR_COMMAND_UPDATE_BEFORE_BOOK_FOR_USER);
            throw new LogicException(CommandMessages.ERR_COMMAND_UPDATE_BEFORE_BOOK_FOR_USER,exception);
        }
        return page;
    }
}
