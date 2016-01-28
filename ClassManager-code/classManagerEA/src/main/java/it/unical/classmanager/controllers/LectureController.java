package it.unical.classmanager.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringEscapeUtils;
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
import it.unical.classmanager.model.dao.CourseClassDAO;
import it.unical.classmanager.model.dao.CourseClassDAOImpl;
import it.unical.classmanager.model.dao.EventDAO;
import it.unical.classmanager.model.dao.EventDAOImpl;
import it.unical.classmanager.model.dao.LectureDAO;
import it.unical.classmanager.model.dao.LectureDAOImpl;
import it.unical.classmanager.model.dao.MaterialDAO;
import it.unical.classmanager.model.dao.MaterialDAOImpl;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Event;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.Material;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.FileManager;

//TODO .exe
/**
 * This class allows to: 
 * - get Files starting from a root (students, lectures)
 * - create a new Lecture
 * - add an Homework (Professor)
 * - upload an Homework or a material for a lecture
 */
@Controller
public class LectureController {

	private final static String HEADER = "lecturesPage/lecturesPageHeader.jsp";
	private final static String BODY = "lecturesPage/lecturesPageBody.jsp";

	@Autowired
	ApplicationContext appContext;
	private static final Logger logger = LoggerFactory.getLogger(LectureController.class);


	@RequestMapping(value = "/lectures", method = RequestMethod.GET)
	public String getClasses(Model model, HttpServletRequest request) {

		if(request.getParameter("path") != null) {
			Integer idCourse = Integer.parseInt(request.getParameter("path"));
			request.getSession().setAttribute("ActiveCourse", idCourse);
		}
		
		
		if(canCreate(request))
			model.addAttribute("canCreate", true);

		model.addAttribute("lecture", new Lecture());

		return getLectures(model, request);
	}


	private String getLectures(Model model, HttpServletRequest request){

		int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
		String courseName = Integer.toString(idCourse);

		String currentPath = FileManager.RESOURCES_PATH + File.separator + courseName + File.separator + FileManager.LECTURES_PATH;

		model.addAttribute("customHeader", LectureController.HEADER);
		model.addAttribute("customBody", LectureController.BODY);

		LectureDAO lectureDao = appContext.getBean("lectureDAO", LectureDAOImpl.class);
		List<Lecture> allLecturesOfACourse = lectureDao.getAllLecturesOfACourse(idCourse);

		List<AbstractFileBean> lectures = new ArrayList<AbstractFileBean>();

		adaptLecture(lectures,allLecturesOfACourse, currentPath);

		model.addAttribute("contents", allLecturesOfACourse);
		model.addAttribute("files", lectures);
		model.addAttribute("pwd", FileManager.LECTURES_PATH);

		logger.info("getLectures");

		return "layout";
	}

	@RequestMapping(value = "/lectures/lectureContent", method = RequestMethod.GET)
	public String getLectureContent(@Valid LectureControllerWrapper params,	
			BindingResult result, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
		String courseName = Integer.toString(idCourse);

		model.addAttribute("customHeader", LectureController.HEADER);
		model.addAttribute("customBody", LectureController.BODY);

		if(result.hasErrors()){
			redirectAttributes.addAttribute("error", "path error");
			return "redirect:/sessionerror";
		}

		List<AbstractFileBean> lectureContent = new ArrayList<AbstractFileBean>();

		int lectureId = params.getParentId();
		String path = FileManager.RESOURCES_PATH + File.separator + courseName + File.separator + FileManager.LECTURES_PATH + File.separator + lectureId;

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		FolderBean homeworks = new FolderBean(lectureId,listOfFiles[0].getName(),AbstractFileBean.FOLDER_TYPE, listOfFiles[0].getPath(), listOfFiles[0].listFiles().length);
		FolderBean materials = new FolderBean(lectureId,listOfFiles[1].getName(),AbstractFileBean.FOLDER_TYPE, listOfFiles[1].getPath(), listOfFiles[1].listFiles().length);

		homeworks.setAction("/lectures/homeworks");
		homeworks.setParentId(lectureId);
		materials.setAction("/lectures/materials");
		materials.setParentId(lectureId);


		lectureContent.add(homeworks);
		lectureContent.add(materials);

		model.addAttribute("files", lectureContent);

		String referred = "/lectures";
		model.addAttribute("backPage", referred);

		logger.info("getLectureContent");

		return "layout";
	}


