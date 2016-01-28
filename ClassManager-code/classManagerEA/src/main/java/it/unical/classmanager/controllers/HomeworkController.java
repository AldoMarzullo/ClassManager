package it.unical.classmanager.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.unical.classmanager.model.AbstractFileBean;
import it.unical.classmanager.model.FileBean;
import it.unical.classmanager.model.FolderBean;
import it.unical.classmanager.model.LectureControllerWrapper;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.HomeworkAttachedDAO;
import it.unical.classmanager.model.dao.HomeworkAttachedDAOImpl;
import it.unical.classmanager.model.dao.HomeworkAttachedStudentSolvingDAO;
import it.unical.classmanager.model.dao.HomeworkAttachedStudentSolvingDAOImpl;
import it.unical.classmanager.model.dao.HomeworkDAO;
import it.unical.classmanager.model.dao.HomeworkDAOImpl;
import it.unical.classmanager.model.dao.HomeworkStudentSolvingDAO;
import it.unical.classmanager.model.dao.HomeworkStudentSolvingDAOImpl;
import it.unical.classmanager.model.dao.LectureDAOImpl;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.data.Homework;
import it.unical.classmanager.model.data.HomeworkAttached;
import it.unical.classmanager.model.data.HomeworkAttachedStudentSolving;
import it.unical.classmanager.model.data.HomeworkStudentSolving;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.FileManager;

@Controller
public class HomeworkController {

	private final static String HEADER = "lecturesPage/lecturesPageHeader.jsp";
	private final static String BODY = "lecturesPage/lecturesPageBody.jsp";
	private static final String STUDENT_BODY = "lecturesPage/studentLecturePage/studentLecturesPageBody.jsp";

	@Autowired
	ApplicationContext appContext;
	private static final Logger logger = LoggerFactory.getLogger(HomeworkController.class);


	@RequestMapping(value = "/lectures/homeworks", method = RequestMethod.GET)
	public String getHomeworks(@Valid LectureControllerWrapper params,	
			BindingResult result, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		//TODO retrieve from session
		int courseId = 1;
		String courseName = Integer.toString(courseId);

		model.addAttribute("customHeader", HomeworkController.HEADER);
		model.addAttribute("customBody", HomeworkController.BODY);

		if(result.hasErrors()){
			redirectAttributes.addAttribute("error", "path error");
			return "redirect:/sessionerror";
		}

		int idLecture = params.getParentId();
		String path = FileManager.RESOURCES_PATH + File.separator + courseName + File.separator + FileManager.LECTURES_PATH + File.separator + idLecture + File.separator + FileManager.HOMEWORK_PATH;

		model.addAttribute("homework", new Homework());

		if(canCreate(request))
			model.addAttribute("canCreate", true);

		return getHomeworks(model, idLecture, path);
	}

	private String getHomeworks(Model model, int idLecture, String path) {

		HomeworkDAO homewrokDAO = appContext.getBean("homeworkDAO", HomeworkDAOImpl.class);
		List<Homework> allLectureHomeworks = homewrokDAO.getAllLectureHomeworks(idLecture);

		List<AbstractFileBean> homeworks = new ArrayList<AbstractFileBean>();

		for (Homework homework : allLectureHomeworks) {

			String name = homework.getName();
			String folderPath = path + File.separator + name;

			int childs = homework.getHomeworkAttacheds().size();

			FolderBean folder = new FolderBean(homework.getId(), name, AbstractFileBean.FOLDER_TYPE, folderPath, childs);
			folder.setParentId(homework.getId());
			folder.setAction("/lectures/homeworkAttached");
			homeworks.add(folder);

		}	

		model.addAttribute("contents", allLectureHomeworks);
		model.addAttribute("files", homeworks);
		model.addAttribute("pwd",FileManager.HOMEWORK_PATH);

		model.addAttribute("parentId", idLecture);

		//BACK PAGE
		String referred = "/lectures/lectureContent?parentId=" + idLecture;
		model.addAttribute("backPage", referred);

		logger.info("getHomeworks");

		return "/layout";
	}


