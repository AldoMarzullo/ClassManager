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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name ="courseClass")
public class CourseClass implements Serializable  {
	private static final long serialVersionUID = 8980414938557627568L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Size(min = 4, max = 64)
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
	@Column(name="name", nullable=false, length=64)
	private String name;
	
	@Min(3)
	@Max(12)
	@Column(name="cfu", nullable=false)
	private int cfu;

	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="activationDate", nullable=false)
	private Date activationDate;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="endDate", nullable=true)
	private Date endDate;

	@Min(1)
	@Max(5)
	@Column(name="referenceYear", nullable=false)
	private short referenceYear;
	
	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "professor")
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name = "degreeCourse")
	private DegreeCourse degreeCourse;
	
	@OneToMany(mappedBy = "courseClass", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Exam> exams;
	
	@OneToMany(mappedBy = "courseClass", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<RegistrationStudentClass> registrationStudentClasses;
	
	@OneToMany(mappedBy = "courseClass", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Lecture> lectures;
	
	/*@OneToMany(mappedBy = "courseClass", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Event> events;*/
	
	public CourseClass() {
		this.id = 0;
		this.name = "";
		this.cfu = 0;
		this.activationDate = null;
		this.endDate = null;
		this.referenceYear = 0;
		this.professor = null;
		this.degreeCourse = null;
		this.exams = new ArrayList<Exam>();
		this.registrationStudentClasses = new ArrayList<RegistrationStudentClass>();
		this.lectures = new ArrayList<Lecture>();
	}

	public CourseClass(int id, String name, int cfu, Date activationDate, Date endDate, short referenceYear,
			Professor professor, DegreeCourse degreeCourse, List<Exam> exams,
			List<RegistrationStudentClass> registrationStudentClasses, List<Lecture> lectures) {
		this.id = id;
		this.name = name;
		this.cfu = cfu;
		this.activationDate = activationDate;
		this.endDate = endDate;
		this.referenceYear = referenceYear;
		this.professor = professor;
		this.degreeCourse = degreeCourse;
		this.exams = exams;
		this.registrationStudentClasses = registrationStudentClasses;
		this.lectures = lectures;
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

	public int getCfu() {
		return cfu;
	}

	public void setCfu(int cfu) {
		this.cfu = cfu;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public short getReferenceYear() {
		return referenceYear;
	}

	public void setReferenceYear(short referenceYear) {
		this.referenceYear = referenceYear;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public DegreeCourse getDegreeCourse() {
		return degreeCourse;
	}

	public void setDegreeCourse(DegreeCourse degreeCourse) {
		this.degreeCourse = degreeCourse;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	public List<RegistrationStudentClass> getRegistrationStudentClasses() {
		return registrationStudentClasses;
	}

	public void setRegistrationStudentClasses(List<RegistrationStudentClass> registrationStudentClasses) {
		this.registrationStudentClasses = registrationStudentClasses;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activationDate == null) ? 0 : activationDate.hashCode());
		result = prime * result + cfu;
		result = prime * result + ((degreeCourse == null) ? 0 : degreeCourse.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((exams == null) ? 0 : exams.hashCode());
		result = prime * result + id;
		result = prime * result + ((lectures == null) ? 0 : lectures.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((professor == null) ? 0 : professor.hashCode());
		result = prime * result + referenceYear;
		result = prime * result + ((registrationStudentClasses == null) ? 0 : registrationStudentClasses.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseClass other = (CourseClass) obj;
		if (activationDate == null) {
			if (other.activationDate != null)
				return false;
		} else if (!activationDate.equals(other.activationDate))
			return false;
		if (cfu != other.cfu)
			return false;
		if (degreeCourse == null) {
			if (other.degreeCourse != null)
				return false;
		} else if (!degreeCourse.equals(other.degreeCourse))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (exams == null) {
			if (other.exams != null)
				return false;
		} else if (!exams.equals(other.exams))
			return false;
		if (id != other.id)
			return false;
		if (lectures == null) {
			if (other.lectures != null)
				return false;
		} else if (!lectures.equals(other.lectures))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		if (referenceYear != other.referenceYear)
			return false;
		if (registrationStudentClasses == null) {
			if (other.registrationStudentClasses != null)
				return false;
		} else if (!registrationStudentClasses.equals(other.registrationStudentClasses))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CourseClass [id=" + id + ", name=" + name + ", cfu=" + cfu + ", activationDate=" + activationDate
				+ ", endDate=" + endDate + ", referenceYear=" + referenceYear + ", professor=" + professor
				+ ", degreeCourse=" + degreeCourse + ", exams=" + exams + ", registrationStudentClasses="
				+ registrationStudentClasses + ", lectures=" + lectures + "]";
	}

	/*public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}*/
	
	
}