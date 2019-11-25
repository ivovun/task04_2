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

/**
 * This Java filter demonstrates how to intercept the req
 * and transform the resp to implement authentication feature.
 * for the website's back-end.
 *
 * @author www.codejava.net
 */
@WebFilter("/admin/*")
public class AdminAuthenticationFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpSession session = httpRequest.getSession(false);
        User adminUser = session != null ?  (User) session.getAttribute(PropertyReader.getProperty("authenticatedUser")) : null;
        boolean isLoggedIn = (adminUser != null && adminUser.getRole().equals(PropertyReader.getProperty("adminRoleName")));

        boolean isLoginRequest = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/admin/login");

        if (isLoggedIn) {
            if ( isLoginRequest) {
                // the admin is already logged in and he's trying to login again
                // then forwards to the admin's homepage
                ((HttpServletResponse) resp).sendRedirect("/admin/list");
            } else {
                // continues the filter chain
                // allows the req to reach the destination
                chain.doFilter(req, resp);
            }
        } else {
            // the admin is not logged in, if user isn't logged in
            //  forwards to the Login page
            //  else Forbidden
            User authenticatedUser = (User) session.getAttribute(PropertyReader.getProperty("authenticatedUser"));
            if (session != null && authenticatedUser != null) {
                req.setAttribute("user", authenticatedUser);
                req.setAttribute("errorMessage", "!!!!!!!"+HttpServletResponse.SC_FORBIDDEN + "!!!!!!!");
                ((HttpServletResponse)resp).setStatus(HttpServletResponse.SC_FORBIDDEN);
                req.getRequestDispatcher("/Forbidden.jsp").forward(req, resp);
            } else {
                ((HttpServletResponse) resp).sendRedirect("/login");
            }
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig fConfig)  {
    }

}