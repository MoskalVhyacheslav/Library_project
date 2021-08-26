package db;

import exceptions.DAOException;
import messages.DAOMessages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
   // private static DataSource dataSource;
    private static ConnectionPool instance=null;
    private static String jdbcUrl;

    private ConnectionPool() {
    }

    public synchronized static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
            System.out.println("Create instance");
        }
        return instance;
    }

    public static Connection getConnection() throws DAOException {
        Connection connection;

        if (System.getProperty("RDS_HOSTNAME") != null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String dbName = System.getProperty("RDS_DB_NAME");
                String userName = System.getProperty("RDS_USERNAME");
                String password = System.getProperty("RDS_PASSWORD");
                String hostname = System.getProperty("RDS_HOSTNAME");
                String port = System.getProperty("RDS_PORT");
                String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user="
                        + userName + "&password=" + password ;

                System.out.println("I load DB"+jdbcUrl);
            }
            catch (ClassNotFoundException e) { LOG.warn(e.toString());}

        }
        try {
            LOG.trace("Getting remote connection with connection string from environment variables.");
            connection = DriverManager.getConnection(jdbcUrl);
            LOG.info("Remote connection successful.");
        } catch (SQLException ex) {
            LOG.error(DAOMessages.ERR_CANNOT_GET_CONNECTION, ex);
            throw new DAOException(DAOMessages.ERR_CANNOT_GET_CONNECTION, ex);
        }
        return connection;
    }

}
