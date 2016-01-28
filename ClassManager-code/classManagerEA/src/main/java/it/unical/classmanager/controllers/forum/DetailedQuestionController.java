package it.unical.classmanager.controllers.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unical.classmanager.comparators.AnswerComparator;
import it.unical.classmanager.model.dao.QuestionDAOImpl;
import it.unical.classmanager.model.data.Answer;
import it.unical.classmanager.model.data.Question;

/**
 * Handles requests for the forum page.
 */
@Controller
public class DetailedQuestionController {
	
	@Autowired
	private ApplicationContext appContext;
	
	private final static String HEADER = "forum/detailedQuestionHead.jsp";
	private final static String BODY = "forum/detailedQuestionBody.jsp";
	
	
	@RequestMapping(value = "/forum/detailedQuestion", method = RequestMethod.GET)
	public String getQuestion(Locale locale, Model model, HttpServletRequest request) {
		
		int questionID = Integer.parseInt(request.getParameter("qid"));
		System.out.println("ID: " + questionID);
		
		QuestionDAOImpl dao = (QuestionDAOImpl) appContext.getBean("questionDAO", QuestionDAOImpl.class);
		Question question = dao.get(questionID);
		
		
		model.addAttribute("question", question);
		
		String username = (String) request.getSession().getAttribute("loggedIn");
		if(question.getUser().getUsername().equals(username)) {
			model.addAttribute("owner", true);
		}
		else {
			model.addAttribute("owner", false);
		}
		
		Set<Answer> answersSet = question.getAnswers();
		List<Answer> answersList = new ArrayList<Answer>(answersSet);
		answersList.sort(new AnswerComparator());
		
		model.addAttribute("sortedAnswers", answersList);
		
		model.addAttribute("loggedUser", username);
		
		model.addAttribute("customHeader", DetailedQuestionController.HEADER);
		model.addAttribute("customBody", DetailedQuestionController.BODY);
		
		return "layout";
	}
	
	
	
	
}
