package it.unical.classmanager.websocket;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ClientWebSocket
{
	private Session session;

	CountDownLatch latch = new CountDownLatch(1);

	@OnWebSocketMessage
	public void onText(Session session, String message) throws IOException
	{
		latch.countDown();
	}

	@OnWebSocketConnect
	public void onConnect(Session session)
	{
		this.session = session;
	}

	public void sendMessage(String str)
	{
		try
		{
			session.getRemote().sendString(str);
			session.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public CountDownLatch getLatch()
	{
		return latch;
	}

}
