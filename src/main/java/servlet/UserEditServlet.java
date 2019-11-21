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

@WebServlet(name = "UserEditServlet", urlPatterns = {"/admin/edit", "/admin/update"})
public class UserEditServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        try {
            userService.updateUser(new User(req.getParameter("id"),
                        req.getParameter("name"),
                        req.getParameter("login"),
                        req.getParameter("password"),
                        req.getParameter("role")));
                resp.sendRedirect("list");

        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
                req.setAttribute("user", userService.selectUser(Long.parseLong(req.getParameter("id"))));
                req.getRequestDispatcher("user-form.jsp").forward(req, resp);

        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