	@RequestMapping(value = "/lectures/materials", method = RequestMethod.GET)
	public String getMaterials(@Valid LectureControllerWrapper params,	
			BindingResult result, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		if(result.hasErrors()){
			redirectAttributes.addAttribute("error", "path error");
			return "redirect:/sessionerror";
		}

		int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
		int idLecture = params.getParentId();

		model.addAttribute("customHeader", LectureController.HEADER);
		model.addAttribute("customBody", LectureController.BODY);
		model.addAttribute("pwd",FileManager.MATERIALS_PATH);

		MaterialDAO materialDAO = appContext.getBean("materialDAO", MaterialDAOImpl.class);
		List<Material> allLectureMaterials = materialDAO.getAllLectureMaterials(idLecture);

		List<AbstractFileBean> materials = new ArrayList<AbstractFileBean>();

		for (Material material : allLectureMaterials) {

			String name = material.getName();
			String filePath = FileManager.RESOURCES_PATH + File.separator + idCourse + File.separator + FileManager.LECTURES_PATH + File.separator + idLecture + File.separator + FileManager.MATERIALS_PATH + File.separator + name;;

			File file = new File(filePath);
			if(file.exists()){
				FileBean fileBean = FileBean.toFileBean(file);
				fileBean.setId(material.getId());
				fileBean.setParentId(idLecture);
				materials.add(fileBean);
			}
		}	

		model.addAttribute("contents", allLectureMaterials);
		model.addAttribute("files", materials);
		model.addAttribute("parentId",idLecture);

		if(canCreate(request))
			model.addAttribute("canCreate", true);

		String referred = "/lectures/lectureContent?parentId=" + idLecture;
		model.addAttribute("backPage", referred);

		logger.info("getMaterials");

		return "layout";
	}


	/**
	 * creates a new class
	 * @param model
	 * @param lecture the informations about the new Lecture
	 * @return classPage.jsp
	 */
	@RequestMapping(value = "/lectures", method = RequestMethod.POST)
	public String createLecture(@Valid @ModelAttribute("lecture") Lecture lectureWrapper, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes redirect) {

		checkLogin();
		
		model.addAttribute("lectureAction", "/lectures");
		if(result.hasErrors()){

			model.addAttribute("modalState", "_open");
			model.addAttribute("lecture", lectureWrapper);

			if(canCreate(request))
				model.addAttribute("canCreate", true);

			return getLectures(model, request);
		}

		Lecture lecture = lectureWrapper;//.getLecture();
		if(lecture.getId() > 0)
			return updateLecture(lecture, result, request, model, redirect);

		int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
		String courseName = Integer.toString(idCourse);

		String currentPath = courseName + File.separator + FileManager.LECTURES_PATH;

		//retrieves the owner course of the new lesson
		CourseClassDAO courseClassDao = appContext.getBean("courseClassDAO",CourseClassDAOImpl.class);
		CourseClass courseClass = courseClassDao.get(idCourse);

		lecture.setCourseClass(courseClass);

		//creates the new lecture
		LectureDAO lectureDao = appContext.getBean("lectureDAO",LectureDAOImpl.class);

		//retrieves the informations about the last lesson owned by the logged user
		Lecture lastLecture = lectureDao.getLastLectureAdded(courseClass.getId());

		//One more than the last lecture
		int number = 1;
		if(lastLecture != null)
			number = lastLecture.getNumber() + 1;

		lecture.setNumber(number);
		lecture.setDate(lecture.getDate());
		lecture.setClassroom(lecture.getClassroom());

		Lecture newLecture = lectureDao.create(lecture);
		if(newLecture == null){
			redirect.addAttribute("error", "server error when creating lecture");
			return "redirect:/sessionerror";
		}

		int newId = newLecture.getId();

		createCalendarEvent(lecture, courseClass);

		//creates the corresponding folder
		String name = Integer.toString(newId);
		boolean success = false;

		success = new FileManager().mkDir(currentPath, name);
		if(success){
			success = new FileManager().mkDir(currentPath + File.separator + name, FileManager.HOMEWORK_PATH);
			if(success)
				success = new FileManager().mkDir(currentPath + File.separator + name, FileManager.MATERIALS_PATH);
		}

		if(!success){
			logger.error("failed to create directory " + name + " in " + currentPath);
			lectureDao.delete(lectureDao.get(newId));
			return "layout";
		}

		logger.info("createClass");

		return "redirect:/lectures";

	}



