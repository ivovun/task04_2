package servlet;

import exception.DBException;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UsersListServlet",  urlPatterns = { "/admin/list"})
public class UserListServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("listUser", userService.selectAllUsers());
            req.getRequestDispatcher("user-list.jsp").forward(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
