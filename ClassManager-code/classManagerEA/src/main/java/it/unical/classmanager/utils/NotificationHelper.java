package it.unical.classmanager.utils;

import java.util.Date;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.NotificationDAO;
import it.unical.classmanager.model.data.Notification;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.websocket.JettyWebSocketClient;

/**
 * Helper class for creation of Notification.
 * 
 * @author Aloisius92
 */
public class NotificationHelper {

	/**
	 * This function create a notification.
	 * 
	 * @param sendUser The user who send the notification
	 * @param destinationUser The user who receive the notification
	 * @param message The notification's message  
	 */
	public static void createNotification(User sendUser, User destinationUser, String message){
		createNotification(sendUser, destinationUser, message, "#");
	}

	/**
	 * This function create a notification.
	 * 
	 * @param sendUser The user who send the notification
	 * @param destinationUser The user who receive the notification
	 * @param message The notification's message
	 * @param url The url of the message
	 */
	public static void createNotification(User sendUser, User destinationUser, String message, String url){
		NotificationDAO notificationDAO = DaoHelper.getNotificationDAO();
		Notification notification = new Notification();
		notification.setMessage(message);
		notification.setUrl(url);
		notification.setSource(sendUser);
		notification.setDestination(destinationUser);
		notification.setDate(new Date());
		notificationDAO.create(notification);	
		JettyWebSocketClient.getInstance().sendNotification(notification.getDestination());
	}
}
