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

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name ="studentExamPartecipation")
public class StudentExamPartecipation implements Serializable  {
	private static final long serialVersionUID = 3302555836365798017L;

	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name="score", nullable=false)
	@Range(min = 0, max = 30)
	private int score;

	@Column(name="praise", nullable=true)
	private boolean praise;

	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "student")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "exam")
	private Exam exam;	

	public StudentExamPartecipation(){
		this.id = 0;
		this.score = 0;
		this.praise = false;
		this.student = null;
		this.exam = null;
	}	

	public StudentExamPartecipation(int id, int score, boolean praise, Student student, Exam exam) {
		this.id = id;
		this.score = score;
		this.praise = praise;
		this.student = student;
		this.exam = exam;
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

	public boolean isPraise() {
		return praise;
	}

	public void setPraise(boolean praise) {
		this.praise = praise;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
}