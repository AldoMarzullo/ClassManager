package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.Communications;

public interface CommunicationsDAO {
	
	public void create(Communications communications);

	public void update(Communications communications);

	public void delete(Communications communications);

	public Communications get(Integer id);

	public void deleteAllCommunications();

	public int numberOfCommunications();

	public List<Communications> getAllCommunications();

}