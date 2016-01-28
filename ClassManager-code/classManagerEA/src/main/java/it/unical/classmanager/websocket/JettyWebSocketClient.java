package it.unical.classmanager.websocket;

import java.net.URI;
import java.util.List;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import it.unical.classmanager.model.data.User;

public class JettyWebSocketClient
{
	private static JettyWebSocketClient _instance = null;

	public static JettyWebSocketClient getInstance()
	{
		if (_instance == null)
			_instance = new JettyWebSocketClient();
		return _instance;
	}

	private JettyWebSocketClient()
	{}
	
	private final String NOTIFICATION_MESSAGE = "Notify:";
	private final String WEB_SOCKET_URI = "ws://localhost:8080/wsexample";

	public void sendNotification(User user)
	{
		WebSocketClient client = new WebSocketClient();
		try
		{
			ClientWebSocket socket = new ClientWebSocket();
			client.start();
			URI echoUri = new URI(WEB_SOCKET_URI);
			ClientUpgradeRequest request1 = new ClientUpgradeRequest();
			client.connect(socket, echoUri, request1);
			socket.getLatch().await();
			String username = user.getUsername();
			socket.sendMessage(NOTIFICATION_MESSAGE + username);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		finally
		{
			try
			{
				client.stop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void sendNotification(List<User> users)
	{
		WebSocketClient client = new WebSocketClient();
		try
		{
			ClientWebSocket socket = new ClientWebSocket();
			client.start();
			URI echoUri = new URI(WEB_SOCKET_URI);
			ClientUpgradeRequest request1 = new ClientUpgradeRequest();
			client.connect(socket, echoUri, request1);
			socket.getLatch().await();
			for (User user : users)
			{
				socket.sendMessage(NOTIFICATION_MESSAGE + user.getUsername());
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		finally
		{
			try
			{
				client.stop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}