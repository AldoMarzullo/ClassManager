package it.unical.classmanager.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DBHandler {
	
	private SessionFactory sessionFactory;
		
	private static enum Operation {
		CREATE, UPDATE, DELETE
	};

	public DBHandler() {
		sessionFactory = null;
	}
	
	private Object performOperation(Object obj, Operation op) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Object toReturn = obj;
		try {
			tx = session.beginTransaction();
			switch (op) {
			case CREATE:
				session.save(obj);
				break;
			case UPDATE:
				session.update(obj);
				break;
			case DELETE:
				session.delete(obj);
				toReturn = null;
				break;
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				toReturn = obj;
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return toReturn;
	}
	
	public Object create(Object obj) {
		return performOperation(obj, Operation.CREATE);
	}
	
	public Object delete(Object obj) {
		return performOperation(obj, Operation.DELETE);
	}
	
	public Object update(Object obj) {
		return performOperation(obj, Operation.UPDATE);
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}