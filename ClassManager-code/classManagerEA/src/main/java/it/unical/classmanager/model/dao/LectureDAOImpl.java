package it.unical.classmanager.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;

public class LectureDAOImpl implements LectureDAO
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
    
	public Lecture create(Lecture lecture)
	{
		return ((Lecture) this.dbHandler.create(lecture));
	}
    
    public void update(Lecture lecture)
    {
	this.dbHandler.update(lecture);
    }
    
    public void delete(Lecture lecture)
    {
	this.dbHandler.delete(lecture);
    }
    
    public Lecture get(Integer id)
    {
	Session session = this.dbHandler.getSessionFactory().openSession();
	Lecture lecture = (Lecture) session.createSQLQuery("SELECT * FROM lecture WHERE id = " + id)
		.addEntity(Lecture.class).uniqueResult();
	session.close();
	return lecture;
    }
    
    public void deleteAllLectures()
    {
	Session session = this.dbHandler.getSessionFactory().openSession();
	session.createQuery("DELETE FROM Lecture").executeUpdate();
	session.close();
    }
    
    public int numberOfLectures()
    {
	Session session = this.dbHandler.getSessionFactory().openSession();
	int numLecture = session.createQuery("FROM Lecture").list().size();
	session.close();
	return numLecture;
    }
    
    @SuppressWarnings("unchecked")
    public List<Lecture> getAllLectures()
    {
	Session session = this.dbHandler.getSessionFactory().openSession();
	List<Lecture> lecture = session.createQuery("FROM Lecture").list();
	session.close();
	return lecture;
    }
    
    @Override
    public Lecture getLastLectureAdded(int idCourse) {
	
	Session session = this.dbHandler.getSessionFactory().openSession();
	Query query = session.createQuery("FROM Lecture as lecture WHERE lecture.courseClass.id = :id order by lecture.id DESC");
	query.setParameter("id", idCourse);
	query.setMaxResults(1);
	Lecture lecture = (Lecture)query.uniqueResult();
	
	return lecture;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Lecture> getAllLecturesOfProfessor(CourseClass course)
    {
	Session session = this.dbHandler.getSessionFactory().openSession();		
	List<Lecture> lecture = session.createQuery("FROM Lecture WHERE courseClass = :course ").setParameter("course", course).list();
	session.close();
	return lecture;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Lecture> getAllLecturesOfACourse(int courseID)
    {
	Session session = this.dbHandler.getSessionFactory().openSession();		
	List<Lecture> lecture = session.createQuery("FROM Lecture WHERE courseClass.id = :course ").setParameter("course", courseID).list();
	session.close();
	return lecture;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Lecture> getAllLecturesOfAProfessor(String professorId)
    {
	Session session = this.dbHandler.getSessionFactory().openSession();		
	List<Lecture> lecture = session.createQuery("FROM Lecture as l WHERE l.courseClass.professor.username = :username").setParameter("username", professorId).list();
	session.close();
	return lecture;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getLastLectures(Professor professor){
	List<Object[]> result = new ArrayList<Object[]>();	
	final int MAX_RESULT = 7;
	
	// Course, Lecture, Date
	Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();
	
	String hql = " select C.name, L.number, L.date "
		+ " from CourseClass C "
		+ " join C.lectures L "
		+ " join C.professor P "
		+ " where P.username = :nameProfessor "
		+ " order by L.date"
		+ " ";
	
	Query query = session.createQuery(hql);
	query.setParameter("nameProfessor", professor.getUsername());
	result = query.setMaxResults(MAX_RESULT).list();
	session.close();
	
	return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getLastLectures(Student student){
	List<Object[]> result = new ArrayList<Object[]>();	
	final int MAX_RESULT = 7;
	
	// Professor, Lecture, Date
	Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();
	
	String hql = " select C.name, L.number, L.date, P.username"
		+ " from CourseClass C "
		+ " join C.lectures L "
		+ " join C.professor P "
		+ " join C.registrationStudentClasses R "
		+ " join R.student S "
		+ " where S.username = :nameStudent "
		+ " order by L.date"
		+ " ";
	
	Query query = session.createQuery(hql);
	query.setParameter("nameStudent", student.getUsername());
	result = query.setMaxResults(MAX_RESULT).list();
	session.close();
	
	return result;
    }
}