	@RequestMapping(value = "/lectures/homeworkAttached", method = RequestMethod.GET)
	public String getHomeworkAttached(@Valid LectureControllerWrapper params,	
			BindingResult result, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		if(result.hasErrors()){
			redirectAttributes.addAttribute("error", "path error");
			return "redirect:/sessionerror";
		}

		int idHomework = params.getParentId();
		String path = params.getPath();

		model.addAttribute("customHeader", HomeworkController.HEADER);
		model.addAttribute("customBody", HomeworkController.BODY);

		HomeworkAttachedDAO homeworkAttachedDAO = appContext.getBean("homeworkAttachedDAO", HomeworkAttachedDAOImpl.class);
		List<HomeworkAttached> allHomeworkAttacheds = homeworkAttachedDAO.getAllHomeworkAttacheds(idHomework);

		List<AbstractFileBean> homeworksAttacheds = new ArrayList<AbstractFileBean>();

		for (HomeworkAttached homeworkAttached : allHomeworkAttacheds) {

			String filePath = homeworkAttached.getFilePath();

			File file = new File(filePath);
			if(file.exists()){
				FileBean fileBean = FileBean.toFileBean(file);
				fileBean.setId(homeworkAttached.getId());
				fileBean.setParentId(idHomework);
				fileBean.setAction("#");
				homeworksAttacheds.add(fileBean);
			}
		}	

		model.addAttribute("pwd",FileManager.HOMEWORK_ATTACHED_PATH);
		model.addAttribute("parentId",idHomework);
		model.addAttribute("files", homeworksAttacheds);
		model.addAttribute("contents", homeworksAttacheds);


		String username = (String) request.getSession().getAttribute("loggedIn");
		UserDAO userDAO = appContext.getBean("userDao", UserDAO.class);
		User user = userDAO.get(username);

		if(user.getRole().equals(User.STUDENT)){
			getHomeworkStudentSolving(request, model, username, homeworksAttacheds, path);
			model.addAttribute("canCreate",false);
		}
		else
			model.addAttribute("canCreate",true);


		//BACK PAGE
		HomeworkDAO homeworkDAO = DaoHelper.getHomeworkDAO();
		String referred = "/lectures/homeworks?parentId=" + homeworkDAO.get(idHomework).getLecture().getId();;
		model.addAttribute("backPage", referred);

		logger.info("getHomeworkAttached");
		return "/layout";
	}

	private void getHomeworkStudentSolving(HttpServletRequest request, Model model, String username, List<AbstractFileBean> homeworksAttacheds, String path) {

		int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
		String courseName = Integer.toString(idCourse);

		HomeworkStudentSolvingDAO homeworkStudentSolvingDAO = appContext.getBean("homeworkStudentSolvingDAO", HomeworkStudentSolvingDAOImpl.class);
		List<HomeworkStudentSolving> allHomeworkStudentSolvings = homeworkStudentSolvingDAO.getAllHomeworkStudentSolvings(username);

		for (HomeworkStudentSolving solution : allHomeworkStudentSolvings) {

			String name = solution.getHomework().getName();
			String folderPath = FileManager.RESOURCES_PATH + File.separator + courseName + File.separator + FileManager.STUDENTS_PATH + File.separator + solution.getStudent().getUsername() + File.separator + FileManager.HOMEWORK_PATH + solution.getId();

			int childs = solution.getHomeworkAttachedStudentSolvings().size();

			FolderBean folder = new FolderBean(solution.getId(), name, AbstractFileBean.FOLDER_TYPE, folderPath, childs);
			folder.setParentId(Integer.parseInt( solution.getStudent().getSerialNumber() ));
			folder.setAction("/lectures/studentHomeworkAttachments");
			folder.setId(solution.getId());
			homeworksAttacheds.add(folder);
		}	

		model.addAttribute("customBody", HomeworkController.STUDENT_BODY);
		model.addAttribute("pwd",FileManager.HOMEWORK_STUDENT_SOLVING_PATH);
		model.addAttribute("files", homeworksAttacheds);
	}


