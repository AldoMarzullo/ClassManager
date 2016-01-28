package it.unical.classmanager.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.RegistrationStudentClass;
import it.unical.classmanager.model.data.Student;

public class RegistrationStudentClassDAOImpl implements RegistrationStudentClassDAO {
	private DBHandler dbHandler;

	public void setDbHandler(DBHandler dbHandler)
	{
		this.dbHandler = dbHandler;
	}

	public DBHandler getDbHandler()
	{
		return dbHandler;
	}

	public void create(RegistrationStudentClass registrationStudentClass){
		this.dbHandler.create(registrationStudentClass);
	}

	public void update(RegistrationStudentClass registrationStudentClass){
		this.dbHandler.update(registrationStudentClass);
	}

	public void delete(RegistrationStudentClass registrationStudentClass){
		this.dbHandler.delete(registrationStudentClass);
	}

	public RegistrationStudentClass get(Integer id){
		Session session = this.dbHandler.getSessionFactory().openSession();
		RegistrationStudentClass registrationStudentClass = 
				(RegistrationStudentClass) session
				.createSQLQuery("SELECT * FROM registrationStudentClass WHERE id = " + id)
				.addEntity(RegistrationStudentClass.class)
				.uniqueResult();
		session.close();
		return registrationStudentClass;
	}

	public void deleteAllRegistrationStudentClasses(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		session.createQuery("DELETE FROM RegistrationStudentClass").executeUpdate();
		session.close();		
	}

	public int numberOfRegistrationStudentClasses(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		int numRegistrationStudentClass = 
				session.createQuery("FROM RegistrationStudentClass").list().size();
		session.close();
		return numRegistrationStudentClass;
	}

