package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.HomeworkAttached;

public class HomeworkAttachedDAOImpl implements HomeworkAttachedDAO {
	private DBHandler dbHandler;

	public void setDbHandler(DBHandler dbHandler)
	{
		this.dbHandler = dbHandler;
	}

	public DBHandler getDbHandler()
	{
		return dbHandler;
	}
	
	public void create(HomeworkAttached homeworkAttached){
		this.dbHandler.create(homeworkAttached);
	}

	public void update(HomeworkAttached homeworkAttached){
		this.dbHandler.update(homeworkAttached);
	}

	public void delete(HomeworkAttached homeworkAttached){
		this.dbHandler.delete(homeworkAttached);
	}

	public HomeworkAttached get(Integer id){
		Session session = this.dbHandler.getSessionFactory().openSession();
		HomeworkAttached homeworkAttached = 
				(HomeworkAttached) session
				.createSQLQuery("SELECT * FROM homeworkAttached WHERE id = " + id)
				.addEntity(HomeworkAttached.class)
				.uniqueResult();
		session.close();
		return homeworkAttached;
	}

	public void deleteAllHomeworkAttacheds(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM HomeworkAttached").executeUpdate();
		session.close();		
	}

	public int numberOfHomeworkAttacheds(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numHomeworkAttached= 
				session.createQuery("FROM HomeworkAttached").list().size();
		session.close();
		return numHomeworkAttached;
	}

	@SuppressWarnings("unchecked")
	public List<HomeworkAttached> getAllHomeworkAttacheds(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<HomeworkAttached> homeworkAttached = 
				session.createQuery("FROM HomeworkAttached").list();
		session.close();
		return homeworkAttached;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HomeworkAttached> getAllHomeworkAttacheds(int idHomework) {
		
		Session session = this.dbHandler.getSessionFactory().openSession();
		Query query = session.createQuery("FROM HomeworkAttached as h WHERE h.homework.id = :id");
		query.setParameter("id", idHomework);
		List<HomeworkAttached> homeworkAttached = query.list();
		
		session.close();
		return homeworkAttached;
	}
	
}
