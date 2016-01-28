package it.unical.classmanager.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Every time a professor uploads a new homework an instance of this class is created
 */
@Entity
@Table(name ="homework")
public class Homework implements Serializable {
	private static final long serialVersionUID = 8212675234817509768L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="name", nullable=false, length=32)
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
	private String name;
	
	@Column(name="description", nullable=false, length=256)
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
	private String description;
	
	@Column(name="filePath", nullable=false, length=256)
	private String filePath;

	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "lecture")
	private Lecture lecture;
	
	@OneToMany(mappedBy = "homework", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<HomeworkAttached> homeworkAttacheds;
	
	@OneToMany(mappedBy = "homework", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<HomeworkStudentSolving> homeworkStudentSolvings;
	
	public Homework(){
		this.id = 0;
		this.name = "";
		this.description = "";
		this.filePath = "";
		this.lecture = null;
		this.homeworkAttacheds = new ArrayList<HomeworkAttached>();
		this.homeworkStudentSolvings = new ArrayList<HomeworkStudentSolving>();		
	}

	public Homework(int id, String name, String description, String filePath, Lecture lecture,
			List<HomeworkAttached> homeworkAttacheds, List<HomeworkStudentSolving> homeworkStudentSolvings) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.filePath = filePath;
		this.lecture = lecture;
		this.homeworkAttacheds = homeworkAttacheds;
		this.homeworkStudentSolvings = homeworkStudentSolvings;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public List<HomeworkAttached> getHomeworkAttacheds() {
		return homeworkAttacheds;
	}

	public void setHomeworkAttacheds(List<HomeworkAttached> homeworkAttacheds) {
		this.homeworkAttacheds = homeworkAttacheds;
	}

	public List<HomeworkStudentSolving> getHomeworkStudentSolvings() {
		return homeworkStudentSolvings;
	}

	public void setHomeworkStudentSolvings(List<HomeworkStudentSolving> homeworkStudentSolvings) {
		this.homeworkStudentSolvings = homeworkStudentSolvings;
	}
	
}