package it.unical.classmanager.controllers.forum.manager;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.unical.classmanager.model.dao.AnswerAttachedContentDAO;
import it.unical.classmanager.model.dao.QuestionAttachedContentDAO;
import it.unical.classmanager.model.data.AnswerAttachedContent;
import it.unical.classmanager.model.data.QuestionAttachedContent;
import it.unical.classmanager.utils.FileManager;

public class ForumAttachmentGCManager {

	private QuestionAttachedContentDAO questionDAO;
	private AnswerAttachedContentDAO answerDAO;
	
	private final long DAY_TIMEOUT = 10;
	
	
	public ForumAttachmentGCManager(QuestionAttachedContentDAO questionDAO, AnswerAttachedContentDAO answerDAO) {
		
		this.questionDAO = questionDAO;
		this.answerDAO = answerDAO;
	}
	
	
	public void garbageCollect() {
		

		List<QuestionAttachedContent> questionAttachments = questionDAO.getAllQuestionAttachedContents();
		List<AnswerAttachedContent> answerAttachments = answerDAO.getAllAnswerAttachedContents();
		
		//check for question
		for(QuestionAttachedContent tmpAttachment : questionAttachments) {
			
			if(tmpAttachment.getQuestion() == null) {
				
				long millisDiff = new Date().getTime() - tmpAttachment.getCreationDate().getTime();
				//long daysDiff = TimeUnit.MILLISECONDS.toDays(millisDiff);
				
				long daysDiff = TimeUnit.MILLISECONDS.toSeconds(millisDiff);
				
				if(daysDiff >= TimeUnit.DAYS.toSeconds(DAY_TIMEOUT)) {
					
					questionDAO.delete(tmpAttachment);
					FileManager fm = new FileManager();
					fm.deleteFile(tmpAttachment.getFilePath());
				}
			}
			
		}
		
		//check for answer
		for(AnswerAttachedContent tmpAttachment : answerAttachments) {
			
			if(tmpAttachment.getAnswer() == null) {
				
				long millisDiff = new Date().getTime() - tmpAttachment.getCreationDate().getTime();
				//long daysDiff = TimeUnit.MILLISECONDS.toDays(millisDiff);
				
				long daysDiff = TimeUnit.MILLISECONDS.toSeconds(millisDiff);
				
				if(daysDiff >= TimeUnit.DAYS.toSeconds(DAY_TIMEOUT)) {
					
					answerDAO.delete(tmpAttachment);
					FileManager fm = new FileManager();
					fm.deleteFile(tmpAttachment.getFilePath());
				}
			}
			
		}
				
		
		
	}
	
	
	
}
