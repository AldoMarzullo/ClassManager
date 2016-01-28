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
public class InsertQuestionController {
	
	@Autowired
	private ApplicationContext appContext;
	
	private final static String HEADER = "forum/insertQuestionHead.jsp";
	private final static String BODY = "forum/insertQuestionBody.jsp";
	
	
	@RequestMapping(value = "/forum/insertQuestion", method = RequestMethod.GET)
	public String createQuestion(Locale locale, Model model, HttpServletRequest request) {
		
		String username = (String) request.getSession().getAttribute("loggedIn");
		model.addAttribute("username", username);
		
		Question question = new Question();
		model.addAttribute("question", question);
		
		
		model.addAttribute("customHeader", InsertQuestionController.HEADER);
		model.addAttribute("customBody", InsertQuestionController.BODY);
		
		return "layout";
	}
	
	
	@RequestMapping(value = "/forum/insertQuestion", method = RequestMethod.POST)
	public String insertQuestion(@Valid @ModelAttribute("question") Question question, BindingResult result, Locale locale, Model model, HttpServletRequest request) {
		
		escapizeQuestionModel(question);
		
		if(result.hasErrors()) {
			
			System.out.println("Errore nei dati");
			
			
			List<QuestionAttachedContent> attachemnts = new ArrayList<QuestionAttachedContent>();
			String attachedFilesID = request.getParameter("attachedFiles");

			if(attachedFilesID != null && !attachedFilesID.equals("")) {
				
				StringTokenizer tokenizer = new StringTokenizer(attachedFilesID, ";");
				QuestionAttachedContentDAO questionAttachedDAO = (QuestionAttachedContentDAOImpl) appContext.getBean("questionAttachedContentDAO", QuestionAttachedContentDAOImpl.class);
			
				while(tokenizer.hasMoreTokens()) {
					String tmpID = tokenizer.nextToken();
					
					QuestionAttachedContent tmpQuestionContent = questionAttachedDAO.get(Integer.parseInt(tmpID));
					attachemnts.add(tmpQuestionContent);
				}
			}
			
			String username = (String) request.getSession().getAttribute("loggedIn");
			model.addAttribute("username", username);
			
			model.addAttribute("attachments", attachemnts);
			model.addAttribute("attachmentsID", attachedFilesID);
			
			model.addAttribute("customHeader", InsertQuestionController.HEADER);
			model.addAttribute("customBody", InsertQuestionController.BODY);
			
			return "layout";
		}
		
		
		UserDAO userDao = appContext.getBean("userDao",UserDAOImpl.class);
		QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		
		String username = (String) request.getSession().getAttribute("loggedIn");
		
		User tmpUser = userDao.get(username);
		question.setUser(tmpUser);
		
		
		Question newQuestion =  questionDAO.create(question);
		
		String attachedFilesID = request.getParameter("attachedFiles");
		
		if(attachedFilesID != null && !attachedFilesID.equals("")) {
			
			StringTokenizer tokenizer = new StringTokenizer(attachedFilesID, ";");
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
	
	
	@RequestMapping(value = "/forum/deleteQuestion", method = RequestMethod.POST)
	public String deleteQuestion(Locale locale, Model model, HttpServletRequest request) {
		
		QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		
		int questionID = Integer.parseInt(request.getParameter("qid"));
		
		Question question = questionDAO.get(questionID);
		questionDAO.delete(question);
		
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
