package eryu_CSCI201_GroupProject;

import java.util.ArrayList;

//wrapper class for a list of ChatMessage
public class ChatMessagesList {
	private ArrayList<ChatMessage> messagesList = null;
	
	public ChatMessagesList(ArrayList<ChatMessage> messagesList) {
		this.messagesList = messagesList;
	}
	
	public ArrayList<ChatMessage> getChatMessagesList() {
		return this.messagesList;
	}
	
	public void setChatMessagesList(ArrayList<ChatMessage> messagesList) {
		this.messagesList = messagesList;
	}
}
