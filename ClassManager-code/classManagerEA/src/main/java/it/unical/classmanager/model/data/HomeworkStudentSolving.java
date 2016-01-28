package it.unical.classmanager.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

/**
 * Every time a student upload a file referred to an {@link Homework} an instance of this class is created.
 */
@Entity
@Table(name ="homeworkStudentSolving")
public class HomeworkStudentSolving implements Serializable  {
	private static final long serialVersionUID = 2356496510705812739L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="score", nullable=false)
	@Range(min = 0, max = 30)
	private int score;
	
	@Column(name="date")
	private Date date;

	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "student")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "homework")
	private Homework homework;
	
	@OneToMany(mappedBy = "homeworkStudentSolving", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)	
	private List<HomeworkAttachedStudentSolving> homeworkAttachedStudentSolvings;
	
	public HomeworkStudentSolving(){
		this.id = 0;
		this.score = 0;
		this.date = null;
		this.student = null;
		this.homework = null;
		this.homeworkAttachedStudentSolvings = new ArrayList<HomeworkAttachedStudentSolving>();		
	}

	public HomeworkStudentSolving(int id, int score, Date date, Student student, Homework homework,
			List<HomeworkAttachedStudentSolving> homeworkAttachedStudentSolvings) {
		this.id = id;
		this.score = score;
		this.date = date;
		this.student = student;
		this.homework = homework;
		this.homeworkAttachedStudentSolvings = homeworkAttachedStudentSolvings;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Homework getHomework() {
		return homework;
	}

	public void setHomework(Homework homework) {
		this.homework = homework;
	}

	public List<HomeworkAttachedStudentSolving> getHomeworkAttachedStudentSolvings() {
		return homeworkAttachedStudentSolvings;
	}

	public void setHomeworkAttachedStudentSolvings(List<HomeworkAttachedStudentSolving> homeworkAttachedStudentSolvings) {
		this.homeworkAttachedStudentSolvings = homeworkAttachedStudentSolvings;
	}

	public Date getDate() {
		return date;
	}

	public void setDate( Date date) {
		this.date = date;
	}

}