package it.unical.classmanager.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class MyWebSocket 
{	
	private Map<String, Session> clients;
	
	public MyWebSocket()
	{
		clients = new HashMap<String, Session>(); 
	}
	
    @OnWebSocketConnect
    public void onConnect(Session session) 
    {
    	try 
    	{
			session.getRemote().sendString("Hello Client");
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        if(message.matches("^helo:(.+)"))
        {
        	String[] str = message.split(":", 2);
        	clients.put(str[1], session);
        	
        }
        else if(message.matches("^Notify:(.*)"))
        {
        	String[] str = message.split(":", 2);
        	sendMessage(str[1]); 	
        }     
    }
    
    public void sendMessage(String user)
    {
    	try
		{
        	Session userSession = clients.get(user);
        	if(userSession != null)
        		userSession.getRemote().sendString("Notify");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    }   
	
    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) 
    {
        clients.remove(session);
    }

    @OnWebSocketError
    public void onError(Throwable t) 
    {
        System.err.println("Error: " + t.getMessage());
    }
}