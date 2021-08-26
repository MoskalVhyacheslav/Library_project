package web;

public final class Path {

    //Command Paths
    public static final String COMMAND_USER_PAGE= "/controller?command=userOrders";
    public static final String COMMAND_ALL_ORDERS= "/controller?command=getAllOrders";
    public static final String COMMAND_ALL_USERS= "/controller?command=getAllUsers";
    public static final String COMMAND_ALL_LIBRARIANS= "/controller?command=getAllLibrarians";

    //Guest
    public static final String LOG_IN_PAGE = "/jsp/LogIn.jsp";
    public static final String SIGN_UP_PAGE = "/jsp/SignUp.jsp";
    public static final String FIND_BOOK_PAGE= "/jsp/FindBook.jsp";
    public static final String HOMEPAGE_PAGE= "/index.jsp";
    public static final String ALL_BOOKS= "/jsp/AllBooks.jsp";
    public static final String ADD_BOOK= "/jsp/AddBook.jsp";

    //User
    public static final String USER_PAGE= "/jsp/UserPage.jsp";

    //Librarian
    public static final String ALL_ORDERS= "/jsp/AllOrders.jsp";
    public static final String ALL_LIBRARIANS= "/jsp/AllLibrarians.jsp";

    //Admin
    public static final String UPDATE_BOOK="/jsp/UpdateBook.jsp";
    public static final String ALL_USERS= "/jsp/AllUsers.jsp";

    //Errors
    public static final String ERROR_PAGE= "/jsp/ErrorPage.jsp";
    public static final String NOT_ENOUGH_MONEY= "/jsp/NotEnoughMoney.jsp";
    public static final String PAGE_NOT_FOUND= "/jsp/PageNotFound.jsp";
    public static final String INCORRECT_ACCESS= "/jsp/IncorrectAccess.jsp";
    public static final String USER_BLOCKED= "/jsp/Blocked.jsp";
}
