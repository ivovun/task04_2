package servlet;

import exception.DBException;
import model.User;
import service.UserService;
import service.UserServiceImpl;
import util.PropertyReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserLoginServlet",  urlPatterns = {"/login", "/admin/login", "/"})
public class UserLoginServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("wrong_password_or_login", "");

        try {
            User user = userService.selectUserByLogin(req.getParameter("login"));
            if (user != null && user.getPassword().equals(req.getParameter("password"))) {
                HttpSession session = req.getSession();
                if (user.getRole().equals(PropertyReader.getProperty("userRoleName"))) {
                    session.setAttribute(PropertyReader.getProperty("authenticatedUser"), user);
                    resp.sendRedirect(   "/user");
                } else if (user.getRole().equals(PropertyReader.getProperty("adminRoleName"))) {
                    session.setAttribute(PropertyReader.getProperty("authenticatedUser"), user);
                    resp.sendRedirect("admin/list");
                } else {
                    session.setAttribute(PropertyReader.getProperty("authenticatedUser"), null);
                    req.setAttribute("wrong_password_or_login", "wrong ROLE NAME !!!!");
                    resp.sendRedirect("/login");
                }
                return;
            } else {
                req.setAttribute("wrong_password_or_login", "wrong password or login!!!!");
                resp.sendRedirect("/login");
//                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        } catch (DBException e) {
            //todo сделать пересыл на повторны й логин или что-то
        }

    }
}
