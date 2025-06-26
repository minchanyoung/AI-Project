package websock;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnError;

import java.lang.Throwable;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


@ServerEndpoint("/websocket")
public class WebSocket {
	Session session;
	
	@OnOpen
	public void handleOpen() {
		System.out.println("Websocket OnOpen");
		// sessionList.add(userSession);
	}
	
	@OnMessage
	public void handleMessage(String msg) {
		System.out.println("수신 된 메세지: " + msg);
		
	}
	
	public void sendMessage(String message) {
		try {
			session.getBasicRemote().sendText(message);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void handleClose() {
		System.out.println("client is now disconnected...");
	}
	
	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}
}
