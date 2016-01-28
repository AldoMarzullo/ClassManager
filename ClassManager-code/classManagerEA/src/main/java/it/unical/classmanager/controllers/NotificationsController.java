package it.unical.classmanager.controllers;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.unical.classmanager.model.NotificationJSON;
import it.unical.classmanager.model.dao.NotificationDAO;
import it.unical.classmanager.model.dao.NotificationDAOImpl;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.dao.UserDAOImpl;
import it.unical.classmanager.model.data.Notification;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.websocket.JettyWebSocketClient;

@Controller
public class NotificationsController
{
	private static final Logger logger = LoggerFactory.getLogger(AttendanceLessonController.class);

	@Autowired
	private ApplicationContext context;

	// TODO eliminare la funzione seguente in quanto serve solo come riferimento per mostrare come creare e inviare le notifiche
	private int ms = 0;
	@RequestMapping(value = "/wsSend", method = RequestMethod.GET)
	public String send(HttpServletRequest request, Model model, Locale locale)
	{
		/**
		 * Creazione delle notifiche
		 */
		NotificationDAO notificationDAO = context.getBean("notificationDAO", NotificationDAOImpl.class);
		Notification n = new Notification();
		n.setMessage("Notification " + ms++);
		n.setUrl("#");
		UserDAO userDAO = context.getBean("userDao", UserDAOImpl.class);
		User currentUser = userDAO.get((String)request.getSession().getAttribute("loggedIn"));
		n.setSource(currentUser);
		// Settare il giusto utente a cui la notifica è indirizzata
		n.setDestination(currentUser);
		n.setDate(new Date());
		notificationDAO.create(n);	
		
		JettyWebSocketClient.getInstance().sendNotification(n.getDestination());
		return "websocket";
	}
	
	/**
	 * Restituisce le notifiche marcate come non lette dall'utente
	 * @param request
	 * @param model
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/getNewNotifications", method = RequestMethod.GET)
	public @ResponseBody String getNewNotifications(HttpServletRequest request, Model model, Locale locale) 
	{	 
		UserDAO userDAO = context.getBean("userDao", UserDAOImpl.class);
		User currentUser = userDAO.get((String)request.getSession().getAttribute("loggedIn"));
		NotificationDAO notificationDAO = context.getBean("notificationDAO", NotificationDAOImpl.class);
		List<NotificationJSON> notifications = notificationDAO.getNewNotifications(currentUser);	
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(notifications);
	    return json;
	}
	
	/**
	 * Restituisce le ultime 5 notifiche ricevute dall'utente marcate come lette
	 * @param request
	 * @param model
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/getOldNotifications", method = RequestMethod.GET)
	public @ResponseBody String getOldNotifications(HttpServletRequest request, Model model, Locale locale) 
	{	 
		UserDAO userDAO = context.getBean("userDao", UserDAOImpl.class);
		User currentUser = userDAO.get((String)request.getSession().getAttribute("loggedIn"));
		NotificationDAO notificationDAO = context.getBean("notificationDAO", NotificationDAOImpl.class);
		List<NotificationJSON> notifications = notificationDAO.getOldNotifications(currentUser, 5);		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(notifications);
	    return json;
	}
	
	/**
	 * Setta tutte le notifiche dell'utente come lette
	 * @param request
	 * @param model
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/setNoficationsRead", method = RequestMethod.GET)
	public @ResponseBody String setNoficationsRead(HttpServletRequest request, Model model, Locale locale) 
	{	 
		UserDAO userDAO = context.getBean("userDao", UserDAOImpl.class);
		User currentUser = userDAO.get((String)request.getSession().getAttribute("loggedIn"));
		NotificationDAO notificationDAO = context.getBean("notificationDAO", NotificationDAOImpl.class);
		notificationDAO.setNotificationsRead(currentUser);
		return "";
	}
	
	/**
	 * Setta solo la notifica ricevuta in GET come letta
	 * @param request
	 * @param model
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/setNoficationRead", method = RequestMethod.GET)
	public @ResponseBody String setNoficationRead(HttpServletRequest request, Model model, Locale locale) 
	{	 
		int id = Integer.parseInt(request.getParameter("id"));
		logger.info("Id: " + id);
		UserDAO userDAO = context.getBean("userDao", UserDAOImpl.class);
		User currentUser = userDAO.get((String)request.getSession().getAttribute("loggedIn"));
		NotificationDAO notificationDAO = context.getBean("notificationDAO", NotificationDAOImpl.class);
		notificationDAO.setNotificationRead(currentUser, id);
		return "";
	}
}
