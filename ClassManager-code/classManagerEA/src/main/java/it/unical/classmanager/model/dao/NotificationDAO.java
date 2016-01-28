package it.unical.classmanager.model.dao;

import java.util.List;

import it.unical.classmanager.model.NotificationJSON;
import it.unical.classmanager.model.data.Notification;
import it.unical.classmanager.model.data.User;

public interface NotificationDAO
{
	public void create(Notification notification);

	public void update(Notification notification);

	public void delete(Notification notification);

	public Notification get(Integer id);
	
	public List<NotificationJSON> getNewNotifications(User user);	

	public void setNotificationsRead(User user);
	
	public List<NotificationJSON> getOldNotifications(User user, int maxResult);	
	
	public void setNotificationRead(User user, int id);
}