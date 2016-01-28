package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.Homework;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.Student;

public interface HomeworkDAO {
	public Homework create(Homework homework);

	public void update(Homework homework);

	public void delete(Homework homework);

	public Homework get(Integer id);

	public void deleteAllHomeworks();

	public int numberOfHomeworks();

	public List<Homework> getAllHomeworks();

	public List<Homework> getAllHomeworks(int idCourse);
	
	public List<Homework> getAllLectureHomeworks(int idLecture);

	public List<Object[]> getLastHomeworks( Professor professor);

	public List<Object[]> getLastHomeworks( Student student);
}