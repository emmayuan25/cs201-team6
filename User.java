package eryu_CSCI201_GroupProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	
	public int getUserID() {
		return this.userID;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getUserProfilePicture() {
		return this.profilePicture;
	}
	
	// ServerThread should send posts to client
	public PostsList displayPosts() {
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
		
		ArrayList<Post> posts = fetchPostsHelper(query);
		PostsList postsList = new PostsList(posts);
		return postsList;
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
	public ChatsList getCurrentChats() {
		// prepare query statement
		String queryUserInfo = "select userName, userImage from User where userID=?";
		
		String queryGetMessagesSent = "select c.messageID, c.fromUID, c.toUID, c.messageText, c.createdAt, u.userName, u.userImage from ChatMessage c, User u where c.fromUID='" + this.userID + "' and c.toUID=u.userID order by c.toUID, c.createdAt desc";
		ArrayList<ChatMessage> messagesSent = getChatMessagesHelper(queryGetMessagesSent, queryUserInfo, "receiver", this.username, this.profilePicture);
		
		String queryGetMessagesReceived = "select c.messageID, c.fromUID, c.toUID, c.messageText, c.createdAt, u.userName, u.userImage from ChatMessage c, User u where c.toUID='" + this.userID + "' and c.fromID=u.userID order by c.fromUID, c.createdAt desc";
		ArrayList<ChatMessage> messagesReceived = getChatMessagesHelper(queryGetMessagesReceived, queryUserInfo, "sender", this.username, this.profilePicture);
		
		// separate the messages into different chats
		// key = friendUserID, value = list of chat messages
		HashMap<Integer, ArrayList<ChatMessage>> messages_map = new HashMap<Integer, ArrayList<ChatMessage>>();
		
		for(ChatMessage message : messagesSent) {
			int receiverID = message.getReceiverID();
			ArrayList<ChatMessage> messages_list = messages_map.get(receiverID);
			
			// if the map does not contain the receiverID, create a new key-value pair
			if(messages_list == null) {
				messages_list = new ArrayList<ChatMessage>();
				messages_list.add(message);
				messages_map.put(receiverID, messages_list);
			}
			
			// if the map contains the receiverID, updates the value
			else {
				messages_list.add(message);
			}
		}
		
		for(ChatMessage message : messagesReceived) {
			int senderID = message.getSenderID();
			ArrayList<ChatMessage> messages_list = messages_map.get(senderID);
			
			// if the map does not contain the senderID, create a new key-value pair
			if(messages_list == null) {
				messages_list = new ArrayList<ChatMessage>();
				messages_list.add(message);
				messages_map.put(senderID, messages_list);
			}
			
			// if the map contains the senderID, updates the value
			else {
				messages_list.add(message);
			}
		}
		
		// hold the list of chats
		ArrayList<Chat> chats_list = new ArrayList<Chat>();
		
		// HashMap iterating reference: https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/
		for(Map.Entry<Integer, ArrayList<ChatMessage>> entry : messages_map.entrySet()) {
			ArrayList<ChatMessage> messages_list = entry.getValue();
			
			// sort the messages from most recent to least recent
			messages_list.sort(new ChatMessageComparator());
			
			// get information of the friend
			ChatMessage message1 = messages_list.get(0);
			
			// if user is sending this message
			if(message1.getSenderID() == this.userID) {
				// create a Chat object and add to chats_list
				Chat current_chat = new Chat(this.userID, message1.getReceiverID(), message1.getSenderUsername(), message1.getReceiverUsername(),
									message1.getSenderProfilePicture(), message1.getReceiverProfilePicture(), messages_list, message1.getTimestamp());
				chats_list.add(current_chat);
			}
			// if user is receiving this message
			else {
				// create a Chat object and add to chats_list
				Chat current_chat = new Chat(this.userID, message1.getSenderID(), message1.getReceiverUsername(), message1.getSenderUsername(),
									message1.getReceiverProfilePicture(), message1.getSenderProfilePicture(), messages_list, message1.getTimestamp());
				chats_list.add(current_chat);
			}	
		}
		
		// the list of chats returned is ordered from most recent to least recent
		chats_list.sort(new ChatComparator());
		ChatsList chatsList = new ChatsList(chats_list);
		return chatsList;
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