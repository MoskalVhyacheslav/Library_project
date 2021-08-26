package commands.admin;

import DAO.BookDAO;
import DAO.DAOFactory;
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
import java.time.LocalDate;

public class AddBookCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddBookCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
        String page;

        try{
            String title = new String(request.getParameter("title")
                    .getBytes("ISO-8859-1"), "UTF-8");
            String author = new String(request.getParameter("author")
                    .getBytes("ISO-8859-1"), "UTF-8");
            String edition = new String(request.getParameter("edition")
                    .getBytes("ISO-8859-1"), "UTF-8");

            LocalDate date = LocalDate.parse(request.getParameter("edition-date"));
            int amount = Integer.parseInt(request.getParameter("amount"));
            int creditDays = Integer.parseInt(request.getParameter("credit-days"));

            BookDAO bookDAO = DAOFactory.getBookDAO();
            bookDAO.insertBook(title,author,edition,date,amount,creditDays);

            page = Path.ALL_BOOKS;
        }catch (DAOException exception){
            LOGGER.error(CommandMessages.ERR_COMMAND_ADD_BOOK);
            throw new LogicException(CommandMessages.ERR_COMMAND_ADD_BOOK,exception);
        }
        return page;
    }
}
