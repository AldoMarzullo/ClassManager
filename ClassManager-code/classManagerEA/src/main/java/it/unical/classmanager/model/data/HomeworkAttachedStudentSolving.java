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
@Table(name ="homeworkAttachedStudentSolving")
public class HomeworkAttachedStudentSolving implements Serializable  {
	private static final long serialVersionUID = 3508705674102327059L;
	
	@Id
	@Column(name="id", nullable=false, length=32)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="filePath", nullable=false, length=256)
	private String filePath;
	
	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "homeworkStudentSolving")
	private HomeworkStudentSolving homeworkStudentSolving;
	
	public HomeworkAttachedStudentSolving(){
		this.id = 0;
		this.filePath = "";
		this.homeworkStudentSolving = null;		
	}

	public HomeworkAttachedStudentSolving(int id, String filePath, HomeworkStudentSolving homeworkStudentSolving) {
		this.id = id;
		this.filePath = filePath;
		this.homeworkStudentSolving = homeworkStudentSolving;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public HomeworkStudentSolving getHomeworkStudentSolving() {
		return homeworkStudentSolving;
	}

	public void setHomeworkStudentSolving(HomeworkStudentSolving homeworkStudentSolving) {
		this.homeworkStudentSolving = homeworkStudentSolving;
	}

}