package DAO;

import db.ConnectionPool;
import entity.Book;
import exceptions.DAOException;
import messages.DAOMessages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends DAOFactory {
    private static final Logger LOGGER = Logger.getLogger(BookDAO.class);

    private static final String INSERT_BOOK = "INSERT INTO books " +
            "(title,author,edition,edition_date,credit_days,amount) VALUES (?,?,?,?,?,?)";

    private static final String UPDATE_BOOK = "UPDATE  books SET edition=?,edition_date=?,amount=?," +
            "credit_days=?  WHERE (title=? AND author=?)";

    private static final String SELECT_All_RANDOM = "SELECT * FROM books ORDER BY RAND()";
    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM books WHERE user_id=?";
    private static final String SELECT_AUTHOR = "SELECT * FROM books WHERE author =?";
    private static final String SELECT_TITLE = "SELECT * FROM books WHERE title  =?";
    private static final String SELECT_TITLE_AND_AUTHOR = "SELECT * FROM books " +
            "WHERE title =? AND author=? LIMIT 1";

    private static final String SELECT_SORTED_TITLE = "SELECT * FROM books ORDER BY title";
    private static final String SELECT_SORTED_AUTHOR = "SELECT * FROM books ORDER BY author";
    private static final String SELECT_SORTED_EDITION = "SELECT * FROM books ORDER BY edition";
    private static final String SELECT_SORTED_EDITION_DATE = "SELECT * FROM books ORDER BY edition_date";

    private static final String DELETE_BOOK = "DELETE FROM books WHERE (title=? AND author=?)";

    public void insertBook(String title, String author, String edition, LocalDate editionDate,
                           int creditDays, int amount) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT_BOOK);
            Date date = Date.valueOf(editionDate);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, edition);
            preparedStatement.setDate(4, date);
            preparedStatement.setInt(5, creditDays);
            preparedStatement.setInt(6, amount);

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_INSERT_BOOK);
            throw new DAOException(DAOMessages.ERR_CANNOT_INSERT_BOOK, exception);
        } finally {
            rollback(connection);
            close(connection, preparedStatement);
        }
    }

    private List<Book> orderBy(String parameter) throws DAOException {
        Connection connection = null;
        Statement statement = null;
        List<Book> booksList = new ArrayList<>();
        Book book = null;

        try {
            connection = ConnectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(parameter);

            while (resultSet.next()) {
                book = new Book();
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setEdition(resultSet.getString("edition"));
                book.setEditionDate((resultSet.getDate("edition_date")).toLocalDate());
                book.setAmount(resultSet.getInt("amount"));
                book.setCreditDays(resultSet.getInt("credit_days"));
                booksList.add(book);
            }
        } catch (SQLException exception) {
            throw new DAOException(exception);
        } finally {
            close(connection, statement);
        }
        return booksList;
    }

    public List<Book> orderByTitle() throws DAOException {
        try {
            return orderBy(SELECT_SORTED_TITLE);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_ORDER_BY_BOOK_BY_TITLE);
            throw new DAOException(DAOMessages.ERR_CANNOT_ORDER_BY_BOOK_BY_TITLE, exception);
        }
    }

    public List<Book> orderByAuthor() throws DAOException {
        try {
            return orderBy(SELECT_SORTED_AUTHOR);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_ORDER_BY_BOOK_BY_AUTHOR);
            throw new DAOException(DAOMessages.ERR_CANNOT_ORDER_BY_BOOK_BY_AUTHOR, exception);
        }
    }

    public List<Book> orderByEdition() throws DAOException {
        try {
            return orderBy(SELECT_SORTED_EDITION);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_ORDER_BY_BOOK_BY_EDITION);
            throw new DAOException(DAOMessages.ERR_CANNOT_ORDER_BY_BOOK_BY_EDITION, exception);
        }
    }

    public List<Book> orderByEditionDate() throws DAOException {
        try {
            return orderBy(SELECT_SORTED_EDITION_DATE);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_ORDER_BY_BOOK_BY_EDITION_DATE);
            throw new DAOException
                    (DAOMessages.ERR_CANNOT_ORDER_BY_BOOK_BY_EDITION_DATE, exception);
        }
    }

    public void updateBook(String title, String author, String edition, LocalDate date, int amount, int creditDays)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE_BOOK);

            preparedStatement.setString(1, edition);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setInt(3, amount);
            preparedStatement.setInt(4, creditDays);

            preparedStatement.setString(5, title);
            preparedStatement.setString(6, author);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_UPDATE_BOOK);
            throw new DAOException(DAOMessages.ERR_CANNOT_UPDATE_BOOK, exception);
        } finally {
            close(connection, preparedStatement);
        }

    }

    private List<Book> select(String parameter, String statement) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Book> books = new ArrayList<>();
        Book book = null;

        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, parameter);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                book = new Book();
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setEdition(rs.getString("edition"));
                book.setEditionDate((rs.getDate("edition_date")).toLocalDate());
                book.setAmount(rs.getInt("amount"));
                book.setCreditDays(rs.getInt("credit_days"));
                books.add(book);
            }

            return books;
        } catch (SQLException exception) {
            throw new DAOException(exception);
        } finally {
            close(connection, preparedStatement);
        }
    }

    public List<Book> selectByTitle(String title) throws DAOException {
        try {
            return select(title, SELECT_TITLE);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_BOOK_BY_TITLE);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_BOOK_BY_TITLE, exception);
        }
    }

    public List<Book> selectByAuthor(String author) throws DAOException {
        try {
            return select(author, SELECT_AUTHOR);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_BOOK_BY_AUTHOR);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_BOOK_BY_AUTHOR, exception);
        }
    }


    public Book selectBookById(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Book book = null;

        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setEdition(rs.getString("edition"));
                book.setEditionDate((rs.getDate("edition_date")).toLocalDate());
                book.setAmount(rs.getInt("amount"));
                book.setCreditDays(rs.getInt("credit_days"));
            }

            return book;
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_BOOK_BY_ID);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_BOOK_BY_ID, exception);
        } finally {
            close(connection, preparedStatement);
        }
    }

    public Book selectByTitleAndAuthor(String title, String author) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Book book = null;

        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_TITLE_AND_AUTHOR);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setEdition(rs.getString("edition"));
                book.setEditionDate((rs.getDate("edition_date")).toLocalDate());
                book.setAmount(rs.getInt("amount"));
                book.setCreditDays(rs.getInt("credit_days"));
            }


        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_BOOK_BY_TITLE_AND_AUTHOR);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_BOOK_BY_TITLE_AND_AUTHOR, exception);
        } finally {
            close(connection, preparedStatement);
        }
        return book;
    }

    public void deleteBook(String title, String author) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE_BOOK);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);

            preparedStatement.executeQuery().next();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_DELETE_BOOK);
            throw new DAOException(DAOMessages.ERR_CANNOT_DELETE_BOOK, exception);
        } finally {
            close(connection, preparedStatement);
        }
    }

}
