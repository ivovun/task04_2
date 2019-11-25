package filters;

import model.User;
import service.UserService;
import util.PropertyReader;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(urlPatterns = {"/*"})
public class FrontEndAuthenticationFilter implements Filter {
    private HttpServletRequest httpRequest;

    private static final String[] loginRequiredURLs = {
            "/", "/user", "/admin", "/list", "/delete", "/insert", "/update", "/edit"
    };

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        httpRequest = (HttpServletRequest) req;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (path.startsWith("/admin/")) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        User authenticatedUser = session != null ? (User) session.getAttribute(PropertyReader.getProperty("authenticatedUser")) : null;

        boolean isLoggedIn = authenticatedUser != null;
        boolean isLoginRequest = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/login");
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("index.jsp");

        if (path.contains("logout") && isLoggedIn) {
            session.setAttribute(PropertyReader.getProperty("authenticatedUser"), null);
            ((HttpServletResponse) resp).sendRedirect("/login");
            return;
        }

        req.setAttribute("wrong_password_or_login", "");

        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            // the user is already logged in and he's trying to login again
            ((HttpServletResponse) resp).sendRedirect(
                    ((User) session.getAttribute(PropertyReader.getProperty("authenticatedUser"))).getRole().equals(PropertyReader.getProperty("adminRoleName"))
                            ? "/admin/list" : "/user");

        } else if (!isLoggedIn && isLoginRequired() && !(isLoginRequest || isLoginPage)) {
            // the user is not logged in, and the requested page requires
            // authentication, then forward to the login page
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            // for other requested pages that do not require authentication
            // or the user is already logged in, continue to the destination
            chain.doFilter(req, resp);
        }
    }

    private boolean isLoginRequired() {
        String requestURL = httpRequest.getRequestURL().toString();

        for (String loginRequiredURL : loginRequiredURLs) {
            if (requestURL.contains(loginRequiredURL)) {
                return true;
            }
        }
        return false;
    }

    public void destroy() {
    }

    public void init(FilterConfig fConfig) {

    }
}
