package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.Answer;

public interface AnswerDAO {
	public Answer create(Answer answer);

	public Answer update(Answer answer);

	public void delete(Answer answer);

	public Answer get(Integer id);

	public void deleteAllAnswers();

	public int numberOfAnswers();

	public List<Answer> getAllAnswers();
}