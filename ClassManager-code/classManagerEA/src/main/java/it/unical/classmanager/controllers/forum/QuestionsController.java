package it.unical.classmanager.controllers.forum;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.unical.classmanager.controllers.forum.manager.QuestionManager;
import it.unical.classmanager.model.dao.QuestionDAO;
import it.unical.classmanager.model.dao.QuestionDAOImpl;
import it.unical.classmanager.model.data.Question;

/**
 * Handles requests for the forum page.
 */
@Controller
public class QuestionsController {
	
	@Autowired
	private ApplicationContext appContext;
	
	private QuestionManager questionManager;
	
	private final static String HEADER = "forum/questionsHead.jsp";
	private final static String BODY = "forum/questionsBody.jsp";
	

	@RequestMapping(value = {"/questions", "/forum"}, method = RequestMethod.GET)
	public String redirectToQuestions(Locale locale, Model model) {
		return "redirect:/forum/questions";
	}
	
	
	@RequestMapping(value = "/forum/questions", method = RequestMethod.GET)
	public String getQuestions(Locale locale, Model model) {
		
		QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		List<Question> allQuestions = questionDAO.getAllQuestions();
		
		this.questionManager = new QuestionManager(allQuestions);
		
		model.addAttribute("questions", this.questionManager.getCurrentPageQuestions());
		model.addAttribute("pageCount", this.questionManager.getPageCount());
		
		model.addAttribute("elemNum", this.questionManager.getElementCount());
		
		model.addAttribute("currPage", Integer.toString(this.questionManager.getCurrentPageNumber()+1));
		model.addAttribute("pageSize", this.questionManager.getPageSize());
		
		
		model.addAttribute("customHeader", QuestionsController.HEADER);
		model.addAttribute("customBody", QuestionsController.BODY);
		
		return "layout";
	}
	
	
	
	
	@RequestMapping(value = "/forum/questionsPage", method = RequestMethod.GET)
	public String getSpecificPage(Locale locale, Model model, @RequestParam("page") int pageNumber) {
		
		QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		List<Question> allQuestions = questionDAO.getAllQuestions();
		
		this.questionManager = new QuestionManager(allQuestions);
		
		model.addAttribute("questions", this.questionManager.getSpecificPageQuestions(pageNumber));
		model.addAttribute("pageCount", this.questionManager.getPageCount());
		
		model.addAttribute("elemNum", this.questionManager.getElementCount());
		
		model.addAttribute("currPage", Integer.toString(this.questionManager.getCurrentPageNumber()+1));
		model.addAttribute("pageSize", this.questionManager.getPageSize());
		
		model.addAttribute("customHeader", QuestionsController.HEADER);
		model.addAttribute("customBody", QuestionsController.BODY);
		
		return "layout";
	}
	
	
}
