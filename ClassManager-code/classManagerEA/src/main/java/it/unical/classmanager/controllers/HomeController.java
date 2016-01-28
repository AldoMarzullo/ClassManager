package it.unical.classmanager.controllers;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import it.unical.classmanager.model.FullCalendarEventBean;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.EventDAO;
import it.unical.classmanager.model.dao.LectureDAO;
import it.unical.classmanager.model.dao.LectureDAOImpl;
import it.unical.classmanager.model.data.Event;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.CustomHeaderAndBody;
import it.unical.classmanager.utils.GenericContainerBeanList;
import it.unical.classmanager.utils.UserSessionChecker;

/**
 * Handles requests for the home page.
 * 
 * @author Aloisius92
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final static String HEADER = "home/homeHead.jsp";
	private final static String BODY_STUDENT = "home/homeBodyStudent.jsp";
	private final static String BODY_PROFESSOR = "home/homeBodyProfessor.jsp";

	@Autowired
	ApplicationContext appContext;

	@Autowired
	MessageSource messageSource;

	/**
	 * Show the home page of a user if it is logged.
	 * Otherwise redirect to the welcome page.
	 * 
	 * Some useful informations are loaded for a user.
	 * They are:
	 * - ActiveCourses,
	 * - Last added lectures,
	 * - Last added materials,
	 * - Last added homeworks.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model,HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);	

		User user = UserSessionChecker.checkUserSession(model, request);
		if (user == null) {			
			return "welcome";
		} 	

		model.addAttribute("user",user);
		String role = (String) request.getSession().getAttribute("role");
		if ( role != null ) {
			model.addAttribute("role",role);
		}

		setWelcomeMessage(locale, model, request);
		setCalendar(locale, model, request, user);

		if(user instanceof Student){
			model.addAttribute("student", (Student)user);
			CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY_STUDENT);
			setCourses(locale, model, request, (Student)user);
			setLastLectures(locale, model, request, (Student)user);
			setLastMaterials(locale, model, request, (Student)user);
			setLastHomeworks(locale, model, request, (Student)user);
		}

		if(user instanceof Professor){
			model.addAttribute("professor", (Professor)user);
			CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY_PROFESSOR);
			setCourses(locale, model, request, (Professor)user);
			setLastLectures(locale, model, request, (Professor)user);
			setLastMaterials(locale, model, request, (Professor)user);
			setLastHomeworks(locale, model, request, (Professor)user);
		}

		return "layout";
	}

	/**
	 * This function set a welcome message 
	 * in function of the time
	 * a user log into the web site. 
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void setWelcomeMessage(Locale locale, Model model,HttpServletRequest request){
		Time time = new Time(System.currentTimeMillis());
		String message1="", message2="";
		if(time.getHours()>=7 && time.getHours()<13){
			message1 = messageSource.getMessage("message.homeGoodMorning1",null,locale);
			message2 = messageSource.getMessage("message.homeGoodMorning2",null,locale);
		} else if(time.getHours()>=13 && time.getHours()<18){
			message1 = messageSource.getMessage("message.homeGoodAfternoon1",null,locale);
			message2 = messageSource.getMessage("message.homeGoodAfternoon2",null,locale);	    
		} else if(time.getHours()>=18 && time.getHours()<21){
			message1 = messageSource.getMessage("message.homeGoodEvening1",null,locale);
			message2 = messageSource.getMessage("message.homeGoodEvening2",null,locale);	    
		} else {
			message1 = messageSource.getMessage("message.homeGoodNight1",null,locale);
			message2 = messageSource.getMessage("message.homeGoodNight2",null,locale);	    
		}

		model.addAttribute("welcomeMessage1", message1);
		model.addAttribute("welcomeMessage2", message2);	
	}

	/**
	 * This function load the events for the user who is logged.
	 * 
	 */
	private void setCalendar(Locale locale, Model model,HttpServletRequest request, User user){
		// TODO check if student
		
		//is the string containing all the events of the calendar	
		EventDAO eventDao = DaoHelper.getEventDAO();
		//retrieves all the event of an user
		List<Event> events = eventDao.getAllEventsOfUser(user.getUsername());

		List<FullCalendarEventBean> eventsAdaped = new ArrayList<FullCalendarEventBean>();
		for (Event event : events) {
			eventsAdaped.add(FullCalendarEventBean.toFullCalendarEventBean(event));
		}
		/*
		 * Is necessary to retrieve create events related to the course schedule
		 */
		//professor side. We retrieve all the lessons of a professor from the "CourseClassDAO"
		if(user.getRole().equals(User.PROFESSOR)){
			LectureDAO lectureDAO = appContext.getBean("lectureDAO", LectureDAOImpl.class);
			List<Lecture> allLectures = lectureDAO.getAllLecturesOfAProfessor(user.getUsername());

			for (Lecture lecture : allLectures) {
				eventsAdaped.add(FullCalendarEventBean.toFullCalendarEventBean(lecture, user));
			}
		}

		model.addAttribute("FullCalendarEventBean", appContext.getBean("event",Event.class));

		String json = new Gson().toJson(eventsAdaped);
		model.addAttribute("events", json);
	}

	/**
	 * This function load the active course for a professor.
	 * 
	 * @param professor the professor
	 * 
	 */
	private void setCourses( Locale locale, Model model, HttpServletRequest request, Professor professor) {
		List<Object[]> objectList = DaoHelper.getCourseClassDAO().getCourses(professor);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			model.addAttribute("courseList", beanList);
		}	
	}
	
	/**
	 * This function load the active course for a student.
	 * 
	 * @param student the student
	 * 
	 */
	private void setCourses( Locale locale, Model model, HttpServletRequest request, Student student) {
		List<Object[]> objectList = DaoHelper.getCourseClassDAO().getCourses(student);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			model.addAttribute("courseList", beanList);
		}
	}

	/**
	 * This function load the last lectures added
	 * which are interesting for a student.
	 * 
	 * @param student the student
	 * 
	 */
	private void setLastLectures(Locale locale, Model model,HttpServletRequest request, Student student){
		List<Object[]> objectList = DaoHelper.getLectureDAO().getLastLectures(student);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			model.addAttribute("lastLectureList", beanList);
		}	
	}

	/**
	 * This function load the last lectures added
	 * which are interesting for a professor.
	 * 
	 * @param professor the professor
	 * 
	 */
	private void setLastLectures(Locale locale, Model model,HttpServletRequest request, Professor professor){
		List<Object[]> objectList = DaoHelper.getLectureDAO().getLastLectures(professor);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			model.addAttribute("lastLectureList", beanList);
		}
	}

	/**
	 * This function load the last materials added
	 * which are interesting for a student.
	 * 
	 * @param student the student
	 * 
	 */
	private void setLastMaterials(Locale locale, Model model,HttpServletRequest request, Student student){
		List<Object[]> objectList = DaoHelper.getMaterialDAO().getLastMaterials(student);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			model.addAttribute("lastMaterialList", beanList);
		}
	}

	/**
	 * This function load the last materials added
	 * which are interesting for a professor.
	 * 
	 * @param professor the professor
	 * 
	 */
	private void setLastMaterials(Locale locale, Model model,HttpServletRequest request, Professor professor){
		List<Object[]> objectList = DaoHelper.getMaterialDAO().getLastMaterials(professor);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			model.addAttribute("lastMaterialList", beanList);
		}
	}

	/**
	 * This function load the last homeworks
	 * added by a professor which are interesting 
	 * for a student.
	 * 
	 * @param student the student
	 * 
	 */
	private void setLastHomeworks(Locale locale, Model model,HttpServletRequest request, Student student){
		List<Object[]> objectList = DaoHelper.getHomeworkDAO().getLastHomeworks(student);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			model.addAttribute("lastHomeworkList", beanList);
		}
	}

	/**
	 * This function load the last homeworks 
	 * solved by student which are interesting 
	 * for a professor.
	 * 
	 * @param professor the professor
	 * 
	 */
	private void setLastHomeworks(Locale locale, Model model,HttpServletRequest request, Professor professor){
		List<Object[]> objectList = DaoHelper.getHomeworkDAO().getLastHomeworks(professor);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			model.addAttribute("lastHomeworkList", beanList);
		}
	}
}