	@RequestMapping(value = "/lectures/studentHomeworkAttachments", method = RequestMethod.GET)
	public String getHomeworkStudentSolvingAttached(@RequestParam("id") int idHomeworkStudentSolving, Model model, HttpServletRequest request) {

		model.addAttribute("customHeader", HomeworkController.HEADER);
		model.addAttribute("customBody", HomeworkController.STUDENT_BODY);

		HomeworkAttachedStudentSolvingDAO homeworkAttachedStudentSolvingDAO = appContext.getBean("homeworkAttachedStudentSolvingDAO", HomeworkAttachedStudentSolvingDAOImpl.class);
		List<HomeworkAttachedStudentSolving> allHomeworkAttachedStudentSolvings = homeworkAttachedStudentSolvingDAO.getAllHomeworkAttachedStudentSolvings(idHomeworkStudentSolving);

		List<AbstractFileBean> homeworksAttacheds = new ArrayList<AbstractFileBean>();

		for (HomeworkAttachedStudentSolving hssAttached : allHomeworkAttachedStudentSolvings) {

			String filePath = hssAttached.getFilePath();

			File file = new File(filePath);
			if(file.exists()){
				FileBean fileBean = FileBean.toFileBean(file);
				fileBean.setId(hssAttached.getId());
				fileBean.setParentId(idHomeworkStudentSolving);
				fileBean.setAction("#");
				homeworksAttacheds.add(fileBean);
			}
		}	

		model.addAttribute("pwd",FileManager.HOMEWORK_STUDENT_SOLVING_ATTACHMENT_PATH);
		model.addAttribute("parentId",idHomeworkStudentSolving);
		model.addAttribute("files", homeworksAttacheds);

		//BACK PAGE
		HomeworkStudentSolvingDAO dao = DaoHelper.getHomeworkStudentSolvingDAO();
		String referred = "/lectures/homeworkAttached?parentId=" + dao.get(idHomeworkStudentSolving).getHomework().getId();
		model.addAttribute("backPage", referred);

		logger.info("getHomeworkStudentSolvingAttached");
		return "/layout";
	}


	/**
	 * allows to create a new directory in which store the files related to a particular homework
	 * @param lecture the lecture to which this homework is referred
	 */
	@RequestMapping(value = "/lectures/homeworks", method = RequestMethod.POST)
	public String addHomework(@Valid @ModelAttribute("homework") Homework homework, BindingResult result, @RequestParam("parentId") int lectureId, Model model, RedirectAttributes redirect, HttpServletRequest request) {

		if(result.hasErrors()){

			model.addAttribute("modalState", "_open");
			model.addAttribute("homework", homework);

			model.addAttribute("customHeader", HomeworkController.HEADER);
			model.addAttribute("customBody", HomeworkController.BODY);

			model.addAttribute("parentId", lectureId);

			if(canCreate(request))
				model.addAttribute("canCreate", true);
			
			return getHomeworks(model,lectureId, homework.getFilePath());
		}

		Lecture lecture = appContext.getBean("lectureDAO",LectureDAOImpl.class).get(lectureId);
		homework.setLecture(lecture);

		HomeworkDAO homeworkDAO = appContext.getBean("homeworkDAO",HomeworkDAOImpl.class);

		if(homework.getId() > 0)
			return updateHomework(homework, result, lectureId, request, model, redirect);
		
		String lessonName = Integer.toString(lectureId);
		String currentPath = lecture.getCourseClass().getId() + File.separator + FileManager.LECTURES_PATH  + File.separator + lessonName + File.separator + FileManager.HOMEWORK_PATH;

		Homework newHomework = homeworkDAO.create(homework);

		if(newHomework == null){
			redirect.addAttribute("error", "error when creating homework");
			return "redirect:/sessionerror";
		}

		int newId = homework.getId();
		homework.setFilePath(currentPath + File.separator + newId);
		homeworkDAO.update(homework);

		boolean success = new FileManager().mkDir(currentPath, Integer.toString(newId));

		if(!success){

			homeworkDAO.delete(homework);
			logger.error("failed to create directory " + homework.getName() + " in " + currentPath);
			return "/layout";
		}

		model.addAttribute("customHeader", HomeworkController.HEADER);
		model.addAttribute("customBody", HomeworkController.BODY);

		model.addAttribute("homework", new Homework());
		model.addAttribute("lectureID", lectureId);

		logger.info("createHomework");

		return "redirect:/lectures/homeworks?path=" + currentPath + "&parentId=" + lectureId;
	}


