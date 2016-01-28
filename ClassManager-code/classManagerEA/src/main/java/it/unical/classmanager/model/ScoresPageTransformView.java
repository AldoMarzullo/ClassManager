package it.unical.classmanager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/*
 * This class is used to organize informations about the evaluations. This organization is useful to visualize
 * data in the related view
 */
public class ScoresPageTransformView {

	//contains the association id-name
	private HashMap<String, String> students;
	//contains the association studentId - HomeworkIds
	private HashMap<String, List<Integer>> studentHomeworks;
	//contains the association HomeworkID - score
	private HashMap<Integer, Integer> scores;
	//contains the association homeworkId - homeworkname
	private HashMap<Integer, String> homeworksName;
	//contains the association (studentId + "-" homeworkId) - homeworkStudentSolvingId
	private HashMap<String, Integer> homeworkSolution;

	public ScoresPageTransformView() {
		
		setStudents(new HashMap<String, String>());
		setStudentHomeworks(new HashMap<String, List<Integer>>());
		setScores(new HashMap<Integer, Integer>());
		setHomeworksName(new HashMap<Integer, String>());
		setHomeworksSolved(new HashMap<String, Integer>());
	}
	
	public void addHomeworkSolution(String key, int idSolution){
		
		this.getHomeworksSolved().put(key, idSolution);
	}
	
	public void addStudent(String id, String name){

		this.getStudents().put(id, name);
	}

	public String getStudent(int id){

		return this.getStudents().get(id);
	}

	public Set<String> getStudentsIds(){

		return this.getStudents().keySet();
	}

	public void addHomework(String username, int idHomework){

		if(this.getStudentHomeworks().get(username) == null)
			this.getStudentHomeworks().put(username, new ArrayList<Integer>());
		
		this.getStudentHomeworks().get(username).add(idHomework);
	}

	public List<Integer> getHomeworks(int idStudent){

		return this.getStudentHomeworks().get(idStudent);
	}

	public void addScore(int idHomework, int score){
		
		this.getScores().put(idHomework, score);
	}
	
	public Integer getScore(int idHomework){
		
		return this.getScores().get(idHomework);
	}
	
	public void addHomeworkName(int idHomework, String name){
		
		this.getHomeworksName().put(idHomework, name);
	}

	public HashMap<String, String> getStudents() {
		return students;
	}

	public void setStudents(HashMap<String, String> students) {
		this.students = students;
	}

	public HashMap<String, List<Integer>> getStudentHomeworks() {
		return studentHomeworks;
	}

	public void setStudentHomeworks(HashMap<String, List<Integer>> studentHomeworks) {
		this.studentHomeworks = studentHomeworks;
	}

	public HashMap<Integer, Integer> getScores() {
		return scores;
	}

	public void setScores(HashMap<Integer, Integer> scores) {
		this.scores = scores;
	}

	public HashMap<Integer, String> getHomeworksName() {
		return homeworksName;
	}

	public void setHomeworksName(HashMap<Integer, String> homeworksName) {
		this.homeworksName = homeworksName;
	}

	public HashMap<String, Integer> getHomeworksSolved() {
		return homeworkSolution;
	}

	public void setHomeworksSolved(HashMap<String, Integer> homeworksSolved) {
		this.homeworkSolution = homeworksSolved;
	}
}
