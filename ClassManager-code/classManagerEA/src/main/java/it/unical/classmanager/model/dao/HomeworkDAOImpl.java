package it.unical.classmanager.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Homework;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;

public class HomeworkDAOImpl implements HomeworkDAO
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
    
    public Homework create(Homework homework)
	{
		return (Homework) this.dbHandler.create(homework);
	}
    
    public void update(Homework homework)
    {
	this.dbHandler.update(homework);
    }
    
    public void delete(Homework homework)
    {
	this.dbHandler.delete(homework);
    }
    
    public Homework get(Integer id)
    {
	Session session = this.dbHandler.getSessionFactory().openSession();
	
	Query finalQuery = session.createQuery("FROM Homework as h WHERE h.id = :id)");
	finalQuery.setParameter("id", id);
	Homework homework = (Homework) finalQuery.uniqueResult();
	
	session.close();
	return homework;
    }
    
    public void deleteAllHomeworks()
    {
	Session session = this.dbHandler.getSessionFactory().openSession();
	session.createQuery("DELETE FROM Homework homework").executeUpdate();
	session.close();
    }
    
    public int numberOfHomeworks()
    {
	Session session = this.dbHandler.getSessionFactory().openSession();
	int numHomework = session.createQuery("FROM Homework").list().size();
	session.close();
	return numHomework;
    }
    
    @SuppressWarnings("unchecked")
    public List<Homework> getAllHomeworks()
    {
	Session session = this.dbHandler.getSessionFactory().openSession();
	List<Homework> homework = session.createQuery("FROM Homework").list();
	session.close();
	return homework;
    }
    
    @SuppressWarnings("unchecked")
    public List<Homework> getAllHomeworks(int idCourse)
    {
	Session session = this.dbHandler.getSessionFactory().openSession();
	
	Query finalQuery = session.createQuery("FROM Homework as h WHERE h.lecture.courseClass IN (FROM CourseClass as course WHERE course.id = :id)");
	finalQuery.setParameter("id", idCourse);
	List<Homework> homeworks = finalQuery.list();
	
	for (Homework homework : homeworks) {
	    homework.getHomeworkStudentSolvings().size();
	}
	
	//Cannot manages multiple bags
	//selects all the courses with "id" equals to "idCourse"
	/*DetachedCriteria courseCriteria = DetachedCriteria.forClass(CourseClass.class,"course")
				.add(Restrictions.eq("id", idCourse));

		//selects all the homeworks where the associated lecture belong to the course selected before
		List<Homework> homework = (List<Homework>) session.createCriteria(Homework.class,"homework")
				.add(Subqueries.in("homework.lecture", courseCriteria));
	 */
	
	session.close();
	return homeworks;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Homework> getAllLectureHomeworks(int idLecture) {
	
	Session session = this.dbHandler.getSessionFactory().openSession();
	
	Query finalQuery = session.createQuery("FROM Homework as h WHERE h.lecture.id = :id");
	finalQuery.setParameter("id", idLecture);
	List<Homework> homeworks = finalQuery.list();
	
	return homeworks;
	
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getLastHomeworks(Professor professor){
	List<Object[]> result = new ArrayList<Object[]>();
	final int MAX_RESULT = 7;
	
	// Student, Course, Homework, Date
	Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();
	
	String hql = " select S.username, C.name, H.name, HSS.date "
		+ " from Homework H "
		+ " join H.homeworkStudentSolvings HSS "
		+ " join HSS.student S "
		+ " join H.lecture L"
		+ " join L.courseClass C "
		+ " join C.professor P"
		+ " where P.username = :nameProfessor "
		+ " order by HSS.date"
		+ " ";
	
	Query query = session.createQuery(hql);
	query.setParameter("nameProfessor", professor.getUsername());
	result = query.setMaxResults(MAX_RESULT).list();
	session.close();
	
	return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getLastHomeworks(Student student){
	List<Object[]> result = new ArrayList<Object[]>();
	final int MAX_RESULT = 7;
	
	// Professor, Course, Homework, Date, Score
	Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();
	
	String hql = " select P.username, C.name, H.name, HSS.date, HSS.score "
		+ " from Homework H "
		+ " join H.homeworkStudentSolvings HSS "
		+ " join HSS.student S "
		+ " join H.lecture L"
		+ " join L.courseClass C "
		+ " join C.professor P"
		+ " where S.username = :nameStudent "
		+ " order by HSS.date"
		+ " ";
	
	Query query = session.createQuery(hql);
	query.setParameter("nameStudent", student.getUsername());
	result = query.setMaxResults(MAX_RESULT).list();
	session.close();
	
	return result;
    }
    
}
