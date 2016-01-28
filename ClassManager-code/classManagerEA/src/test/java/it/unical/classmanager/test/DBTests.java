package it.unical.classmanager.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.IUnwovenClassFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import it.unical.classmanager.model.dao.CourseClassDAO;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.dao.UserDAOImpl;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Homework;
import it.unical.classmanager.model.data.HomeworkStudentSolving;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.RegistrationStudentClass;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.utils.DateTimeFactory;

import sun.util.logging.resources.logging;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/WEB-INF/spring/root-context.xml"})
public class DBTests{

	private ArrayList<User> newUsersids;
	
	@Autowired
	private ApplicationContext context;

	@Before
	public void init() {
		
		this.newUsersids = new ArrayList<User>();
		
		UserDAO userDao  = (UserDAO) context.getBean("userDao");
		for ( int i = 0 ; i < 10 ; i++ ) {
			User u = new User();
			u.setUsername("user"+i);
			u.setPassword("userPass"+i);
			u.setConfirmPassword("userPass"+i);
			u.setFirstName("userName"+1);
			u.setHash(u.getPassword());
			u.setLastName("userLastName"+1);
			u.setRole("user");
			u.setEmail("userEmail@email.it");
			u.setSerialNumber(""+i);
			u.setBirthDate(DateTimeFactory.getRandomDateLessThanYear(
					Calendar.getInstance().get(Calendar.YEAR)-18).getTime());
			userDao.create(u);
			newUsersids.add(u);
		}
	}
	
	
	@Test @Ignore
	public void deleteAllUsers() {
		UserDAO userDao = ((UserDAO) context.getBean("userDao"));
		ArrayList<User> users = (ArrayList<User>) userDao.getAllUsers();
		for ( User user : users ) {
			userDao.delete(user);
		}
		assertEquals(0, userDao.getAllUsers().size());
	}
	

	@After
	public void delete() {
		UserDAO userDao = ((UserDAO) context.getBean("userDao"));
		for ( User user : newUsersids ) {
			userDao.delete(user);
		}
	}

	@Test 
	public void testNumberOfUsers() {
		int users = ((UserDAO) context.getBean("userDao")).getAllUsers().size();
		assertEquals(10, users);
	}

}
