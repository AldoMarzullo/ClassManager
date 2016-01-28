package it.unical.classmanager.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="student")
@PrimaryKeyJoinColumn(name="username")
public class Student extends User implements Serializable  {
	private static final long serialVersionUID = -8091594666965016805L;

	@Column(name="subscriptionDate", nullable=false)
	private Date subscriptionDate;

	//	Foreign key section
	@OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<StudentExamPartecipation> studentExamPartecipations;

	@OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<AttendanceStudentLecture> attendanceStudentLectures;

	@OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<RegistrationStudentClass> registrationStudentClasses;

	@OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<HomeworkStudentSolving> homeworkStudentSolvings;

	public Student(){
		super();
		this.subscriptionDate = null;
		this.studentExamPartecipations = new ArrayList<StudentExamPartecipation>();
		this.attendanceStudentLectures = new ArrayList<AttendanceStudentLecture>();
		this.registrationStudentClasses = new ArrayList<RegistrationStudentClass>();
		this.homeworkStudentSolvings = new ArrayList<HomeworkStudentSolving>();
	}

	public Student(int id, String username, String password, String confirmPassword, String role, String firstName, String lastName,
			String email, Date birthDate, String address, List<Event> events, List<Question> questions,
			List<Answer> answers) {
		super(username, password, confirmPassword, role, firstName, lastName, email, birthDate, address, events, questions, answers);
		this.subscriptionDate = null;
		this.studentExamPartecipations = new ArrayList<StudentExamPartecipation>();
		this.attendanceStudentLectures = new ArrayList<AttendanceStudentLecture>();
		this.registrationStudentClasses = new ArrayList<RegistrationStudentClass>();
		this.homeworkStudentSolvings = new ArrayList<HomeworkStudentSolving>();
	}

	public Student(int id, String username, String password, String confirmPassword, String role, String firstName, String lastName,
			String email, Date birthDate, String address, List<Event> events, List<Question> questions,
			List<Answer> answers, Date subscriptionDate,
			List<StudentExamPartecipation> studentExamPartecipations,
			List<AttendanceStudentLecture> attendanceStudentLectures,
			List<RegistrationStudentClass> registrationStudentClasses,
			List<HomeworkStudentSolving> homeworkStudentSolvings) {
		super(username, password, confirmPassword, role, firstName, lastName, email, birthDate, address, events, questions, answers);
		this.subscriptionDate = subscriptionDate;
		this.studentExamPartecipations = studentExamPartecipations;
		this.attendanceStudentLectures = attendanceStudentLectures;
		this.registrationStudentClasses = registrationStudentClasses;
		this.homeworkStudentSolvings = homeworkStudentSolvings;
	}

	public Student(User user){
		super(user);
		this.subscriptionDate = null;
		this.studentExamPartecipations = new ArrayList<StudentExamPartecipation>();
		this.attendanceStudentLectures = new ArrayList<AttendanceStudentLecture>();
		this.registrationStudentClasses = new ArrayList<RegistrationStudentClass>();
		this.homeworkStudentSolvings = new ArrayList<HomeworkStudentSolving>();
	}

	public Student(User user, int identificationNumber, Date subscriptionDate,
			List<StudentExamPartecipation> studentExamPartecipations,
			List<AttendanceStudentLecture> attendanceStudentLectures,
			List<RegistrationStudentClass> registrationStudentClasses,
			List<HomeworkStudentSolving> homeworkStudentSolvings){
		super(user);
		this.subscriptionDate = subscriptionDate;
		this.studentExamPartecipations = studentExamPartecipations;
		this.attendanceStudentLectures = attendanceStudentLectures;
		this.registrationStudentClasses = registrationStudentClasses;
		this.homeworkStudentSolvings = homeworkStudentSolvings;
	}

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public List<StudentExamPartecipation> getStudentExamPartecipations() {
		return studentExamPartecipations;
	}

	public void setStudentExamPartecipations(List<StudentExamPartecipation> studentExamPartecipations) {
		this.studentExamPartecipations = studentExamPartecipations;
	}

	public List<AttendanceStudentLecture> getAttendanceStudentLectures() {
		return attendanceStudentLectures;
	}

	public void setAttendanceStudentLectures(List<AttendanceStudentLecture> attendanceStudentLectures) {
		this.attendanceStudentLectures = attendanceStudentLectures;
	}

	public List<RegistrationStudentClass> getRegistrationStudentClasses() {
		return registrationStudentClasses;
	}

	public void setRegistrationStudentClasses(List<RegistrationStudentClass> registrationStudentClasses) {
		this.registrationStudentClasses = registrationStudentClasses;
	}

	public List<HomeworkStudentSolving> getHomeworkStudentSolvings() {
		return homeworkStudentSolvings;
	}

	public void setHomeworkStudentSolvings(List<HomeworkStudentSolving> homeworkStudentSolvings) {
		this.homeworkStudentSolvings = homeworkStudentSolvings;
	}

	@Override
	public String toString() {
		return "Student [getUsername()=" + getUsername() + ", getRole()=" + getRole() + ", getFirstName()="
				+ getFirstName() + ", getLastName()=" + getLastName() + ", getEmail()=" + getEmail()
				+ ", getBirthDate()=" + getBirthDate() + ", getSerialNumber()=" + getSerialNumber() + ", getPassword()="
				+ getPassword() + ", getConfirmPassword()=" + getConfirmPassword() + ", getHash()=" + getHash() + "]";
	}	
	
	

}