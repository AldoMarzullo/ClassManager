package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Exam;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.StudentExamPartecipation;

public class StudentExamPartecipationDAOImpl implements StudentExamPartecipationDAO
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

	public void create(StudentExamPartecipation studentExamPartecipation)
	{
		this.dbHandler.create(studentExamPartecipation);
	}

	public void update(StudentExamPartecipation studentExamPartecipation)
	{
		this.dbHandler.update(studentExamPartecipation);
	}

	public void delete(StudentExamPartecipation studentExamPartecipation)
	{
		this.dbHandler.delete(studentExamPartecipation);
	}

	public StudentExamPartecipation get(Integer id)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		StudentExamPartecipation studentExamPartecipation = (StudentExamPartecipation) session
				.createSQLQuery("SELECT * FROM studentExamPartecipation WHERE id = " + id)
				.addEntity(StudentExamPartecipation.class).uniqueResult();
		session.close();
		return studentExamPartecipation;
	}

	public void deleteAllStudentExamPartecipations()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM StudentExamPartecipation").executeUpdate();
		session.close();
	}

	public int numberOfStudentExamPartecipations()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numStudentExamPartecipations = session.createQuery("FROM StudentExamPartecipation").list().size();
		session.close();
		return numStudentExamPartecipations;
	}

	@SuppressWarnings("unchecked")
	public List<StudentExamPartecipation> getAllStudentExamPartecipations()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<StudentExamPartecipation> studentExamPartecipations = session.createQuery("FROM StudentExamPartecipation")
				.list();
		session.close();
		return studentExamPartecipations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentExamPartecipation> getPartecipationBy(Student student, Exam exam) {
		Session session = this.dbHandler.getSessionFactory().openSession();
		String queryString = "FROM StudentExamPartecipation s WHERE s.student = :student AND s.exam= :exam";
		Query query = session.createQuery(queryString);
		query.setParameter("student", student);
		query.setParameter("exam", exam);
		List<StudentExamPartecipation> partecipations = query.list();
		session.close();
		return partecipations;
	}

}