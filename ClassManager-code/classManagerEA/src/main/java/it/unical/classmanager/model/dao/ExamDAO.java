package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.Exam;

public interface ExamDAO {
	public void create(Exam exam);

	public void update(Exam exam);

	public void delete(Exam exam);

	public Exam get(Integer id);

	public void deleteAllExams();

	public int numberOfExams();

	public List<Exam> getAllExams();	
}