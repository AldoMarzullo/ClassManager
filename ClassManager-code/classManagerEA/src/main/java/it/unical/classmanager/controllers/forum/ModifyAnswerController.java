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
import it.unical.classmanager.model.dao.AnswerAttachedContentDAO;
import it.unical.classmanager.model.dao.AnswerAttachedContentDAOImpl;
import it.unical.classmanager.model.dao.AnswerDAO;
import it.unical.classmanager.model.dao.AnswerDAOImpl;
import it.unical.classmanager.model.dao.QuestionDAO;
import it.unical.classmanager.model.dao.QuestionDAOImpl;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.dao.UserDAOImpl;
import it.unical.classmanager.model.data.Answer;
import it.unical.classmanager.model.data.AnswerAttachedContent;
import it.unical.classmanager.model.data.Question;
import it.unical.classmanager.model.data.User;

/**
 * Handles requests for the forum page.
 */
@Controller
public class ModifyAnswerController {
	
	@Autowired
	private ApplicationContext appContext;
	
	private final static String HEADER = "forum/modifyAnswerHead.jsp";
	private final static String BODY = "forum/modifyAnswerBody.jsp";
	
	@RequestMapping(value = "/forum/modifyAnswer", method = RequestMethod.POST)
	public String modifyAnswer(Locale locale, Model model, HttpServletRequest request) {
		
		QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		int qid = Integer.parseInt(request.getParameter("qid"));
		Question question = questionDAO.get(qid);

		model.addAttribute("question", question);
		
		AnswerDAO answerDAO = (AnswerDAOImpl) appContext.getBean("answerDAO", AnswerDAOImpl.class);
		int aid = Integer.parseInt(request.getParameter("aid"));
		Answer answer = answerDAO.get(aid);
		
		model.addAttribute("answer", answer);
		
		List<AnswerAttachedContent> preAttachements = new ArrayList<AnswerAttachedContent>();
		String preAttachmentsID = "";
		
		for(AnswerAttachedContent tmpAttachment : answer.getAnswerAttachedContents()) {
			preAttachmentsID += tmpAttachment.getId() + ";";
			preAttachements.add(tmpAttachment);
		}
		
		preAttachmentsID = preAttachmentsID.substring(0, preAttachmentsID.length()-1);
		
		model.addAttribute("preAttachments", preAttachements);
		model.addAttribute("preAttachmentsID", preAttachmentsID);
		
			
		model.addAttribute("customHeader", ModifyAnswerController.HEADER);
		model.addAttribute("customBody", ModifyAnswerController.BODY);
		
		return "layout";
	}
	
	
	@RequestMapping(value = "/forum/updateAnswer", method = RequestMethod.POST)
	public String updateAnswer(@Valid @ModelAttribute("answer") Answer answer, BindingResult result, Locale locale, Model model, HttpServletRequest request) {
		
		escapizeAnswerModel(answer);
		
		if(result.hasErrors()) {
			
			System.out.println("Errore nei dati");
			
			
			//PRE ATTACHMENT
			List<AnswerAttachedContent> preAttachements = new ArrayList<AnswerAttachedContent>();
			String preAttachmentsID = request.getParameter("preAttachedFilesID");
			
			if(preAttachmentsID != null && !preAttachmentsID.equals("")) {
				
				StringTokenizer tokenizer = new StringTokenizer(preAttachmentsID, ";");
				AnswerAttachedContentDAO answerAttachedDAO = (AnswerAttachedContentDAOImpl) appContext.getBean("answerAttachedContentDAO", AnswerAttachedContentDAOImpl.class);
			
				while(tokenizer.hasMoreTokens()) {
					String tmpID = tokenizer.nextToken();
					
					AnswerAttachedContent tmpAnswerContent = answerAttachedDAO.get(Integer.parseInt(tmpID));
					preAttachements.add(tmpAnswerContent);
				}
			}
			
			model.addAttribute("preAttachments", preAttachements);
			model.addAttribute("preAttachmentsID", preAttachmentsID);
			
			//NEW ATTACHMENT
			String newAttachedFilesID = request.getParameter("newAttachedFilesID");
			List<AnswerAttachedContent> newAttachemnts = new ArrayList<AnswerAttachedContent>();
			
			if(newAttachedFilesID != null && !newAttachedFilesID.equals("")) {
				
				StringTokenizer tokenizer = new StringTokenizer(newAttachedFilesID, ";");
				AnswerAttachedContentDAO answerAttachedDAO = (AnswerAttachedContentDAOImpl) appContext.getBean("answerAttachedContentDAO", AnswerAttachedContentDAOImpl.class);
			
				while(tokenizer.hasMoreTokens()) {
					String tmpID = tokenizer.nextToken();
					
					AnswerAttachedContent tmpAnswerContent = answerAttachedDAO.get(Integer.parseInt(tmpID));
					newAttachemnts.add(tmpAnswerContent);
				}
			}

			
			model.addAttribute("newAttachments", newAttachemnts);
			model.addAttribute("newAttachmentsID", newAttachedFilesID);
			
			QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
			int qid = Integer.parseInt(request.getParameter("qid"));
			Question question = questionDAO.get(qid);

			model.addAttribute("question", question);
			
			model.addAttribute("customHeader", ModifyAnswerController.HEADER);
			model.addAttribute("customBody", ModifyAnswerController.BODY);
			
			return "layout";
		}
		
		
		UserDAO userDao = appContext.getBean("userDao",UserDAOImpl.class);
		QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		AnswerDAO answerDAO = (AnswerDAOImpl) appContext.getBean("answerDAO", AnswerDAOImpl.class);
		
		
		String username = (String) request.getSession().getAttribute("loggedIn");
		User tmpUser = userDao.get(username);
		
		int qid = Integer.parseInt((String) request.getParameter("qid"));
		Question tmpQuestion = questionDAO.get(qid);
		
		answer.setUser(tmpUser);
		answer.setQuestion(tmpQuestion);
		
		Answer newAnswer =  answerDAO.update(answer);
		
		String attachedFilesID = request.getParameter("attachedFiles");
		
		if(attachedFilesID != null && !attachedFilesID.equals("")) {
			
			StringTokenizer tokenizer = new StringTokenizer(attachedFilesID, ";");
			AnswerAttachedContentDAO answerAttachedDAO = (AnswerAttachedContentDAOImpl) appContext.getBean("answerAttachedContentDAO", AnswerAttachedContentDAOImpl.class);
		
			while(tokenizer.hasMoreTokens()) {
				String tmpID = tokenizer.nextToken();
				
				AnswerAttachedContent tmpAnswerContent = answerAttachedDAO.get(Integer.parseInt(tmpID));
				tmpAnswerContent.setAnswer(newAnswer);
				answerAttachedDAO.update(tmpAnswerContent);
			}
		}
		
			
		return "redirect:detailedQuestion?qid=" + qid;
	}
	
	
	
	private void escapizeAnswerModel(Answer answer) {
		
		answer.setDescription(EscapeManager.escapizeString(answer.getDescription()));
		if(answer.getDescription().equals("")) {
			answer.setDescription("Description not inserted");
		}
		
	}
	
}
