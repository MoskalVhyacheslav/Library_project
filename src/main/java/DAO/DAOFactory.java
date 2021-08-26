package DAO;

import exceptions.DAOException;
import messages.DAOMessages;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DAOFactory {
    private static final Logger LOGGER = Logger.getLogger(DAOFactory.class);

    public static UserDAO getUserDAO() {
        return new UserDAO();
    }

    public static BookDAO getBookDAO() {
        return new BookDAO();
    }

    public static OrderDAO getOrderDAO() {
        return new OrderDAO();
    }

    protected static void close(Connection connection, Statement statement) throws DAOException {
        try {
            if (connection != null)
                connection.close();
            if (statement != null)
                statement.close();
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_CLOSE_CONNECTION);
            throw new DAOException(DAOMessages.ERR_CANNOT_CLOSE_CONNECTION, exception);
        }
    }

    protected static void close(PreparedStatement statement) throws DAOException {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_CLOSE_STATEMENT);
            throw new DAOException(DAOMessages.ERR_CANNOT_CLOSE_STATEMENT, exception);
        }
    }

    protected static void rollback(Connection connection) throws DAOException {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_ROLLBACK_CONNECTION);
            throw new DAOException(DAOMessages.ERR_CANNOT_ROLLBACK_CONNECTION, exception);
        }
    }

    protected static boolean isInteger(String parameter) {
        boolean amIValid = false;
        try {
            Integer.parseInt(parameter);
            amIValid = true;
        } catch (NumberFormatException e) {
            amIValid = false;
        }
        return amIValid;
    }

    protected static boolean isLocalDate(String parameter) {
        DateTimeFormatter dfs = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        boolean amIValid = false;
        try {
            LocalDate.parse(parameter);
            return true;
        } catch (UnsupportedTemporalTypeException e) {
            return false;
        }
    }

    protected static boolean isBoolean(String parameter) {
        boolean amIValid = false;
        try {
            Boolean.parseBoolean(parameter);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
