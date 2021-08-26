package web;

import commands.Command;
import commands.NoCommand;
import commands.admin.*;
import commands.book.BeforeUpdateBookCommand;
import commands.book.FindBooksCommand;
import commands.book.OrderBooksCommand;
import commands.book.UpdateBookCommand;
import commands.guest.LogInCommand;
import commands.guest.SignUpCommand;
import commands.librarian.GetOrdersCommand;
import commands.user.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandController {
    private static final Logger LOG = Logger.getLogger(CommandController.class);
    private static final Map<String, Command> commandKeeper = new HashMap<>();

    private CommandController() {
    }

    static {
        //For guest
        commandKeeper.put("signUp", new SignUpCommand());
        commandKeeper.put("logIn", new LogInCommand());
        commandKeeper.put("logOut", new LogOutCommand());

        //User
        commandKeeper.put("userOrders", new UserOrdersCommand());
        commandKeeper.put("addOrder", new AddOrderCommand());
        commandKeeper.put("payDeleteOrder", new PayDeleteOrderCommand());
        commandKeeper.put("updateBalance", new ChangeBalanceCommand());
        commandKeeper.put("updateUser", new UpdateUserCommand());

        //Books
        commandKeeper.put("findBook", new FindBooksCommand());
        commandKeeper.put("sortBooks", new OrderBooksCommand());
        commandKeeper.put("addBook", new AddBookCommand());
        commandKeeper.put("updateBook", new UpdateBookCommand());
        commandKeeper.put("beforeUpdateBook", new BeforeUpdateBookCommand());

        //For Admin and Librarian
        commandKeeper.put("getAllOrders", new GetOrdersCommand());
        commandKeeper.put("getAllUsers", new GetUsersCommand());
        commandKeeper.put("getAllLibrarians", new GetLibrariansCommand());
        commandKeeper.put("addLibrarian", new AddLibrarianCommand());
        commandKeeper.put("deleteLibrarian", new DeleteLibrarianCommand());
        commandKeeper.put("blockUser", new BlockUserCommand());
        commandKeeper.put("unblockUser", new UnblockUserCommand());
    }


    public static Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");

        LOG.trace("Command value ->" + action);
        Command command = commandKeeper.get(action);
        if (command == null || !commandKeeper.containsKey(action)) {
            command = new NoCommand();
        }
        return command;
    }

}
