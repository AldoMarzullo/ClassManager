package it.unical.classmanager.controllers.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unical.classmanager.controllers.forum.data.QuestionSearchSetting;
import it.unical.classmanager.controllers.forum.manager.QuestionManager;
import it.unical.classmanager.model.dao.QuestionDAO;
import it.unical.classmanager.model.dao.QuestionDAOImpl;
import it.unical.classmanager.model.data.Question;

/**
 * Handles requests for the forum page.
 */
@Controller
public class SearchQuestionController {
	
	@Autowired
	private ApplicationContext appContext;
	
	private QuestionManager questionManager;
	
	private final static String HEADER = "forum/searchQuestionHead.jsp";
	private final static String BODY = "forum/searchQuestionBody.jsp";
	
	@RequestMapping(value = "/forum/searchQuestion", method = RequestMethod.GET)
	public String getSearchQuestionForm(Locale locale, Model model) {
		
		
		model.addAttribute("searchSettings", new QuestionSearchSetting());
		model.addAttribute("questionsList", new ArrayList<Question>());
		
		model.addAttribute("customHeader", SearchQuestionController.HEADER);
		model.addAttribute("customBody", SearchQuestionController.BODY);
		
		return "layout";
	}
	
	
	
	@RequestMapping(value = "/forum/searchQuestion", method = RequestMethod.POST)
	public String getSearchedQuestion(Locale locale, Model model, @ModelAttribute("searchSettings") QuestionSearchSetting searchSettings) {
		
		QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		List<Question> searchedQuestions = questionDAO.searchQuestion(searchSettings);
		this.questionManager = new QuestionManager(searchedQuestions);
		
		model.addAttribute("searchSettings", searchSettings);
		
		model.addAttribute("questionsList", searchedQuestions);
		
		
		model.addAttribute("questionsList", this.questionManager.getCurrentPageQuestions());
		model.addAttribute("pageCount", this.questionManager.getPageCount());
		
		model.addAttribute("elemNum", this.questionManager.getElementCount());
		
		model.addAttribute("currPage", Integer.toString(this.questionManager.getCurrentPageNumber())+1);
		model.addAttribute("pageSize", this.questionManager.getPageSize());
		
		
		model.addAttribute("customHeader", SearchQuestionController.HEADER);
		model.addAttribute("customBody", SearchQuestionController.BODY);
		
		return "layout";
	}
	
	
	
	@RequestMapping(value = "/forum/questionsSearchedPage", method = RequestMethod.POST)
	public String getSpecificSearchedPage(Locale locale, Model model, HttpServletRequest request) {
		
		int pageNumber = Integer.parseInt(request.getParameter("pagenum"));
		String qName = request.getParameter("qname");
		String qDescription = request.getParameter("qdescription");
		String qAuthor = request.getParameter("qauthor");
		String  qTags = request.getParameter("qtags");
		
		QuestionSearchSetting searchSettings = new QuestionSearchSetting();
		searchSettings.setQuestionName(qName);
		searchSettings.setQuestionDescription(qDescription);
		searchSettings.setUsername(qAuthor);
		searchSettings.setTags(qTags);
		
		QuestionDAO questionDAO = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		System.out.println(searchSettings.getUsername());
		List<Question> searchedQuestions = questionDAO.searchQuestion(searchSettings);
		
		this.questionManager = new QuestionManager(searchedQuestions);
		
		model.addAttribute("searchSettings", searchSettings);
		
		model.addAttribute("questionsList", this.questionManager.getSpecificPageQuestions(pageNumber));
		model.addAttribute("pageCount", this.questionManager.getPageCount());
		
		model.addAttribute("elemNum", this.questionManager.getElementCount());
		
		model.addAttribute("currPage", Integer.toString(this.questionManager.getCurrentPageNumber()+1));
		model.addAttribute("pageSize", this.questionManager.getPageSize());
		
		model.addAttribute("customHeader", SearchQuestionController.HEADER);
		model.addAttribute("customBody", SearchQuestionController.BODY);
		
		return "layout";
	}
	
}
