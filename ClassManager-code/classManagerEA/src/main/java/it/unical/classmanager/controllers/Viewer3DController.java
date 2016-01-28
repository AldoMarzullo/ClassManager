package it.unical.classmanager.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.CustomHeaderAndBody;
import it.unical.classmanager.utils.UserSessionChecker;

/**
 * Handles request for the viewer of 3D contents.
 * 
 * @author Aloisius92
 */
@Controller
public class Viewer3DController {    
	private final static String HEADER = "viewer3D/viewerHead.jsp";
	private final static String BODY = "viewer3D/viewerBody.jsp";

	@RequestMapping(value = "/viewer3D", method = RequestMethod.GET)
	public String viewer(Model model,HttpServletRequest request) {

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);

		return "layout";
	}    
}
