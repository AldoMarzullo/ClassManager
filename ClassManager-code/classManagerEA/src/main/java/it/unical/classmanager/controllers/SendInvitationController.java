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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.RegistrationStudentClassDAO;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.RegistrationStudentClass;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.CustomHeaderAndBody;
import it.unical.classmanager.utils.GenericContainerBeanList;
import it.unical.classmanager.utils.NotificationHelper;
import it.unical.classmanager.utils.UserSessionChecker;

/**
 * Handles requests for send invitation to students.
 * 
 * @author Aloisius92
 */
@Controller
public class SendInvitationController {
	private static final Logger logger = LoggerFactory.getLogger(SendInvitationController.class);
	private final static String HEADER = "invitation/invitationHead.jsp";
	private final static String BODY = "invitation/sendInvitation.jsp";
	
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/sendInvitation", method = RequestMethod.GET)
	public String sendInvitation(
			@RequestParam(value = "courseName", required = false) String courseName,
			Locale locale, Model model, HttpServletRequest request) {
		logger.info("SendInvitation Page (GET)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}		

		if(courseName!=null){
			model.addAttribute("courseSelected", courseName);
		}

		processSelectableCourse(locale, model, request, user.getUsername());
		processSelectableStudent(locale, model, request, courseName);
		processCancellableStudent(locale, model, request, courseName);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}

	@RequestMapping(value = "/sendInvitation", method = RequestMethod.POST)
	public String sendInvitation(
			@RequestParam(value = "courseName", required = false) String courseName,
			@RequestParam(value = "sendFile", required = false) CommonsMultipartFile sendFile,
			Locale locale, Model model, HttpServletRequest request) {
		logger.info("SendInvitation Page - FileUpload (POST)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}		

		if(courseName!=null){
			System.err.println("CourseName received: "+courseName.trim());
			model.addAttribute("courseSelected", courseName);
		}

		processInviteStudentFromFile(model, sendFile, (Professor) user, locale);

		processSelectableCourse(locale, model, request, user.getUsername());
		processSelectableStudent(locale, model, request, courseName);
		processCancellableStudent(locale, model, request, courseName);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}

	@RequestMapping(value = "/sendInvitation_research", method = RequestMethod.POST)
	public String sendInvitationResearch(
			@RequestParam(value = "courseName", required = false) String courseName,
			@RequestParam(value = "researchBar", required = true) String research,
			Locale locale, Model model, HttpServletRequest request) {
		logger.info("SendInvitation Page - Research (POST)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}		

		if(courseName!=null){
			model.addAttribute("courseSelected", courseName);
		}

		model.addAttribute("researchField", research);

		processSelectableCourse(locale, model, request, user.getUsername());
		processSelectableStudent(locale, model, request, courseName, research);
		processCancellableStudent(locale, model, request, courseName, research);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}



	@RequestMapping(value = "/sendInvitation_InviteAll", method = RequestMethod.POST)
	public String inviteAll(
			@RequestParam(value = "courseSelected", required = true) String courseSelected,
			@RequestParam(value = "InviteAll", required = true) String value, 
			Locale locale, Model model, HttpServletRequest request){
		logger.info("SendInvitation Page - Invite All (POST)", locale);

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		model.addAttribute("courseSelected", courseSelected);
		processInviteAll((Professor) user, courseSelected, locale);

		processSelectableCourse(locale, model, request, user.getUsername());
		processSelectableStudent(locale, model, request, courseSelected);
		processCancellableStudent(locale, model, request, courseSelected);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}    

	@RequestMapping(value = "/sendInvitation_InviteSingle", method = RequestMethod.POST)
	public String inviteSingle(
			@RequestParam(value = "courseSelected", required = true) String courseSelected,
			@RequestParam(value = "StudentName", required = true) String studentName, 
			Locale locale, Model model, HttpServletRequest request){	
		logger.info("SendInvitation Page - Invite Single (POST)", locale);	

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		model.addAttribute("courseSelected", courseSelected);
		processInviteSingle((Professor) user, courseSelected, studentName, locale);

		processSelectableCourse(locale, model, request, user.getUsername());
		processSelectableStudent(locale, model, request, courseSelected);
		processCancellableStudent(locale, model, request, courseSelected);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}

	@RequestMapping(value = "/sendInvitation_CancelAll", method = RequestMethod.POST)
	public String cancelAll(
			@RequestParam(value = "courseSelected", required = true) String courseSelected,
			@RequestParam(value = "CancelAll", required = true) String value, 
			Locale locale, Model model, HttpServletRequest request){
		logger.info("SendInvitation Page - Cancel All (POST)", locale);	

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		model.addAttribute("courseSelected", courseSelected);
		processCancellAll((Professor) user, courseSelected);
	
		processSelectableCourse(locale, model, request, user.getUsername());
		processSelectableStudent(locale, model, request, courseSelected);
		processCancellableStudent(locale, model, request, courseSelected);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}

	@RequestMapping(value = "/sendInvitation_CancelSingle", method = RequestMethod.POST)
	public String cancelSingle(
			@RequestParam(value = "courseSelected", required = true) String courseSelected,
			@RequestParam(value = "StudentName", required = true) String studentName, 
			Locale locale, Model model, HttpServletRequest request){	
		logger.info("SendInvitation Page - Cancel Single (POST)", locale);		

		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) {			
			return "redirect:/";
		}	

		model.addAttribute("courseSelected", courseSelected);
		processCancellSingle((Professor) user, courseSelected, studentName);
		
		processSelectableCourse(locale, model, request, user.getUsername());
		processSelectableStudent(locale, model, request, courseSelected);
		processCancellableStudent(locale, model, request, courseSelected);
		InvitationController.checkNewInvitations(model, user);
		CustomHeaderAndBody.setCustomHeadAndBody(model, HEADER, BODY);
		return "layout";
	}


	/**
	 * This function add to the model the courses
	 * supported by a professor.
	 * 
	 * @param professorName the professor's username
	 * 
	 */
	private void processSelectableCourse(Locale locale, Model model,HttpServletRequest request, String professorName){
		GenericContainerBeanList list = getSelectableCourse(professorName);
		if(list!=null){
			model.addAttribute("courseList", list);
		}
	}

	/**
	 * This function load the courses
	 * supported by a professor.
	 * 
	 * @param professorName the professor's username
	 * 
	 */
	private GenericContainerBeanList getSelectableCourse(String professorName){
		if(professorName!=null){
			Professor professor = (Professor) DaoHelper.getUserDAO().get(professorName);
			List<Object[]> objectList = DaoHelper.getRegistrationStudentClassDAO().getSelectableCourse(professor);
			if(objectList.size()>0){
				GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
				return beanList;
			}
		}
		return null;
	}

	/**
	 * This function add to the model the students 
	 * that a professor can invite to its course.
	 * 
	 * @param professorName the professor's username
	 * 
	 */
	private void processSelectableStudent(Locale locale, Model model,HttpServletRequest request, String courseName){
		processSelectableStudent(locale, model, request, courseName, null);
	}

	/**
	 * This function add to the model the students 
	 * that a professor can invite to its course.
	 * 
	 * @param professorName the professor's username
	 * @param research String which contains filtering data
	 *  
	 */
	private void processSelectableStudent(Locale locale, Model model, HttpServletRequest request, String courseName, String research) {
		GenericContainerBeanList list = getSelectableStudent(courseName, research);
		if(list!=null){
			model.addAttribute("studentList", list);
		} 
	}

	/**
	 * This function load the students 
	 * that a professor can invite to its course.
	 * 
	 * @param professorName the professor's username
	 * @param research String which contains filtering data 
	 * 
	 * @return A GenericContainerBeanList which contains the selectable students
	 * @see it.unical.classmanager.utils.GenericContainerBeanList
	 */
	private GenericContainerBeanList getSelectableStudent(String courseName, String research){
		if(courseName!=null){
			CourseClass courseClass = DaoHelper.getCourseClassDAO().get(courseName.substring(1, courseName.length()-1));
			List<Object[]> objectList = DaoHelper.getRegistrationStudentClassDAO().getSelectableStudent(courseClass, research);
			if(objectList.size()>0){
				GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
				return beanList;
			}
		}
		return null;
	}

	/**
	 * This function load the students 
	 * that a professor can invite to its course.
	 * 
	 * @param professorName the professor's username
	 * 
	 * @return A GenericContainerBeanList which contains the selectable students
	 * @see it.unical.classmanager.utils.GenericContainerBeanList
	 */
	private GenericContainerBeanList getSelectableStudent(String courseName){
		return getSelectableStudent(courseName, null);
	}

	/**
	 * This function add to the model the students 
	 * that a professor can cancel from its invitations.
	 * 
	 * @param professorName the professor's username
	 * 
	 */
	private void processCancellableStudent(Locale locale, Model model,HttpServletRequest request, String courseName){
		processCancellableStudent(locale, model, request, courseName, null);   
	}

	/**
	 * This function add to the model the students 
	 * that a professor can cancel from its invitations.
	 * 
	 * @param professorName the professor's username
	 * @param research String which contains filtering data
	 * 
	 */
	private void processCancellableStudent(Locale locale, Model model, HttpServletRequest request, String courseName, String research) {
		GenericContainerBeanList list = getCancellableStudent(courseName, research);
		if(list!=null){
			model.addAttribute("invitedStudent", list);
		} 		
	}

	/**
	 * This function load the students 
	 * that a professor can cancel 
	 * from its invitations
	 * 
	 * @param professorName the professor's username
	 * @param research String which contains filtering data 
	 * 
	 * @return A GenericContainerBeanList which contains the cancellable students
	 * @see it.unical.classmanager.utils.GenericContainerBeanList
	 */
	private GenericContainerBeanList getCancellableStudent(String courseName, String research){
		if(courseName!=null){
			CourseClass courseClass = DaoHelper.getCourseClassDAO().get(courseName.substring(1, courseName.length()-1));
			List<Object[]> objectList = DaoHelper.getRegistrationStudentClassDAO().getCancellableStudent(courseClass, research);
			if(objectList.size()>0){
				GenericContainerBeanList beanList = new GenericContainerBeanList(objectList);
				return beanList;
			}
		}
		return null;
	}

	/**
	 * This function load the students 
	 * that a professor can cancel 
	 * from its invitations.
	 * 
	 * @param professorName the professor's username
	 * 
	 * @return A GenericContainerBeanList which contains the cancellable students
	 * @see it.unical.classmanager.utils.GenericContainerBeanList
	 */
	private GenericContainerBeanList getCancellableStudent(String courseName){
		return getCancellableStudent(courseName, null);
	}

	/**
	 * This function send invitations to
	 * all selectable students for the
	 * selected course.
	 * 
	 * @param professor the professor who send invitations
	 * @param courseSelected the course seleted by a professor
	 * 
	 */
	private void processInviteAll(Professor professor, String courseSelected, Locale locale) {
		GenericContainerBeanList selectableStudent = getSelectableStudent(courseSelected);
		for(int i=0; i<selectableStudent.size(); i++){
			processInviteSingle(professor, courseSelected, selectableStudent.get(i).getField1(), locale);
		}
	}

	/**
	 * This function send invitations 
	 * to the selected student for the
	 * selected course.
	 * 
	 * @param professor the professor who send invitations
	 * @param courseSelected the course seleted by a professor
	 * @param studentName the of the student to which send invitations
	 * 
	 */
	private void processInviteSingle(Professor professor, String courseSelected, String studentName, Locale locale) {
		CourseClass courseClass = DaoHelper.getCourseClassDAO().get(courseSelected);
		Student student = (Student)  DaoHelper.getUserDAO().get(studentName);
		RegistrationStudentClassDAO registrationStudentClassDAO = DaoHelper.getRegistrationStudentClassDAO();
		int maxIndex = registrationStudentClassDAO.getMaxIndex();
		Calendar cal = Calendar.getInstance();

		if(courseClass!=null && student!=null && !registrationStudentClassDAO.existRegistration(student, courseClass)){
			RegistrationStudentClass registrationStudentClass = new RegistrationStudentClass(
					maxIndex+1,
					cal.getTime(), 
					null, 
					null, 
					student, 
					courseClass);

			registrationStudentClassDAO.create(registrationStudentClass);
			
			String message = messageSource.getMessage("message.ProfessorNotification1",null,locale)
					+" "					
					+professor.getUsername()
					+" "
					+messageSource.getMessage("message.ProfessorNotification2",null,locale)
					+" "
					+courseSelected;
			NotificationHelper.createNotification(professor, student, message, "/checkRequestedInvitations");
			//System.err.println("Correct invitation for "+student.getUsername()+", Course: "+courseClass.getName()+"\n");
		}
	}  

	/**
	 * This function send invitations to
	 * all students readed from the specified file.
	 * 
	 * @param sendFile the script file which contains the student to invite
	 * @param professor the professor who send invitations
	 * 
	 */
	private void processInviteStudentFromFile(Model model, CommonsMultipartFile sendFile, Professor professor, Locale locale) {
		if(sendFile != null){
			if (!sendFile.isEmpty()) {
				byte[] bytes = sendFile.getBytes();
				String fileContent = new String(bytes);
				String[] courseStudentRows = fileContent.split(";");
				for (String row : courseStudentRows){
					String[] rowSplitted = row.split(",");
					if(rowSplitted.length==2){
						String courseName = rowSplitted[0].trim();
						String studentUsername = rowSplitted[1].trim();
						//System.out.println("Send invitations to "+studentUsername+" for "+courseName);
						processInviteSingle(professor, courseName, studentUsername, locale);
					} else {
						// File format wrong
						System.err.println("Wrong file format! File:"+sendFile.getOriginalFilename());
						model.addAttribute("fileFormatError", "fileFormatErrorMessage");
						return;
					}
				}
			} 
		}
	}

	/**
	 * This function cancel invitations sended 
	 * to all cancellable students for the
	 * selected course.
	 * 
	 * @param professor the professor who cancel invitations
	 * @param courseSelected the course seleted by a professor
	 * @param studentName the of the student to which cancel sended invitations
	 * 
	 */
	private void processCancellAll(Professor professor, String courseSelected) {
		GenericContainerBeanList cancellableStudent = getCancellableStudent(courseSelected);
		for(int i=0; i<cancellableStudent.size(); i++){
			processCancellSingle(professor, courseSelected, cancellableStudent.get(i).getField1());
		}
	}

	/**
	 * This function cancel invitations sended 
	 * to the selected student 
	 * for the selected course.
	 * 
	 * @param professor the professor who cancel invitations
	 * @param courseSelected the course seleted by a professor
	 * 
	 */
	private void processCancellSingle(Professor professor, String courseSelected, String studentName) {
		CourseClass courseClass = DaoHelper.getCourseClassDAO().get(courseSelected);
		Student student = (Student)  DaoHelper.getUserDAO().get(studentName);
		RegistrationStudentClassDAO registrationStudentClassDAO = DaoHelper.getRegistrationStudentClassDAO();

		if(registrationStudentClassDAO.existRegistration(student, courseClass)){
			RegistrationStudentClass registrationStudentClass = registrationStudentClassDAO.getRegistration(student, courseClass);
			registrationStudentClassDAO.delete(registrationStudentClass);
		}
	}  

}
