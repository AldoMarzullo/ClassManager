package it.unical.classmanager.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;

public class CourseClassDAOImpl implements CourseClassDAO {
    private DBHandler dbHandler;
    
    public void setDbHandler(DBHandler dbHandler)
    {
	this.dbHandler = dbHandler;
    }
    
    public DBHandler getDbHandler()
    {
	return dbHandler;
    }
    
    public void create(CourseClass courseClass){
	this.dbHandler.create(courseClass);
    }
    
    public void update(CourseClass courseClass){
	this.dbHandler.update(courseClass);
    }
    
    public void delete(CourseClass courseClass){
	this.dbHandler.delete(courseClass);
    }
    
    public CourseClass get(Integer id){
	Session session = this.dbHandler.getSessionFactory().openSession();
	CourseClass courseClass = 
		(CourseClass) session
		.createSQLQuery("SELECT * FROM courseClass WHERE id = " + id)
		.addEntity(CourseClass.class)
		.uniqueResult();
	session.close();
	return courseClass;
    }
    
    public void deleteAllCourseClasses(){
	Session session = this.dbHandler.getSessionFactory().openSession();
	session.createQuery("DELETE FROM CourseClass").executeUpdate();
	session.close();		
    }
    
    public int numberOfCourseClasses(){
	Session session = this.dbHandler.getSessionFactory().openSession();
	int numCourseClass= 
		session.createQuery("FROM CourseClass").list().size();
	session.close();
	return numCourseClass;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<CourseClass> getAllCourseClasses() {
	Session session = this.dbHandler.getSessionFactory().openSession();
	List<CourseClass> courseClass = 
		session.createQuery("FROM CourseClass").list();
	session.close();
	return courseClass;
    }
    
    
    @Override
    public CourseClass get(String name) {
	
	if(name.toCharArray()[0]=='\''){
	    name = name.substring(1, name.length()-1);
	}	
	
	Session session = this.dbHandler.getSessionFactory().openSession();
	String queryString = "FROM CourseClass WHERE name =:name";
	Query query = session.createQuery(queryString);
	query.setParameter("name",name);	
	CourseClass courseClass = (CourseClass) query.uniqueResult();
	session.close();	
	return courseClass;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<CourseClass> getCourseClasses(Professor professor){  
	Session session = this.dbHandler.getSessionFactory().openSession();		  
	List<CourseClass> courseClasses = session.createQuery("FROM CourseClass "  
		+ "WHERE professor = :professor").setParameter("professor", professor).list();  
	session.close();  
	return courseClasses;  
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<CourseClass> getCourseClasses(Student student)
    {  
		Session session = this.dbHandler.getSessionFactory().openSession();		  
		List<CourseClass> courseClasses = session.createQuery("SELECT courseClass FROM RegistrationStudentClass "  
			+ "WHERE student = :student").setParameter("student", student).list();  
		session.close();  
		return courseClasses;  
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getCourses(Professor professor) {
	List<Object[]> result = new ArrayList<Object[]>();
	
	// Course, Professor
	Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();
	
	String hql = " select C.name, P.username, C.id "
		+ " from CourseClass C "
		+ " join C.professor P "
		+ " where P.username = :nameProfessor "
		+ " ";
	
	Query query = session.createQuery(hql);
	query.setParameter("nameProfessor", professor.getUsername());
	result = query.list();
	session.close();
	
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getCourses(Student student) {
	List<Object[]> result = new ArrayList<Object[]>();
	
	// Course, Student
	Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();
	
	String hql = " select C.name, S.username "
		+ " from CourseClass C "
		+ " join C.registrationStudentClasses R "
		+ " join R.student S "
		+ " where S.username = :nameStudent "
		+ " ";
	
	Query query = session.createQuery(hql);
	query.setParameter("nameStudent", student.getUsername());
	result = query.list();
	session.close();
	
	return result;
    }  
    
}