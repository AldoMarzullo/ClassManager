package it.unical.classmanager.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unical.classmanager.model.dao.AttendanceStudentLectureDAO;
import it.unical.classmanager.model.dao.AttendanceStudentLectureDAOImpl;
import it.unical.classmanager.model.dao.CourseClassDAO;
import it.unical.classmanager.model.dao.CourseClassDAOImpl;
import it.unical.classmanager.model.dao.LectureDAO;
import it.unical.classmanager.model.dao.LectureDAOImpl;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.dao.UserDAOImpl;
import it.unical.classmanager.model.data.AttendanceStudentLecture;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.UserSessionChecker;

/**
 * Gestisce la view per le presenze
 * 
 * @author Alessandro
 *
 */

@Controller
public class AttendanceLessonController
{
	private final static String HEADER = "attendance/attendanceCourseHeader.jsp";
	private final static String BODY = "attendance/attendanceLessonBody.jsp";
	
	private static final Logger logger = LoggerFactory.getLogger(AttendanceCourseController.class);
	
	@Autowired
	private ApplicationContext context;
	
	private Lecture currentLecture;
	private AttendanceStudentLectureDAO attendanceStudentLectureDAO;
	private List<String> initialStudends;
	private UserDAO userDAO;
	
	@RequestMapping(value = "/attendance", method = RequestMethod.GET)
	public String loadLessonAttendances(HttpServletRequest request, @ModelAttribute("lecture") Lecture lecture, Model model) 
	{
		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null || user.getRole().equals("Student")) {			
		    return "redirect:/";
		}	
		
		if(lecture == null)
		{
			lecture = new Lecture();
			return "redirect:/view_attendance";
		}
		else
		{
			LectureDAO lectureDAO = context.getBean("lectureDAO", LectureDAOImpl.class);
			currentLecture = lectureDAO.get(lecture.getId());
		}
			
		userDAO = context.getBean("userDao", UserDAOImpl.class);
		attendanceStudentLectureDAO = context.getBean("attendanceStudentLectureDAO", AttendanceStudentLectureDAOImpl.class);
		List<Student> studentsNotPresent = attendanceStudentLectureDAO.getAllStudentsNotPresentOnALecture(currentLecture);
		List<Student> studentsPresent = attendanceStudentLectureDAO.getAllAttendanceStudentLecturesOfALecture(currentLecture);
		initialStudends = new ArrayList<String>();
		for (Student student : studentsPresent)
		{
			initialStudends.add(student.getUsername());
		}
			
		model.addAttribute("professor", (Professor) user);
		CourseClassDAO courseClassDAO = context.getBean("courseClassDAO", CourseClassDAOImpl.class);
		model.addAttribute("course", courseClassDAO.get((Integer) request.getSession().getAttribute("ActiveCourse")));
		
		model.addAttribute("lecture", currentLecture);
		model.addAttribute("studentsPresent", studentsPresent);
		model.addAttribute("studentsNotPresent", studentsNotPresent);
		
		model.addAttribute("customHeader", AttendanceLessonController.HEADER);
		model.addAttribute("customBody", AttendanceLessonController.BODY);
		return "layout";
	}
	
	@RequestMapping(value = "/attendance", method = RequestMethod.POST)
	public String saveLessonAttendances(HttpServletRequest request, @ModelAttribute("lecture") Lecture lecture, Model model) 
	{
		if(lecture == null || lecture.getAttendanceStudentLectures() == null)
		{
			lecture = new Lecture();
		}
		logger.info("Size: " + lecture.getAttendanceStudentLectures().size());
		
		for (AttendanceStudentLecture asl : lecture.getAttendanceStudentLectures())
		{
			logger.info(asl.getStudent().getUsername());
			Student currentStudent = asl.getStudent();
			if(!initialStudends.contains(currentStudent.getUsername()))
				attendanceStudentLectureDAO.create(asl);
			else
				initialStudends.remove(currentStudent.getUsername());
		}
		for (String student : initialStudends)
		{
			Student s = (Student)userDAO.get(student);
			List<AttendanceStudentLecture> attendanceStudentLecture = attendanceStudentLectureDAO.getAllAttendanceStudentLecturesOfAStudentAndALecture(s, currentLecture);
			for (AttendanceStudentLecture asl : attendanceStudentLecture)
			{
				attendanceStudentLectureDAO.delete(asl);
			}
		}

		currentLecture.setAttendanceStudentLectures(lecture.getAttendanceStudentLectures());
		LectureDAO lectureDAO = context.getBean("lectureDAO", LectureDAOImpl.class);
		lectureDAO.update(currentLecture);	
		
		model.addAttribute("id", currentLecture.getId());
		return "redirect:/attendance";
	}
	
	/**
	 * La funzione seguente permette mappare gli oggetti, in questo caso gli studenti che vengono dichiarati presenti alla suddetta lezione, come appartenenti alla classe Studente.
	 * Questo perchè Spring non permette di restituire una lista di oggetti, ma solo una lista di String (int, float ecc.). 
	 * Quindi, all'interno di questa list<String> vengono presi gli studenti nel seguente modo: in tale lista sono contenute le chiavi primarie degli studenti, che, tramite query vengono presi dal DB.
	 * Successivamente vengono create le tuple relative alla partecipazione dello studente alla data lezione, e vengono restituite. 
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception 
	{
		binder.registerCustomEditor(List.class, "attendanceStudentLectures", new CustomCollectionEditor(List.class) 
		{
			@Override
			protected Object convertElement(Object element) 
			{	
				if (element instanceof String) 
				{
					AttendanceStudentLecture attendanceStudentLecture = new AttendanceStudentLecture(); 
					UserDAO userDAO = context.getBean("userDao", UserDAOImpl.class); 
					Student s = (Student) userDAO.get((String)element);
					attendanceStudentLecture.setStudent(s);
					attendanceStudentLecture.setLecture(currentLecture);
					return attendanceStudentLecture;
				}
				System.out.println("Don't know what to do with: " + element);
				return null;
			}
		});
	}
}