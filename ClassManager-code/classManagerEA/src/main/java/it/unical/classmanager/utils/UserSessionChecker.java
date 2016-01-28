package it.unical.classmanager.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import it.unical.classmanager.controllers.InvitationController;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.data.User;

/**
 * Helper class for user session checking.
 * 
 * @author Aloisius92
 */
public class UserSessionChecker {

	/**
	 * This function check if a user is logged or not.
	 * 
	 * @param model The Model
	 * @param request The Http Request
	 *  
	 * @return If yes return its reference, otherwise return a null value.
	 */
	public static User checkUserSession(Model model, HttpServletRequest request){
		User user = null;
		String username = (String) request.getSession().getAttribute("loggedIn");
		if ( username != null ) {
			user = DaoHelper.getUserDAO().get(username);
			model.addAttribute("user",user.getUsername());
			InvitationController.checkNewInvitations(model, user);
		}			
		
		return user;
	}

}
