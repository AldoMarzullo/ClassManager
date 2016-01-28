package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.HomeworkAttachedStudentSolving;

public interface HomeworkAttachedStudentSolvingDAO {
	public HomeworkAttachedStudentSolving create(HomeworkAttachedStudentSolving homeworkAttachedStudentSolving);

	public void update(HomeworkAttachedStudentSolving homeworkAttachedStudentSolving);

	public void delete(HomeworkAttachedStudentSolving homeworkAttachedStudentSolving);

	public HomeworkAttachedStudentSolving get(Integer id);

	public void deleteAllHomeworkAttachedStudentSolvings();

	public int numberOfHomeworkAttachedStudentSolvings();

	public List<HomeworkAttachedStudentSolving> getAllHomeworkAttachedStudentSolvings();

	public List<HomeworkAttachedStudentSolving> getAllHomeworkAttachedStudentSolvings(int homeworkStudentSolvingId);
}