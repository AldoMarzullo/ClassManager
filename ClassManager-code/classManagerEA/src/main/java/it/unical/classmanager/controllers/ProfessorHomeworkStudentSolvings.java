package it.unical.classmanager.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.unical.classmanager.model.AbstractFileBean;
import it.unical.classmanager.model.FileBean;
import it.unical.classmanager.model.FolderBean;
import it.unical.classmanager.model.dao.CourseClassDAO;
import it.unical.classmanager.model.dao.CourseClassDAOImpl;
import it.unical.classmanager.model.dao.HomeworkAttachedStudentSolvingDAO;
import it.unical.classmanager.model.dao.HomeworkAttachedStudentSolvingDAOImpl;
import it.unical.classmanager.model.dao.HomeworkStudentSolvingDAO;
import it.unical.classmanager.model.dao.HomeworkStudentSolvingDAOImpl;
import it.unical.classmanager.model.dao.RegistrationStudentClassDAO;
import it.unical.classmanager.model.dao.RegistrationStudentClassDAOImpl;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.HomeworkAttachedStudentSolving;
import it.unical.classmanager.model.data.HomeworkStudentSolving;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.utils.FileManager;

//TODO reload of the page when creating new things, update lecture button not yet implemented, retrieve path from session, modal to create lecture (date and hour), deny upload exe
//TODO validation
/**
 * This class allows to: 
 * - get Files starting from a root (students)
 * - view the homeworks solved by a student
 * - view the attachment of this homework
 */
@Controller
public class ProfessorHomeworkStudentSolvings {

	private final static String HEADER = "lecturesPage/lecturesPageHeader.jsp";
	private final static String HSS_BODY = "lecturesPage/homeworkStudentSolvings/homeworkStudentSolvingsBody.jsp";

	@Autowired
	ApplicationContext appContext;
	private static final Logger logger = LoggerFactory.getLogger(ProfessorHomeworkStudentSolvings.class);

	/*
	 * this path is used when a professor wants to see the folder of his students
	 */
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public String getStudents(Model model) {

		model.addAttribute("customHeader", ProfessorHomeworkStudentSolvings.HEADER);
		model.addAttribute("customBody", ProfessorHomeworkStudentSolvings.HSS_BODY);

		//TODO retrieve from session
		int idCourse = 1;
		String courseName = Integer.toString(idCourse);

		CourseClassDAO courseClassDAO = appContext.getBean("courseClassDAO", CourseClassDAOImpl.class);
		CourseClass course = courseClassDAO.get(idCourse);	

		RegistrationStudentClassDAO userDao = appContext.getBean("registrationStudentClassDAO", RegistrationStudentClassDAOImpl.class);

		List<Student> students = userDao.getStudentsRegisteredToACourse(course);
		List<AbstractFileBean> files = new ArrayList<AbstractFileBean>();

		for (Student student : students) {

			String name = student.getLastName() + " " + student.getFirstName();
			String folderPath = FileManager.RESOURCES_PATH + File.separator + courseName + File.separator + FileManager.STUDENTS_PATH + File.separator + student.getUsername();

			int childs = student.getHomeworkStudentSolvings().size();

			FolderBean folder = new FolderBean(Integer.parseInt(student.getSerialNumber()), name, AbstractFileBean.FOLDER_TYPE, folderPath, childs);
			folder.setParentId(0);
			folder.setAction("/studentHomeworks");
			folder.setId(Integer.parseInt( student.getSerialNumber()));
			files.add(folder);
		}	

		model.addAttribute("contents", students);
		model.addAttribute("files", files);
		model.addAttribute("pwd",FileManager.STUDENTS_PATH);

		//BACK PAGE
		String referred = "/students";
		model.addAttribute("backPage", referred);

		logger.info("get students of a course");

		return "layout";
	}

	@RequestMapping(value = "/studentHomeworks", method = RequestMethod.GET)
	public String getStudentsHomeworkSolvings(Model model, @RequestParam("username") String studentId) {

		model.addAttribute("customHeader", ProfessorHomeworkStudentSolvings.HEADER);
		model.addAttribute("customBody", ProfessorHomeworkStudentSolvings.HSS_BODY);

		//TODO retrieve from session
		int idCourse = 1;
		String courseName = Integer.toString(idCourse);

		HomeworkStudentSolvingDAO homeworkStudentSolvingDAO = appContext.getBean("homeworkStudentSolvingDAO", HomeworkStudentSolvingDAOImpl.class);
		List<HomeworkStudentSolving> allHomeworkStudentSolvings = homeworkStudentSolvingDAO.getAllHomeworkStudentSolvings(studentId);

		List<AbstractFileBean> files = new ArrayList<AbstractFileBean>();

		for (HomeworkStudentSolving solution : allHomeworkStudentSolvings) {

			String name = solution.getHomework().getName();
			String folderPath = FileManager.RESOURCES_PATH + File.separator + courseName + File.separator + FileManager.STUDENTS_PATH + File.separator + solution.getStudent().getUsername() + File.separator + FileManager.HOMEWORK_PATH + File.separator + solution.getId();

			int childs = solution.getHomeworkAttachedStudentSolvings().size();

			FolderBean folder = new FolderBean(solution.getId(), name, AbstractFileBean.FOLDER_TYPE, folderPath, childs);
			//folder.setParentId(studentId);
			folder.setAction("/professorStudentHomeworkAttachments");
			folder.setId(solution.getId());
			files.add(folder);
		}	

		model.addAttribute("contents", allHomeworkStudentSolvings);
		model.addAttribute("files", files);
		model.addAttribute("pwd",FileManager.HOMEWORK_PATH);

		//BACK PAGE
		String referred = "/students";
		model.addAttribute("backPage", referred);
		
		model.addAttribute("parentId", studentId);

		return "layout";
	}


	@RequestMapping(value = "/professorStudentHomeworkAttachments", method = RequestMethod.GET)
	public String getStudentsHomeworkSolvingsAttachmets(Model model, @RequestParam("id") int homeworkStudentSolvingId, @RequestParam("parentId") String parentId) {

		model.addAttribute("customHeader", ProfessorHomeworkStudentSolvings.HEADER);
		model.addAttribute("customBody", ProfessorHomeworkStudentSolvings.HSS_BODY);

		HomeworkAttachedStudentSolvingDAO homeworkAttachedStudentSolvingDAO = appContext.getBean("homeworkAttachedStudentSolvingDAO", HomeworkAttachedStudentSolvingDAOImpl.class);
		List<HomeworkAttachedStudentSolving> allHomeworkAttachedStudentSolvings = homeworkAttachedStudentSolvingDAO.getAllHomeworkAttachedStudentSolvings(homeworkStudentSolvingId);

		List<AbstractFileBean> files = new ArrayList<AbstractFileBean>();

		for (HomeworkAttachedStudentSolving solution : allHomeworkAttachedStudentSolvings) {

			String filePath = solution.getFilePath();

			File file = new File(filePath);
			if(file.exists()){
				FileBean fileBean = FileBean.toFileBean(file);
				fileBean.setId(solution.getId());
				fileBean.setParentId(homeworkStudentSolvingId);
				fileBean.setAction("#");
				files.add(fileBean);
			}
		}	

		model.addAttribute("contents", allHomeworkAttachedStudentSolvings);
		model.addAttribute("files", files);
		model.addAttribute("pwd",FileManager.HOMEWORK_PATH);

		//BACK PAGE
		String referred = "/studentHomeworks?username=" + parentId;
		model.addAttribute("backPage", referred);

		return "layout";
	}

}
