package it.unical.classmanager.controllers.forum.manager;

import java.util.List;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;

import it.unical.classmanager.model.data.Question;

public class QuestionManager {
	
	private List<Question> questions;
	private PagedListHolder<Question> paginationHolder;
	
	private int pageSize;
	private final int INITIAL_PAGE_SIZE = 5;
	
	
	public QuestionManager(List<Question> questions) {
		
		this.questions = questions;
		
		this.pageSize = INITIAL_PAGE_SIZE;
		initPagination();
	}

	private void initPagination() {
		
		this.paginationHolder = new PagedListHolder<Question>(this.questions, new MutableSortDefinition("id", true, false));
		this.paginationHolder.setPageSize(this.pageSize);
		
		this.paginationHolder.resort();
	}
	
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Question> getPreviousPageQuestions(int index) {
		
		return getSpecificPageQuestions(index-1);
	}
	
	public List<Question> getNextPageQuestions(int index) {
		
		return getSpecificPageQuestions(index+1);
	}
	
	public List<Question> getCurrentPageQuestions() {
		return this.paginationHolder.getPageList();
	}
	
	
	public int getCurrentPageNumber() {
		return this.paginationHolder.getPage();
	}
	
	public int getPageCount() {
		return this.paginationHolder.getPageCount();
	}
	
	public int getElementCount() {
		return this.paginationHolder.getNrOfElements();
	}
	
	
	public List<Question> getSpecificPageQuestions(int index) {
		
		if(index >= 0 && index < this.getPageCount()) {
			
			while(index < this.getCurrentPageNumber()) {
				this.paginationHolder.previousPage();
			}
			
			while(index > this.getCurrentPageNumber()) {
				this.paginationHolder.nextPage();
			}
		}
		
		return this.getCurrentPageQuestions();
	}
	
}
