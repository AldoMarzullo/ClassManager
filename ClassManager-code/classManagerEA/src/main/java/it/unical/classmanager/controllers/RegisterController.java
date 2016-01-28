package it.unical.classmanager.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import it.unical.classmanager.model.PasswordHashing;
import it.unical.classmanager.model.UserJsonResponse;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RegisterController {
    
    
    @Autowired
    private ApplicationContext context;
    
    @Autowired  
    private MessageSource messageSource;
    
    
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Locale locale, Model model,HttpServletRequest request) {
		if ( request.getSession().getAttribute("loggedIn") != null ) {
		    return "redirect:/";
		}
		
		
		model.addAttribute("userRegisterForm", new User() );
		return "register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody UserJsonResponse handleRegistration(@Valid @RequestBody User user,BindingResult result,Locale locale, Model model,HttpServletRequest request ) {
		logger.info(user.toString());
		UserJsonResponse userJsonResponse = new UserJsonResponse();
		if ( request.getSession().getAttribute("loggedIn") != null ) {
		    userJsonResponse.setStatus("SUCCESS");
		    return userJsonResponse;
		}
		
		if ( result.hasErrors() ) {
		    
		    Map<String ,String> errors = new HashMap<String, String>();
		    List<FieldError> fieldErrors = result.getFieldErrors();
		    for (FieldError fieldError : fieldErrors) {
			String[] resolveMessageCodes = result.resolveMessageCodes(fieldError.getCode());
			String string = resolveMessageCodes[0];
			//System.out.println("resolveMessageCodes : "+string);
			String message = messageSource.getMessage(string+"."+fieldError.getField(), new Object[]{fieldError.getRejectedValue()}, locale);
			//System.out.println("Meassage : "+message);
			errors.put(fieldError.getField(), message)    ;
		    }
		    userJsonResponse.setStatus("ERROR");
		    userJsonResponse.setErrorsMap(errors);
		    return userJsonResponse;
		}
		
		UserDAO userDao = (UserDAO) context.getBean("userDao");
			if ( userDao.get(user.getUsername()) != null ) {
				return handleError(userJsonResponse, "username", locale);
			}	else if ( userDao.getUserBySerialNumber(user.getSerialNumber()) != null ) {
				return handleError(userJsonResponse, "serialNumber", locale);
			} else {
				String hash = DaoHelper.getInstance().getPasswordHashing().getHashAndSalt(user.getPassword());
				user.setHash(hash);
			    user.setRole("Student");
			    Student student = new Student(user);
			    student.setSubscriptionDate(new Date());
			    logger.info(student.toString());
			    userDao.create(student);
			    userJsonResponse.setStatus("SUCCESS");
			    return userJsonResponse;
			}
    }
    
    private UserJsonResponse handleError(UserJsonResponse userJsonResponse,String type,Locale locale) {
		Map<String ,String> errors = new HashMap<String, String>();
	    String message = messageSource.getMessage("message."+type+"Taken",null,locale);
	    errors.put(type, message);
	    userJsonResponse.setStatus("ERROR");
	    userJsonResponse.setErrorsMap(errors);
	    return userJsonResponse;
    }
    
}
