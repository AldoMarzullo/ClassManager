package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.controllers.forum.data.QuestionSearchSetting;
import it.unical.classmanager.model.data.Question;

public interface QuestionDAO {
	public Question create(Question question);

	public Question update(Question question);

	public void delete(Question question);

	public Question get(Integer id);

	public void deleteAllQuestions();

	public int numberOfQuestions();

	public List<Question> getAllQuestions();
	
	public List<Question> searchQuestion(QuestionSearchSetting searchSettings);
}