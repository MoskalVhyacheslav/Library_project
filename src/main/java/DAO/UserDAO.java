package DAO;

import db.ConnectionPool;
import entity.User;
import exceptions.DAOException;
import messages.DAOMessages;
import org.apache.log4j.Logger;
import util.HashingPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAOFactory {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);

    private static final String INSERT_NEW_ACCOUNT = "INSERT INTO users(login,mail,password) " +
            "VALUES (?,?,?)";

    private static final String INSERT_NEW_LIBRARIAN = "INSERT INTO users(login,mail,password,role) " +
            "VALUES (?,?,?,2)";

    private static final String SELECT_AUTHORIZE_ACCOUNT = "SELECT * FROM users " +
            "WHERE (login=? AND password=?)";
    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?  LIMIT 1";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE users_id=?  LIMIT 1";
    private static final String SELECT_USER_ID_BY_LOGIN = "SELECT users_id FROM user " +
            "WHERE login=? LIMIT 1";
    private static final String SELECT_USER_BALANCE_BY_LOGIN = "SELECT balance FROM user " +
            "WHERE login=? AND role=3 LIMIT 1";
    private static final String SELECT_USER_CREDIT_BOOKS_BY_ID = "SELECT * FROM epamproject.orders" +
            " WHERE orders.users_id=? ";
    private static final String SELECT_ONLY_USERS = "SELECT * FROM users WHERE role=3";
    private static final String SELECT_ONLY_LIBRARIAN = "SELECT * FROM users WHERE role=2";

    private static final String SELECT_USER_BLOCKED = "SELECT blocked FROM users WHERE role=1 AND login=?";
    private static final String SELECT_EXISTS_LIBRARIAN = "SELECT EXISTS " +
            "(SELECT 1 FROM users WHERE role=2 AND login=?)";

    private static final String UPDATE_USER_MAIL = "UPDATE  users SET mail=? WHERE login=?";
    private static final String UPDATE_USER_PASSWORD = "UPDATE  users SET password=? WHERE login=?";
    private static final String UPDATE_USER_BALANCE = "UPDATE  users SET balance=balance+? WHERE login=?";
    private static final String UPDATE_USER_CREDIT = "UPDATE  users SET credit=credit-? WHERE login=?";
    private static final String UPDATE_USER_BLOCK = "UPDATE  users SET blocked=true WHERE login=?";
    private static final String UPDATE_USER_UNBLOCK = "UPDATE  users SET blocked=false  WHERE login=?";

    private static final String DELETE_LIBRARIAN = "DELETE FROM users WHERE login=? AND role=2";
    private static final String DELETE_USER = "DELETE FROM users WHERE login=? AND role=1";

    public UserDAO() {
    }

    public void insertNewAccount(String login, String mail, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT_NEW_ACCOUNT);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, mail);
            preparedStatement.setString(3, HashingPassword.hash(password));

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_SIGN_UP_USER);
            throw new DAOException(DAOMessages.ERR_CANNOT_SIGN_UP_USER, exception);
        } finally {
            close(connection, preparedStatement);
        }
    }

    public void insertNewLibrarian(String login, String password, String mail) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT_NEW_LIBRARIAN);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, mail);
            preparedStatement.setString(3, HashingPassword.hash(password));

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            LOGGER.error(DAOMessages.ERR_CANNOT_INSERT_LIBRARIAN);
            throw new DAOException(DAOMessages.ERR_CANNOT_INSERT_LIBRARIAN, exception);
        } finally {
            close(connection, preparedStatement);
        }
    }


    public User authorizeAccount(String login, String password) throws DAOException {
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = null;
        User user = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT_AUTHORIZE_ACCOUNT);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, HashingPassword.hash(password));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setMail(resultSet.getString("mail"));
                user.setBalance(resultSet.getDouble("balance"));
                user.setCredit(resultSet.getDouble("credit"));
                user.setBlocked(resultSet.getBoolean("blocked"));
                user.setRoleId(resultSet.getInt("role"));
            }

            return user;
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_AUTHORIZE_USER);
            throw new DAOException(DAOMessages.ERR_CANNOT_AUTHORIZE_USER, exception);
        } finally {
            close(connection, preparedStatement);
        }
    }


    public boolean librarianExists(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean exists;

        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EXISTS_LIBRARIAN);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            exists = resultSet.getBoolean(1);
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_CHECK_LIBRARIAN_EXISTS);
            throw new DAOException(DAOMessages.ERR_CANNOT_CHECK_LIBRARIAN_EXISTS, exception);
        }
        return exists;
    }

    public boolean userBlocked(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean blocked;

        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BLOCKED);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            blocked = resultSet.getBoolean(1);
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_USER_BLOCKED);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_USER_BLOCKED, exception);
        }
        return blocked;
    }

    public User findUserById(int userId) throws DAOException {
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = null;
        User user = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, userId);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                user = new User();
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setMail(result.getString("mail"));
                user.setBalance(result.getDouble("balance"));
                user.setCredit(result.getDouble("credit"));
                user.setBlocked(result.getBoolean("blocked"));
            }

        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_FIND_USER_BY_ID);
            throw new DAOException(DAOMessages.ERR_CANNOT_FIND_USER_BY_ID, exception);
        } finally {
            close(connection, preparedStatement);
        }
        return user;
    }

    public double selectUserBalance(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BALANCE_BY_LOGIN);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt("balance");
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_GET_USER_BALANCE);
            throw new DAOException();
        } finally {
            close(connection, preparedStatement);
        }
    }

    public User findUserByLogin(String login) throws DAOException {
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = null;
        User user = new User();

        try {
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setMail(result.getString("mail"));
                user.setBalance(result.getDouble("balance"));
                user.setCredit(result.getDouble("credit"));
                user.setBlocked(result.getBoolean("blocked"));
            }

        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_FIND_USER_BY_LOGIN);
            throw new DAOException(DAOMessages.ERR_CANNOT_FIND_USER_BY_LOGIN, exception);
        } finally {
            close(connection, preparedStatement);
        }
        return user;
    }

    private List<User> select(String statement) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementForCreditBooks = null;
        List<User> userList = new ArrayList<>();
        User user;

        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(statement);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                user = new User();
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setMail(result.getString("mail"));
                user.setBalance(result.getDouble("balance"));
                user.setCredit(result.getDouble("credit"));
                user.setBlocked(result.getBoolean("blocked"));

                int userId = findUserId(user.getLogin());
                preparedStatementForCreditBooks = connection.prepareStatement
                        (SELECT_USER_CREDIT_BOOKS_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                preparedStatementForCreditBooks.setInt(1, userId);

                ResultSet resultSet = preparedStatementForCreditBooks.executeQuery();
                resultSet.last();
                user.setCreditBooks(result.getRow());

                userList.add(user);
            }

        } catch (SQLException exception) {
            throw new DAOException(exception);
        } finally {
            close(connection, preparedStatement);
        }
        return userList;
    }

    public int findUserId(String login) throws DAOException {
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement preparedStatement = null;
        int userId;

        try {
            preparedStatement = connection.prepareStatement(SELECT_USER_ID_BY_LOGIN);
            preparedStatement.setString(1, login);

            ResultSet result = preparedStatement.executeQuery();
            result.next();
            userId = result.getInt("user_id");

            return userId;
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_FIND_USER_BY_LOGIN);
            throw new DAOException(DAOMessages.ERR_CANNOT_FIND_USER_BY_LOGIN, exception);
        } finally {
            close(connection, preparedStatement);
        }
    }

    public List<User> selectLibrarians() throws DAOException {
        try {
            return select(SELECT_ONLY_LIBRARIAN);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_LIBRARIANS);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_LIBRARIANS, exception);
        }
    }

    public List<User> selectUsers() throws DAOException {
        try {
            return select(SELECT_ONLY_USERS);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_SELECT_USERS);
            throw new DAOException(DAOMessages.ERR_CANNOT_SELECT_USERS, exception);
        }
    }

    private void update(String login, String parameter, String statement) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, parameter);
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            throw new DAOException(exception);
        } finally {
            close(connection, preparedStatement);
        }
    }


    public void updateUserMail(String login, String mail) throws DAOException {
        try {
            update(login, mail, UPDATE_USER_MAIL);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_UPDATE_USER_MAIL);
            throw new DAOException(DAOMessages.ERR_CANNOT_UPDATE_USER_MAIL, exception);
        }
    }

    public void updateUserPassword(String login, String password) throws DAOException {
        try {
            update(login, password, UPDATE_USER_PASSWORD);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_UPDATE_USER_PASSWORD);
            throw new DAOException(DAOMessages.ERR_CANNOT_UPDATE_USER_PASSWORD, exception);
        }
    }


    public void updateUserBalance(String login, double balance) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE_USER_BALANCE);
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_UPDATE_USER_BALANCE);
            throw new DAOException(DAOMessages.ERR_CANNOT_UPDATE_USER_BALANCE, exception);
        } finally {
            rollback(connection);
            close(connection, preparedStatement);
        }
    }

    public void updateUserCredit(String login, double credit) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE_USER_CREDIT);
            preparedStatement.setDouble(1, credit);
            preparedStatement.setString(2, login);

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_UPDATE_USER_CREDIT);
            throw new DAOException(DAOMessages.ERR_CANNOT_UPDATE_USER_CREDIT, exception);
        } finally {
            rollback(connection);
            close(connection, preparedStatement);
        }
    }

    public void blockUser(String login) throws DAOException {
        try {
            blockUnblockUser(login, UPDATE_USER_BLOCK);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_BLOCK_USER);
            throw new DAOException(DAOMessages.ERR_CANNOT_BLOCK_USER, exception);
        }
    }

    public void unblockUser(String login) throws DAOException {
        try {
            blockUnblockUser(login, UPDATE_USER_UNBLOCK);
        } catch (DAOException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_UNBLOCK_USER);
            throw new DAOException(DAOMessages.ERR_CANNOT_UNBLOCK_USER, exception);
        }
    }

    public void deleteLibrarian(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE_LIBRARIAN);
            preparedStatement.setString(1, login);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_DELETE_LIBRARIAN);
            throw new DAOException(DAOMessages.ERR_CANNOT_DELETE_LIBRARIAN, exception);
        } finally {
            rollback(connection);
            close(connection, preparedStatement);
        }
    }

    public void deleteUser(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setString(1, login);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error(DAOMessages.ERR_CANNOT_DELETE_USER);
            throw new DAOException(DAOMessages.ERR_CANNOT_DELETE_USER, exception);
        } finally {
            rollback(connection);
            close(connection, preparedStatement);
        }
    }

    private void blockUnblockUser(String login, String statement) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, login);

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            throw new DAOException(exception);
        } finally {
            close(connection, preparedStatement);
        }
    }

}
