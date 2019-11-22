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

@WebServlet(name = "UserLoginServlet",  urlPatterns = {"/login", "/admin/login", "/"})
public class UserLoginServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);

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
            if (user != null && user.getPassword().equals(req.getParameter("password"))) {
                HttpSession session = req.getSession();
                if (user.getRole().equals(UserService.userRoleName)) {
                    session.setAttribute(UserService.customerUser, user);
                    session.setAttribute(UserService.adminUser, null);
//                    resp.sendRedirect("/user");
                    req.setAttribute("user", user);
                    //todo редиректы на страницы
                    req.getRequestDispatcher(   "/user/user.jsp").forward(req, resp);
                } else if (user.getRole().equals(UserService.adminRoleName)) {
                    session.setAttribute(UserService.customerUser, null);
                    session.setAttribute(UserService.adminUser, user);
                    resp.sendRedirect("admin/list");
                } else {
                    session.setAttribute(UserService.customerUser, null);
                    session.setAttribute(UserService.adminUser, null);
                }
                return;
            } else {
                req.setAttribute("wrong_password_or_login", "wrong password or login!!!!");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        } catch (DBException e) {
            //todo сделать пересыл на повторны й логин или что-то
        }

    }
}
