package filters;

import service.UserService;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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
// todo  один
        boolean isLoggedIn = (session != null && session.getAttribute(UserService.adminUser) != null);

        boolean isLoginRequest = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/admin/login");


        if (isLoggedIn && (isLoginRequest || httpRequest.getRequestURI().endsWith("index.jsp"))) {
            // the admin is already logged in and he's trying to login again
            // then forwards to the admin's homepage
            req.getRequestDispatcher("/admin/list").forward(req, resp);

        } else if (isLoggedIn || isLoginRequest) {
            // continues the filter chain
            // allows the req to reach the destination
            chain.doFilter(req, resp);

        } else {
            // the admin is not logged in, so authentication is required
            // forwards to the Login page
            req.getRequestDispatcher("/login").forward(req, resp);
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig fConfig)  {
    }

}