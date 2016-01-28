package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Departement;

public class DepartementDAOImpl implements DepartementDAO
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

	public void create(Departement departement)
	{
		this.dbHandler.create(departement);
	}

	public void update(Departement departement)
	{
		this.dbHandler.update(departement);
	}

	public void delete(Departement departement)
	{
		this.dbHandler.delete(departement);
	}

	public Departement get(Integer id)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		Departement departement = (Departement) session.createSQLQuery("SELECT * FROM departement WHERE id = " + id)
				.addEntity(Departement.class).uniqueResult();
		session.close();
		return departement;
	}

	public void deleteAllDepartements()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM Departement").executeUpdate();
		session.close();
	}

	public int numberOfDepartements()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numDepartement = session.createQuery("FROM Departement").list().size();
		session.close();
		return numDepartement;
	}

	@SuppressWarnings("unchecked")
	public List<Departement> getAllDepartements()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<Departement> departement = session.createQuery("FROM Departement").list();
		session.close();
		return departement;
	}

}
