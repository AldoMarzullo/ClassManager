package it.unical.classmanager.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unical.classmanager.model.PasswordHashing;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.EventDAO;
import it.unical.classmanager.model.dao.EventDAOImpl;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.dao.UserDAOImpl;
import it.unical.classmanager.model.data.Event;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AldoLoginController {
	
	@Autowired
	private ApplicationContext context;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AldoLoginController.class);
	
	/**
	 * Manages the request related to the calendar
	 */
	@RequestMapping(value = "/aldo", method = RequestMethod.GET)
	public String getCalendar(Model model,HttpServletRequest request) {
		
		String username = "AldoConte";
		UserDAO userDao = context.getBean("userDao",UserDAOImpl.class);
		
		User user = new User();
		user.setUsername(username);
		user.setFirstName("aldo");
		user.setLastName("aldo");
		
		Calendar date = new GregorianCalendar(1996, Calendar.JULY, 3);
	    date.add(Calendar.DAY_OF_MONTH, -7);
	    
		user.setBirthDate(date.getTime());
		user.setEmail("aldo@aldo.it");
		user.setRole("admin");
		user.setPassword("ginopaoli");
		user.setConfirmPassword(user.getPassword());
		user.setSerialNumber("0000");
		String hash = DaoHelper.getInstance().getPasswordHashing().getHashAndSalt(user.getPassword());
		
		user.setHash(hash);
		
		Professor aldo = new Professor(user);
		
		if(userDao.get(username) == null) {
			userDao.create(aldo);
		}
		
		
		
		EventDAO eventDao = context.getBean("eventDao",EventDAOImpl.class);
		Date startDate = date.getTime();
		Date endDate = date.getTime();
		
		Event event = new Event(0, "event", "", startDate, endDate, "", null, null, user);
		eventDao.create(event);
		
		User aldoUser = userDao.get(username);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		String dateString = sdf.format(Calendar.getInstance().getTime());
		String toHash = aldoUser.getHash().split(":")[0] +":"+ dateString;
		String toSaveInSession = DaoHelper.getInstance().getPasswordHashing().getHashAndSalt(toHash);
		request.getSession().setAttribute("hash", toSaveInSession);
		request.getSession().setAttribute("loggedIn",username);
		request.getSession().setAttribute("role",user.getRole());
		return "redirect:/";
			
	}
	
}
