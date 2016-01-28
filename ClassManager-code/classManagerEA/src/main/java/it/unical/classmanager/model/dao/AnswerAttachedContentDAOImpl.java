package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.AnswerAttachedContent;

public class AnswerAttachedContentDAOImpl implements AnswerAttachedContentDAO
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

	public AnswerAttachedContent create(AnswerAttachedContent answerAttachedContent)
	{
		return (AnswerAttachedContent) this.dbHandler.create(answerAttachedContent);
	}

	public AnswerAttachedContent update(AnswerAttachedContent answerAttachedContent)
	{
		return (AnswerAttachedContent) this.dbHandler.update(answerAttachedContent);
	}

	public void delete(AnswerAttachedContent answerAttachedContent)
	{
		this.dbHandler.delete(answerAttachedContent);
	}

	public AnswerAttachedContent get(Integer id)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		AnswerAttachedContent answerAttachedContent = (AnswerAttachedContent) session.createSQLQuery(
				"SELECT * FROM answerAttachedContent WHERE id = " + id)
				.addEntity(AnswerAttachedContent.class).uniqueResult();
		session.close();
		return answerAttachedContent;
	}

	public void deleteAllAnswerAttachedContents()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM AnswerAttachedContent").executeUpdate();
		session.close();
	}

	public int numberOfAnswerAttachedContents()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numAnswerAttachedContent = session.createQuery("FROM AnswerAttachedContent").list().size();
		session.close();
		return numAnswerAttachedContent;
	}

	@SuppressWarnings("unchecked")
	public List<AnswerAttachedContent> getAllAnswerAttachedContents()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<AnswerAttachedContent> answerAttachedContents = session.createQuery("FROM AnswerAttachedContent").list();
		session.close();
		return answerAttachedContents;
	}
	
	
	public AnswerAttachedContent searchByPath(String path) {
		
		Session session = this.dbHandler.getSessionFactory().openSession();
		AnswerAttachedContent answerAttachedContent = 
				(AnswerAttachedContent) session
				.createSQLQuery("SELECT * FROM answerAttachedContent WHERE filePath = '" + path + "'")
				.addEntity(AnswerAttachedContent.class)
				.uniqueResult();
		session.close();
		return answerAttachedContent;
	}

}