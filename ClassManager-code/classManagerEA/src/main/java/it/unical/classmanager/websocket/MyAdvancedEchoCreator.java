package it.unical.classmanager.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Permette di aprire un solo ed unico web socket. Senza tale creator ad ogni sessione creava un socket diverso non dando la possibilità di poter comunicare tra i client
 * @author Alessandro
 *
 */
public class MyAdvancedEchoCreator implements WebSocketCreator 
{  
    private MyWebSocket textEcho;
 
    public MyAdvancedEchoCreator() 
    {
        this.textEcho = new MyWebSocket();
    }
 
    @SuppressWarnings("unused")
	@Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) 
    {
        for (String subprotocol : req.getSubProtocols()) 
        {            
        	return textEcho;
        }
        return textEcho;
    }
}