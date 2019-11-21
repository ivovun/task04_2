package filters;

import service.UserService;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This Java filter demonstrates how to intercept the request
 * and transform the response to implement authentication feature.
 * for the website's back-end.
 *
 * @author www.codejava.net
 */
@WebFilter("/admin/*")
public class AdminAuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute(UserService.adminRoleSessionAttributeName) != null);

        boolean isLoginRequest = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/admin/login");


        if (isLoggedIn && (isLoginRequest || httpRequest.getRequestURI().endsWith("index.jsp"))) {
            // the admin is already logged in and he's trying to login again
            // then forwards to the admin's homepage
            request.getRequestDispatcher("/admin/").forward(request, response);

        } else if (isLoggedIn || isLoginRequest) {
            // continues the filter chain
            // allows the request to reach the destination
            chain.doFilter(request, response);

        } else {
            // the admin is not logged in, so authentication is required
            // forwards to the Login page
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}