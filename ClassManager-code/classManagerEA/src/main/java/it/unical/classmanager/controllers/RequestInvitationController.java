package it.unical.classmanager.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.RegistrationStudentClassDAO;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.RegistrationStudentClass;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.CustomHeaderAndBody;
import it.unical.classmanager.utils.GenericContainerBeanList;
import it.unical.classmanager.utils.NotificationHelper;
import it.unical.classmanager.utils.UserSessionChecker;

/**
 * Handles requests for request invitation to professors.
 * 
 * @author Aloisius92
 */
@Controller
public class RequestInvitationController {   
	private static final Logger logger = LoggerFactory.getLogger(RequestInvitationController.class);
	private final static String HEADER = "pageCommons/head.jsp";
	private final static String BODY = "invitation/requestInvitation.jsp";

	@Autowired  
	MessageSource messageSource;

	@RequestMapping(value = "/requestInvitation", method = RequestMethod.GET)
	public String requestInvitation(Locale locale, Model model,HttpServletRequest request) {
		logger.info("RequestInvitation Page (GET)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		processSelectableCourse(locale, model, request, (Student) user);
		processCancellableCourse(locale, model, request, (Student) user);	
		InvitationController.checkNewInvitations(model, user);		
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}

	@RequestMapping(value = "/requestInvitation_All", method = RequestMethod.POST)
	public String requestAll(
			@RequestParam(value = "RequestAll", required = true) String value, 
			Locale locale,
			Model model,
			HttpServletRequest request){
		logger.info("RequestInvitation Page - Request All (POST)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		processRequestInvitationAll((Student) user, locale);		
		processSelectableCourse(locale, model, request, (Student) user);
		processCancellableCourse(locale, model, request, (Student) user);
		InvitationController.checkNewInvitations(model, user);		
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}

	@RequestMapping(value = "/requestInvitation_Single", method = RequestMethod.POST)
	public String requestSingle(
			@RequestParam(value = "CourseName", required = true) String courseName, 
			@RequestParam(value = "ProfessorName", required = false) String professorName,
			Locale locale,
			Model model,
			HttpServletRequest request){
		logger.info("RequestInvitation Page - Request Single (POST)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		processRequestInvitationSingle((Student) user, courseName, locale);
		processSelectableCourse(locale, model, request, (Student) user);
		processCancellableCourse(locale, model, request, (Student) user);
		InvitationController.checkNewInvitations(model, user);		
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}

	@RequestMapping(value = "/requestInvitation_CancelAll", method = RequestMethod.POST)
	public String cancelAll(
			@RequestParam(value = "CancelAll", required = true) String value, 
			Locale locale,
			Model model,
			HttpServletRequest request){
		logger.info("RequestInvitation Page - Cancel All (POST)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}		

		processCancellInvitationAll((Student) user);
		processSelectableCourse(locale, model, request, (Student) user);
		processCancellableCourse(locale, model, request, (Student) user);
		InvitationController.checkNewInvitations(model, user);		
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}

	@RequestMapping(value = "/requestInvitation_CancelSingle", method = RequestMethod.POST)
	public String cancelSingle(
			@RequestParam(value = "CourseName", required = true) String courseName, 
			@RequestParam(value = "ProfessorName", required = false) String professorName, 
			Locale locale,
			Model model,
			HttpServletRequest request){
		logger.info("RequestInvitation Page - Cancel Single (POST)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}		

		processCancellInvitationSingle((Student) user, courseName, professorName);
		processSelectableCourse(locale, model, request, (Student) user);
		processCancellableCourse(locale, model, request, (Student) user);
		InvitationController.checkNewInvitations(model, user);		
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);

		return "layout";
	}

	/**
	 * This function add to the model 
	 * the selectable courses
	 * for a student.
	 * 
	 * @param student the student
	 * 
	 */
	private void processSelectableCourse(Locale locale, Model model, HttpServletRequest request, Student student){
		GenericContainerBeanList selectableCourse = getSelectableCourse(student);
		if(selectableCourse!=null){
			model.addAttribute("selectableCourse", selectableCourse);
		}
	}

	/**
	 * This function load 
	 * the selectable courses
	 * for a student.
	 * 
	 * @param student the student
	 * 
	 */
	private GenericContainerBeanList getSelectableCourse(Student student){
		List<Object[]> objectList = DaoHelper.getRegistrationStudentClassDAO().getSelectableCourse(student);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			return beanList;
		}	
		return null;
	}

	/**
	 * This function add to the model 
	 * the cancellable courses
	 * for a student.
	 * 
	 * @param student the student
	 * 
	 */
	private void processCancellableCourse(Locale locale, Model model, HttpServletRequest request, Student student){
		GenericContainerBeanList cancellableCourse = getCancellableCourse(student);
		if(cancellableCourse!=null){
			model.addAttribute("cancellableCourse", cancellableCourse);
		}
	}

	/**
	 * This function load 
	 * the cancellable courses
	 * for a student.
	 * 
	 * @param student the student
	 * 
	 */
	private GenericContainerBeanList getCancellableCourse(Student student){
		List<Object[]> objectList = DaoHelper.getRegistrationStudentClassDAO().getCancellableCourse(student);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			return beanList;
		}
		return null;
	}    

	/**
	 * This function request invitations
	 * for all selectable courses given
	 * a student.
	 * 
	 * @param student the student
	 * 
	 */
	private void processRequestInvitationAll(Student student, Locale locale) {
		GenericContainerBeanList selectableCourse = getSelectableCourse(student);
		for(int i=0; i<selectableCourse.size(); i++){
			processRequestInvitationSingle(student, selectableCourse.get(i).getField1(), locale);
		}
	}

	/**
	 * This function request invitations to
	 * a selectable courses given
	 * a student.
	 * 
	 * @param student the student
	 * @param courseName the course's name
	 * @param professorName the professor's username
	 * 
	 */
	private void processRequestInvitationSingle(Student student, String courseName, Locale locale) {
		CourseClass courseClass = DaoHelper.getCourseClassDAO().get(courseName);
		RegistrationStudentClassDAO registrationStudentClassDAO = DaoHelper.getRegistrationStudentClassDAO();
		int maxIndex = registrationStudentClassDAO.getMaxIndex();
		Calendar cal = Calendar.getInstance();

		if(!registrationStudentClassDAO.existRegistration(student, courseClass)){
			RegistrationStudentClass registrationStudentClass = new RegistrationStudentClass(
					maxIndex+1,
					null, 
					null, 
					cal.getTime(), 
					student, 
					courseClass);

			registrationStudentClassDAO.create(registrationStudentClass);	

			String message =  messageSource.getMessage("message.StudentNotification1",null,locale)
					+" "
					+student.getUsername()
					+" "
					+messageSource.getMessage("message.StudentNotification2",null,locale)
					+" "
					+courseName;
			
			NotificationHelper.createNotification(student, courseClass.getProfessor(), message, "/checkInvitations");
		}
	}   

	/**
	 * This function cancell all
	 * requested invitations given
	 * a student.
	 * 
	 * @param student the student
	 * 
	 */
	private void processCancellInvitationAll(Student student) {
		GenericContainerBeanList selectableCourse = getCancellableCourse(student);
		for(int i=0; i<selectableCourse.size(); i++){
			processCancellInvitationSingle(student, selectableCourse.get(i).getField1(), selectableCourse.get(i).getField2());
		}
	}

	/**
	 * This function cancell a single
	 * requested invitations given
	 * a student and a course name.
	 * 
	 * @param student the student
	 * @param courseName the course's name
	 * @param professorName the professor's username
	 * 
	 */
	private void processCancellInvitationSingle(Student student, String courseName, String professorName) {
		CourseClass courseClass = DaoHelper.getCourseClassDAO().get(courseName);
		RegistrationStudentClassDAO registrationStudentClassDAO = DaoHelper.getRegistrationStudentClassDAO();

		if(registrationStudentClassDAO.existRegistration(student, courseClass)){
			RegistrationStudentClass registrationStudentClass = registrationStudentClassDAO.getRegistration(student, courseClass);
			registrationStudentClassDAO.delete(registrationStudentClass);
		}
	} 

}
