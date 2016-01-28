package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.Departement;

public interface DepartementDAO {
	public void create(Departement departement);

	public void update(Departement departement);

	public void delete(Departement departement);

	public Departement get(Integer id);

	public void deleteAllDepartements();

	public int numberOfDepartements();

	public List<Departement> getAllDepartements();
}