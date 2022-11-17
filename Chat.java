package eryu_CSCI201_GroupProject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

import eryu_CSCI201_Assignment1.Datum;

public class Chat {
	private int userID;
	private int friendID;
	private String username;
	private String friendUsername;
	private String userProfilePicture;
	private String friendProfilePicture;
	// ordered from most recent to least recent; contains messages sent by both sender and receiver
	private ArrayList<ChatMessage> messagesSent;
	private ArrayList<ChatMessage> messagesReceived;
	private Timestamp lastMessageTime;
	private SimpleDateFormat timeFormatter;
	
	public Chat(int userID, int friendID, String username, String friendUsername, String userProfilePicture, String friendProfilePicture, ArrayList<ChatMessage> messagesSent, ArrayList<ChatMessage> messagesReceived, Timestamp lastMessageTime) {
		this.userID = userID;
		this.friendID = friendID;
		this.username = username;
		this.friendUsername = friendUsername;
		this.userProfilePicture = userProfilePicture;
		this.friendProfilePicture = friendProfilePicture;
		this.messagesSent = messagesSent;
		this.messagesReceived = messagesReceived;
		this.lastMessageTime = lastMessageTime;
		
		// reference for SimpleDateFormat: https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html
		// and reference: https://www.geeksforgeeks.org/program-to-convert-milliseconds-to-a-date-format-in-java/
		// and reference: https://www.javatpoint.com/java-get-current-date
		String timePattern = "yyyy-MM-dd HH:mm:ss";
		this.timeFormatter = new SimpleDateFormat(timePattern);
	}

	public int getUserID() {
		return this.userID;
	}
	
	public int getFriendID() {
		return this.friendID;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getFriendUsername() {
		return this.friendUsername;
	}
	
	public String getUserProfilePicture() {
		return this.userProfilePicture;
	}
	
	public String getFriendProfilePicture() {
		return this.friendProfilePicture;
	}
	
	public ArrayList<ChatMessage> getMessagesSent() {
		return this.messagesSent;
	}
	
	public ArrayList<ChatMessage> getMessagesReceived() {
		return this.messagesReceived;
	}
	
	// TO DO
	public ArrayList<ChatMessage> getAllMessages() {
		
	}
	
	public Timestamp getLastMessageTime() {
		return this.lastMessageTime;
	}
	
	public String getLatestAccessTimeString() {
		String latestAccessTimeString = this.timeFormatter.format(this.lastMessageTime);
		return latestAccessTimeString;
	}
	
	public void setMessagesSent(ArrayList<ChatMessage> messagesSent) {
		this.messagesSent = messagesSent;
	}
	
	public void setMessagesReceived(ArrayList<ChatMessage> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}
	
	public void setLastMessageTime(Timestamp lastMessageTime) {
		this.lastMessageTime = lastMessageTime;
	}
}

//reference: https://www.geeksforgeeks.org/comparator-interface-java/
class ChatComparator implements Comparator<Chat> {
	public int compare(Chat a, Chat b) {
		Timestamp a_lastMessageTime = a.getLastMessageTime();
		Timestamp b_lastMessageTime = b.getLastMessageTime();
		
		// if a has a later lastMessageTime, it should come first when sorting
		if(a_lastMessageTime.compareTo(b_lastMessageTime) > 0) return -1;
		else if(a_lastMessageTime.compareTo(b_lastMessageTime) < 0) return 1;
		else return 0;
	}
}
