package it.unical.classmanager.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;

/**
 * This is a DAO for perform queries related to statistics.
 * This is the implementation.
 * 
 * @author Aloisius92
 */
public class CartQueryDAOImpl implements CartQueryDAO
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

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getCourseByProfessor(Professor professor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCourseByProfessor( Professor professor) {
		List<Object[]> result = new ArrayList<Object[]>();	

		/*
		 * Professor, NumberCourse
		 * Professor, numberCourse
		 * Professor, numberCourse
		 * Professor, numberCourse
		 * 
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();
		String hql = "SELECT C.professor.username, count(*) "
				+ "FROM CourseClass C "
				+ "GROUP BY C.professor";
		Query query = session.createQuery(hql);
		result = query.list();
		session.close();

		//printResultSet(result, professor);

		return result;
	}

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getForYearLectureByWeekDaySingleProfessor( Professor professor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getForYearLectureByWeekDay(Professor professor) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * 2012, Monday, NumberLecture
		 * 2012, Tuesday, NumberLecture
		 * 2012, Wednesday, NumberLecture
		 * 2012, Thursday, NumberLecture
		 * 2012, Friday, NumberLecture
		 * 2012, Saturday, NumberLecture
		 * 2012, Sunday, NumberLecture
		 * 2013, Monday, NumberLecture
		 * 2013, Tuesday, NumberLecture
		 * 2013, Wednesday, NumberLecture
		 * 2013, Thursday, NumberLecture
		 * 2013, Friday, NumberLecture
		 * 2013, Saturday, NumberLecture
		 * 2013, Sunday, NumberLecture
		 * 2014, Monday, NumberLecture
		 * 2014, Tuesday, NumberLecture
		 * 2014, Wednesday, NumberLecture
		 * 2014, Thursday, NumberLecture
		 * 2014, Friday, NumberLecture
		 * 2014, Saturday, NumberLecture
		 * 2014, Sunday, NumberLecture
		 * 
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select year(L.date), dayofweek(L.date), count(*) "
				+ "from CourseClass C join C.lectures L "
				+ "where C.professor.username=:username_professor "
				+ "group by year(L.date), dayofweek(L.date) "
				+ "order by year(L.date), dayofweek(L.date) ";

		Query query = session.createQuery(hql);
		query.setParameter("username_professor", professor.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, professor);

		return result;
	}

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getAvgLectureByWeekDaySingleProfessor( Professor professor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAvgLectureByWeekDaySingleProfessor( Professor professor) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * Monday, NumberLecture
		 * Tuesday, NumberLecture
		 * Wednesday, NumberLecture
		 * Thursday, NumberLecture
		 * Friday, NumberLecture
		 * Saturday, NumberLecture
		 * Sunday, NumberLecture
		 * 
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select dayofweek(L.date), count(*)*100.0/("
				+ "select count(*) from CourseClass C1 join C1.lectures L1 "
				+ "where C1.professor.username=:username_professor "
				+ ") "
				+ "from CourseClass C join C.lectures L "
				+ "where C.professor.username=:username_professor "
				+ "group by dayofweek(L.date) "
				+ "order by dayofweek(L.date) ";

		Query query = session.createQuery(hql);
		query.setParameter("username_professor", professor.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, professor);

		return result;
	}

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getAvgLectureByWeekDayAllProfessor( List<Professor> professors)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAvgLectureByWeekDayAllProfessor( List<Professor> professors) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * Professor, Monday, NumberLecture
		 * Professor, Tuesday, NumberLecture
		 * Professor, Wednesday, NumberLecture
		 * Professor, Thursday, NumberLecture
		 * Professor, Friday, NumberLecture
		 * Professor, Saturday, NumberLecture
		 * Professor, Sunday, NumberLecture
		 * 
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select C.professor.username, dayofweek(L.date), count(*) "
				+ "from CourseClass C join C.lectures L "
				+ "group by C.professor.username, dayofweek(L.date) "
				+ "order by C.professor.username, dayofweek(L.date) ";

		Query query = session.createQuery(hql);
		result = query.list();
		session.close();

		//printResultSet(result, null);

		return result;
	}

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getAvgTimeDeliveryHomework( Professor professor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAvgTimeDeliveryHomework(Professor professor, String research) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * CourseClass, Student, AvgTime
		 * CourseClass, Student, AvgTime
		 * CourseClass, Student, AvgTime
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select C.name, HSS.student.username, avg("
				+ "DATEDIFF(DAY, HSS.date, L.date)"
				+ ") "
				+ "from CourseClass C join C.lectures L "
				+ "join L.homeworks H join H.homeworkStudentSolvings HSS "
				+ "where C.professor.username = :username_professor "
				+ "group by C.name, HSS.student.username "
				+ "order by C.name, HSS.student.username";

		Query query = session.createQuery(hql);
		query.setParameter("username_professor", professor.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, professor);

		return filterResultSet(result, research);	
	}

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getAvgScoreHomework( Professor professor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAvgScoreHomework( Professor professor, String research) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * CourseClass, Student, AvgScore
		 * CourseClass, Student, AvgScore
		 * CourseClass, Student, AvgScore
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select C.name, HSS.student.username, avg(HSS.score) "
				+ "from CourseClass C join C.lectures L "
				+ "join L.homeworks H join H.homeworkStudentSolvings HSS "
				+ "where C.professor.username = :username_professor "
				+ "group by C.name, HSS.student.username "
				+ "order by C.name, HSS.student.username";

		Query query = session.createQuery(hql);
		query.setParameter("username_professor", professor.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, professor);

		return filterResultSet(result, research);
	}

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getAvgAttendanceAllStudent( Professor professor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAvgAttendanceStudent( Professor professor) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * CourseClass, AvgAttendance
		 * CourseClass, AvgAttendance
		 * CourseClass, AvgAttendance
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select C.name, avg("
				+ "(select count(*) from AttendanceStudentLecture ASL "
				+ "where ASL.lecture.id=L.id )*100.0/"
				+ "(select count(*) "
				+ "from RegistrationStudentClass R join R.courseClass CR "
				+ "where CR.id=C.id )"
				+ ")  "
				+ "from CourseClass C join C.lectures L "
				+ "join L.attendanceStudentLectures A "
				+ "where C.professor.username=:username_professor "
				+ "group by C.name "
				+ "order by C.name ";

		Query query = session.createQuery(hql);
		query.setParameter("username_professor", professor.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, professor);

		return result;
	}    

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getAvgHomeworksByStudent( Student student)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAvgHomeworksByStudent( Student student) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * avgHomeworksByStudent
		 * Course1, AvgScore
		 * Course2, AvgScore
		 * ...
		 * CourseN, AvgScore
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select C.name, avg(HSS.score) "
				+ "from CourseClass C join C.lectures L "
				+ "join L.homeworks H join H.homeworkStudentSolvings HSS "
				+ "where HSS.student.username = :username_student "
				+ "group by C.name";

		Query query = session.createQuery(hql);
		query.setParameter("username_student", student.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, student);

		return result;
	}


	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getAvgTimeDeliveryHomeworksByStudent( Student student)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAvgTimeDeliveryHomeworksByStudent( Student student) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * avgTimeDeliveryHomeworksByStudent
		 * Course, AvgTime
		 * Course, AvgTime
		 * Course, AvgTime
		 * ...
		 * Course, AvgTime
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select C.name, avg("
				+ "DATEDIFF(DAY, HSS.date, L.date)"
				+ ") "
				+ "from CourseClass C join C.lectures L "
				+ "join L.homeworks H join H.homeworkStudentSolvings HSS "
				+ "where HSS.student.username = :username_student "
				+ "group by C.name";

		Query query = session.createQuery(hql);
		query.setParameter("username_student", student.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, student);

		return result;
	}    

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getHomeworkScoreSeriesByStudent( Student student)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getHomeworkScoreSeriesByStudent( Student student) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * Course, Homework, Score
		 * Course, Homework, Score
		 * Course, Homework, Score
		 * ...
		 * Course, Homework, Score
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();
		String hql = "select C.name, H.name, HSS.score "
				+ "from CourseClass C join C.lectures L "
				+ "join L.homeworks H join H.homeworkStudentSolvings HSS "
				+ "where HSS.student.username = :username_student "
				+ "group by C.name, H.name, HSS.score "
				+ "order by C.name";

		Query query = session.createQuery(hql);
		query.setParameter("username_student", student.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, student);

		return result;
	}


	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getExamScoreSeriesByStudent( Student student)
	 */
	@SuppressWarnings({
		"unchecked"
	})
	@Override
	public List<Object[]> getExamScoreSeriesByStudent( Student student) {
		List<Object[]> result = new ArrayList<Object[]>();

		/*
		 * Anno, Date, ExamName, Score
		 * Anno, Date, ExamName, Score
		 * Anno, Date, ExamName, Score
		 * Anno, Date, ExamName, Score
		 * ...
		 * Anno, Date, ExamName, Score
		 */

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select year(E.date), E.date, E.name, EP.score "
				+ "from CourseClass C join C.exams E "
				+ "join E.studentExamPartecipations EP "
				+ "where EP.student.username = :username_student "
				+ "group by year(E.date), E.date, E.name, EP.score "
				+ "order by year(E.date), E.date, E.name, EP.score ";

		Query query = session.createQuery(hql);
		query.setParameter("username_student", student.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, student);
		
		return result;
	}    

	/* (non-Javadoc)
	 * @see it.unical.classmanager.model.dao.CartQueryDAO#getAvgAttendanceByStudent( Student student)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAvgAttendanceByStudent( Student student) {
		List<Object[]> result = new ArrayList<Object[]>();

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select C.name, (count(*)*100.0)/(select count(*) from L where L.courseClass.name=C.name) "
				+ "from RegistrationStudentClass R join R.courseClass C join C.lectures L "
				+ "join L.attendanceStudentLectures A "
				+ "where R.student.username=:username_student and  A.student.username = :username_student "
				+ "group by C.name";

		Query query = session.createQuery(hql);
		query.setParameter("username_student", student.getUsername());
		result = query.list();
		session.close();

		//printResultSet(result, student);
		
		return result;
	}
	
	private List<Object[]> filterResultSet(List<Object[]> resultSet, String research){
		// Filter by search string
		if(research != null){
			List<Object[]> result = new ArrayList<Object[]>();			
			for(int i=0; i<resultSet.size(); i++){
				boolean matchResearch = false;
				for(int j=0; j<resultSet.get(i).length; j++){
					if(resultSet.get(i)[j].toString().contains(research)){
						matchResearch = true;
					}
				}
				if(matchResearch){
					result.add(resultSet.get(i));
				}
			}
			return result;
		}
		return resultSet;
	}

	protected void printResultSet(List<Object[]> resultSet, User user){
		System.out.println("Student: "+user.getUsername());
		for(int i=0; i<resultSet.size(); i++){
			Object[] objects = resultSet.get(i);
			for(int j=0; j<objects.length; j++){
				if(j>0){
					System.out.print(", ");
				}
				System.out.print("Prop "+(j+1)+": "+objects[j]);		
			}
			System.out.println();
		}
	}

}