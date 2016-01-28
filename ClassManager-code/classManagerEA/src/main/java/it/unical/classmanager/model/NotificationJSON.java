package it.unical.classmanager.model;

import java.util.Date;

public class NotificationJSON
{
	private int id;
	private String message;
	private String url;
	private Date date;
	private String source;
	private String destination;
	
	public NotificationJSON()
	{
		this.id = 0;
		this.message = "";
		this.url = "";
		this.date = new Date();
		this.source = "";
		this.destination = "";
	}

	public NotificationJSON(int id, String message, String url, boolean read, Date date, String source, String destination)
	{
		this.id = id;
		this.message = message;
		this.url = url;
		this.date = date;
		this.source = source;
		this.destination = destination;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public String getDestination()
	{
		return destination;
	}

	public void setDestination(String destination)
	{
		this.destination = destination;
	}	
}
