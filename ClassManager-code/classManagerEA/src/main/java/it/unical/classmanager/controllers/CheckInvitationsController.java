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
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.RegistrationStudentClass;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.CustomHeaderAndBody;
import it.unical.classmanager.utils.GenericContainerBeanList;
import it.unical.classmanager.utils.UserSessionChecker;

/**
 * Handles requests for checking the sended invitations.
 * 
 * @author Aloisius92
 */
@Controller
public class CheckInvitationsController {
	private static final Logger logger = LoggerFactory.getLogger(CheckInvitationsController.class);
	private final static String HEADER = "pageCommons/head.jsp";
	private final static String BODY = "invitation/checkInvitations.jsp";

	@Autowired  
	MessageSource messageSource;

	@RequestMapping(value = "/checkInvitations", method = RequestMethod.GET)
	public String checkInvitations(Locale locale, Model model,HttpServletRequest request) {
		logger.info("CheckInvitations Page (GET)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		processAcceptableStudent(locale, model, request, (Professor) user);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);

		return "layout";
	}
	
	@RequestMapping(value = "/checkInvitations_research", method = RequestMethod.POST)
	public String checkInvitationsResearch(
			@RequestParam(value = "researchBar", required = true) String research,
			Locale locale, Model model, HttpServletRequest request) {
		logger.info("CheckInvitations Page - Research (POST)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	
		
		model.addAttribute("researchField", research);

		processAcceptableStudent(locale, model, request, (Professor) user, research);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);

		return "layout";
	}


	@RequestMapping(value = "/checkInvitations_AcceptAll", method = RequestMethod.POST)
	public String acceptAll(
			@RequestParam(value = "AcceptAll", required = true) String value, 
			Locale locale,
			Model model,
			HttpServletRequest request){
		logger.info("CheckInvitations Page - Accept All (POST)", locale);
		
		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		processAcceptAll((Professor) user);
		processAcceptableStudent(locale, model, request, (Professor) user);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);

		return "layout";
	}

	@RequestMapping(value = "/checkInvitations_AcceptSingle", method = RequestMethod.POST)
	public String acceptSingle(
			@RequestParam(value = "courseName", required = true) String courseName,
			@RequestParam(value = "studentName", required = true) String studentName, 
			Locale locale,
			Model model,
			HttpServletRequest request){
		logger.info("CheckInvitations Page - Accept Single (POST)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		processAcceptSingle((Professor) user, studentName, courseName);
		processAcceptableStudent(locale, model, request, (Professor) user);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);

		return "layout";
	}

	/**
	 * This function add to the model the students 
	 * that a professor can accept invitations for its course.
	 * 
	 * @param professor the professor
	 * 
	 */
	private void processAcceptableStudent(Locale locale, Model model,HttpServletRequest request, Professor professor){
		processAcceptableStudent(locale, model, request, professor, null);
	}

	/**
	 * This function add to the model the students 
	 * that a professor can accept invitations for its course.
	 * 
	 * @param professor the professor
	 * @param research String which contains filtering data
	 * 
	 */
	private void processAcceptableStudent(Locale locale, Model model, HttpServletRequest request, Professor professor,
			String research) {
		GenericContainerBeanList list = getAcceptableStudent(professor, research);
		if(list!=null){
			model.addAttribute("studentList", list);
		}
	}
	
	/**
	 * This function load the students 
	 * that a professor can accept invitations for its course.
	 * 
	 * @param professor the professor
	 * 
	 * @return A GenericContainerBeanList which contains the acceptable students
	 * @see it.unical.classmanager.utils.GenericContainerBeanList
	 */
	private GenericContainerBeanList getAcceptableStudent(Professor professor){
		return getAcceptableStudent(professor, null);
	}
	
	/**
	 * This function load the students 
	 * that a professor can accept invitations for its course.
	 * 
	 * @param professor the professor
	 * @param research String which contains filtering data 
	 * 
	 * @return A GenericContainerBeanList which contains the acceptable students
	 * @see it.unical.classmanager.utils.GenericContainerBeanList
	 */
	private GenericContainerBeanList getAcceptableStudent(Professor professor, String research) {
		List<Object[]> objectList = DaoHelper.getRegistrationStudentClassDAO().getAcceptableStudent(professor, research);
		if(objectList.size()>0){
			GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
			return beanList;
		}
		return null;
	}

	/**
	 * This function accept all acceptable
	 * requested invitations sended by students
	 * 
	 * @param professor the professor
	 * 
	 */
	private void processAcceptAll(Professor professor) {
		GenericContainerBeanList acceptableStudent = getAcceptableStudent(professor);
		for(int i=0; i<acceptableStudent.size(); i++){
			processAcceptSingle(professor, acceptableStudent.get(i).getField1(), acceptableStudent.get(i).getField2());
		}
	}

	/**
	 * This function accept the
	 * requested invitations 
	 * sended the student.
	 * 
	 * @param professor the professor
	 * @param studentName the studetn username who request invitation
	 * @param courseSelected the course which the student request invitations
	 * 
	 */
	private void processAcceptSingle(Professor professor, String studentName, String courseSelected) {
		CourseClass courseClass = DaoHelper.getCourseClassDAO().get(courseSelected);
		Student student = (Student)  DaoHelper.getUserDAO().get(studentName);
		RegistrationStudentClassDAO registrationStudentClassDAO = DaoHelper.getRegistrationStudentClassDAO();

		if(registrationStudentClassDAO.existRegistration(student, courseClass)){
			RegistrationStudentClass registrationStudentClass = registrationStudentClassDAO.getRegistration(student, courseClass);
			registrationStudentClass.setAcceptedDate(Calendar.getInstance().getTime());
			registrationStudentClassDAO.update(registrationStudentClass);
		}
	}  

}
