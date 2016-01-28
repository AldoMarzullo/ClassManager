package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.QuestionAttachedContent;


public class QuestionAttachedContentDAOImpl implements QuestionAttachedContentDAO {
	private DBHandler dbHandler;

	public void setDbHandler(DBHandler dbHandler)
	{
		this.dbHandler = dbHandler;
	}

	public DBHandler getDbHandler()
	{
		return dbHandler;
	}
	
	public QuestionAttachedContent create(QuestionAttachedContent questionAttachedContent){
		return (QuestionAttachedContent) this.dbHandler.create(questionAttachedContent);
	}

	public QuestionAttachedContent update(QuestionAttachedContent questionAttachedContent){
		return (QuestionAttachedContent) this.dbHandler.update(questionAttachedContent);
	}

	public void delete(QuestionAttachedContent questionAttachedContent){
		this.dbHandler.delete(questionAttachedContent);
	}

	public QuestionAttachedContent get(Integer id){
		Session session = this.dbHandler.getSessionFactory().openSession();
		QuestionAttachedContent questionAttachedContent = 
				(QuestionAttachedContent) session
				.createSQLQuery("SELECT * FROM questionAttachedContent WHERE id = " + id)
				.addEntity(QuestionAttachedContent.class)
				.uniqueResult();
		session.close();
		return questionAttachedContent;
	}

	public void deleteAllQuestionAttachedContents(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM QuestionAttachedContent").executeUpdate();
		session.close();		
	}

	public int numberOfQuestionAttachedContents(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numQuestion= 
				session.createQuery("FROM QuestionAttachedContent").list().size();
		session.close();
		return numQuestion;
	}

	@SuppressWarnings("unchecked")
	public List<QuestionAttachedContent> getAllQuestionAttachedContents(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<QuestionAttachedContent> questionAttachedContents = 
				session.createQuery("FROM QuestionAttachedContent").list();
		session.close();
		return questionAttachedContents;
	}
	
	
	public QuestionAttachedContent searchByPath(String path) {
		
		Session session = this.dbHandler.getSessionFactory().openSession();
		QuestionAttachedContent questionAttachedContent = 
				(QuestionAttachedContent) session
				.createSQLQuery("SELECT * FROM questionAttachedContent WHERE filePath = '" + path + "'")
				.addEntity(QuestionAttachedContent.class)
				.uniqueResult();
		session.close();
		return questionAttachedContent;
	}

}