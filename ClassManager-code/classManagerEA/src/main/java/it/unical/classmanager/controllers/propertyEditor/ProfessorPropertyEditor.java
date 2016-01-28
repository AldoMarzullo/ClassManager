package it.unical.classmanager.controllers.propertyEditor;

import java.beans.PropertyEditorSupport;

import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.data.Professor;

/**
 * Classe che gestisce il mapping tra ciò che restituisce una form con l'oggetto DegreeCourse
 * 
 * @author Alessandro
 *
 */
public class ProfessorPropertyEditor extends PropertyEditorSupport
{
	private UserDAO userDAO;

	public ProfessorPropertyEditor(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException
	{
		Professor professor = (Professor) userDAO.get(text);
		setValue(professor);
	}
}