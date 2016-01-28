package it.unical.classmanager.model.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name ="communications")
public class Communications implements Serializable  {
	private static final long serialVersionUID = 3020678895004265567L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="name", nullable=false, length=32)
	@Size(min=4,max=32)
	private String name;
	
	@Column(name="description", nullable=false, length=256)
	@Size(min=4,max=256)
	private String description;	
	
	@Column(name="date", nullable=false)
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private Date date;

	@Type(type="yes_no")
	private boolean serviceMessage;
	
	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "professor")
	private Professor professor;
	
	public Communications(){
		this.id = 0;
		this.name = "";
		this.description = "";
		this.professor = null;
		this.date = null;
		this.serviceMessage = false;	
	}

	public Communications(int id, String name, String description, Professor professor,Date date,boolean serviceMessage) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.professor = professor;
		this.date = date;
		this.serviceMessage = serviceMessage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setServiceMessage(boolean serviceMessage) {
		this.serviceMessage = serviceMessage;
	}
	
	public boolean isServiceMessage() {
		return serviceMessage;
	}
	
	@Override
	public String toString() {
		return id + ", " + name + ", " + description + ", " + date + ", " + professor.getLastName();
		
	}
	
}