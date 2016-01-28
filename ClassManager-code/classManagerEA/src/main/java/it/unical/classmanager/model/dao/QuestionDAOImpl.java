package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.controllers.forum.data.QuestionSearchSetting;
import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Question;

public class QuestionDAOImpl implements QuestionDAO
{
	private DBHandler dbHandler;

	public void setDbHandler(DBHandler dbHandler)
	{
		this.dbHandler = dbHandler;
	}

	public DBHandler getDbHandler()
	{
		return dbHandler;
	}

	public Question create(Question question)
	{
		return (Question) this.dbHandler.create(question);
	}

	public Question update(Question question)
	{
		return (Question) this.dbHandler.update(question);
	}

	public void delete(Question question)
	{
		this.dbHandler.delete(question);
	}

	public Question get(Integer id)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		Question question = (Question) session.createSQLQuery("SELECT * FROM question WHERE id = " + id)
				.addEntity(Question.class).uniqueResult();
		session.close();
		return question;
	}

	public void deleteAllQuestions()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM Question").executeUpdate();
		session.close();
	}

	public int numberOfQuestions()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numQuestion = session.createQuery("FROM Question").list().size();
		session.close();
		return numQuestion;
	}

	@SuppressWarnings("unchecked")
	public List<Question> getAllQuestions()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<Question> questions = session.createQuery("FROM Question").list();
		
		/*
		for(Question question : questions) {
			question.getAnswers().size();
		}
		*/
		
		session.close();
		return questions;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Question> searchQuestion(QuestionSearchSetting searchSettings)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		
		String[] tags = searchSettings.getTags().split(",");
		
		String queryString = "SELECT q FROM Question as q, User as u WHERE "
				+ "q.user = u.username "
				+ "AND LOWER(q.title) LIKE LOWER(:questionName) "
				+ "AND LOWER(q.description) LIKE LOWER(:questionDescription) "
				+ "AND LOWER(u.username) LIKE LOWER(:username)";
		
		
		for(int i=0; i < tags.length; i++) {
			queryString += "AND LOWER(q.tags) LIKE LOWER(:tag"+ i +")";
		}
		
		
		Query query = session.createQuery(queryString);
		
		query.setParameter("questionName",  "%" + searchSettings.getQuestionName() + "%");
		query.setParameter("questionDescription",  "%" + searchSettings.getQuestionDescription() + "%");
		query.setParameter("username",  "%" + searchSettings.getUsername() + "%");
		
		for(int i=0; i < tags.length; i++) {
			query.setParameter("tag" + i, "%" + tags[i] + "%");
		}
		
		
		List<Question> questions = (List<Question>) query.list();
		
		session.close();
		return questions;
	}
	
	
}