package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.Event;

public interface EventDAO {
	public void create(Event event);

	public void update(Event event);

	public void delete(Event event);

	public Event get(Integer id);

	public void deleteAllEvents();

	public int numberOfEvents();

	public List<Event> getAllEvents();
	
	public List<Event> getAllEventsOfUser(String userId);

	public void deleteAllEvents(String username);
}