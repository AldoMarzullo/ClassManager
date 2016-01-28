package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Answer;

public class AnswerDAOImpl implements AnswerDAO
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

	public Answer create(Answer answer)
	{
		return (Answer) this.dbHandler.create(answer);
	}

	public Answer update(Answer answer)
	{
		return (Answer) this.dbHandler.update(answer);
	}

	public void delete(Answer answer)
	{
		this.dbHandler.delete(answer);
	}

	public Answer get(Integer id)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		Answer answer = (Answer) session.createSQLQuery("SELECT * FROM answer WHERE id = " + id).addEntity(Answer.class)
				.uniqueResult();
		session.close();
		return answer;
	}

	public void deleteAllAnswers()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM Answer").executeUpdate();
		session.close();
	}

	public int numberOfAnswers()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numAnswer = session.createQuery("FROM Answer").list().size();
		session.close();
		return numAnswer;
	}

	@SuppressWarnings("unchecked")
	public List<Answer> getAllAnswers()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<Answer> answer = session.createQuery("FROM Answer").list();
		session.close();
		return answer;
	}
}