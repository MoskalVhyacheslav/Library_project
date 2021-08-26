package messages;

public final class CommandMessages {

    private CommandMessages() {
    }

    public static final String ERR_COMMAND_LOG_IN = "Can not Log In user";
    public static final String ERR_CANNOT_SIGN_UP_WITH_SAME= "Sorry,but this login or mail actually exists!" +
            "Please,try another login or email!";
    public static final String ERR_COMMAND_SIGN_UP = "Can not Sign Up user";

    public static final String ERR_COMMAND_FIND_BOOK = "Can not find book";
    public static final String ERR_COMMAND_ADD_BOOK = "Can not find book";
    public static final String ERR_COMMAND_ORDER_BOOKS = "Can not order books";

    public static final String ERR_COMMAND_GET_BOOKS_FOR_USER = "Can not get books for user";
    public static final String ERR_COMMAND_ADD_BOOK_FOR_USER = "Can not add book for user";
    public static final String ERR_COMMAND_DELETE_BOOK_FOR_USER = "Can not delete book for user";
    public static final String ERR_COMMAND_UPDATE_BOOK_FOR_USER = "Can not update book for user";
    public static final String ERR_COMMAND_UPDATE_BEFORE_BOOK_FOR_USER = "Can not before update book for user";
    public static final String ERR_COMMAND_UPDATE_BALANCE_FOR_USER = "Can not update balance for user";

    public static final String ERR_COMMAND_GET_USERS = "Can not get users";
    public static final String ERR_COMMAND_GET_LIBRARIANS = "Can not get librarians";
    public static final String ERR_COMMAND_BLOCK_USER = "Can not block user";
    public static final String ERR_COMMAND_UNBLOCK_USER = "Can not unblock user";
    public static final String ERR_COMMAND_UPDATE_USER = "Can not update user";

    public static final String ERR_COMMAND_GET_ORDERS = "Can not get orders";
    public static final String ERR_COMMAND_PAY_DELETE_ORDER = "Can not pay and delete order";

    public static final String ERR_COMMAND_ADD_LIBRARIAN= "Can not add Librarian with this parameters!";
    public static final String ERR_COMMAND_DELETE_LIBRARIAN= "Can not add librarian";

}
