package it.unical.classmanager.controllers;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.unical.classmanager.model.UserJsonResponse;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.data.User;

@Controller
public class UsersListController {

	
	private final static String HEADER = "userslist/userslistHeader.jsp";
	private final static String BODY = "userslist/userslistBody.jsp";
	
	@Autowired
	ApplicationContext appContext;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private MutableSortDefinition mutableSortDefiniton;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userslist", method = RequestMethod.GET)
	public String usersList(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
				
		if ( !request.getSession().getAttribute("role").equals("admin") ) {
			return "redirect:/";
		}
		
		UserDAO userDao = (UserDAO) appContext.getBean("userDao");
		
		
		
		PagedListHolder<User> usersList = (PagedListHolder<User>) request.getSession().getAttribute("UserListController_usersList");
		if ( usersList == null ) {
			usersList = new PagedListHolder<User>(userDao.getAllUsers());
		} else {
			usersList.setSource(userDao.getAllUsers());
		}
		usersList.setSort(mutableSortDefiniton);
		usersList.resort();
	    request.getSession().setAttribute("UserListController_usersList", usersList);
		
	    int pageCount = usersList.getPageCount();
	    int pageNumber = usersList.getPage() + 1; 
		model.addAttribute("users",usersList);
		model.addAttribute("pageNumber",pageNumber);
		model.addAttribute("pageCount",pageCount);
		model.addAttribute("pageSize",usersList.getPageSize());
		model.addAttribute("prop",this.mutableSortDefiniton.getProperty());
		model.addAttribute("asc",this.mutableSortDefiniton.isAscending());
		model.addAttribute("customHeader", UsersListController.HEADER);
		model.addAttribute("customBody", UsersListController.BODY);

		return "layout";
	}
	
	
	

	
	/**
	 * Search for user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchusers", method = RequestMethod.GET)
	public String searchUsers(Model model,Locale locale,HttpServletRequest request ) {
		logger.info("Welcome home! The client locale is {}.", locale);
				
		if ( !request.getSession().getAttribute("role").equals("admin") ) {
			return "redirect:/";
		}
		
		PagedListHolder<User> usersList = (PagedListHolder<User>) request.getSession().getAttribute("UserListController_usersList");
		if ( usersList == null ) {
			//TODO: HANDLE SESSION TIMEOUT; FOR NOW JUST A REDIRECT
			handleSessionTimeOut();
		}
		
		String nav = request.getParameter("page");
		if ( nav != null ) {
			//if the nav parameter is the next page
			if ( nav.equals("prev") ) {
				usersList.previousPage();
				//or the previous page
			} else if ( nav.equals("next") ) {
				usersList.nextPage();
				//or a page number
			} else {
				int pageToGet = 0;
				try {
					pageToGet = Integer.parseUnsignedInt(nav);
					pageToGet -= 1;
				} catch ( NumberFormatException e ) {
					pageToGet = 0;
				}
				usersList.setPage(pageToGet);
				
			}
			
//			usersList.resort();
			request.getSession().setAttribute("UserListController_usersList", usersList);
			
			int pageCount = usersList.getPageCount();
		    int pageNumber = usersList.getPage() + 1; 
			model.addAttribute("users",usersList);
			model.addAttribute("pageNumber",pageNumber);
			model.addAttribute("pageCount",pageCount);
			model.addAttribute("pageSize", usersList.getPageSize());
			
			model.addAttribute("customHeader", UsersListController.HEADER);
			model.addAttribute("customBody", UsersListController.BODY);
			
		    return "layout";
		}
		
		
		String lastname = request.getParameter("query");
		if ( lastname == null || lastname.length() < 3 ) {
			model.addAttribute("error", messageSource.getMessage("searchField.length.error", null, locale));
		}
		
		String users = request.getParameter("users");
		int usersPerPage = ( users == null ) ? 10 : Integer.valueOf(users);
		if ( usersPerPage < 10 ) {
			usersPerPage = 10;
		} else if ( usersPerPage > 100 ) {
			usersPerPage = 100;
		}
		
		
		UserDAO userDao = (UserDAO) appContext.getBean("userDao");
		usersList.setSource(userDao.getUsersByLastName(lastname));
	    usersList.setPageSize(usersPerPage);
	    
	    
	    
		usersList.resort();
		request.getSession().setAttribute("UserListController_usersList", usersList);
	    
	    int pageCount = usersList.getPageCount();
	    int pageNumber = usersList.getPage() + 1; 
		model.addAttribute("users",usersList);
		model.addAttribute("pageNumber",pageNumber);
		model.addAttribute("pageCount",pageCount);
		model.addAttribute("pageSize", usersPerPage);
	    
	    model.addAttribute("users", usersList);
	    
	    model.addAttribute("customHeader", UsersListController.HEADER);
		model.addAttribute("customBody", UsersListController.BODY);
	    return "layout";
	}
	
	/**
	 * makes user Professor or deletes
	 */
	@RequestMapping(value = "/edituser", method = RequestMethod.POST)
	public @ResponseBody UserJsonResponse promoteuser(Locale locale, Model model,HttpServletRequest request,@RequestParam("user") String userName,@RequestParam("action") String action) {
		logger.info("Welcome home! The client locale is {}.", locale);
		UserJsonResponse userJsonResponse = new UserJsonResponse();
				
		if ( !request.getSession().getAttribute("role").equals("admin")  ) {
			userJsonResponse = new UserJsonResponse();
			userJsonResponse.setStatus("ERROR");
			HashMap<String,String> errorsMap = new HashMap<String, String>();
			errorsMap.put("delete", messageSource.getMessage("message.errordelete", null, locale));
			userJsonResponse.setErrorsMap(errorsMap);
			return userJsonResponse;
		}
				
		if ( userName == null || userName.length() <= 0 || action == null || action.length() <= 0 ) {
			userJsonResponse = new UserJsonResponse();
			userJsonResponse.setStatus("ERROR");
			HashMap<String,String> errorsMap = new HashMap<String, String>();
			errorsMap.put("delete", messageSource.getMessage("message.errordelete", null, locale));
			userJsonResponse.setErrorsMap(errorsMap);
			return userJsonResponse;
		}
		
		UserDAO userDao = (UserDAO) appContext.getBean("userDao");
		User user = (User) userDao.get(userName);
		if ( action.equals("delete") ) {
			logger.info("deleting " + userName + " on action " + action);
			userDao.delete(user);
		} else if ( action.equals("promote") || action.equals("demote") ) {
			if ( !userDao.doAction(user,action) ) {
				userJsonResponse = new UserJsonResponse();
				userJsonResponse.setStatus("ERROR");
				HashMap<String,String> errorsMap = new HashMap<String, String>();
				errorsMap.put("delete", messageSource.getMessage("message.errordelete", null, locale));
				userJsonResponse.setErrorsMap(errorsMap);
				return userJsonResponse;
			}
		} 
		
		model.addAttribute("customHeader", UsersListController.HEADER);
		model.addAttribute("customBody", UsersListController.BODY);
		
		userJsonResponse.setStatus("SUCCESS");
		return userJsonResponse;
	}
	
	
	/**
	 * changes the sort definition
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sort", method = RequestMethod.GET)
	public String sort(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		if ( !request.getSession().getAttribute("role").equals("admin")  ) {
			return "redirect:/";
		}
		
		PagedListHolder<User> usersList = (PagedListHolder<User>) request.getSession().getAttribute("UserListController_usersList");
		if ( usersList == null ) {
			//TODO: HANDLE SESSION TIMEOUT; FOR NOW JUST A REDIRECT
			handleSessionTimeOut();
		}
		String property = request.getParameter("prop");
		if ( property != null ) {
			this.mutableSortDefiniton.setProperty(property);
			this.mutableSortDefiniton.setToggleAscendingOnProperty(true);
		}
		
		usersList.resort();
		
		model.addAttribute("prop", property);
		model.addAttribute("asc", this.mutableSortDefiniton.isAscending());
		
		int pageCount = usersList.getPageCount();
	    int pageNumber = usersList.getPage() + 1; 
		model.addAttribute("users",usersList);
		model.addAttribute("pageNumber",pageNumber);
		model.addAttribute("pageCount",pageCount);
		model.addAttribute("pageSize", usersList.getPageSize());
		
		model.addAttribute("customHeader", UsersListController.HEADER);
		model.addAttribute("customBody", UsersListController.BODY);
		
		return "redirect:/userslist";
	}
	
	private String handleSessionTimeOut() {
		return "redirect:/userslist";
	};

	
}