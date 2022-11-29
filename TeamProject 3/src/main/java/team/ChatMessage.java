package team;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class ChatMessage {
	private int messageID;
	private int senderID;
	private int receiverID;
	private String senderUsername;
	private String receiverUsername;
	private String senderProfilePicture;
	private String receiverProfilePicture;
	private String messageText;
	private Timestamp timestamp;
	private SimpleDateFormat timeFormatter;
	
	public ChatMessage(int messageID, int senderID, int receiverID, String senderUsername, String receiverUsername, String senderProfilePicture, String receiverProfilePicture, String messageText, Timestamp timestamp) {
		this.messageID = messageID;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.senderUsername = senderUsername;
		this.receiverUsername = receiverUsername;
		this.senderProfilePicture = senderProfilePicture;
		this.receiverProfilePicture = receiverProfilePicture;
		this.messageText = messageText;
		this.timestamp = timestamp;
		
		// reference for SimpleDateFormat: https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html
		// and reference: https://www.geeksforgeeks.org/program-to-convert-milliseconds-to-a-date-format-in-java/
		// and reference: https://www.javatpoint.com/java-get-current-date
		String timePattern = "yyyy-MM-dd HH:mm:ss";
		this.timeFormatter = new SimpleDateFormat(timePattern);
	}
	public int getMessageID() {
		return this.messageID;
	}
	public int getSenderID() {
		return this.senderID;
	}
	public int getReceiverID() {
		return this.receiverID;
	}
	public String getSenderUsername() {
		return this.senderUsername;
	}
	public String getReceiverUsername() {
		return this.receiverUsername;
	}
	public String getSenderProfilePicture() {
		return this.senderProfilePicture;
	}
	public String getReceiverProfilePicture() {
		return this.receiverProfilePicture;
	}
	public String getMessageText() {
		return this.messageText;
	}
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	public String getTimestampString() {
		String timestampString = this.timeFormatter.format(this.timestamp);
		return timestampString;
	}
}

//reference: https://www.geeksforgeeks.org/comparator-interface-java/
class ChatMessageComparator implements Comparator<ChatMessage> {
	public int compare(ChatMessage a, ChatMessage b) {
		Timestamp a_messageTime = a.getTimestamp();
		Timestamp b_messageTime = b.getTimestamp();
		
		// if a has a later messageTime, it should come first when sorting
		if(a_messageTime.compareTo(b_messageTime) > 0) return -1;
		else if(a_messageTime.compareTo(b_messageTime) < 0) return 1;
		else return 0;
	}
}