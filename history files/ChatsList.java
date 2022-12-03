package eryu_CSCI201_GroupProject;

import java.util.ArrayList;

// wrapper class for a list of chats
public class ChatsList {
	private ArrayList<Chat> chatsList = null;
	
	public ChatsList(ArrayList<Chat> chatsList) {
		this.chatsList = chatsList;
	}
	
	public ArrayList<Chat> getChatsList() {
		return this.chatsList;
	}
	
	public void setChatsList(ArrayList<Chat> chatsList) {
		this.chatsList = chatsList;
	}
}