	private void checkLogin() {
		// TODO Auto-generated method stub
		
	}


	@RequestMapping(value = "/lectures/update_lecture", method = RequestMethod.POST)
	public String updateLecture(@Valid @ModelAttribute("lecture") Lecture lecture, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes redirect) {

		if(result.hasErrors()){

			model.addAttribute("modalState", "_open");
			model.addAttribute("lecture", lecture);
			model.addAttribute("lectureAction", "/lectures/update_lecture");

			if(canCreate(request))
				model.addAttribute("canCreate", true);

			return getLectures(model, request);
		}

		LectureDAO lectureDao = appContext.getBean("lectureDAO",LectureDAOImpl.class);
		Lecture old = lectureDao.get(lecture.getId());

		if(!lecture.getTopic().equals(old.getTopic()) && !lecture.getTopic().equals(""))
			old.setTopic(lecture.getTopic());

		if(!lecture.getDescription().equals(old.getDescription()) && !lecture.getDescription().equals(""))
			old.setDescription(lecture.getDescription());

		if(lecture.getDate() != null)
			if(lecture.getDate().compareTo(old.getDate()) != 0)
				old.setDate(lecture.getDate());

		if(lecture.getBeginHour() != null)
			if(lecture.getBeginHour().compareTo(old.getBeginHour()) != 0)
				old.setBeginHour(lecture.getBeginHour());

		if(lecture.getEndHour() != null)
			if(lecture.getEndHour().compareTo(old.getEndHour()) != 0)
				old.setEndHour(lecture.getEndHour());

		if(!lecture.getClassroom().equals(old.getClassroom()) && !lecture.getClassroom().equals(""))
			old.setClassroom(lecture.getClassroom());

		lectureDao.update(old);

		logger.info("update lecture");

		return "redirect:/lectures"; 
	}

	/**
	 * Handles the requests to upload a new file. Creates the file in the "path" in the file system
	 * @param file the file uploaded
	 * @param parentId the id of the parentFolder
	 * @return
	 */
	@RequestMapping(value="/lectures/upload_materials", method=RequestMethod.POST)
	public @ResponseBody String uploadMaterial(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("parentId") int lectureId) {

		int idCourse =  (Integer) request.getSession().getAttribute("ActiveCourse");
		String courseName = Integer.toString(idCourse);

		Lecture lecture = appContext.getBean("lectureDAO",LectureDAOImpl.class).get(lectureId);
		String folder_name = Integer.toString(lectureId);

		String path = courseName + File.separator + FileManager.LECTURES_PATH + File.separator + folder_name + File.separator + FileManager.MATERIALS_PATH;
		String filePath = FileManager.RESOURCES_PATH + File.separator + path + File.separator;
		String fileName = generateName(filePath, file);

		boolean success = new FileManager().mkMultipartFile(file, path, fileName);
		if(!success){
			logger.error("cannot save the file " + path + "/" + file.getOriginalFilename());
			return "500";
		}

		Material material = new Material();

		material.setName(fileName);
		material.setFilePath(filePath + fileName);
		material.setHidden(false);
		material.setType(file.getContentType().split("/")[1]);
		material.setLecture(lecture);

		MaterialDAO materialDAO = appContext.getBean("materialDAO", MaterialDAOImpl.class);
		Material newMaterial = materialDAO.create(material);
		if(newMaterial == null){

			new FileManager().deleteDirectory(filePath + fileName);
			return "400";
		}

		logger.info("saving file");

		return "200";
	}


