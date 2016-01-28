package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;

public interface CourseClassDAO {
	public void create(CourseClass courseClass);

	public void update(CourseClass courseClass);

	public void delete(CourseClass courseClass);

	public CourseClass get(Integer id);

	public CourseClass get(String name);

	public void deleteAllCourseClasses();

	public int numberOfCourseClasses();

	public List<CourseClass> getAllCourseClasses();	

	public List<CourseClass> getCourseClasses(Professor professor);
	
	public List<CourseClass> getCourseClasses(Student studente);

	public List<Object[]> getCourses(Professor professor);
	
	public List<Object[]> getCourses(Student student);
}