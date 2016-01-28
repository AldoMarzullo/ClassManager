package it.unical.classmanager.model.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="homeworkAttached")
public class HomeworkAttached implements Serializable  {
	private static final long serialVersionUID = -1005491657239348830L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="name", nullable=true, length=256)
	private String name;
	
	@Column(name="filePath", nullable=false, length=256)
	private String filePath;

	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "homework")
	private Homework homework;
	
	public HomeworkAttached(){
		this.id = 0;
		this.filePath = "";
		this.name = "tmp";
		this.homework = null;	
	}

	public HomeworkAttached(int id, String filePath, Homework homework) {
		this.id = id;
		this.filePath = filePath;
		this.homework = homework;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Homework getHomework() {
		return homework;
	}

	public void setHomework(Homework homework) {
		this.homework = homework;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}