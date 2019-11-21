package servlet;

import exception.DBException;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "UserPageServlet",  urlPatterns = { "/user", "/admin/user"})
public class UserPageServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute(UserService.customerUser);
        req.setAttribute("user", user == null ? (User) req.getSession().getAttribute(UserService.adminUser) : user);
        req.getRequestDispatcher("/user/user.jsp").forward(req, resp);
    }
}