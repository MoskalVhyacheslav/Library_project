package messages;

final public class DAOMessages {

    private DAOMessages(){
    }

    //Application DAOMessages
    public static final String ERR_WITH_NAMING= "Can not configure JNDI";
    public static final String ERR_WITH_LOG4J= "Can not configure log4j";

    //SQL DAOMessages
    public static final String ERR_CANNOT_GET_CONNECTION = "Can not obtain a connection from the pool";
    public static final String ERR_CANNOT_CLOSE_CONNECTION = "Can not close a connection from the pool";
    public static final String ERR_CANNOT_CLOSE_STATEMENT= "Can not close a statement from the pool";
    public static final String ERR_CANNOT_ROLLBACK_CONNECTION= "Can not rollback a connection";


    //UserDAO Errors
    public static final String ERR_CANNOT_SIGN_UP_USER = "Can not insert user";
    public static final String ERR_CANNOT_SIGN_UP_WITH_SAME= "Sorry,but this login or mail  actually exists!" +
            "Please,try another login or";
    public static final String ERR_CANNOT_INSERT_LIBRARIAN= "Can not insert librarian";
    public static final String ERR_CANNOT_AUTHORIZE_USER = "Can not authorize  user";
    public static final String ERR_CANNOT_FIND_USER_BY_LOGIN = "Can not find user by login ";
    public static final String ERR_CANNOT_FIND_USER_BY_ID= "Can not find user by id";
    public static final String ERR_CANNOT_GET_USER_BALANCE= "Can not get user balance";

    public static final String ERR_CANNOT_SELECT_USERS= "Can not select users";
    public static final String ERR_CANNOT_SELECT_LIBRARIANS= "Can not select librarians";
    public static final String ERR_CANNOT_CHECK_LIBRARIAN_EXISTS= "Can not check if librarian exists";

    public static final String ERR_CANNOT_UPDATE_USER_MAIL = "Can not update user mail";
    public static final String ERR_CANNOT_UPDATE_USER_PASSWORD= "Can not update user password";
    public static final String ERR_CANNOT_UPDATE_USER_BALANCE= "Can not update user balance";
    public static final String ERR_CANNOT_UPDATE_USER_CREDIT= "Can not update user credit";

    public static final String ERR_CANNOT_BLOCK_USER= "Can not block user";
    public static final String ERR_CANNOT_UNBLOCK_USER= "Can not unblock user";
    public static final String ERR_CANNOT_SELECT_USER_BLOCKED= "Can not unblock user";

    public static final String ERR_CANNOT_DELETE_LIBRARIAN= "Can not delete librarian";
    public static final String ERR_CANNOT_DELETE_USER= "Can not delete user";


    //BookDAO
    public static final String ERR_CANNOT_INSERT_BOOK= "Can not insert book";

    public static final String ERR_CANNOT_UPDATE_BOOK= "Can not update book";

    public static final String ERR_CANNOT_SELECT_BOOK_BY_TITLE = "Can not select book by title";
    public static final String ERR_CANNOT_SELECT_BOOK_BY_ID= "Can not select book by id";
    public static final String ERR_CANNOT_SELECT_BOOK_BY_AUTHOR = "Can not select book by author";
    public static final String ERR_CANNOT_SELECT_BOOK_BY_TITLE_AND_AUTHOR = "Can not select book by title and author";

    public static final String ERR_CANNOT_ORDER_BY_BOOK_BY_TITLE = "Can not order books by title";
    public static final String ERR_CANNOT_ORDER_BY_BOOK_BY_AUTHOR= "Can not order books by author";
    public static final String ERR_CANNOT_ORDER_BY_BOOK_BY_EDITION = "Can not order books by edition";
    public static final String ERR_CANNOT_ORDER_BY_BOOK_BY_EDITION_DATE = "Can not order books by edition date";

    public static final String ERR_CANNOT_DELETE_BOOK= "Can not delete book";


    //OrderDAO
    public static final String ERR_CANNOT_INSERT_ORDER= "Can not insert order";

    public static final String ERR_CANNOT_SELECT_ORDERS_BY_USER = "Can not select orders by user";
    public static final String ERR_CANNOT_SELECT_ORDERS_BY_USER_AND_BOOKS = "Can not select orders by user and book";
    public static final String ERR_CANNOT_SELECT_ALL="Can not select all orders";

    public static final String ERR_CANNOT_FIND_ORDERS_BY_USER= "Can not find order by user";
    public static final String ERR_CANNOT_FIND_ORDERS_BY_BOOK= "Can not find book by book";

    public static final String ERR_CANNOT_UPDATE_ORDER_CREDIT= "Can not update order credit";
    public static final String ERR_CANNOT_UPDATE_ORDER_CREDIT_DATE= "Can not update order credit date";

    public static final String ERR_CANNOT_DELETE_ORDER= "Can not delete order";

}
