package it.unical.classmanager.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.data.User;

/**
 * Servlet Filter implementation class LoginCheckerFilter
 */
public class ProfessorCheckerFilter implements Filter {

	/**
	 * Default constructor. 
	 */
	public ProfessorCheckerFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// pass the request along the filter chain
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String username = (String) httpRequest.getSession().getAttribute("loggedIn");
		

		UserDAO userDAO = DaoHelper.getUserDAO();
		User user = userDAO.get(username);

		if(user == null){
			httpResponse.sendRedirect("/privilegeError");
			return;
		}
		
		if(user.getRole().equals(User.STUDENT) && httpRequest.getMethod().equalsIgnoreCase("POST")){
			httpResponse.sendRedirect("/privilegeError");
			return;
		} 
		
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
