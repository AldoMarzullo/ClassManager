package it.unical.classmanager.controllers.blackboard;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Handles requests for the forum page.
 */
@Controller
public class BlackboardCollectorController {
	
	@SuppressWarnings("unused")
	@Autowired
	private ApplicationContext appContext;
	
	private final static String HEADER = "blackboard/blackboardHead.jsp";
	private final static String BODY = "blackboard/blackboardBody.jsp";
	
	@RequestMapping(value = "/blackboard", method = RequestMethod.GET)
	public String redirecting(Locale locale, Model model) {
		return "redirect:/blackboard/blackboard";
	}
	
	@RequestMapping(value = "/blackboard/blackboard", method = RequestMethod.GET)
	public String getBlackboardPage(Locale locale, Model model, HttpServletRequest request) {
		
		String username = (String) request.getSession().getAttribute("loggedIn");
		model.addAttribute("username", username);
		
		
		model.addAttribute("customHeader", BlackboardCollectorController.HEADER);
		model.addAttribute("customBody", BlackboardCollectorController.BODY);
		
		return "layout";
	}
	
}
