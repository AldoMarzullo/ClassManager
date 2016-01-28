package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.QuestionAttachedContent;


public interface QuestionAttachedContentDAO {
	public QuestionAttachedContent create(QuestionAttachedContent questionAttachedContent);

	public QuestionAttachedContent update(QuestionAttachedContent questionAttachedContent);

	public void delete(QuestionAttachedContent questionAttachedContent);

	public QuestionAttachedContent get(Integer id);

	public void deleteAllQuestionAttachedContents();

	public int numberOfQuestionAttachedContents();

	public List<QuestionAttachedContent> getAllQuestionAttachedContents();
	
	public QuestionAttachedContent searchByPath(String path);
}