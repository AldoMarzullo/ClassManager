package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.DegreeCourse;

public interface DegreeCourseDAO {
	public void create(DegreeCourse degreeCourse);

	public void update(DegreeCourse degreeCourse);

	public void delete(DegreeCourse degreeCourse);

	public DegreeCourse get(Integer id);

	public void deleteAllDegreeCourses();

	public int numberOfDegreeCourses();

	public List<DegreeCourse> getAllDegreeCourses();
}