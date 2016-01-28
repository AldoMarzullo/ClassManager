package it.unical.classmanager.controllers.errors;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the forum page.
 */
@Controller
public class PrivilegeErrorsController {
	
	@RequestMapping(value = "/privilegeError", method = RequestMethod.GET)
	public String getQuestion(Locale locale, Model model, HttpServletRequest request) {
		
		return "errors/privilegeError";
	}
		
}
