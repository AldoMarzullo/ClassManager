package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.RegistrationStudentClass;
import it.unical.classmanager.model.data.Student;

public interface RegistrationStudentClassDAO {

	public void create(RegistrationStudentClass registrationStudentClass);

	public void update(RegistrationStudentClass registrationStudentClass);

	public void delete(RegistrationStudentClass registrationStudentClass);

	public RegistrationStudentClass get(Integer id);

	public void deleteAllRegistrationStudentClasses();

	public int numberOfRegistrationStudentClasses();

	public List<RegistrationStudentClass> getAllRegistrationStudentClasses();

	public List<Student> getStudentsRegisteredToACourse( CourseClass course);

	public List<Object[]> getSelectableCourse( Student student);

	public List<Object[]> getSelectableCourse( Professor professor);

	public List<Object[]> getCancellableCourse( Student student);

	public List<Object[]> getSelectableStudent( CourseClass courseClass, String research);

	public List<Object[]> getCancellableStudent( CourseClass courseClass, String research);

	public List<Object[]> getAcceptableCourse( Student student);

	public List<Object[]> getAcceptableStudent( Professor professor, String research);

	public int getNewInvitationsOfStudent( Student student);

	public int getNewInvitationsOfProfessor( Professor professor);

	public int getMaxIndex();

	public boolean existRegistration(Student student, CourseClass courseClass);

	public RegistrationStudentClass getRegistration(Student student, CourseClass courseClass);

}
