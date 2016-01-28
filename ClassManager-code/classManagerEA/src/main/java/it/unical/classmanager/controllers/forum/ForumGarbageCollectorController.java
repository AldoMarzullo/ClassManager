package it.unical.classmanager.controllers.forum;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unical.classmanager.controllers.forum.manager.ForumAttachmentGCManager;
import it.unical.classmanager.model.dao.AnswerAttachedContentDAO;
import it.unical.classmanager.model.dao.AnswerAttachedContentDAOImpl;
import it.unical.classmanager.model.dao.QuestionAttachedContentDAO;
import it.unical.classmanager.model.dao.QuestionAttachedContentDAOImpl;

/**
 * Handles requests for the forum page.
 */
@Controller
public class ForumGarbageCollectorController {
	
	@Autowired
	private ApplicationContext appContext;
	

	
	@RequestMapping(value = "/gc", method = RequestMethod.GET)
	public String attachmentGarbageCollect(Locale locale, Model model) {
		
		QuestionAttachedContentDAO questionAttachedDAO = (QuestionAttachedContentDAOImpl) appContext.getBean("questionAttachedContentDAO", QuestionAttachedContentDAOImpl.class);
		AnswerAttachedContentDAO answerAttachedDAO = (AnswerAttachedContentDAOImpl) appContext.getBean("answerAttachedContentDAO", AnswerAttachedContentDAOImpl.class);
		
		ForumAttachmentGCManager gc = new ForumAttachmentGCManager(questionAttachedDAO, answerAttachedDAO);
		gc.garbageCollect();
		
		return "redirect:/";
	}
	
}