	/**
	 * allows to create a new directory in which store the files related to a particular homeworkStudentSolving
	 * @param homeworkId the homework to which this homeworStudentSolving is referred
	 */
	@RequestMapping(value = "/lectures/homeworksStudentSolving", method = RequestMethod.POST)
	public String addHomeworkStudentSolving(@RequestParam("parentId") int homeworkId, HttpServletRequest request, Model model, RedirectAttributes redirect) {

		int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
		String courseName = Integer.toString(idCourse);

		//retrieving the logged student
		String username = (String) request.getSession().getAttribute("loggedIn");
		UserDAO userDAO = appContext.getBean("userDao", UserDAO.class);
		User user = userDAO.get(username);
		Student student = new Student(user);

		//retrieving the referred homework
		HomeworkDAO homeworkDAO = appContext.getBean("homeworkDAO",HomeworkDAOImpl.class);
		Homework homework = homeworkDAO.get(homeworkId);

		//creating the solution
		HomeworkStudentSolving hss = new HomeworkStudentSolving();
		hss.setDate(new Date());
		hss.setStudent(student);
		hss.setHomework(homework);

		HomeworkStudentSolvingDAO homeworkStudentSolvingDAO = appContext.getBean("homeworkStudentSolvingDAO", HomeworkStudentSolvingDAOImpl.class);
		HomeworkStudentSolving newHss = homeworkStudentSolvingDAO.create(hss);

		if(newHss == null){
			redirect.addAttribute("error", "path error");
			return "redirect:/sessionerror";
		}

		int hssId = newHss.getId();
		String folderPath = courseName + File.separator + FileManager.STUDENTS_PATH + File.separator + username + File.separator + FileManager.HOMEWORK_PATH;
		boolean success = new FileManager().mkDir(folderPath, Integer.toString(hssId));

		if(!success){
			homeworkStudentSolvingDAO.delete(homeworkStudentSolvingDAO.get(hssId));
			logger.error("failed to create directory " + homework.getName() + " in " + folderPath);
			return "/layout";
		}

		return "redirect:/lectures/homeworks?path=" + homework.getFilePath() + "&parentId=" + homework.getLecture().getId();
	}


	@RequestMapping(value = "/lectures/update_homeworks", method = RequestMethod.POST)
	public String updateHomework(@Valid Homework homework, BindingResult result, @RequestParam("parentId") int lectureId, HttpServletRequest request, Model model, RedirectAttributes redirect) {

		if(result.hasErrors()){
			
			int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
				
			model.addAttribute("customHeader", HomeworkController.HEADER);
			model.addAttribute("customBody", HomeworkController.BODY);
			model.addAttribute("modalState", "_open");
			
			String path = FileManager.RESOURCES_PATH + File.separator + idCourse + File.separator + FileManager.LECTURES_PATH + File.separator + lectureId + File.separator + FileManager.HOMEWORK_PATH;

			model.addAttribute("homework", homework);

			if(canCreate(request))
				model.addAttribute("canCreate", true);

			return getHomeworks(model, lectureId, path);
		}
		
		//TODO
		HomeworkDAO homeworkDAO = appContext.getBean("homeworkDAO",HomeworkDAOImpl.class);
		Homework old = homeworkDAO.get(homework.getId());

		if(!homework.getDescription().equals(old.getDescription()) && !homework.getDescription().equals(""))
			old.setDescription(homework.getDescription());

		if(!homework.getName().equals(old.getName()) && !homework.getName().equals(""))
			old.setName(homework.getName());

		homeworkDAO.update(old);

		logger.info("update homework");

		return "redirect:/lectures/homeworks?path=" + homework.getFilePath() + "&parentId=" + lectureId;
	}




