package it.unical.classmanager.model.dao;

import it.unical.classmanager.model.DBHandler;
import it.unical.classmanager.model.PasswordHashing;

/**
 * @author Aloisius92
 * This is a class that behave
 * like a global point to access all daos.
 */
public class DaoHelper {    
	private static DaoHelper instance;
	private  DBHandler dbHandler;
	private  AnswerAttachedContentDAO answerAttachedContentDAO;
	private  AnswerDAO answerDAO;
	private  AttendanceStudentLectureDAO attendanceStudentLectureDAO;
	private  CartQueryDAO cartQueryDAO;
	private  CommunicationsDAO communicationsDAO;
	private  CourseClassDAO courseClassDAO;
	private  DegreeCourseDAO degreeCourseDAO;
	private  DepartementDAO departementDAO;
	private  EventDAO eventDAO;
	private  ExamDAO examDAO;
	private  HomeworkAttachedDAO homeworkAttachedDAO;
	private  HomeworkAttachedStudentSolvingDAO homeworkAttachedStudentSolvingDAO;
	private  HomeworkDAO homeworkDAO;
	private  HomeworkStudentSolvingDAO homeworkStudentSolvingDAO;
	private  LectureDAO lectureDAO;
	private  MaterialDAO materialDAO;
	private  QuestionAttachedContentDAO questionAttachedContentDAO;
	private  QuestionDAO questionDAO;
	private  RegistrationStudentClassDAO registrationStudentClassDAO;
	private  StudentExamPartecipationDAO studentExamPartecipationDAO;
	private  NotificationDAO notificationDAO;
	private  UserDAO userDAO;
	
	private PasswordHashing passwordHashing;

	public DaoHelper() {

	}

	public static DaoHelper getInstance() {
		return instance;
	}

	public void setInstance( Object arg0) {
		DaoHelper.instance = (DaoHelper) arg0;	
	}

	public void setDbHandler(DBHandler dbHandler)
	{
		this.dbHandler = dbHandler;
	}

	public static DBHandler getDbHandler()
	{
		return DaoHelper.instance.dbHandler;
	}

	public static AnswerAttachedContentDAO getAnswerAttachedContentDAO() {
		return DaoHelper.instance.answerAttachedContentDAO;
	}

	public void setAnswerAttachedContentDAO( AnswerAttachedContentDAO answerAttachedContentDAO) {
		this.answerAttachedContentDAO = answerAttachedContentDAO;
	}

	public static AnswerDAO getAnswerDAO() {
		return DaoHelper.instance.answerDAO;
	}

	public void setAnswerDAO( AnswerDAO answerDAO) {
		this.answerDAO = answerDAO;
	}

	public static AttendanceStudentLectureDAO getAttendanceStudentLectureDAO() {
		return DaoHelper.instance.attendanceStudentLectureDAO;
	}

	public void setAttendanceStudentLectureDAO( AttendanceStudentLectureDAO attendanceStudentLectureDAO) {
		this.attendanceStudentLectureDAO = attendanceStudentLectureDAO;
	}

	public static CartQueryDAO getCartQueryDAO() {
		return DaoHelper.instance.cartQueryDAO;
	}

	public void setCartQueryDAO( CartQueryDAO cartQueryDAO) {
		this.cartQueryDAO = cartQueryDAO;
	}

	public static CommunicationsDAO getCommunicationsDAO() {
		return DaoHelper.instance.communicationsDAO;
	}

	public void setCommunicationsDAO( CommunicationsDAO communicationsDAO) {
		this.communicationsDAO = communicationsDAO;
	}

	public static CourseClassDAO getCourseClassDAO() {
		return DaoHelper.instance.courseClassDAO;
	}

	public void setCourseClassDAO( CourseClassDAO courseClassDAO) {
		this.courseClassDAO = courseClassDAO;
	}	

	public static DegreeCourseDAO getDegreeCourseDAO() {
		return DaoHelper.instance.degreeCourseDAO;
	}

	public void setDegreeCourseDAO( DegreeCourseDAO degreeCourseDAO) {
		this.degreeCourseDAO = degreeCourseDAO;
	}

	public static DepartementDAO getDepartementDAO() {
		return DaoHelper.instance.departementDAO;
	}

