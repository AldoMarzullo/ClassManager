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

@Entity
@Table(name ="exam")
public class Exam implements Serializable  {
	private static final long serialVersionUID = 1624700992756827837L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
		
	@Column(name="name", nullable=false, length=32)
	private String name;
	
	@Column(name="date", nullable=false)
	private Date date;
	
	@Column(name="tipology", nullable=false, length=32)
	private String tipology;
	
	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "courseClass")
	private CourseClass courseClass;
	
	@OneToMany(mappedBy = "exam", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<StudentExamPartecipation> studentExamPartecipations;
	
	public Exam(){
		this.id = 0;
		this.name = "";
		this.date = null;
		this.tipology = "";
		this.courseClass = null;
		this.studentExamPartecipations = new ArrayList<StudentExamPartecipation>();		
	}

	public Exam(int id, String name, Date date, String tipology, CourseClass courseClass,
			List<StudentExamPartecipation> studentExamPartecipations) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.tipology = tipology;
		this.courseClass = courseClass;
		this.studentExamPartecipations = studentExamPartecipations;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTipology() {
		return tipology;
	}

	public void setTipology(String tipology) {
		this.tipology = tipology;
	}

	public CourseClass getCourseClass() {
		return courseClass;
	}

	public void setCourseClass(CourseClass courseClass) {
		this.courseClass = courseClass;
	}

	public List<StudentExamPartecipation> getStudentExamPartecipations() {
		return studentExamPartecipations;
	}

	public void setStudentExamPartecipations(List<StudentExamPartecipation> studentExamPartecipations) {
		this.studentExamPartecipations = studentExamPartecipations;
	}
	
}