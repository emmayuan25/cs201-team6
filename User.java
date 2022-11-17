package eryu_CSCI201_GroupProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {
	private int userID;
	private String username;
	private String profilePicture;
	private ArrayList<Integer> interestID;
	private ArrayList<Integer> followingID;
	private static String connectionURL;
	
	// constructor takes in arguments from ServerThread.createNewUser()
	// the presence of the user in the database should be checked before instantiating User
	public User(int userID, String username, String profilePicture, ArrayList<Integer> interestID, ArrayList<Integer> followingID, String url) {
		this.userID = userID;
		this.username = username;
		this.profilePicture = profilePicture;
		this.interestID = interestID;
		this.followingID = followingID;
		User.connectionURL = url; // this should be the URL needed to connect to database
	}
	
	// getter method for username
	public String getUsername() {
		return this.username;
	}
	
	// ServerThread should send posts to client
	public ArrayList<Post> displayPosts() {
		// prepare the query statement for fetchPosts
		// get the posts made by the user and posts made by those who the user follows
		// and the posts that match user's interests
		String query = "select p.postID, p.postText, p.postImage, p.time, u.userName, u.profilePicture from Post p, User u where p.userID=u.userID and (p.userID='" + this.userID + "'";
		if(!followingID.isEmpty()) {
			for(int id : followingID) {
				query += " or p.userID='" + id + "'";
			}
		}
		if(!interestID.isEmpty()) {
			for(int id : interestID) {
				query += " or p.interestID='" + id + "'";
			}
		}
		// sort the posts based on creation time; from most recent to least recent
		query += ") order by p.time desc";
		
		ArrayList<Post> postList = fetchPostsHelper(query);
		return postList;
	}
	
	// combines fetchPostByUser, fetchPostByInterest, and sortByTime
	private static synchronized ArrayList<Post> fetchPostsHelper(String query) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Post> postList = new ArrayList<Post>();
		try {
			conn = DriverManager.getConnection(User.connectionURL);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				// initialize Post objects based on result set and add to list
				int postID = rs.getInt("p.postID");
				String postText = rs.getString("p.postText");
				String postImage = rs.getString("p.postImage");
				Timestamp time = rs.getTimestamp("p.time");
				String username = rs.getString("u.userName");
				String profilePicture = rs.getString("u.profilePicture");
				Post post = new Post(postID, postText, postImage, time, username, profilePicture);
				postList.add(post);
			}			
		} catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException sqle) {
				System.out.println(sqle.getMessage());
			}	
		}
		
		return postList;
	}
	
	// return a Post object that has information about the new post
	public Post createNewPost(String postText, String postImage, String interest) {
		// we know userID; postID is auto-generated; 
		
		// find interestID for the given interest
		int interestID = getInterestID(interest); // TO DO: check if Interest table has been changed
		
		// use current time for time variable when inserting into database
		long time = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(time);
		
		String queryInsertNewPost = "insert into Post (postText, postImage, userID, interestID, time) values (?, ?, " + this.userID + ", " + interestID + ", ?)";
		
		// insert the new post into database, which returns the postID of the new post
		int postID = createNewPostHelper(queryInsertNewPost, postText, postImage, timestamp);
		
		Post post = null;
		// if insertion of new post was successful
		if(postID != -1) {
			post = new Post(postID, postText, postImage, timestamp, this.username, this.profilePicture);
		}

		return post;
	}
	
	// find the interestID for a given interest String
	private static synchronized int getInterestID(String interestName) {
		String query = "select interestID from Interest where interestName=?"; // TO DO: check if Interest table has been changed
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int interestID = -1;
		try {
			conn = DriverManager.getConnection(User.connectionURL);
			ps = conn.prepareStatement(query);
			ps.setString(1, interestName);
			rs = ps.executeQuery();
			if(rs.next()) {
				// update interestID based on query result
				interestID = rs.getInt("interestID");
			}			
		} catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException sqle) {
				System.out.println(sqle.getMessage());
			}	
		}
		
		return interestID;
	}
	
	
	// returns postID of the new post inserted
	private static synchronized int createNewPostHelper(String queryInsertNewPost, String postText, String postImage, Timestamp timestamp) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		int postID = -1;
		try {
			conn = DriverManager.getConnection(User.connectionURL);
			
			ps1 = conn.prepareStatement(queryInsertNewPost);
			ps1.setString(1, postText);
			ps1.setString(2, postImage);
			ps1.setTimestamp(3, timestamp);
			rs1 = ps1.executeQuery();
			
			// get postID of the new post
			ps2 = conn.prepareStatement("select scope_identity()");
			rs2 = ps2.executeQuery();
			if(rs2.next()) {
				postID = rs2.getInt("postID"); // check if this works
			}

		} catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			try {
				if(rs2 != null) rs2.close();
				if(ps2 != null) ps2.close();
				if(rs1 != null) rs1.close();
				if(ps1 != null) ps1.close();
				if(conn != null) conn.close();
			} catch(SQLException sqle) {
				System.out.println(sqle.getMessage());
			}	
		}
		
		return postID;
	}
	
	// when user wants to edit a post, the client side should provide the postID
	public void editExistingPost(int postID, String newPostText, String newPostImage) {
		// TO DO: prepare query statement for UPDATE
		String query = null;
		editExistingPostHelper(query);
	}
	
	// TO DO
	private static synchronized void editExistingPostHelper(String query) {
		
	}
	
	// when user wants to delete a post, the client side should provide the postID
	public void deletePost(int postID) {
		String query = "delete from Post where postID=?";
		deletePostHelper(query, postID);
	}
	
	private static synchronized void deletePostHelper(String query, int postID) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(User.connectionURL);
			
			ps = conn.prepareStatement(query);
			ps.setInt(1, postID);
			rs = ps.executeQuery();
		} catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException sqle) {
				System.out.println(sqle.getMessage());
			}	
		}
	}
	
	// the list of chats returned is ordered from most recent to least recent
	public ArrayList<Chat> getCurrentChats() {
		// prepare query statement
		String queryUserInfo = "select userName, userImage from User where userID=?";
		
		String queryGetMessagesSent = "select c.messageID, c.fromUID, c.toUID, c.messageText, c.createdAt, u.userName, u.userImage from ChatMessage c, User u where c.fromUID='" + this.userID + "' and c.toUID=u.userID order by c.toUID, c.createdAt desc";
		ArrayList<ChatMessage> messagesSent = getChatMessagesHelper(queryGetMessagesSent, queryUserInfo, "receiver", this.username, this.profilePicture);
		
		String queryGetMessagesReceived = "select c.messageID, c.fromUID, c.toUID, c.messageText, c.createdAt, u.userName, u.userImage from ChatMessage c, User u where c.toUID='" + this.userID + "' and c.fromID=u.userID order by c.fromUID, c.createdAt desc";
		ArrayList<ChatMessage> messagesReceived = getChatMessagesHelper(queryGetMessagesReceived, queryUserInfo, "sender", this.username, this.profilePicture);
		
		// keep track of the chats we instantiated
		// key = friendUserID, value = chat
		HashMap<Integer, Chat> chats_map = new HashMap<Integer, Chat>();
		
		// separate the chat messages by the chats
		ArrayList<Chat> chat_list = new ArrayList<Chat>();
		ArrayList<ChatMessage> message_list = new ArrayList<ChatMessage>();
		for(int i=0; i<messagesSent.size(); i++) {
			ChatMessage current_message = messagesSent.get(i);
			message_list.add(current_message);
			
			// if this element is the last one, or if the next element has a different receiverID, we know this is where chats change
			if(i == messagesSent.size()-1 || messagesSent.get(i+1).getReceiverID() !=  current_message.getReceiverID()) {
				int userID = this.userID;
				int friendID = current_message.getReceiverID();
				String username = this.username;
				String friendUsername = current_message.getReceiverUsername();
				String userProfilePicture = this.profilePicture;
				String friendProfilePicture = current_message.getReceiverProfilePicture();
				ArrayList<ChatMessage> messagesSentForCurrentChat = message_list;
				ArrayList<ChatMessage> messagesReceivedForCurrentChat = null;
				Timestamp lastMessageTime = message_list.get(0).getTimestamp();
				Chat currentChat = new Chat(userID, friendID, username, friendUsername, userProfilePicture, friendProfilePicture, messagesSentForCurrentChat, messagesReceivedForCurrentChat, lastMessageTime);
				chat_list.add(currentChat);
				chats_map.put(friendID, currentChat);
				message_list = new ArrayList<ChatMessage>();
			}
		}
		
		message_list = new ArrayList<ChatMessage>();
		for(int i=0; i<messagesReceived.size(); i++) {
			ChatMessage current_message = messagesReceived.get(i);
			message_list.add(current_message);
			
			// if this element is the last one, or if the next element has a different receiverID, we know this is where chats change
			if(i == messagesReceived.size()-1 || messagesReceived.get(i+1).getSenderID() !=  current_message.getSenderID()) {
				// check if a Chat object between the sender and the user already exists
				Chat currentChat = chats_map.get(current_message.getSenderID());
				// if the currentChat already exists, add messagesReceivedForCurrentChat
				if(currentChat != null) {
					// add messagesReceived to currentChat
					ArrayList<ChatMessage> messagesReceivedForCurrentChat = message_list;
					currentChat.setMessagesReceived(messagesReceivedForCurrentChat);
					
					// compare lastMessageTime
					Timestamp lastMessageReceivedTime = message_list.get(0).getTimestamp();
					Timestamp lastMessageSentTime = currentChat.getLastMessageTime();
					// if lastMessagereceivedtime is later
					if(lastMessageReceivedTime.compareTo(lastMessageSentTime) > 0) {
						currentChat.setLastMessageTime(lastMessageReceivedTime);
					}
				}
				
				else {
					int userID = this.userID;
					int friendID = current_message.getSenderID();
					String username = this.username;
					String friendUsername = current_message.getSenderUsername();
					String userProfilePicture = this.profilePicture;
					String friendProfilePicture = current_message.getSenderProfilePicture();
					ArrayList<ChatMessage> messagesSentForCurrentChat = null;
					ArrayList<ChatMessage> messagesReceivedForCurrentChat = message_list;
					Timestamp lastMessageTime = message_list.get(0).getTimestamp();
					currentChat = new Chat(userID, friendID, username, friendUsername, userProfilePicture, friendProfilePicture, messagesSentForCurrentChat, messagesReceivedForCurrentChat, lastMessageTime);
					chat_list.add(currentChat);
					message_list = new ArrayList<ChatMessage>();
				}	
			}
		}
		
		// the list of chats returned is ordered from most recent to least recent
		chat_list.sort(new ChatComparator());
		return chat_list;
	}
	
	// searchUserType lets the method know whether we are searching user info for the sender or the receiver
	private static synchronized ArrayList<ChatMessage> getChatMessagesHelper(String queryMessages, String queryUserInfo, String searchUserType, String callerUsername, String callerProfilePicture) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		ArrayList<ChatMessage> messageList = new ArrayList<ChatMessage>();
		try {
			conn = DriverManager.getConnection(User.connectionURL);
			ps1 = conn.prepareStatement(queryMessages);
			rs1 = ps1.executeQuery();
			while(rs1.next()) {
				// initialize ChatMessage objects based on result set and add to list
				int messageID = rs1.getInt("c.messageID");
				int senderID = rs1.getInt("c.fromUID");
				int receiverID = rs1.getInt("c.toUID");
				String messageText = rs1.getString("c.messageText");
				Timestamp timestamp = rs1.getTimestamp("c.createdAt");
				String friendUsername = rs1.getString("u.userImage");
				String friendProfilePicture = rs1.getString("u.userImage");
				
				if(searchUserType.equals("receiver")) {
					ChatMessage message = new ChatMessage(messageID, senderID, receiverID, callerUsername, friendUsername, callerProfilePicture, friendProfilePicture, messageText, timestamp);
					messageList.add(message);
				}
				
				else {
					ChatMessage message = new ChatMessage(messageID, senderID, receiverID, friendUsername, callerUsername, friendProfilePicture, callerProfilePicture, messageText, timestamp);
					messageList.add(message);
				}	
			}			
		} catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			try {
				if(rs1 != null) rs1.close();
				if(ps1 != null) ps1.close();
				if(conn != null) conn.close();
			} catch(SQLException sqle) {
				System.out.println(sqle.getMessage());
			}	
		}
		
		return messageList;
	}
}