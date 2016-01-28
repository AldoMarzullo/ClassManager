package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.AnswerAttachedContent;

public interface AnswerAttachedContentDAO {
	public AnswerAttachedContent create(AnswerAttachedContent answerAttachedContent);

	public AnswerAttachedContent update(AnswerAttachedContent answerAttachedContent);

	public void delete(AnswerAttachedContent answerAttachedContent);

	public AnswerAttachedContent get(Integer id);

	public void deleteAllAnswerAttachedContents();

	public int numberOfAnswerAttachedContents();

	public List<AnswerAttachedContent> getAllAnswerAttachedContents();
	
	public AnswerAttachedContent searchByPath(String path);
}