	public void setDepartementDAO( DepartementDAO departementDAO) {
		this.departementDAO = departementDAO;
	}

	public static EventDAO getEventDAO() {
		return DaoHelper.instance.eventDAO;
	}

	public void setEventDAO( EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public static ExamDAO getExamDAO() {
		return DaoHelper.instance.examDAO;
	}

	public void setExamDAO( ExamDAO examDAO) {
		this.examDAO = examDAO;
	}

	public static HomeworkAttachedDAO getHomeworkAttachedDAO() {
		return DaoHelper.instance.homeworkAttachedDAO;
	}

	public void setHomeworkAttachedDAO( HomeworkAttachedDAO homeworkAttachedDAO) {
		this.homeworkAttachedDAO = homeworkAttachedDAO;
	}

	public static HomeworkAttachedStudentSolvingDAO getHomeworkAttachedStudentSolvingDAO() {
		return DaoHelper.instance.homeworkAttachedStudentSolvingDAO;
	}

	public void setHomeworkAttachedStudentSolvingDAO( HomeworkAttachedStudentSolvingDAO homeworkAttachedStudentSolvingDAO) {
		this.homeworkAttachedStudentSolvingDAO = homeworkAttachedStudentSolvingDAO;
	}

	public static HomeworkDAO getHomeworkDAO() {
		return DaoHelper.instance.homeworkDAO;
	}

	public void setHomeworkDAO( HomeworkDAO homeworkDAO) {
		this.homeworkDAO = homeworkDAO;
	}

	public static HomeworkStudentSolvingDAO getHomeworkStudentSolvingDAO() {
		return DaoHelper.instance.homeworkStudentSolvingDAO;
	}

	public void setHomeworkStudentSolvingDAO( HomeworkStudentSolvingDAO homeworkStudentSolvingDAO) {
		this.homeworkStudentSolvingDAO = homeworkStudentSolvingDAO;
	}

	public static LectureDAO getLectureDAO() {
		return DaoHelper.instance.lectureDAO;
	}

	public void setLectureDAO( LectureDAO lectureDAO) {
		this.lectureDAO = lectureDAO;
	}

	public static MaterialDAO getMaterialDAO() {
		return DaoHelper.instance.materialDAO;
	}

	public void setMaterialDAO( MaterialDAO materialDAO) {
		this.materialDAO = materialDAO;
	}

	public static QuestionAttachedContentDAO getQuestionAttachedContentDAO() {
		return DaoHelper.instance.questionAttachedContentDAO;
	}

	public void setQuestionAttachedContentDAO( QuestionAttachedContentDAO questionAttachedContentDAO) {
		this.questionAttachedContentDAO = questionAttachedContentDAO;
	}

	public static QuestionDAO getQuestionDAO() {
		return DaoHelper.instance.questionDAO;
	}

	public void setQuestionDAO( QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	public static RegistrationStudentClassDAO getRegistrationStudentClassDAO() {
		return DaoHelper.instance.registrationStudentClassDAO;
	}

	public void setRegistrationStudentClassDAO( RegistrationStudentClassDAO registrationStudentClassDAO) {
		this.registrationStudentClassDAO = registrationStudentClassDAO;
	}

	public static StudentExamPartecipationDAO getStudentExamPartecipationDAO() {
		return DaoHelper.instance.studentExamPartecipationDAO;
	}

	public void setStudentExamPartecipationDAO( StudentExamPartecipationDAO studentExamPartecipationDAO) {
		this.studentExamPartecipationDAO = studentExamPartecipationDAO;
	}

	public static UserDAO getUserDAO() {
		return DaoHelper.instance.userDAO;
	}

	public void setUserDAO( UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public static NotificationDAO getNotificationDAO() {
		return DaoHelper.instance.notificationDAO;
	}

	public void setNotificationDAO(NotificationDAO notificationDAO) {
		this.notificationDAO = notificationDAO;
	}
	
	public void setPasswordHashing(PasswordHashing passwordHashing) {
		this.passwordHashing = passwordHashing;
	}
	
	public PasswordHashing getPasswordHashing() {
		return passwordHashing;
	}

}
