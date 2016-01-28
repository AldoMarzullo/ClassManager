package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Communications;

public class CommunicationsDAOImpl implements CommunicationsDAO
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

	public void create(Communications communications)
	{
		this.dbHandler.create(communications);
	}

	public void update(Communications communications)
	{
		this.dbHandler.update(communications);
	}

	public void delete(Communications communications)
	{
		this.dbHandler.delete(communications);
	}

	public Communications get(Integer id)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		Communications communications = (Communications) session
				.createSQLQuery("SELECT * FROM communications WHERE id = " + id).addEntity(Communications.class)
				.uniqueResult();
		session.close();
		return communications;
	}

	public void deleteAllCommunications()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM Communications").executeUpdate();
		session.close();
	}

	public int numberOfCommunications()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numCommunications = session.createQuery("FROM Communications").list().size();
		session.close();
		return numCommunications;
	}

	@SuppressWarnings("unchecked")
	public List<Communications> getAllCommunications()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<Communications> communications = session.createQuery("FROM Communications com ORDER BY com.date desc").list();
		session.close();
		return communications;
	}

}