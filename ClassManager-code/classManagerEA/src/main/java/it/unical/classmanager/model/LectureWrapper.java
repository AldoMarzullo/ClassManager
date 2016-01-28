package it.unical.classmanager.model;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Pattern;

import it.unical.classmanager.model.data.Lecture;

public class LectureWrapper{

	private Lecture lecture;
	
	@SuppressWarnings("unused")
	private int id;
	
	@SuppressWarnings("unused")
	private int number;
	
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
	private String topic;
	
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
	private String description;
	
	@SuppressWarnings("unused")
	private Date date;
	
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
	private String classroom;

	@SuppressWarnings("unused")
	//@Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$")
	private String beginHour;

	@SuppressWarnings("unused")
	//@Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$")
	private String endHour;
	
	public LectureWrapper(){

		this.setLecture(new Lecture());
	}

	public int getId() {
		return getLecture().getId();
	}

	public void setId(int id) {
		getLecture().setId(id);
		this.id = id;
	}

	public int getNumber() {
		return this.getLecture().getNumber();
	}

	public void setNumber(int number) {
		this.getLecture().setNumber(number);
		this.number = number;
	}

	public String getTopic() {
		return this.getLecture().getTopic();
	}

	public void setTopic(String topic) {
		this.getLecture().setTopic(topic);
		this.topic = topic;
	}

	public String getDescription() {
		return this.getLecture().getDescription();
	}

	public void setDescription(String description) {
		this.lecture.setDescription(description);
		this.description = description;
	}

	public Date getDate() {
		return getLecture().getDate();
	}

	public void setDate(Date date) {
		this.getLecture().setDate(date);
		this.date = date;
	}

	public Time getBeginHour() {
		return getLecture().getBeginHour();
	}

	public void setBeginHour(String beginHour) {

		DateFormat formatter = new SimpleDateFormat("hh:mm");
		Time time;
		
		try {
		
			time = new Time(formatter.parse(beginHour).getTime());
			this.getLecture().setBeginHour(time);
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.beginHour = beginHour;
	
	}

	public Time getEndHour() {
		return getLecture().getEndHour();
	}

	public void setEndHour(String endHour) {
		
		DateFormat formatter = new SimpleDateFormat("hh:mm");
		Time time;
		
		try {
		
			time = new Time(formatter.parse(endHour).getTime());
			this.getLecture().setEndHour(time);
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.endHour = endHour;
	}

	public String getClassroom() {
		return getLecture().getClassroom();
	}

	public void setClassroom(String classroom) {
		this.getLecture().setClassroom(classroom);
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
}