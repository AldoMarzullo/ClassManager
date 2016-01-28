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

@Entity
@Table(name ="registrationStudentClass")
public class RegistrationStudentClass implements Serializable  {
	private static final long serialVersionUID = 2484692237674690778L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="invitationDate", nullable=true)
	private Date invitationDate;
	
	@Column(name="acceptedDate", nullable=true)
	private Date acceptedDate;
	
	@Column(name="requestedDate", nullable=true)
	private Date requestedDate;

	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "student")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "courseClass")
	private CourseClass courseClass;
	
	public RegistrationStudentClass(){
		this.id = 0;
		this.invitationDate = null;
		this.acceptedDate = null;
		this.requestedDate = null;
		this.student = null;
		this.courseClass = null;
	}

	public RegistrationStudentClass(int id, Date invitationDate, Date acceptedDate, Date requestedDate, Student student,
			CourseClass courseClass) {
		this.id = id;
		this.invitationDate = invitationDate;
		this.acceptedDate = acceptedDate;
		this.requestedDate = requestedDate;
		this.student = student;
		this.courseClass = courseClass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(Date invitationDate) {
		this.invitationDate = invitationDate;
	}

	public Date getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public CourseClass getCourseClass() {
		return courseClass;
	}

	public void setCourseClass(CourseClass courseClass) {
		this.courseClass = courseClass;
	}
	
}