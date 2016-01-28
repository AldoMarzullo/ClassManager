package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Exam;

public class ExamDAOImpl implements ExamDAO
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

	public void create(Exam exam)
	{
		this.dbHandler.create(exam);
	}

	public void update(Exam exam)
	{
		this.dbHandler.update(exam);
	}

	public void delete(Exam exam)
	{
		this.dbHandler.delete(exam);
	}

	public Exam get(Integer id)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		Exam exam = (Exam) session.createSQLQuery("SELECT * FROM exam WHERE id = " + id).addEntity(Exam.class)
				.uniqueResult();
		session.close();
		return exam;
	}

	public void deleteAllExams()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM Exam").executeUpdate();
		session.close();
	}

	public int numberOfExams()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numExam = session.createQuery("FROM Exam").list().size();
		session.close();
		return numExam;
	}

	@SuppressWarnings("unchecked")
	public List<Exam> getAllExams()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<Exam> exam = session.createQuery("FROM Exam").list();
		session.close();
		return exam;
	}

}