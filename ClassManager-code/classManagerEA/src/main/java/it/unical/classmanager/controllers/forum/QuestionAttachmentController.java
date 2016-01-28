package it.unical.classmanager.controllers.forum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.unical.classmanager.model.dao.QuestionAttachedContentDAO;
import it.unical.classmanager.model.dao.QuestionAttachedContentDAOImpl;
import it.unical.classmanager.model.data.QuestionAttachedContent;
import it.unical.classmanager.utils.FileManager;

/**
 * Handles requests for the forum page.
 */
@Controller
public class QuestionAttachmentController {
	
	@Autowired
	private ApplicationContext appContext;
	
	
	@RequestMapping(value = "/forum/uploadQuestionAttachment", method = RequestMethod.POST)
	public @ResponseBody String uploadQuestionAttachment(Locale locale, Model model, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		
		String username = (String) request.getSession().getAttribute("loggedIn");
		
		FileManager fm = new FileManager();
		String filePath = "files/forumAttachment/questionAttachment/" + username + "/" + file.getOriginalFilename();

		String newFileName = file.getOriginalFilename();
		if(new File(filePath).exists()) {
			
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
			filePath = "files/forumAttachment/questionAttachment/" + username + "/" + newFileName;
		}
		
		newFileName = StringEscapeUtils.escapeSql(newFileName);
		filePath = StringEscapeUtils.escapeSql(filePath);
		
		fm.mkMultipartFile(file, "forumAttachment/questionAttachment/" + username + "/", newFileName);
		
		
		QuestionAttachedContentDAO questionAttachedDAO = (QuestionAttachedContentDAOImpl) appContext.getBean("questionAttachedContentDAO", QuestionAttachedContentDAOImpl.class);
		
		QuestionAttachedContent questionAttached = new QuestionAttachedContent();
		
		File tmpFile = new File(filePath);
		String tmpMimeType = null;
		try {
			tmpMimeType = Files.probeContentType(tmpFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		questionAttached.setName(newFileName);
		questionAttached.setFilePath(filePath);
		questionAttached.setType(tmpMimeType);
		questionAttached.setCreationDate(new Date());
		questionAttached = questionAttachedDAO.create(questionAttached);
		
		
		return "{\"name\": \""+ newFileName +"\", \"id\": \""+ Integer.toString(questionAttached.getId()) +"\", \"type\":\"insert\"}";
	}
	
	
	
	@RequestMapping(value = "/forum/removeQuestionAttachment", method = RequestMethod.POST)
	public @ResponseBody boolean removeQuestionAttachment(Locale locale, Model model, HttpServletRequest request) {
		
		int attachedFilesIDRemove = Integer.parseInt(request.getParameter("attachedIdRemove"));
		
		QuestionAttachedContentDAO questionAttachedDAO = (QuestionAttachedContentDAOImpl) appContext.getBean("questionAttachedContentDAO", QuestionAttachedContentDAOImpl.class);
		
		try{
			
			QuestionAttachedContent questionAttached = questionAttachedDAO.get(attachedFilesIDRemove);
			
			FileManager fm = new FileManager();
			fm.deleteFile(questionAttached.getFilePath());
			
			questionAttachedDAO.delete(questionAttached);
			
			return true;
		}
		catch(Exception e) {
			return false;
		}
	
	}
	
	
	
}