	/**
	 * Handles the requests to upload a new file. Creates the file in the "path" in the file system
	 * @param file the file uploaded
	 * @param parentId the id of the parentFolder
	 * @return
	 */
	@RequestMapping(value="/lectures/upload_homeworkAttached", method=RequestMethod.POST)
	public @ResponseBody String uploadHomework(@RequestParam("file") MultipartFile file, @RequestParam("parentId") int homeworkId) {

		HomeworkDAO homeworkDAO = appContext.getBean("homeworkDAO",HomeworkDAOImpl.class);
		Homework homework = homeworkDAO.get(homeworkId);

		String path = homework.getFilePath();
		System.out.println(file.getOriginalFilename());
		boolean success = new FileManager().mkMultipartFile(file, path, file.getOriginalFilename());
		if(!success){
			logger.error("cannot save the file " + path + "/" + file.getOriginalFilename());
			return "500";
		}


		HomeworkAttached homeworkAttached = new HomeworkAttached();
		homeworkAttached.setFilePath(FileManager.RESOURCES_PATH + File.separator + path + File.separator + file.getOriginalFilename());
		homeworkAttached.setHomework(homework);
		homeworkAttached.setName(file.getOriginalFilename());

		HomeworkAttachedDAO homeworkAttachedDAO = appContext.getBean("homeworkAttachedDAO", HomeworkAttachedDAOImpl.class);
		homeworkAttachedDAO.create(homeworkAttached);

		logger.info("saving file");

		//return "redirect:/homeworkAttached?path=" + path + "&parentId=" + homeworkId;
		return "200";
	}

	/**
	 * Handles the requests to upload a new file. Creates the file in the "path" in the file system
	 * @param file the file uploaded
	 * @param parentId the id of the parentFolder
	 * @return
	 */
	@RequestMapping(value="/lectures/upload_homeworkStudentSolvingAttachment", method=RequestMethod.POST)
	public @ResponseBody String uploadHomeworkStudentSolving(@RequestParam("file") MultipartFile file, @RequestParam("parentId") int homeworkStudentSolvingId, HttpServletRequest request) {
		
		int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
		String courseName = Integer.toString(idCourse);

		HomeworkStudentSolvingDAO homeworkStudentSolvingDAO = appContext.getBean("homeworkStudentSolvingDAO", HomeworkStudentSolvingDAOImpl.class);
		HomeworkStudentSolving hss = homeworkStudentSolvingDAO.get(homeworkStudentSolvingId);

		//creating the attachment to track the file
		HomeworkAttachedStudentSolving hss_attachment = new HomeworkAttachedStudentSolving();
		hss_attachment.setHomeworkStudentSolving(hss);

		String username = (String) request.getSession().getAttribute("loggedIn");

		//creating the file on the file system
		String folderPath = courseName + File.separator + FileManager.STUDENTS_PATH + File.separator + username + File.separator + FileManager.HOMEWORK_PATH + File.separator + hss.getId();
		hss_attachment.setFilePath(FileManager.RESOURCES_PATH + File.separator + folderPath + File.separator + file.getOriginalFilename());


		HomeworkAttachedStudentSolvingDAO homeworkAttachedStudentSolvingDAO = appContext.getBean("homeworkAttachedStudentSolvingDAO", HomeworkAttachedStudentSolvingDAOImpl.class);
		HomeworkAttachedStudentSolving attached = homeworkAttachedStudentSolvingDAO.create(hss_attachment);

		if(attached == null)
			return "400";

		boolean success = new FileManager().mkMultipartFile(file, folderPath, file.getOriginalFilename());

		if(!success){
			homeworkAttachedStudentSolvingDAO.delete(attached);
			return "400";
		}


		return "200";
	}



