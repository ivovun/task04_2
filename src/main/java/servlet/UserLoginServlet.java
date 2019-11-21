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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserLoginServlet",  urlPatterns = {"/login", "/"})
public class UserLoginServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
//        try {
//            req.setAttribute("listUser", userService.selectAllUsers());
//            req.getRequestDispatcher("user-list.jsp").forward(req, resp);
//        } catch (DBException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("wrong_password_or_login", "");

        try {
            User user = userService.selectUserByLogin(req.getParameter("login"));
            if (user.getPassword().equals(req.getParameter("password"))) {
                HttpSession session = req.getSession();
                if (user.getRole().equals(UserService.userRoleName)) {
                    session.setAttribute(UserService.userRoleSessionAttributeName, user);
                    session.setAttribute(UserService.adminRoleSessionAttributeName, null);
                    resp.sendRedirect("/user");
                } else if (user.getRole().equals(UserService.adminRoleName)) {
                    session.setAttribute(UserService.userRoleSessionAttributeName, null);
                    session.setAttribute(UserService.adminRoleSessionAttributeName, user);
                    resp.sendRedirect("admin/user");
                } else {
                    session.setAttribute(UserService.userRoleSessionAttributeName, null);
                    session.setAttribute(UserService.adminRoleSessionAttributeName, null);
                }
                return;
            }
            resp.sendRedirect("/login");
        } catch (DBException e) {
            //todo сделать пересыл на повторны й логин или что-то
        }
        req.setAttribute("wrong_password_or_login", "wrong password or login!!!!");

    }
}
