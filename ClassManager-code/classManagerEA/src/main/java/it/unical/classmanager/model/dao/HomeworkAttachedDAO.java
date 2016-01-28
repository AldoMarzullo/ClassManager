package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.HomeworkAttached;

public interface HomeworkAttachedDAO {
	public void create(HomeworkAttached homeworkAttached);

	public void update(HomeworkAttached homeworkAttached);

	public void delete(HomeworkAttached homeworkAttached);

	public HomeworkAttached get(Integer id);

	public void deleteAllHomeworkAttacheds();

	public int numberOfHomeworkAttacheds();

	public List<HomeworkAttached> getAllHomeworkAttacheds();

	public List<HomeworkAttached> getAllHomeworkAttacheds(int idHomework);
}