	@Override
	public int getMaxIndex(){
		int maxIndex = 0;
		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = " select max(R.id) "
				+ " from RegistrationStudentClass R "
				+ " ";

		Query query = session.createQuery(hql);
		maxIndex = (Integer) query.uniqueResult();
		session.close();

		return maxIndex;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existRegistration(Student student, CourseClass courseClass){
		List<Object[]> result = new ArrayList<Object[]>();

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = " from RegistrationStudentClass R join R.student S "
				+ " join R.courseClass C "
				+ " where S.username = :nameStudent "
				+ " and C.name = :nameCourse"
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameStudent", student.getUsername());
		query.setParameter("nameCourse", courseClass.getName());
		result = query.list();
		session.close();

		return result.size()>0;
	}

	@Override
	public RegistrationStudentClass getRegistration(Student student, CourseClass courseClass){
		RegistrationStudentClass result = null;

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select R "
				+ " from RegistrationStudentClass R join R.student S "
				+ " join R.courseClass C "
				+ " where S.username = :nameStudent "
				+ " and C.name = :nameCourse"
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameStudent", student.getUsername());
		query.setParameter("nameCourse", courseClass.getName());
		result = (RegistrationStudentClass) query.uniqueResult();
		session.close();

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<RegistrationStudentClass> getAllRegistrationStudentClasses(){
		Session session = this.dbHandler.getSessionFactory().openSession();
		List<RegistrationStudentClass> registrationStudentClasses = 
				session.createQuery("FROM RegistrationStudentClass").list();
		session.close();
		return registrationStudentClasses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentsRegisteredToACourse(CourseClass course)
	{
		Session session = this.dbHandler.getSessionFactory().openSession();		
		List<Student> students = session.createQuery("SELECT student FROM RegistrationStudentClass WHERE courseClass = :course ").setParameter("course", course).list();
		session.close();
		return students;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getSelectableCourse(Student student){
		List<Object[]> result = new ArrayList<Object[]>();

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select C.name, C.cfu, C.referenceYear, P.username "
				+ "from CourseClass C join C.professor P "
				+ "where C.name not in ("
				+ " select C1.name "
				+ " from RegistrationStudentClass R1 join R1.student S1 join R1.courseClass C1 "
				+ " where S1.username = :nameStudent"
				+ ") "
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameStudent", student.getUsername());
		result = query.list();
		session.close();

		return result;
	}

	@Override
	public List<Object[]> getSelectableCourse(Professor professor) {
		List<Object[]> result = new ArrayList<Object[]>();
		List<CourseClass> courseClasses = DaoHelper.getCourseClassDAO().getCourseClasses(professor);
		for(int i=0; i<courseClasses.size(); i++){
			Object[] row = {""};
			row[0] = courseClasses.get(i).getName();
			result.add(row);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCancellableCourse(Student student){
		List<Object[]> result = new ArrayList<Object[]>();

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = " select C.name, C.cfu, C.referenceYear, P.username "
				+ " from RegistrationStudentClass R join R.student S "
				+ " join R.courseClass C join C.professor P "
				+ " where S.username = :nameStudent "
				+ " and R.requestedDate IS NOT NULL"
				+ " and R.acceptedDate IS NULL"
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameStudent", student.getUsername());
		result = query.list();
		session.close();

		return result;	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getSelectableStudent(CourseClass courseClass, String research){
		List<Object[]> result = new ArrayList<Object[]>();

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = "select S.username, S.serialNumber, S.subscriptionDate, S.firstName, S.lastName "
				+ "from Student S  "
				+ "where S.username not in ("
				+ " select S1.username "
				+ " from RegistrationStudentClass R1 join R1.student S1 join R1.courseClass C1 "
				+ " where C1.name = :nameCourse"
				+ ") "
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameCourse", courseClass.getName());
		result = query.list();
		session.close();

		return filterResultSet(result, research);
		//		return result;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCancellableStudent(CourseClass courseClass, String research){
		List<Object[]> result = new ArrayList<Object[]>();

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = " select S.username, S.serialNumber, S.subscriptionDate, S.firstName, S.lastName "
				+ " from RegistrationStudentClass R join R.student S "
				+ " join R.courseClass C "
				+ " where C.name = :nameCourse "
				+ " and R.invitationDate IS NOT NULL"
				+ " and R.acceptedDate IS NULL"
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameCourse", courseClass.getName());
		result = query.list();
		session.close();

		return filterResultSet(result, research);
		//		return result;	
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAcceptableCourse(Student student) {
		List<Object[]> result = new ArrayList<Object[]>();

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = " select C.name, C.cfu, C.referenceYear, P.username "
				+ " from RegistrationStudentClass R join R.student S "
				+ " join R.courseClass C join C.professor P "
				+ " where S.username = :nameStudent "
				+ " and R.invitationDate IS NOT NULL"
				+ " and R.acceptedDate IS NULL"
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameStudent", student.getUsername());
		result = query.list();
		session.close();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAcceptableStudent(Professor professor, String research) {
		List<Object[]> result = new ArrayList<Object[]>();

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = " select S.username, C.name, S.serialNumber, S.subscriptionDate, S.firstName, S.lastName  "
				+ " from RegistrationStudentClass R join R.student S "
				+ " join R.courseClass C join C.professor P "
				+ " where P.username = :nameProfessor "
				+ " and R.requestedDate IS NOT NULL"
				+ " and R.acceptedDate IS NULL"
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameProfessor", professor.getUsername());
		result = query.list();
		session.close();

		return filterResultSet(result, research);
		//		return result;
	}

	@Override
	public int getNewInvitationsOfStudent(Student student) {	
		int size = 0;
		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = " from RegistrationStudentClass R join R.student S "
				+ " join R.courseClass C join C.professor P "
				+ " where S.username = :nameStudent "
				+ " and R.invitationDate IS NOT NULL"
				+ " and R.acceptedDate IS NULL"
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameStudent", student.getUsername());
		size = query.list().size();
		session.close();

		return size;	
	}

	@Override
	public int getNewInvitationsOfProfessor(Professor professor) {	
		int size = 0;

		Session session = DaoHelper.getDbHandler().getSessionFactory().openSession();

		String hql = " from RegistrationStudentClass R join R.student S "
				+ " join R.courseClass C join C.professor P "
				+ " where P.username = :nameProfessor "
				+ " and R.requestedDate IS NOT NULL"
				+ " and R.acceptedDate IS NULL"
				+ " ";

		Query query = session.createQuery(hql);
		query.setParameter("nameProfessor", professor.getUsername());
		size = query.list().size();
		session.close();

		return size;	
	}

}