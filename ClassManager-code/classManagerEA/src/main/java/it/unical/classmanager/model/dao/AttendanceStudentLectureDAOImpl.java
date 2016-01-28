package it.unical.classmanager.model.dao;

import java.util.List;

import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.AttendanceStudentLecture;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.Student;

public class AttendanceStudentLectureDAOImpl implements AttendanceStudentLectureDAO
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

	public void create(AttendanceStudentLecture attendanceStudentLecture)
	{
		this.dbHandler.create(attendanceStudentLecture);
	}

	public void update(AttendanceStudentLecture attendanceStudentLecture)
	{
		this.dbHandler.update(attendanceStudentLecture);
	}

	public void delete(AttendanceStudentLecture attendanceStudentLecture)
	{
		this.dbHandler.delete(attendanceStudentLecture);
	}

	public AttendanceStudentLecture get(Integer id)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		AttendanceStudentLecture attendanceStudentLecture = (AttendanceStudentLecture) session
				.createSQLQuery("SELECT * FROM attendanceStudentLecture WHERE id = " + id)
				.addEntity(AttendanceStudentLecture.class).uniqueResult();
		session.close();
		return attendanceStudentLecture;
	}

	public void deleteAllAttendanceStudentLectures()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM AttendanceStudentLecture").executeUpdate();
		session.close();
	}

	public int numberOfAttendanceStudentLectures()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numAttendanceStudentLecture = session.createQuery("FROM AttendanceStudentLecture").list().size();
		session.close();
		return numAttendanceStudentLecture;
	}

	@SuppressWarnings("unchecked")
	public List<AttendanceStudentLecture> getAllAttendanceStudentLectures()
	{
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<AttendanceStudentLecture> attendanceStudentLecture = session.createQuery("FROM AttendanceStudentLecture")
				.list();
		session.close();
		return attendanceStudentLecture;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceStudentLecture> getAllAttendanceStudentLecturesOfACourse(Student student, CourseClass course)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();		
		List<AttendanceStudentLecture> students = session.createQuery("SELECT A "
				+ "FROM AttendanceStudentLecture A, Lecture L "
				+ "WHERE A.student = :student AND L.id = A.lecture AND lecture IN ( FROM Lecture WHERE courseClass = :course ) "
				+ "ORDER BY L.number ASC").setParameter("student", student).setParameter("course", course).list();
		session.close();
		return students;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getAllAttendanceStudentLecturesOfALecture(Lecture lecture)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();		
		List<Student> students = session.createQuery("FROM Student S "
				+ "WHERE S.username IN (SELECT A.student "
										+ "FROM AttendanceStudentLecture A "
										+ "WHERE lecture = :lecture) "
				+ "ORDER BY S.username ASC").setParameter("lecture", lecture).list();
session.close();
		return students;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getAllStudentsNotPresentOnALecture(Lecture lecture)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();		
		List<Student> students = session.createQuery("FROM Student S "
													+ "WHERE S.username NOT IN (SELECT A.student "
																			+ "FROM AttendanceStudentLecture A "
																			+ "WHERE lecture = :lecture) "
													+ "AND S.username IN (SELECT R.student "
																		+ "FROM RegistrationStudentClass R "
																		+ "WHERE R.courseClass = :course) "
													+ "ORDER BY S.username ASC").setParameter("lecture", lecture).setParameter("course", lecture.getCourseClass()).list();
		session.close();
		return students;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceStudentLecture> getAllAttendanceStudentLecturesOfAStudentAndALecture(Student student, Lecture lecture)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();		
		List<AttendanceStudentLecture> students = session.createQuery("FROM AttendanceStudentLecture "
				+ "WHERE student = :student AND "
				+ "lecture = :lecture").setParameter("student", student).setParameter("lecture", lecture).list();
		session.close();
		return students;
	}

}