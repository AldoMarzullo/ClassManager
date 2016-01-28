package it.unical.classmanager.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.unical.classmanager.controllers.propertyEditor.DegreeCoursePropertyEditor;
import it.unical.classmanager.controllers.propertyEditor.ProfessorPropertyEditor;
import it.unical.classmanager.model.dao.CourseClassDAO;
import it.unical.classmanager.model.dao.CourseClassDAOImpl;
import it.unical.classmanager.model.dao.DegreeCourseDAO;
import it.unical.classmanager.model.dao.DegreeCourseDAOImpl;
import it.unical.classmanager.model.dao.UserDAOImpl;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.DegreeCourse;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.UserSessionChecker;

/**
 * Gestione view relativa ai corsi del professore loggato con la possibilit� di aggiungere nuovi
 * corsi
 * 
 * @author Alessandro *
 */
@Controller
public class CourseController
{
	private final static String HEADER = "course/courseHeader.jsp";
	private final static String BODY = "course/courseBody.jsp";

	private static final Logger logger = LoggerFactory.getLogger(AttendanceCourseController.class);

	@Autowired
	private ApplicationContext context;

	private Professor professor;
	private List<CourseClass> courseClasses;
	private List<DegreeCourse> degreeCourses;
	private CourseClass courseForm;

	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public String loadCourses(HttpServletRequest request, Model model, Locale locale, RedirectAttributes redirectAttributes)
	{	
		User user = UserSessionChecker.checkUserSession(model, request);
		if ( user == null ) 
		{			
		    return "redirect:/";
		}		

		CourseClassDAO courseClassDAO = context.getBean("courseClassDAO", CourseClassDAOImpl.class);
		
		if(user.getRole().equals("Student"))
		{
			Student student = (Student) user;
			courseClasses = courseClassDAO.getCourseClasses(student);
			professor = null;
		}
		else
		{
			professor = (Professor) user;
			model.addAttribute("professor", professor);
			courseClasses = courseClassDAO.getCourseClasses(professor);
			DegreeCourseDAO degreeCourseDAO = context.getBean("degreeCourseDAO", DegreeCourseDAOImpl.class);
			degreeCourses = degreeCourseDAO.getAllDegreeCourses();
			model.addAttribute("degreeCourses", degreeCourses);
		}

		courseForm = new CourseClass();
		model.addAttribute("courseForm", courseForm); // To create new Course
		model.addAttribute("state", "modalClosed");
		model.addAttribute("courses", courseClasses);
		model.addAttribute("selectCourseForm", new CourseClass());
		model.addAttribute("customHeader", CourseController.HEADER);
		model.addAttribute("customBody", CourseController.BODY);
		return "layout";
	}

	@RequestMapping(value = "/courses", method = RequestMethod.POST)
	public String saveCourse(HttpServletRequest request, @Valid @ModelAttribute("courseForm") CourseClass course, BindingResult result, Model model, Locale locale)
	{
		if (professor == null || degreeCourses == null)
			return "redirect:/courses";

		logger.info(course.toString());
		if (result.hasErrors())
		{
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors)
			{
				logger.info("Errors: " + fieldError.getField() + " " + fieldError.getDefaultMessage());
			}

			model.addAttribute("professor", professor);
			model.addAttribute("courses", courseClasses);
			model.addAttribute("degreeCourses", degreeCourses);
			model.addAttribute("selectCourseForm", new CourseClass());
			model.addAttribute("state", "modalOpened");
			model.addAttribute("customHeader", CourseController.HEADER);
			model.addAttribute("customBody", CourseController.BODY);
			return "layout";
		}
		else
		{
			CourseClassDAO courseDAO = context.getBean("courseClassDAO", CourseClassDAOImpl.class);
			courseDAO.create(course);
			
			// Added session variable
			request.getSession().setAttribute("ActiveCourse", course.getId());
			
			return "redirect:/lectures?path=" + course.getId();
		}
	}
	
	@RequestMapping(value = "/selectCourse", method = RequestMethod.POST)
	public String selectCourse(HttpServletRequest request, @ModelAttribute("selectCourseForm") CourseClass course, BindingResult result, Model model, Locale locale)
	{		
		if(course == null)
			return "redirect:/courses";

		logger.info(course.toString());
		
		request.getSession().setAttribute("ActiveCourse", course.getId());
		
		return "redirect:/lectures?path=" + course.getId();
	}

	/**
	 * Binder per mappare la data scelta all'interno della view a quella che si aspetta la classe
	 * CourseClass
	 * 
	 * @param binder
	 */
	@InitBinder
	public void bindingPreparation(WebDataBinder binder)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
		CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, orderDateEditor);
	}

	/**
	 * Binder per mappare:
	 * il DegreeCourse scelto nella form alla relativa classe
	 * il Professor che l'ha creato alla relativa classe
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(DegreeCourse.class, new DegreeCoursePropertyEditor(context.getBean("degreeCourseDAO", DegreeCourseDAOImpl.class)));
		binder.registerCustomEditor(Professor.class, new ProfessorPropertyEditor(context.getBean("userDao", UserDAOImpl.class)));
	}
}