	@RequestMapping(value="/lectures/delete_homeworks", method=RequestMethod.POST)
	public String deleteHomework(@RequestParam("homeworkId") int id) {

		HomeworkDAO homeworkDAO = appContext.getBean("homeworkDAO",HomeworkDAOImpl.class);
		Homework homework = homeworkDAO.get(id);
		if(homework != null){
			homeworkDAO.delete(homework);
		}

		String path = homework.getFilePath();
		boolean success = new FileManager().deleteDirectory(path);

		if(!success){
			logger.info("cannot delete the file " + path);
		}
		return "redirect:/lectures/homeworks?parentId=" + homework.getId(); 
	}

	@RequestMapping(value="/lectures/delete_homeworkAttached", method=RequestMethod.POST)
	public String deleteHomeworkAttached(@RequestParam("homeworkAttachedId") int id) {

		HomeworkAttachedDAO homeworkAttachedDAO = appContext.getBean("homeworkAttachedDAO", HomeworkAttachedDAOImpl.class);
		HomeworkAttached homeworkAttached = homeworkAttachedDAO.get(id);
		if(homeworkAttached != null)
			homeworkAttachedDAO.delete(homeworkAttached);

		String path = homeworkAttached.getFilePath();

		boolean success = new FileManager().deleteFile(path);

		if(!success){
			logger.info("cannot delete the file " + path);
		}

		return "redirect:/lectures/homeworkAttached?path=" + path + "&parentId=" + homeworkAttached.getHomework().getId();
	}

	@RequestMapping(value="/lectures/delete_homeworkStudentSolving", method=RequestMethod.POST)
	public String deleteHomeworkStudentSolving(@RequestParam("homeworkStudentSolvingId") int id, HttpServletRequest request) {

		//TODO retrieve from session
		int idCourse = 1;
		String courseName = Integer.toString(idCourse);

		HomeworkStudentSolvingDAO homeworkAttachedDAO = appContext.getBean("homeworkStudentSolvingDAO", HomeworkStudentSolvingDAOImpl.class);
		HomeworkStudentSolving hss = homeworkAttachedDAO.get(id);
		if(hss != null)
			homeworkAttachedDAO.delete(hss);

		String username = (String) request.getSession().getAttribute("loggedIn");
		String folderPath = FileManager.RESOURCES_PATH + File.separator + courseName + File.separator + FileManager.STUDENTS_PATH + File.separator + username + File.separator + FileManager.HOMEWORK_PATH + File.separator + hss.getId();

		boolean success = new FileManager().deleteFile(folderPath);

		if(!success){
			logger.info("cannot delete the file " + folderPath);
		}

		return "redirect:/lectures/homeworksStudentSolving?parentId=" + hss.getHomework().getId();
	}

	@RequestMapping(value="/lectures/delete_homeworkStudentSolvingAttachment", method=RequestMethod.POST)
	public String deleteHomeworkStudentSolvingAttached(@RequestParam("homeworkStudentSolvingAttachedId") int id) {

		HomeworkAttachedStudentSolvingDAO homeworkAttachedStudentSolvingDAO = appContext.getBean("homeworkAttachedStudentSolvingDAO", HomeworkAttachedStudentSolvingDAOImpl.class);
		HomeworkAttachedStudentSolving homeworkAttachedStudentSolving = homeworkAttachedStudentSolvingDAO.get(id);

		if(homeworkAttachedStudentSolving != null)
			homeworkAttachedStudentSolvingDAO.delete(homeworkAttachedStudentSolving);

		String path = homeworkAttachedStudentSolving.getFilePath();

		boolean success = new FileManager().deleteFile(path);

		if(!success){
			logger.info("cannot delete the file " + path);
		}

		return "redirect:/lectures/studentHomeworkAttachments?id=" + homeworkAttachedStudentSolving.getHomeworkStudentSolving().getId();
	}

	/*
	 * checks if the logged student can create homeworks or lectures
	 */
	private boolean canCreate(HttpServletRequest request) {

		//retrieving the logged student
		String username = (String) request.getSession().getAttribute("loggedIn");
		UserDAO userDAO = appContext.getBean("userDao", UserDAO.class);
		User user = userDAO.get(username);

		if(user.getRole().equals(User.STUDENT))
			return false;

		return true;
	}

}
