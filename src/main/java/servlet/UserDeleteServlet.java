package servlet;

import exception.DBException;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserDeleteServlet",  urlPatterns = {"/admin/delete"})
public class UserDeleteServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        try {
            userService.deleteUser(Long.parseLong(req.getParameter("id")));
            resp.sendRedirect("/admin/list");
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
