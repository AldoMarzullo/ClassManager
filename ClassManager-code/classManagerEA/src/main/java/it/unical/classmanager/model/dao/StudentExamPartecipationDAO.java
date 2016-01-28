package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.Exam;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.StudentExamPartecipation;

public interface StudentExamPartecipationDAO {
	public void create(StudentExamPartecipation studentExamPartecipation);

	public void update(StudentExamPartecipation studentExamPartecipation);

	public void delete(StudentExamPartecipation studentExamPartecipation);

	public StudentExamPartecipation get(Integer id);

	public void deleteAllStudentExamPartecipations();

	public int numberOfStudentExamPartecipations();

	public List<StudentExamPartecipation> getAllStudentExamPartecipations();
	
	public List<StudentExamPartecipation> getPartecipationBy(Student student, Exam exam);
}