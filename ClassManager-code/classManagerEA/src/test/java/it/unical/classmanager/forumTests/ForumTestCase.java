package it.unical.classmanager.forumTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.unical.classmanager.controllers.forum.manager.ForumAttachmentGCManager;
import it.unical.classmanager.model.dao.AnswerAttachedContentDAO;
import it.unical.classmanager.model.dao.AnswerAttachedContentDAOImpl;
import it.unical.classmanager.model.dao.QuestionAttachedContentDAO;
import it.unical.classmanager.model.dao.QuestionAttachedContentDAOImpl;
import it.unical.classmanager.model.data.QuestionAttachedContent;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/WEB-INF/spring/root-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForumTestCase {
	
	@Autowired
	private ApplicationContext appContext;
	
	private static int questionAttachedId;

	@Test
	public void t1_garbageCollectorTestNotRemove() throws IOException {
		
		String fileName = "prova.jpg";
		String filePath = "files/forumAttachmentTest/" + fileName;
		new File("files/forumAttachmentTest/").mkdirs();
		new File(filePath).createNewFile();
		
		
		QuestionAttachedContentDAO questionAttachedDAO = (QuestionAttachedContentDAOImpl) appContext.getBean("questionAttachedContentDAO", QuestionAttachedContentDAOImpl.class);
		AnswerAttachedContentDAO answerAttachedDAO = (AnswerAttachedContentDAOImpl) appContext.getBean("answerAttachedContentDAO", AnswerAttachedContentDAOImpl.class);
		
		QuestionAttachedContent questionAttached = new QuestionAttachedContent();
		
		questionAttached.setName(fileName);
		questionAttached.setFilePath(filePath);
		questionAttached.setCreationDate(new Date());
		questionAttached = questionAttachedDAO.create(questionAttached);
		ForumTestCase.questionAttachedId = questionAttached.getId();
		
		ForumAttachmentGCManager gc = new ForumAttachmentGCManager(questionAttachedDAO, answerAttachedDAO);
		gc.garbageCollect();
		
		assertNotNull(questionAttachedDAO.get(questionAttachedId));
		assertTrue(new File(filePath).exists());
		
		
	}
	
	
	
	@Test
	public void t2_garbageCollectorTestRemove() {
		
		String filePath = "files/forumAttachmentTest/prova.jpg";
		
		QuestionAttachedContentDAO questionAttachedDAO = (QuestionAttachedContentDAOImpl) appContext.getBean("questionAttachedContentDAO", QuestionAttachedContentDAOImpl.class);
		AnswerAttachedContentDAO answerAttachedDAO = (AnswerAttachedContentDAOImpl) appContext.getBean("answerAttachedContentDAO", AnswerAttachedContentDAOImpl.class);
		
		QuestionAttachedContent questionAttached = questionAttachedDAO.get(questionAttachedId);
		
		questionAttached.setCreationDate(new GregorianCalendar(1990, 9, 27).getTime());
		questionAttached = questionAttachedDAO.update(questionAttached);
		ForumTestCase.questionAttachedId = questionAttached.getId();
		
		ForumAttachmentGCManager gc = new ForumAttachmentGCManager(questionAttachedDAO, answerAttachedDAO);
		gc.garbageCollect();
		
		assertNull(questionAttachedDAO.get(questionAttachedId));
		assertFalse(new File(filePath).exists());
		
	}
	

}
