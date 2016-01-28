package it.unical.classmanager.controllers;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import it.unical.classmanager.model.AllScoresWrapper;
import it.unical.classmanager.model.PartecipationWrapper;
import it.unical.classmanager.model.dao.CourseClassDAO;
import it.unical.classmanager.model.dao.CourseClassDAOImpl;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.HomeworkDAO;
import it.unical.classmanager.model.dao.HomeworkDAOImpl;
import it.unical.classmanager.model.dao.HomeworkStudentSolvingDAO;
import it.unical.classmanager.model.dao.HomeworkStudentSolvingDAOImpl;
import it.unical.classmanager.model.dao.RegistrationStudentClassDAO;
import it.unical.classmanager.model.dao.RegistrationStudentClassDAOImpl;
import it.unical.classmanager.model.dao.StudentExamPartecipationDAO;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Exam;
import it.unical.classmanager.model.data.Homework;
import it.unical.classmanager.model.data.HomeworkStudentSolving;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.StudentExamPartecipation;

@Controller
public class ScoreController {

	@Autowired
	ApplicationContext appContext;
	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

	private final static String HEADER = "scoresPage/scoresPageHeader.jsp";
	private final static String BODY = "scoresPage/scoresPageBody.jsp";

	/**
	 * This controller handles requests related to the visualization and evaluation of homeworks
	 */
	//TODO ADD ALSO EXAM EVALUATION
	@RequestMapping(value = "/scores", method = RequestMethod.GET)
	public String getScores(Model model, Integer yearFilter, Integer lectureFilter, Integer pagination, HttpServletRequest request) {

		//TODO prendere dalla sessione
		int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
		
		if(pagination == null)
			pagination = 25;
				
		model.addAttribute("customHeader",ScoreController.HEADER);
		model.addAttribute("customBody",ScoreController.BODY);

		HomeworkDAO homeworkDAO = appContext.getBean("homeworkDAO",HomeworkDAOImpl.class);
		List<Homework> homeworks = homeworkDAO.getAllHomeworks(idCourse);

		CourseClassDAO courseClassDAO = appContext.getBean("courseClassDAO", CourseClassDAOImpl.class);
		CourseClass course = courseClassDAO.get(idCourse);

		RegistrationStudentClassDAO userDao = appContext.getBean("registrationStudentClassDAO", RegistrationStudentClassDAOImpl.class);
		List<Student> students = userDao.getStudentsRegisteredToACourse(course);
		
		List<Exam> exams = course.getExams();
		
		/* pagination */
		PagedListHolder<Student> studentsPaged = new PagedListHolder<Student>(students);
		studentsPaged.setPage(pagination);
		
		int pageNumber = studentsPaged.getPage() + 1;
		int pageCount = studentsPaged.getPageCount();
		
		model.addAttribute("pageNumber",pageNumber);
		model.addAttribute("pageCount",pageCount);
		
		model.addAttribute("pageSize", pagination);
		/* ** */
		
		model.addAttribute("students", studentsPaged.getPageList());
		model.addAttribute("homeworks", homeworks);

		model.addAttribute("exams", exams);

		model.addAttribute("lectureId", lectureFilter);
		model.addAttribute("yearFilter", yearFilter);
		
		model.addAttribute("lectures", course.getLectures());
		model.addAttribute("lecture", new Lecture());
		
		model.addAttribute("formatter", new SimpleDateFormat("yyyyy-mm-dd"));
		logger.info("getScores");
		return "layout";

	}

	@RequestMapping(value = "scores/update_score", method = RequestMethod.POST)
	public @ResponseBody String updateHomework(@Valid @RequestBody HomeworkStudentSolving homeworkStudentSolving, BindingResult result, Model model) {

		int id = homeworkStudentSolving.getId();
		int score = homeworkStudentSolving.getScore();

		if(result.hasErrors())
			return "{errors:{'code': 400, 'msg': \"incorrect range\"}}";
		
		HomeworkStudentSolvingDAO dao = appContext.getBean("homeworkStudentSolvingDAO",HomeworkStudentSolvingDAOImpl.class);
		HomeworkStudentSolving original = dao.get(id);
		original.setScore(score);

		dao.update(original);

		logger.info("update_score");
		return "{errors:{'code': 200, 'msg': \"ok\"}}";

	}
	
	@RequestMapping(value = "scores/create_Partecipation", method = RequestMethod.POST)
	public @ResponseBody String updateExam(@Valid @RequestBody PartecipationWrapper part, BindingResult result, Model model) {

		boolean praise = part.getPraise();
		int examId = part.getParentId();
		String studentId = part.getStudentId();
		int score = part.getScore();
		
		if(result.hasErrors())
			return "{errors:{'code': 400, 'msg': \"incorrect range\"}}";
		
		if(praise && score < 30)
			praise = false;
		
		Exam exam = DaoHelper.getExamDAO().get(examId);
		Student student = new Student(DaoHelper.getUserDAO().get(studentId));
		
		StudentExamPartecipationDAO studentExamPartecipationDAO = DaoHelper.getStudentExamPartecipationDAO();
		StudentExamPartecipation sep = new StudentExamPartecipation();
		sep.setExam(exam);
		sep.setPraise(praise);
		sep.setStudent(student);
		sep.setScore(score);
		
		studentExamPartecipationDAO.create(sep);

		logger.info("create partecipation");
		return "{errors:{'code': 200, 'msg': \"ok\"}}";

	}
	
	@RequestMapping(value = "scores/update_Partecipation", method = RequestMethod.POST)
	public @ResponseBody String updatePartecipation(@Valid @RequestBody StudentExamPartecipation studentExamPartecipation, BindingResult result,Model model) {

		int id = studentExamPartecipation.getId();
		int score = studentExamPartecipation.getScore();
		boolean praise = studentExamPartecipation.isPraise();

		if(result.hasErrors())
			return "{errors:{'code': 400, 'msg': \"incorrect range\"}}";
		
		if(praise && score < 30)
			praise = false;
		
		StudentExamPartecipationDAO studentExamPartecipationDAO = DaoHelper.getStudentExamPartecipationDAO();
		StudentExamPartecipation original = studentExamPartecipationDAO.get(id);
		original.setScore(score);
		original.setPraise(praise);

		studentExamPartecipationDAO.update(original);

		logger.info("update partecipation");
		return "{errors:{'code': 200, 'msg': \"ok\"}}";

	}
	
	@RequestMapping(value = "scores/update_all", method = RequestMethod.POST)
	public @ResponseBody String updateAll(@RequestBody AllScoresWrapper data, BindingResult result, Model model) {
		
		for (HomeworkStudentSolving homeworkStudentSolving : data.getHss())			
			updateHomework(homeworkStudentSolving, result, model);
		
		for (StudentExamPartecipation exam : data.getOldExams())
			updatePartecipation(exam, result, model);
		
		for (PartecipationWrapper newExam : data.getNewExams())
			updateExam(newExam, result, model);
		
		
		return "200";
	}
}
