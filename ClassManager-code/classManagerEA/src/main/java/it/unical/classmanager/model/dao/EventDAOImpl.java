package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Event;

public class EventDAOImpl implements EventDAO{

	private DBHandler dbHandler;

	public void setDbHandler(DBHandler dbHandler) {
		this.dbHandler = dbHandler;
	}

	public DBHandler getDbHandler() {
		return dbHandler;
	}

	@Override
	public void create(Event event){
		this.dbHandler.create(event);
	}

	@Override
	public void update(Event event){
		this.dbHandler.update(event);
	}

	@Override
	public void delete(Event event){
		this.dbHandler.delete(event);
	}

	@Override
	public Event get(Integer id){
		Session session = this.dbHandler.getSessionFactory().openSession();
		Event event = 
				(Event) session
				.createSQLQuery("SELECT * FROM event WHERE id = " + id)
				.addEntity(Event.class)
				.uniqueResult();
		session.close();
		return event;
	}

	@Override
	public void deleteAllEvents(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM Event").executeUpdate();
		session.close();		
	}

	@Override
	public int numberOfEvents(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numEvent= 
				session.createQuery("FROM Event").list().size();
		session.close();
		return numEvent;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Event> getAllEvents(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<Event> event = 
				session.createQuery("FROM Event").list();
		session.close();
		return event;
	}

	@Override
	public List<Event> getAllEventsOfUser(String userId) {
		
		Session session = this.dbHandler.getSessionFactory().openSession();
		Query query = session.createQuery("FROM Event as event WHERE event.user.username = :user");
		query.setParameter("user", userId);
		
		@SuppressWarnings("unchecked")
		List<Event> event = query.list();
		event.size();
		
		session.close();
		return event;
	}

	@Override
	public void deleteAllEvents(String username) {
		
		Session session = this.dbHandler.getSessionFactory().openSession();
		Query query = session.createQuery("DELETE FROM Event as e WHERE e.user.username = :username");
		query.setParameter("username", username);
		query.executeUpdate();
		
		session.close();
	}


}
