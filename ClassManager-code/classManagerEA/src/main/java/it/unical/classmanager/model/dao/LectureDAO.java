package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;

public interface LectureDAO {
	public Lecture create(Lecture lecture);

	public void update(Lecture lecture);

	public void delete(Lecture lecture);

	public Lecture get(Integer id);

	public void deleteAllLectures();

	public int numberOfLectures();

	public List<Lecture> getAllLectures();	
	
	public Lecture getLastLectureAdded(int idCourse);
    
	public List<Lecture> getAllLecturesOfProfessor(CourseClass course);

	public List<Lecture> getAllLecturesOfACourse(int courseID);

	public List<Lecture> getAllLecturesOfAProfessor(String professorId);

	public List<Object[]> getLastLectures( Professor professor);

	public List<Object[]> getLastLectures( Student student);
}