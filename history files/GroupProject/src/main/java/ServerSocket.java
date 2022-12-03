import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@ServerEndpoint(value = "/ws")
public class ServerSocket {

	private static List<Session> sessionList = Collections.synchronizedList(new ArrayList<Session>());
	
	@OnOpen
	public void open(Session session) {
		// add the new session to sessionList
		System.out.println("in server socket on open");

		sessionList.add(session);
	}
	
	@OnMessage
	public void onMessage(String messageString, Session session) {
		System.out.println("in server socket on msg");

		
		// store message information through GSON
		Gson gson = new Gson();

//		ChatMessage newMessage = gson.fromJson(messageString, ChatMessage.class);
//		
//		int senderID = newMessage.getSenderID();
//		int receiverID = newMessage.getReceiverID();
//		String messageText = newMessage.getMessageText();
		
		String[] content = messageString.split(" ", 3);
		int senderID = Integer.parseInt(content[0]);
		int receiverID = Integer.parseInt(content[1]);
		String messageText = content[2];
		
		System.out.println(messageText);
		
		// update the database with the new message
		JDBCConnector.addMessage(senderID, receiverID, messageText);
		
		// send the updated list of messages where sender is receiverID, receiver is senderID
		ChatMessagesList messagesList = JDBCConnector.getMessagesList(receiverID, senderID);
		
		// convert to String in JSON
		String messagesListString = gson.toJson(messagesList);
		
		// send chatMessage to every session except where the message came from
		// the front end should decide whether the message is relevant for its user
		// synchronized to prevent adding or removing sessions when iterating through sessionList
		synchronized(sessionList) {
			for(Session s : sessionList) {
				if(s != session) {
					// getBasicRemote() is for synchronous communication
					// getAsyncRemote() is for asynchronous communication
					s.getAsyncRemote().sendText(messagesListString);
				}
			}
		}
	}
	
	@OnClose
	public void close(Session session) {
		// remove the session from sessionList
		sessionList.remove(session);
	}
	
	@OnError
	public void error(Throwable error) {
		// inform the clients that an error has occurred
		for(Session s : sessionList) {
			// getBasicRemote() is for synchronous communication
			// getAsyncRemote() is for asynchronous communication
			s.getAsyncRemote().sendText("error");
		}
	}
}

/*
		//the message should start with the sender's userID and the receiver's userID
		String[] content = message.split(" ", 3);
		int senderID = Integer.parseInt(content[0]);
		int receiverID = Integer.parseInt(content[1]);
		String messageText = content[2];
*/