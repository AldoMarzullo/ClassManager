package it.unical.classmanager.filters;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.classmanager.model.PasswordHashing;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.data.User;


/**
 * Servlet Filter implementation class LoginCheckerFilter
 */
public class LoginCheckerFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginCheckerFilter() {
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

		
		
		String hash = (String) httpRequest.getSession().getAttribute("hash");
		String username = (String) httpRequest.getSession().getAttribute("loggedIn");
		if ( hash == null || username == null ) {
			httpResponse.sendRedirect("/privilegeError");
		}
		
		String hashAndDateFromSession = hash.split(":")[0];
		String salt = hash.split(":")[1];
		
		
		UserDAO userDao = DaoHelper.getUserDAO();
		User user = userDao.get(username);
		String userHash = user.getHash().split(":")[0];
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		String dateString = sdf.format(Calendar.getInstance().getTime());
		
		String composedHashAndDate = userHash+":"+dateString;
		String hashAndDateComputed = DaoHelper.getInstance().getPasswordHashing().getHash(composedHashAndDate, salt);
		
		if( !hashAndDateComputed.equals(hashAndDateFromSession) ) {
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
