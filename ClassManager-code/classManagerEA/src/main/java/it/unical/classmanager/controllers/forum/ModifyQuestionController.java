package it.unical.classmanager.controllers.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unical.classmanager.controllers.forum.xss.EscapeManager;
import it.unical.classmanager.model.dao.QuestionAttachedContentDAO;
import it.unical.classmanager.model.dao.QuestionAttachedContentDAOImpl;
import it.unical.classmanager.model.dao.QuestionDAO;
import it.unical.classmanager.model.dao.QuestionDAOImpl;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.dao.UserDAOImpl;
import it.unical.classmanager.model.data.Question;
import it.unical.classmanager.model.data.QuestionAttachedContent;
import it.unical.classmanager.model.data.User;


/**
 * Handles requests for the forum page.
 */
@Controller
public class ModifyQuestionController {
	
	@Autowired
	private ApplicationContext appContext;
	
	private final static String HEADER = "forum/modifyQuestionHead.jsp";
	private final static String BODY = "forum/modifyQuestionBody.jsp";
	
	
	@RequestMapping(value = "/forum/modifyQuestion", method = RequestMethod.POST)
	public String modifyQuestion(Locale locale, Model model, HttpServletRequest request) {
		
		int questionID = Integer.parseInt(request.getParameter("qid"));
		System.out.println("ID: " + questionID);
		
		QuestionDAOImpl dao = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		Question question = dao.get(questionID);
		
		model.addAttribute("question", question);
		
		List<QuestionAttachedContent> preAttachements = new ArrayList<QuestionAttachedContent>();
		String preAttachmentsID = "";
		
		for(QuestionAttachedContent tmpAttachment : question.getQuestionAttachedContents()) {
			preAttachmentsID += tmpAttachment.getId() + ";";
			preAttachements.add(tmpAttachment);
		}
		
		if(preAttachmentsID.length() != 0) {
			
			preAttachmentsID = preAttachmentsID.substring(0, preAttachmentsID.length()-1);
		}
		
		model.addAttribute("preAttachments", preAttachements);
		model.addAttribute("preAttachmentsID", preAttachmentsID);
		
		String username = (String) request.getSession().getAttribute("loggedIn");
		model.addAttribute("username", username);
		
		model.addAttribute("customHeader", ModifyQuestionController.HEADER);
		model.addAttribute("customBody", ModifyQuestionController.BODY);
		
		return "layout";
	}
	
	
	@RequestMapping(value = "/forum/updateQuestion", method = RequestMethod.POST)
	public String updateQuestion(@Valid @ModelAttribute("question") Question question, BindingResult result, Locale locale, Model model, HttpServletRequest request) {
		
		escapizeQuestionModel(question);
		
		if(result.hasErrors()) {
			
			System.out.println("Errore nei dati");
			
			
			//PRE ATTACHMENT
			List<QuestionAttachedContent> preAttachements = new ArrayList<QuestionAttachedContent>();
			String preAttachmentsID = request.getParameter("preAttachedFilesID");
			
			if(preAttachmentsID != null && !preAttachmentsID.equals("")) {
				
				StringTokenizer tokenizer = new StringTokenizer(preAttachmentsID, ";");
				QuestionAttachedContentDAO questionAttachedDAO = (QuestionAttachedContentDAOImpl) appContext.getBean("questionAttachedContentDAO", QuestionAttachedContentDAOImpl.class);
			
				while(tokenizer.hasMoreTokens()) {
					String tmpID = tokenizer.nextToken();
					
					QuestionAttachedContent tmpQuestionContent = questionAttachedDAO.get(Integer.parseInt(tmpID));
					preAttachements.add(tmpQuestionContent);
				}
			}
			
			model.addAttribute("preAttachments", preAttachements);
			model.addAttribute("preAttachmentsID", preAttachmentsID);
			
			//NEW ATTACHMENT
			String newAttachedFilesID = request.getParameter("newAttachedFilesID");
			List<QuestionAttachedContent> newAttachemnts = new ArrayList<QuestionAttachedContent>();
			
			if(newAttachedFilesID != null && !newAttachedFilesID.equals("")) {
				
				StringTokenizer tokenizer = new StringTokenizer(newAttachedFilesID, ";");
				QuestionAttachedContentDAO questionAttachedDAO = (QuestionAttachedContentDAOImpl) appContext.getBean("questionAttachedContentDAO", QuestionAttachedContentDAOImpl.class);
			
				while(tokenizer.hasMoreTokens()) {
					String tmpID = tokenizer.nextToken();
					
					QuestionAttachedContent tmpQuestionContent = questionAttachedDAO.get(Integer.parseInt(tmpID));
					newAttachemnts.add(tmpQuestionContent);
				}
			}
			
			
			model.addAttribute("newAttachments", newAttachemnts);
			model.addAttribute("newAttachmentsID", newAttachedFilesID);
			
			String username = (String) request.getSession().getAttribute("loggedIn");
			model.addAttribute("username", username);
			
			model.addAttribute("customHeader", ModifyQuestionController.HEADER);
			model.addAttribute("customBody", ModifyQuestionController.BODY);
			
			return "layout";
		}
		
		UserDAO userDao = appContext.getBean("userDao",UserDAOImpl.class);
		QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		
		String username = (String) request.getSession().getAttribute("loggedIn");

		User tmpUser = userDao.get(username);
		question.setUser(tmpUser);
		
		Question newQuestion =  questionDAO.update(question);
		
		String newAttachedFilesID = request.getParameter("newAttachedFilesID");
		
		if(newAttachedFilesID != null && !newAttachedFilesID.equals("")) {
			
			StringTokenizer tokenizer = new StringTokenizer(newAttachedFilesID, ";");
			QuestionAttachedContentDAO questionAttachedDAO = (QuestionAttachedContentDAOImpl) appContext.getBean("questionAttachedContentDAO", QuestionAttachedContentDAOImpl.class);
		
			while(tokenizer.hasMoreTokens()) {
				String tmpID = tokenizer.nextToken();
				
				QuestionAttachedContent tmpQuestionContent = questionAttachedDAO.get(Integer.parseInt(tmpID));
				tmpQuestionContent.setQuestion(newQuestion);
				questionAttachedDAO.update(tmpQuestionContent);
			}
		}
		
		return "redirect:questions";
	}
	
	
	
	private void escapizeQuestionModel(Question question) {
		
		question.setTitle(EscapeManager.escapizeString(question.getTitle()));
		if(question.getTitle().equals("")) {
			question.setTitle("Title not inserted");
		}
		
		question.setDescription(EscapeManager.escapizeString(question.getDescription()));
		if(question.getDescription().equals("")) {
			question.setDescription("Description not inserted");
		}
		
		question.setTags(EscapeManager.escapizeString(question.getTags()));
	}
	
	
}
