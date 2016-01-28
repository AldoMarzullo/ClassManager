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
@Table(name ="attendanceStudentLecture")
public class AttendanceStudentLecture implements Serializable  {
	private static final long serialVersionUID = -7656285211153292323L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "student")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "lecture")
	private Lecture lecture;
	
	public AttendanceStudentLecture(){
		this.id = 0;
		this.student = null;
		this.lecture = null;
	}

	public AttendanceStudentLecture(int id, Student student, Lecture lecture) {
		this.id = id;
		this.student = student;
		this.lecture = lecture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
	
}