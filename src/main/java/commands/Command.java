package commands;

import exceptions.LogicException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException, LogicException;
}
