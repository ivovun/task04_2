package servlet;

import exception.DBException;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserInsertServlet",  urlPatterns = {"/admin/insert"})
public class UserInsertServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        try {
            userService.insertUser(new User(req.getParameter("name"),
                    req.getParameter("login"),
                    req.getParameter("password"),
                    req.getParameter("role")));
            resp.sendRedirect("list");
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
