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

import it.unical.classmanager.model.dao.AnswerAttachedContentDAO;
import it.unical.classmanager.model.dao.AnswerAttachedContentDAOImpl;
import it.unical.classmanager.model.data.AnswerAttachedContent;
import it.unical.classmanager.utils.FileManager;

/**
 * Handles requests for the forum page.
 */
@Controller
public class AnswerAttachmentController {
	
	@Autowired
	private ApplicationContext appContext;
	
	
	
	@RequestMapping(value = "/forum/uploadAnswerAttachment", method = RequestMethod.POST)
	public @ResponseBody String uploadAnswerAttachment(Locale locale, Model model, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		
		String username = (String) request.getSession().getAttribute("loggedIn");
		
		FileManager fm = new FileManager();
		String filePath = "files/forumAttachment/answerAttachment/" + username + "/" + file.getOriginalFilename();

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
			filePath = "files/forumAttachment/answerAttachment/" + username + "/" + newFileName;
		}
		
		newFileName = StringEscapeUtils.escapeSql(newFileName);
		filePath = StringEscapeUtils.escapeSql(filePath);
		
		fm.mkMultipartFile(file, "forumAttachment/answerAttachment/" +  username + "/", newFileName);
		
		
		AnswerAttachedContentDAO answerAttachedDAO = (AnswerAttachedContentDAOImpl) appContext.getBean("answerAttachedContentDAO", AnswerAttachedContentDAOImpl.class);
		AnswerAttachedContent answerAttached = new AnswerAttachedContent();
		
		File tmpFile = new File(filePath);
		String tmpMimeType = null;
		try {
			tmpMimeType = Files.probeContentType(tmpFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

			
		answerAttached.setName(newFileName);
		answerAttached.setFilePath(filePath);
		answerAttached.setType(tmpMimeType);
		answerAttached.setCreationDate(new Date());
		answerAttached = answerAttachedDAO.create(answerAttached);
			
		
		return "{\"name\": \""+ newFileName +"\", \"id\": \""+ Integer.toString(answerAttached.getId()) +"\", \"type\":\"insert\"}";
	}
	
	
	
	@RequestMapping(value = "/forum/removeAnswerAttachment", method = RequestMethod.POST)
	public @ResponseBody boolean removeAnswerAttachment(Locale locale, Model model, HttpServletRequest request) {
		
		
		int attachedFilesIDRemove = Integer.parseInt(request.getParameter("attachedIdRemove"));
		
		AnswerAttachedContentDAO answerAttachedDAO = (AnswerAttachedContentDAOImpl) appContext.getBean("answerAttachedContentDAO", AnswerAttachedContentDAOImpl.class);
		
		try{
			
			AnswerAttachedContent answerAttached = answerAttachedDAO.get(attachedFilesIDRemove);
			
			FileManager fm = new FileManager();
			fm.deleteFile(answerAttached.getFilePath());
			
			answerAttachedDAO.delete(answerAttached);
			
			return true;
		}
		catch(Exception e) {
			return false;
		}
	
	}
	
	
}
