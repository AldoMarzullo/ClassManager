package it.unical.classmanager.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import it.unical.classmanager.model.dao.CourseClassDAO;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.HomeworkStudentSolvingDAO;
import it.unical.classmanager.model.dao.LectureDAO;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.data.CourseClass;
import it.unical.classmanager.model.data.Homework;
import it.unical.classmanager.model.data.HomeworkStudentSolving;
import it.unical.classmanager.model.data.Lecture;
import it.unical.classmanager.model.data.RegistrationStudentClass;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import sun.util.logging.resources.logging;



@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/WEB-INF/spring/root-context.xml"})
public class DBStressTest{


	@Test
	public void lectureQueryStressTest(){

		final int CONNECTED_USERS = 3;

		for (int users = 0; users < CONNECTED_USERS; users++) {

			new Thread(new Runnable() {

				@Override
				public void run() {

					final int MAX_TIME = 5000;
					LectureDAO lectureDao = DaoHelper.getLectureDAO();

					double start_time = System.currentTimeMillis();
					List<Lecture> allLectures = lectureDao.getAllLectures();
					for (Lecture lecture : allLectures) {
						lecture.getHomeworks();
						lecture.getMaterials();
					}

					double end_time = System.currentTimeMillis();

					double elapsed_time = end_time - start_time;
					org.junit.Assert.assertTrue(elapsed_time < MAX_TIME);

				}
			}).start();
		}

	}

	@Test
	public void scoresQueryStressTest(){

		final int CONNECTED_USERS = 3;

		for (int users = 0; users < CONNECTED_USERS; users++) {

			new Thread(new Runnable() {

				@Override
				public void run() {

					final int MAX_TIME = 5000;
					HomeworkStudentSolvingDAO hsSolvingDAO = DaoHelper.getHomeworkStudentSolvingDAO();

					double start_time = System.currentTimeMillis();
					for (HomeworkStudentSolving hss : hsSolvingDAO.getAllHomeworkStudentSolvings())
						hss.getScore();					
					
					double end_time = System.currentTimeMillis();

					double elapsed_time = end_time - start_time;
					org.junit.Assert.assertTrue(elapsed_time < MAX_TIME);

				}
			}).start();
		}

	}

	/*@Test
public void lectureQueryGETStressTest(){

	final int MAX_TIME = 5000;
	final int CONNECTED_USERS = 5000;
	LectureDAO lectureDao = DaoHelper.getLectureDAO();

	String url = "http://localhost:8080";

	HttpURLConnection con;
	try {

		URL obj = new URL(url);
		con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		for (int users = 0; users < CONNECTED_USERS; users++) {

			double start_time = System.currentTimeMillis();
			lectureDao.getAllLectures();
			//con.getResponseCode();
			double end_time = System.currentTimeMillis();

			double elapsed_time = end_time - start_time;

			org.junit.Assert.assertTrue(elapsed_time < MAX_TIME);
		}

	} catch (IOException e) {
		e.printStackTrace();
	}

}*/

}
