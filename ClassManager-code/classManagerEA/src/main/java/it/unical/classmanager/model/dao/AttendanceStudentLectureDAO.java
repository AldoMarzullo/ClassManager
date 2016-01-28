package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.data.AttendanceStudentLecture;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.Student;

public interface AttendanceStudentLectureDAO {
	public void create(AttendanceStudentLecture attendanceStudentLecture);

	public void update(AttendanceStudentLecture attendanceStudentLecture);

	public void delete(AttendanceStudentLecture attendanceStudentLecture);

	public AttendanceStudentLecture get(Integer id);

	public void deleteAllAttendanceStudentLectures();

	public int numberOfAttendanceStudentLectures();

	public List<AttendanceStudentLecture> getAllAttendanceStudentLectures();	

	public List<AttendanceStudentLecture> getAllAttendanceStudentLecturesOfACourse(Student student, CourseClass course);
	
	public List<Student> getAllAttendanceStudentLecturesOfALecture(Lecture lecture);
	
	public List<Student> getAllStudentsNotPresentOnALecture(Lecture lecture);
	
	public List<AttendanceStudentLecture> getAllAttendanceStudentLecturesOfAStudentAndALecture(Student student, Lecture lecture);
}