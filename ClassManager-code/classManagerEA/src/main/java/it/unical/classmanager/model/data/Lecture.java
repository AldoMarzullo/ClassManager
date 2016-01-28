package it.unical.classmanager.model.data;

import java.io.Serializable;
import java.sql.Time;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name ="lecture")
public class Lecture implements Serializable  {
	private static final long serialVersionUID = -5352067424398543272L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="number", nullable=false)
	private int number;
	
	@Column(name="topic", nullable=false, length=32)
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
	private String topic;
	
	@Column(name="description", nullable=false, length=256)
	@Pattern(regexp = "^[A-Za-z0-9 ]+$")
	private String description;
	
	@Column(name="date", nullable=false, length=32)
	@NotNull
	private Date date;
	
	@Column(name="beginHour", nullable=false, length=32)
	@NotNull
	private Time beginHour;
	
	@Column(name="endHour", nullable=false, length=32)
	@NotNull
	private Time endHour;
	
	@Column(name="classroom", nullable=false, length=32)
	@Pattern(regexp = "^[A-Za-z0-9]+$")
	private String classroom;

	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "courseClass")
	private CourseClass courseClass;
	
	@OneToMany(mappedBy = "lecture", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Material> materials;
	
	@OneToMany(mappedBy = "lecture", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<AttendanceStudentLecture> attendanceStudentLectures;
	
	@OneToMany(mappedBy = "lecture", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Homework> homeworks;
	
	public Lecture(){
		this.id = 0;
		this.number = 0;
		this.topic = "";
		this.description = "";
		this.date = null;
		this.beginHour = null;
		this.endHour = null;
		this.classroom = "";
		this.courseClass = null;
		this.materials = new ArrayList<Material>();
		this.attendanceStudentLectures = new ArrayList<AttendanceStudentLecture>();
		this.homeworks = new ArrayList<Homework>();
	}

	public Lecture(int id, int number, String topic, String description, Date date, Time beginHour, Time endHour,
			String classroom, CourseClass courseClass, List<Material> materials,
			List<AttendanceStudentLecture> attendanceStudentLectures, List<Homework> homeworks) {
		this.id = id;
		this.number = number;
		this.topic = topic;
		this.description = description;
		this.date = date;
		this.beginHour = beginHour;
		this.endHour = endHour;
		this.classroom = classroom;
		this.courseClass = courseClass;
		this.materials = materials;
		this.attendanceStudentLectures = attendanceStudentLectures;
		this.homeworks = homeworks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getBeginHour() {
		return beginHour;
	}

	public void setBeginHour(Time beginHour) {
		this.beginHour = beginHour;
	}

	public Time getEndHour() {
		return endHour;
	}

	public void setEndHour(Time endHour) {
		this.endHour = endHour;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public CourseClass getCourseClass() {
		return courseClass;
	}

	public void setCourseClass(CourseClass courseClass) {
		this.courseClass = courseClass;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public List<AttendanceStudentLecture> getAttendanceStudentLectures() {
		return attendanceStudentLectures;
	}

	public void setAttendanceStudentLectures(List<AttendanceStudentLecture> attendanceStudentLectures) {
		this.attendanceStudentLectures = attendanceStudentLectures;
	}

	public List<Homework> getHomeworks() {
		return homeworks;
	}

	public void setHomeworks(List<Homework> homeworks) {
		this.homeworks = homeworks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attendanceStudentLectures == null) ? 0 : attendanceStudentLectures.hashCode());
		result = prime * result + ((beginHour == null) ? 0 : beginHour.hashCode());
		result = prime * result + ((classroom == null) ? 0 : classroom.hashCode());
		result = prime * result + ((courseClass == null) ? 0 : courseClass.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endHour == null) ? 0 : endHour.hashCode());
		result = prime * result + ((homeworks == null) ? 0 : homeworks.hashCode());
		result = prime * result + id;
		result = prime * result + ((materials == null) ? 0 : materials.hashCode());
		result = prime * result + number;
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
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
		Lecture other = (Lecture) obj;
		if (attendanceStudentLectures == null) {
			if (other.attendanceStudentLectures != null)
				return false;
		} else if (!attendanceStudentLectures.equals(other.attendanceStudentLectures))
			return false;
		if (beginHour == null) {
			if (other.beginHour != null)
				return false;
		} else if (!beginHour.equals(other.beginHour))
			return false;
		if (classroom == null) {
			if (other.classroom != null)
				return false;
		} else if (!classroom.equals(other.classroom))
			return false;
		if (courseClass == null) {
			if (other.courseClass != null)
				return false;
		} else if (!courseClass.equals(other.courseClass))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endHour == null) {
			if (other.endHour != null)
				return false;
		} else if (!endHour.equals(other.endHour))
			return false;
		if (homeworks == null) {
			if (other.homeworks != null)
				return false;
		} else if (!homeworks.equals(other.homeworks))
			return false;
		if (id != other.id)
			return false;
		if (materials == null) {
			if (other.materials != null)
				return false;
		} else if (!materials.equals(other.materials))
			return false;
		if (number != other.number)
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		return true;
	}
	
	
}