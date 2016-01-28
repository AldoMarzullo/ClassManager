package it.unical.classmanager.controllers.propertyEditor;

import java.beans.PropertyEditorSupport;

import it.unical.classmanager.model.dao.DegreeCourseDAO;
import it.unical.classmanager.model.data.DegreeCourse;

/**
 * Classe che gestisce il mapping tra ciò che restituisce una form con l'oggetto DegreeCourse
 * 
 * @author Alessandro
 *
 */
public class DegreeCoursePropertyEditor extends PropertyEditorSupport
{
	private DegreeCourseDAO degreeCourseDAO;

	public DegreeCoursePropertyEditor(DegreeCourseDAO degreeCourseDAO)
	{
		this.degreeCourseDAO = degreeCourseDAO;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException
	{
		DegreeCourse degreeCourse = degreeCourseDAO.get(Integer.parseInt(text));
		setValue(degreeCourse);
	}
}