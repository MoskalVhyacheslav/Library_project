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

public class OrderBooksCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(OrderBooksCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LogicException {
       String page;
       String orderBy =  request.getParameter("order by");
       List<Book> bookList;

       try{
           BookDAO bookDAO = DAOFactory.getBookDAO();

           if("title".equals(orderBy))
               bookList = bookDAO.orderByTitle();

           else if("author".equals(orderBy))
               bookList = bookDAO.orderByAuthor();

           else if("edition".equals(orderBy))
               bookList = bookDAO.orderByEdition();

           else if("edition-date".equals(orderBy))
               bookList = bookDAO.orderByEditionDate();

           else
               return Path.ALL_BOOKS;


           request.getSession().setAttribute("sortedBooks",bookList);
           page=Path.ALL_BOOKS;
       }catch (DAOException exception){
           LOGGER.error(CommandMessages.ERR_COMMAND_ORDER_BOOKS);
           throw  new LogicException(CommandMessages.ERR_COMMAND_ORDER_BOOKS,exception);
       }
        return page;
    }


}
