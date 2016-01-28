package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;

public interface UserDAO {

	public void create(User user);

	public void update(User user);

	public boolean delete(User user);

	public void deleteAllUser();

	public int numberOfUsers();
	
	public List<User> getAllUsers();

	public List<Student> getAllStudents();

	public List<Professor> getAllProfessors();
	
	public User get(String username);

	public User getUserBySerialNumber(String serialNumber);
	
	public List<User> getUsersByLastName(String lastName);
	
	public boolean doAction(User user,String action);

}

