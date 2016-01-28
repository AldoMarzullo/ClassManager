package it.unical.classmanager.model;

import java.util.ArrayList;
import java.util.List;

import it.unical.classmanager.model.data.HomeworkStudentSolving;
import it.unical.classmanager.model.data.StudentExamPartecipation;

public class AllScoresWrapper {

	private List<HomeworkStudentSolving> hss = new ArrayList<HomeworkStudentSolving>();
	private List<StudentExamPartecipation> oldExams = new ArrayList<StudentExamPartecipation>();
	private List<PartecipationWrapper> newExams = new ArrayList<PartecipationWrapper>();
	
	public List<HomeworkStudentSolving> getHss() {
		return hss;
	}
	public void setHss(List<HomeworkStudentSolving> hss) {
		this.hss = hss;
	}
	public List<StudentExamPartecipation> getOldExams() {
		return oldExams;
	}
	public void setOldExams(List<StudentExamPartecipation> oldExams) {
		this.oldExams = oldExams;
	}
	public List<PartecipationWrapper> getNewExams() {
		return newExams;
	}
	public void setNewExams(List<PartecipationWrapper> newExams) {
		this.newExams = newExams;
	}
	
}
