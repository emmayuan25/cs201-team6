package eryu_CSCI201_GroupProject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Chat {
	private int userID;
	private int friendID;
	private String username;
	private String friendUsername;
	private String userProfilePicture;
	private String friendProfilePicture;
	// ordered from least recent to most recent; contains messages sent by both sender and receiver
	private ArrayList<ChatMessage> messagesSent;
	private ArrayList<ChatMessage> messagesReceived;
	private Timestamp latestAccessTime;
	private SimpleDateFormat timeFormatter;
	
	public Chat(int userID, int friendID, String username, String friendUsername, String userProfilePicture, String friendProfilePicture, ArrayList<ChatMessage> messagesSent, ArrayList<ChatMessage> messagesReceived, Timestamp latestAccessTime) {
		this.userID = userID;
		this.friendID = friendID;
		this.username = username;
		this.friendUsername = friendUsername;
		this.userProfilePicture = userProfilePicture;
		this.friendProfilePicture = friendProfilePicture;
		this.messagesSent = messagesSent;
		this.messagesReceived = messagesReceived;
		this.latestAccessTime = latestAccessTime;
		
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
	
	public Timestamp getLatestAccessTime() {
		return this.latestAccessTime;
	}
	
	public String getLatestAccessTimeString() {
		String latestAccessTimeString = this.timeFormatter.format(this.latestAccessTime);
		return latestAccessTimeString;
	}
}
