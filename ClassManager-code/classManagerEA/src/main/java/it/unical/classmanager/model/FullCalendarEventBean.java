package it.unical.classmanager.model;

import java.awt.Color;

import it.unical.classmanager.model.data.Event;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.User;

public class FullCalendarEventBean{

	private int id;
	private String title;
	private String description;
	private String start;
	private String end;
	private String place;
	private String username;
	private String color;
	private int[] dow;
	private boolean editable;

	public FullCalendarEventBean(){

		this.id = 0;
		this.title = "";
		this.description = "";
		this.start = null;
		this.end = null;
		this.place = "";
		this.color = null;
		this.setUsername(null);
		this.editable = true;
	}

	/*public Event(int id, String title, String description, Date startDate, Date endDate, String place, Time hourBegin,
			Time hourEnd, User user) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.start = startDate;
		this.end = endDate;
		this.place = place;
		this.hourBegin = hourBegin;
		this.hourEnd = hourEnd;
		this.user = user;
	}*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String startDate) {
		this.start = startDate;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String endDate) {
		this.end = endDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public int[] getDow() {
		return dow;
	}

	public void setDow(int[] dow) {
		this.dow = dow;
	}

	public static FullCalendarEventBean toFullCalendarEventBean(Event event){

		FullCalendarEventBean toReturn = new FullCalendarEventBean();
		toReturn.setId(event.getId());
		toReturn.setTitle(event.getTitle());
		toReturn.setDescription(event.getDescription());
		toReturn.setStart(event.getStartDate().toString());
		toReturn.setEnd(event.getEndDate().toString());
		toReturn.setPlace(event.getPlace());
		toReturn.setUsername(event.getUser().getUsername());
		toReturn.setColor(event.getColor());
		toReturn.setDow(event.getDow());
		toReturn.setEditable(true);

		return toReturn;
	}

	public static FullCalendarEventBean toFullCalendarEventBean(Lecture lecture, User user){

		FullCalendarEventBean toReturn = new FullCalendarEventBean();
		toReturn.setId(lecture.getId());
		toReturn.setTitle(lecture.getTopic());
		toReturn.setDescription(lecture.getDescription());
		toReturn.setStart(lecture.getDate().toString());
		toReturn.setEnd(lecture.getDate().toString());
		toReturn.setPlace(lecture.getClassroom());
		toReturn.setUsername(user.getUsername());
		toReturn.setColor(Color.darkGray.toString());
		toReturn.setEditable(false);

		return toReturn;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}