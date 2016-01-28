package it.unical.classmanager.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import it.unical.classmanager.model.FullCalendarEventBean;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.EventDAO;
import it.unical.classmanager.model.dao.EventDAOImpl;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.dao.UserDAOImpl;
import it.unical.classmanager.model.data.Event;
import it.unical.classmanager.model.data.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CalendarController {

	private final static String HEADER = "calendar/calendarHeader.jsp";
	private final static String BODY = "calendar/calendarBody.jsp";


	@Autowired
	ApplicationContext appContext;
	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

	/**
	 * Manages the request related to the calendar
	 */
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public String getCalendar(Model model) {

		model.addAttribute("FullCalendarEventBean", new Event());

		/*
		 * You can return the layout.jsp setting your custom header and body
		 * using the parameters "customHeader" and "customBody"
		 */
		model.addAttribute("customHeader",CalendarController.HEADER);
		model.addAttribute("customBody",CalendarController.BODY);

		logger.info("getCalendar");
		return "layout";

	}

	/**
	 * returns all the events of the calendar in json form.
	 * Refer: calendar.jsp
	 * input: String start, String end - the range of the events to retrieve
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/calendar/events", method = RequestMethod.GET)
	public @ResponseBody String getEvents(Model model, @RequestParam("start") String start, @RequestParam("end") String end, @RequestParam("_") Long preventCaching, HttpServletRequest request) {

		//is the string containing all the events of the calendar
		EventDAO eventDao = appContext.getBean("eventDao",EventDAOImpl.class);

		//retrieves all the event of an user
		String username = (String) request.getSession().getAttribute("loggedIn");
		List<Event> events = eventDao.getAllEventsOfUser(username);

		List<FullCalendarEventBean> eventsAdaped = new ArrayList<FullCalendarEventBean>();
		for (Event event : events) 
			eventsAdaped.add(FullCalendarEventBean.toFullCalendarEventBean(event));

		/*
		 * Is necessary retrieve created events related to the course schedule
		 */

		//We retrieve the lectures of all the courses where the student is registered
		UserDAO userDAO = DaoHelper.getUserDAO();
		User user = userDAO.get(username);

		/*if(user.getRole().equals(User.STUDENT)){

			Student student = new Student(user);
			List<RegistrationStudentClass> registrationStudentClasses = student.getRegistrationStudentClasses();

			for (RegistrationStudentClass registrationStudentClass : registrationStudentClasses) {
				for (Event event : registrationStudentClass.getCourseClass().getEvents()) {

					eventsAdaped.add(FullCalendarEventBean.toFullCalendarEventBean(event));
				}	
			}
		}*/

		String json = new Gson().toJson(eventsAdaped);
		model.addAttribute("FullCalendarEventBean", new Event());

		logger.info("getEvents");
		return json;
	}


	@RequestMapping(value = "/calendar/update_calendar", method = RequestMethod.POST)
	public @ResponseBody String updateCalendar(Model model, @RequestBody Set<Event> events, HttpServletRequest request) {
		
		EventDAO eventDao = appContext.getBean("eventDao",EventDAOImpl.class);
		UserDAO userDao = appContext.getBean("userDao",UserDAOImpl.class);

		String username = (String) request.getSession().getAttribute("loggedIn");

		User user = userDao.get(username);

		for (Event event : events) {
			if(event.getId() > Event.ID_EVENT_TEMP){

				Event old = eventDao.get(event.getId());
				if(old.getUser().getUsername().equals(username)){

					old.update(event);			
					eventDao.update(old);	
				}
			}
			else{
				event.setCourseClass(null);
				event.setUser(user);
				eventDao.create(event);
			}
		}
	
		model.addAttribute("FullCalendarEventBean", appContext.getBean("event",Event.class));

		logger.info("updateEvent");
		return "200";
	}
	
	@RequestMapping(value = "/calendar/delete_events", method = RequestMethod.POST)
	public @ResponseBody String deleteCalendar(Model model, @RequestBody Set<Integer> events, HttpServletRequest request) {

		EventDAO eventDao = appContext.getBean("eventDao",EventDAOImpl.class);
		String username = (String) request.getSession().getAttribute("loggedIn");

		for (Integer eventId : events) {

			Event old = eventDao.get(eventId);
			if(old.getUser().getUsername().equals(username)){
			
				eventDao.delete(old);	
			}
		}
	
		model.addAttribute("FullCalendarEventBean", appContext.getBean("event",Event.class));

		logger.info("updateEvent");
		return "200";
	}
	
	//	/**
	//	 * This path is invoked every time an event is updated (moved in another date or hour)
	//	 */
	//	@RequestMapping(value = "/update_event", method = RequestMethod.POST)
	//	public @ResponseBody String updateEvent(Model model, @RequestBody FullCalendarEventBean event) {
	//
	//		model.addAttribute("FullCalendarEventBean", appContext.getBean("event",Event.class));
	//		logger.info("updateEvent");
	//		return "redirect:calendar";
	//	}
	//
	//	/**
	//	 * This path is invoked every time an event is deleted
	//	 */
	//	@RequestMapping(value = "/delete_event", method = RequestMethod.POST)
	//	public String deleteEvent(Model model, FullCalendarEventBean event) {
	//
	//		model.addAttribute("FullCalendarEventBean", appContext.getBean("event",Event.class));
	//		logger.info("deleteEvent");
	//		return "redirect:calendar";
	//	}
	//
	//	/**
	//	 * This path is invoked every time an new event is created
	//	 */
	//	@RequestMapping(value = "/create_event", method = RequestMethod.POST)
	//	public @ResponseBody String createEvent(Model model, @RequestBody Event event) {
	//
	//		EventDAO eventDao = appContext.getBean("eventDao",EventDAOImpl.class);
	//		eventDao.create(event);
	//		model.addAttribute("FullCalendarEventBean", appContext.getBean("event",Event.class));
	//
	//		logger.info("updateEvent");
	//		return "redirect:calendar";
	//	}

}
