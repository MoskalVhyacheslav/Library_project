package web;

import commands.Command;
import db.ConnectionPool;
import exceptions.LogicException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet
        implements javax.servlet.Servlet {
    private static final Logger LOGGER= Logger.getLogger(Controller.class);


    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest
                                        request, HttpServletResponse response)
            throws ServletException, IOException {
        String page;
        Command command = CommandController.getCommand(request);

        LOGGER.trace("Process Request");

        page = Path.ERROR_PAGE;
        try {
            page = command.execute(request, response);
        } catch (LogicException exception){
            request.getSession().setAttribute("errorMessage",exception.getMessage());
        }


        if("GET".equals(request.getMethod())) {
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
        else if ("POST".equals(request.getMethod())){
            response.sendRedirect(page);
        }
    }
}