	@RequestMapping(value="/lectures/delete_lecture", method=RequestMethod.POST)
	public String deleteLecture(@RequestParam("lectureId") int id) {

		LectureDAOImpl lectureDao = appContext.getBean("lectureDAO",LectureDAOImpl.class);
		Lecture lecture = lectureDao.get(id);

		if(lecture != null)
			lectureDao.delete(lecture);
		else
			return "redirect:/lectures";

		String name = Integer.toString(lecture.getId());
		String path = FileManager.RESOURCES_PATH + File.separator + "enterpriseApplication" + File.separator + FileManager.LECTURES_PATH + File.separator + name;

		boolean success = new FileManager().deleteDirectory(path);

		if(!success){
			logger.info("cannot delete the file " + path);
		}


		return "redirect:/lectures";
	}

	@RequestMapping(value="/lectures/delete_materials", method=RequestMethod.POST)
	public String deleteMaterial(@RequestParam("materialId") int id) {

		MaterialDAO materialDAO = appContext.getBean("materialDAO", MaterialDAOImpl.class);
		Material material = materialDAO.get(id);

		if(material != null)
			materialDAO.delete(material);
		else
			return "redirect:/lectures";

		String path = material.getFilePath();
		boolean success = new FileManager().deleteFile(path);

		if(!success){
			logger.info("cannot delete the file " + path);
		}

		return "redirect:/lectures/materials?parentId=" + material.getLecture().getId();
	}

	/*
	 * simply adapt java File to an useful format required by the view
	 */
	private void adaptLecture(List<AbstractFileBean> dest, List<Lecture> source, String currentPath) {

		for (Lecture lecture : source) {

			String name = lecture.getNumber() + " - " + lecture.getTopic();
			String folderPath = currentPath + File.separator + lecture.getId();

			File f = new File(folderPath);
			int childs = 0;
			if(f.exists())
				childs = new File(folderPath).listFiles().length;

			FolderBean folder = new FolderBean(lecture.getId(), name, AbstractFileBean.FOLDER_TYPE, folderPath, childs);
			folder.setAction("/lectures/lectureContent");
			folder.setParentId(lecture.getId());
			dest.add(folder);
		}	
	}

	/*
	 * creates a calendar event starting from the new lecture
	 */
	private void createCalendarEvent(Lecture lecture, CourseClass courseClass) {

		Event event = new Event();
		event.setColor("##0000ff");
		event.setDescription(lecture.getDescription());
		event.setEndDate(lecture.getDate());
		event.setStartDate(lecture.getDate());
		event.setHourBegin(lecture.getBeginHour());
		event.setHourEnd(lecture.getEndHour());
		event.setPlace("");
		event.setTitle(lecture.getTopic());
		event.setUser(lecture.getCourseClass().getProfessor());
		event.setType(Event.EVENT_LECTURE_TYPE);
		//TODO
		event.setCourseClass(courseClass);

		EventDAO eventDao = appContext.getBean("eventDao",EventDAOImpl.class);
		eventDao.create(event);

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

	private String generateName(String filePath, MultipartFile file){

		String newFileName = file.getOriginalFilename();
		if(new File(filePath + newFileName).exists()) {

			ArrayList<String> fileNameSplitted = new ArrayList<String>(Arrays.asList(file.getOriginalFilename().split("\\.(?=[^\\.]+$)")));
			if(fileNameSplitted.size() == 1) {
				fileNameSplitted.add("");
			}
			else {
				fileNameSplitted.add(1, "." + fileNameSplitted.get(1));
			}


			String name = fileNameSplitted.get(0);
			String extension = fileNameSplitted.get(1);
			name += "_" + new Random(System.currentTimeMillis()).nextInt(1000000);

			newFileName = name + extension;
		}

		return newFileName = StringEscapeUtils.escapeSql(newFileName);
	}

}
