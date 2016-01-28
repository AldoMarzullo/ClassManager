package it.unical.classmanager.model.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="notification")
public class Notification
{
	@Id
	@Column(name="id", nullable=false, length=20)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="message", nullable=false, length=10000)
	private String message;
	
	@Column(name="url", nullable=false, length=255)
	private String url;
	
	@Column(name="read", nullable=false)
	private boolean read;
	
	@Column(name="date", nullable=false)
	private Date date;

	//	Foreign key section
	@ManyToOne
	@JoinColumn(name = "source")
	private User source;
	
	@ManyToOne
	@JoinColumn(name = "destination")
	private User destination;
	
	public Notification()
	{
		this.id = 0;
		this.message = "";
		this.url = "";
		this.read = false;
		this.date = new Date();
		this.source = null;
		this.destination = null;
	}

	public Notification(int id, String message, String url, boolean read, Date date, User source, User destination)
	{
		this.id = id;
		this.message = message;
		this.url = url;
		this.read = read;
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

	public boolean isRead()
	{
		return read;
	}

	public void setRead(boolean read)
	{
		this.read = read;
	}
	
	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public User getSource()
	{
		return source;
	}

	public void setSource(User source)
	{
		this.source = source;
	}

	public User getDestination()
	{
		return destination;
	}

	public void setDestination(User destination)
	{
		this.destination = destination;
	}

	@Override
	public String toString()
	{
		return "Notification [id=" + id + ", message=" + message + ", url=" + url + ", read=" + read + ", date=" + date + ", source=" + source + ", destination=" + destination
				+ "]";
	}
}
