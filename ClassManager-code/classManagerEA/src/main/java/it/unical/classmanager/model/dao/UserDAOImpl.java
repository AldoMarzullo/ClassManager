package it.unical.classmanager.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Communications;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;

public class UserDAOImpl implements UserDAO {

	private DBHandler dbHandler;
	
	public void setDbHandler(DBHandler dbHandler) {
		this.dbHandler = dbHandler;
	}
	
	public DBHandler getDbHandler() {
		return dbHandler;
	}
	
	@Override
	public void create(User user){
		this.dbHandler.create(user);
	}

	@Override
	public void update(User user){
		this.dbHandler.update(user);
	}

	@Override
	public boolean delete(User user){
		return this.dbHandler.delete(user) == null;
	}

	@Override
	public void deleteAllUser(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM User").executeUpdate();
		session.close();		
	}

	@Override
	public int numberOfUsers(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numUsers = session.createQuery("FROM User").list().size();
		session.close();
		return numUsers;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<User> users = session.createQuery("FROM User").list();
		session.close();
		return users;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getAllStudents() {
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<Student> students = session.createQuery("FROM Student").list();
		session.close();
		return students;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Professor> getAllProfessors() {
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<Professor> professors = session.createQuery("FROM Professor").list();
		session.close();
		return professors;
	}
	
	@Override
	public User get(String username) {
		Session session = this.dbHandler.getSessionFactory().openSession();
		String queryString = "FROM User WHERE username =:username";
		Query query = session.createQuery(queryString);
		query.setParameter("username",username);
		User user = (User) query.uniqueResult();
		session.close();
		return user;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByLastName(String lastName) {
		Session session = this.dbHandler.getSessionFactory().openSession();
		String queryString = "FROM User WHERE lower(lastName) like :lastName";
		Query query = session.createQuery(queryString);
		query.setParameter("lastName",  "%" + lastName + "%");
		List<User> users = query.list();
		session.close();
		return users;
	}
	
	
	@Override
	public User getUserBySerialNumber(String serialNumber) {
		Session session = this.dbHandler.getSessionFactory().openSession();
		String queryString = "FROM User WHERE serialNumber like :serialNumber";
		Query query = session.createQuery(queryString);
		query.setParameter("serialNumber",  "%" + serialNumber + "%");
		User user = (User) query.uniqueResult();
		session.close();
		return user;
	}
	
	@Override
	public boolean doAction(User user,String action) {

		if ( !delete(user) ) {
			return false;
		}
		
		//THIS IS A FAKE PASSWORD
		user.setPassword("password");
		user.setConfirmPassword(user.getPassword());
		if ( action.equals("promote") ) {
			user.setRole("Professor");
			Professor professor = new Professor(user,0,new ArrayList<Communications>(),new ArrayList<CourseClass>());
			create(professor);
		} else if ( action.equals("demote") ) {
			user.setRole("Student");
			Student student = new Student(user);
			student.setSubscriptionDate(new Date());
			create(student);
		}
		return true;
	}
	
}

