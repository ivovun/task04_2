package servlet;

import util.PropertyReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "UserPageServlet",  urlPatterns = { "/user", "/admin/user"})
public class UserPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", req.getSession().getAttribute(PropertyReader.getProperty("authenticatedUser")));
        req.getRequestDispatcher("/user/user.jsp").forward(req, resp);
    }
}