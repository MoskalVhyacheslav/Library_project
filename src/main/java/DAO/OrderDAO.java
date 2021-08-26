package DAO;

import db.ConnectionPool;
import entity.Book;
import entity.Order;
import entity.User;
import exceptions.DAOException;
import javafx.util.Pair;
import messages.DAOMessages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DAOFactory {
    private static final Logger LOGGER = Logger.getLogger(OrderDAO.class);

    private static final String INSERT_ORDER = "INSERT INTO orders " +
            "(user_id,book_id,credit_date) VALUES (?,?,DATE_ADD(NOW(),INTERVAL ? DAY))";

    private static final String UPDATE_CREDIT = "UPDATE orders SET credit=? " +
            "WHERE (user_id=? AND book_id=?)";

    private static final String UPDATE_CREDIT_DATE = "UPDATE orders SET credit_date=? " +
            "WHERE (user_id=? AND book_id=?)";

    private static final String DELETE_ORDER = "DELETE FROM orders WHERE (user_id=? AND book_id=?)";
    private static final String SELECT_ALL_BOOKS_BY_USER = "SELECT* FROM orders WHERE user_id=?";
    private static final String SELECT_ALL_BOOKS_BY_USER_AND_BOOK = "SELECT* FROM orders " +
            "WHERE user_id=? AND book_id=?";
    private static final String SELECT_ALL = "SELECT* FROM orders";

    private static final String FIND_USER_BY_LOGIN = "SELECT *FROM users WHERE login=?";
    private static final String FIND_USER_BY_ID = "SELECT *FROM users WHERE user_id=?";
    private static final String FIND_BOOK_BY_ID = "SELECT *FROM books WHERE book_id=?";
    private static final String FIND_BOOK_BY_TITLE_AND_AUTHOR = "SELECT * FROM books " +
            "WHERE title=? AND author=?";


    public void insertOrder(String login, String title, String author, int creditDays)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatementToOrder = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatementToOrder = connection.prepareStatement(INSERT_ORDER);

            int userId = findUserId(login, connection);
            int bookId = findBookId(title, author, connection);

            preparedStatementToOrder.setInt(1, userId);
            preparedStatementToOrder.setInt(2, bookId);
            preparedStatementToOrder.setInt(3, creditDays);

            preparedStatementToOrder.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_INSERT_ORDER);
            throw new DAOException(DAOMessages.ERR_CANNOT_INSERT_ORDER, exception);
        }
    }

    public Pair<List<Order>, List<Book>> selectOrderByUser(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement statementToOrder = null;
        PreparedStatement statementToBook = null;
        List<Order> ordersList = new ArrayList<>();
        List<Book> booksList = new ArrayList<>();
        Order order;
        Book book;
        int userId;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            //Request to UserDAO
            userId = findUserId(login, connection);

            //Request to OrderDAO
            statementToOrder = connection.prepareStatement(SELECT_ALL_BOOKS_BY_USER);
            statementToOrder.setInt(1, userId);

            ResultSet resultSetToOrder = statementToOrder.executeQuery();

            while (resultSetToOrder.next()) {
                statementToBook = connection.prepareStatement(FIND_BOOK_BY_ID);
                statementToBook.setInt(1, resultSetToOrder.getInt("book_id"));
                ResultSet resultSetToBooks = statementToBook.executeQuery();

                book = new Book();
                if (resultSetToBooks.next()) {
                    book.setTitle(resultSetToBooks.getString("title"));
                    book.setAuthor(resultSetToBooks.getString("author"));
                    book.setEdition(resultSetToBooks.getString("edition"));
                    book.setEditionDate((resultSetToBooks.getDate("edition_date")).toLocalDate());
                    book.setAmount(resultSetToBooks.getInt("amount"));
                    book.setCreditDays(resultSetToBooks.getInt("credit_days"));
                }
                booksList.add(book);

                order = new Order();
                order.setCredit(resultSetToOrder.getDouble("credit"));
                order.setCreditDate((resultSetToOrder.getDate("credit_date")).toLocalDate());
                order.setBook(book);
                ordersList.add(order);
            }

            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_ORDERS_BY_USER);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_ORDERS_BY_USER, exception);
        } finally {
            close(connection, statementToBook);
            close(connection, statementToOrder);
        }
        return new Pair<>(ordersList, booksList);
    }

    public List<Order> selectAll() throws DAOException {
        Connection connection = null;
        PreparedStatement statementToBooks = null;
        PreparedStatement statementToUsers = null;
        PreparedStatement statementToOrders = null;
        List<Order> result = new ArrayList<>();
        Order order = null;
        User user = null;
        Book book = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            //Get all values from Orders
            statementToOrders = connection.prepareStatement(SELECT_ALL);
            statementToBooks = connection.prepareStatement(FIND_BOOK_BY_ID);
            statementToUsers = connection.prepareStatement(FIND_USER_BY_ID);
            ResultSet resultAll = statementToOrders.executeQuery();

            while (resultAll.next()) {
                order = new Order();

                //bookId
                statementToBooks.setInt(1, resultAll.getInt("book_id"));
                //userId
                statementToUsers.setInt(1, resultAll.getInt("user_id"));

                ResultSet resultSetUsers = statementToUsers.executeQuery();
                ResultSet resultSetBooks = statementToBooks.executeQuery();

                //Add User
                user = new User();
                if (resultSetUsers.next()) {
                    user.setLogin(resultSetUsers.getString("login"));
                    user.setPassword(resultSetUsers.getString("password"));
                    user.setMail(resultSetUsers.getString("mail"));
                    user.setBalance(resultSetUsers.getDouble("balance"));
                    user.setCredit(resultSetUsers.getDouble("credit"));
                    user.setBlocked(resultSetUsers.getBoolean("blocked"));
                }
                order.setUser(user);

                //Add Book
                book = new Book();
                if (resultSetBooks.next()) {
                    book.setTitle(resultSetBooks.getString("title"));
                    book.setAuthor(resultSetBooks.getString("author"));
                    book.setEdition(resultSetBooks.getString("edition"));
                    book.setEditionDate((resultSetBooks.getDate("edition_date")).toLocalDate());
                    book.setAmount(resultSetBooks.getInt("amount"));
                    book.setCreditDays(resultSetBooks.getInt("credit_days"));
                }
                order.setBook(book);

                order.setCredit(resultAll.getDouble("credit"));
                order.setCreditDate((resultAll.getDate("credit_date").toLocalDate()));

                result.add(order);
            }

            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_ALL);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_ALL, exception);
        } finally {
            close(connection, statementToOrders);
            close(statementToUsers);
            close(statementToBooks);
        }
        return result;
    }

    public Order selectOrderByBookAndUser(String login, String title, String author) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement statementToBooks = null;
        PreparedStatement statementToUsers = null;
        Order order = new Order();
        User user;
        Book book;

        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS_BY_USER_AND_BOOK);

            int userId = findUserId(login, connection);
            int bookId = findBookId(title, author, connection);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                order = new Order();

                statementToBooks = connection.prepareStatement(FIND_BOOK_BY_ID);
                statementToUsers = connection.prepareStatement(FIND_USER_BY_ID);
                statementToBooks.setInt(1, bookId);
                statementToUsers.setInt(1, userId);

                ResultSet resultSetUsers = statementToUsers.executeQuery();
                ResultSet resultSetBooks = statementToBooks.executeQuery();

                //Add User
                user = new User();
                if (resultSetUsers.next()) {
                    user.setLogin(resultSetUsers.getString("login"));
                    user.setPassword(resultSetUsers.getString("password"));
                    user.setMail(resultSetUsers.getString("mail"));
                    user.setBalance(resultSetUsers.getDouble("balance"));
                    user.setCredit(resultSetUsers.getDouble("credit"));
                    user.setBlocked(resultSetUsers.getBoolean("blocked"));
                }
                order.setUser(user);

                //Add Book
                book = new Book();
                if (resultSetBooks.next()) {
                    book.setTitle(resultSetBooks.getString("title"));
                    book.setAuthor(resultSetBooks.getString("author"));
                    book.setEdition(resultSetBooks.getString("edition"));
                    book.setEditionDate((resultSetBooks.getDate("edition_date")).toLocalDate());
                    book.setAmount(resultSetBooks.getInt("amount"));
                    book.setCreditDays(resultSetBooks.getInt("credit_days"));
                }
                order.setBook(book);

                order.setCredit(resultSet.getDouble("credit"));
                order.setCreditDate((resultSet.getDate("credit_date").toLocalDate()));
            }
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_ORDERS_BY_USER_AND_BOOKS);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_ORDERS_BY_USER_AND_BOOKS, exception);
        } finally {
            close(connection, preparedStatement);
            close(statementToBooks);
            close(statementToUsers);
        }
        return order;
    }

    public void updateCredit(int credit, String login, String title, String author) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int userId;
        int bookId;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            userId = findUserId(login, connection);
            bookId = findBookId(title, author, connection);

            preparedStatement = connection.prepareStatement(UPDATE_CREDIT);
            preparedStatement.setInt(1, credit);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, bookId);

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_UPDATE_ORDER_CREDIT);
            throw new DAOException(DAOMessages.ERR_CANNOT_UPDATE_ORDER_CREDIT, exception);
        } finally {
            close(connection, preparedStatement);
        }
    }


    public void updateCreditDate(LocalDate creditDate, String login, String title, String author) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int userId;
        int bookId;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            userId = findUserId(login, connection);
            bookId = findBookId(title, author, connection);

            preparedStatement = connection.prepareStatement(UPDATE_CREDIT_DATE);
            preparedStatement.setDate(1, Date.valueOf(creditDate));
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, bookId);

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_UPDATE_ORDER_CREDIT_DATE);
            throw new DAOException(DAOMessages.ERR_CANNOT_UPDATE_ORDER_CREDIT_DATE, exception);
        } finally {
            close(connection, preparedStatement);
        }
    }


    public void deleteOrder(String login, String title, String author) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int userId;
        int bookId;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            userId = findUserId(login, connection);
            bookId = findBookId(title, author, connection);

            preparedStatement = connection.prepareStatement(DELETE_ORDER);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_DELETE_ORDER);
            throw new DAOException(DAOMessages.ERR_CANNOT_DELETE_ORDER, exception);
        } finally {
            close(connection, preparedStatement);
        }
    }

    private int findUserId(String login, Connection connection) throws DAOException {
        PreparedStatement preparedStatement = null;
        int userId;

        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSetToUser = preparedStatement.executeQuery();

            resultSetToUser.next();
            userId = resultSetToUser.getInt("user_id");

            return userId;
        } catch (SQLException exception) {
            throw new DAOException(DAOMessages.ERR_CANNOT_FIND_ORDERS_BY_USER, exception);
        } finally {
            close(preparedStatement);
        }
    }


    private int findBookId(String title, String author, Connection connection) throws DAOException {
        PreparedStatement preparedStatement;
        int bookId;

        try {
            preparedStatement = connection.prepareStatement(FIND_BOOK_BY_TITLE_AND_AUTHOR);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);

            ResultSet resultSetToUser = preparedStatement.executeQuery();
            resultSetToUser.next();
            bookId = resultSetToUser.getInt("book_id");

            return bookId;
        } catch (SQLException exception) {
            throw new DAOException(DAOMessages.ERR_CANNOT_FIND_ORDERS_BY_BOOK, exception);
        }
    }

}
