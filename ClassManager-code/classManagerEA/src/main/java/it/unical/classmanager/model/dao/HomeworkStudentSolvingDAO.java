package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.HomeworkStudentSolving;

public interface HomeworkStudentSolvingDAO {
	public HomeworkStudentSolving create(HomeworkStudentSolving homeworkStudentSolving);

	public void update(HomeworkStudentSolving homeworkStudentSolving);

	public void delete(HomeworkStudentSolving homeworkStudentSolving);

	public HomeworkStudentSolving get(Integer id);

	public void deleteAllHomeworkStudentSolvings();

	public int numberOfHomeworkStudentSolvings();

	public List<HomeworkStudentSolving> getAllHomeworkStudentSolvings();

	public List<HomeworkStudentSolving> getAllHomeworkStudentSolvings(String studentId);
}