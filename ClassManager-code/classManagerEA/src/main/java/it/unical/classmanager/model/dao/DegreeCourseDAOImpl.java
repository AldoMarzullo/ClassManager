package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.DegreeCourse;

public class DegreeCourseDAOImpl implements DegreeCourseDAO
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

	public void create(DegreeCourse degreeCourse)
	{
		this.dbHandler.create(degreeCourse);
	}

	public void update(DegreeCourse degreeCourse)
	{
		this.dbHandler.update(degreeCourse);
	}

	public void delete(DegreeCourse degreeCourse)
	{
		this.dbHandler.delete(degreeCourse);
	}

	public DegreeCourse get(Integer id)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		DegreeCourse degreeCourse = (DegreeCourse) session.createSQLQuery("SELECT * FROM degreeCourse WHERE id = " + id)
				.addEntity(DegreeCourse.class).uniqueResult();
		session.close();
		return degreeCourse;
	}

	public void deleteAllDegreeCourses()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM DegreeCourse").executeUpdate();
		session.close();
	}

	public int numberOfDegreeCourses()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numDegreeCourse = session.createQuery("FROM DegreeCourse").list().size();
		session.close();
		return numDegreeCourse;
	}

	@SuppressWarnings("unchecked")
	public List<DegreeCourse> getAllDegreeCourses()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<DegreeCourse> degreeCourse = session.createQuery("FROM DegreeCourse").list();
		session.close();
		return degreeCourse;
	}

}