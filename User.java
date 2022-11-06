package eryu_CSCI201_GroupProject;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private int userID;
	private String username;
	private ArrayList<Integer> interestID;
	private ArrayList<Integer> followingID;
	
	// constructor takes in arguments from ServerThread.createNewUser()
	// the presence of the user in the database should be checked before instantiating User
	public User(int userID, String username, ArrayList<Integer> interestID, ArrayList<Integer> followingID) {
		this.userID = userID;
		this.username = username;
		this.interestID = interestID;
		this.followingID = followingID;
	}
	
	// ServerThread should send posts to client
	public ArrayList<Post> displayPosts() {
		// prepare the query statement for fetchPosts
		// get the posts made by the user and posts made by those who the user follows
		// and the posts that match user's interests
		String query = "select p.postID, p.postText, p.postImage, p.time, u.userName, u.profilePicture from Post p, UserLogin u where p.userID=u.userID and (p.userID=" + userID;
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
		
		ArrayList<Post> postList = fetchPosts(query);
		return postList;
	}
	
	// combines fetchPostByUser, fetchPostByInterest, and sortByTime
	public static synchronized ArrayList<Post> fetchPosts(String query) {
		String url = null;	// change this to the URL needed to connect to database
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Post> postList = new ArrayList<Post>();
		try {
			conn = DriverManager.getConnection(url);
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				// initialize Post objects based on result set and add to list
				int postID = rs.getInt("p.postID");
				String postText = rs.getString("p.postText");
				String postImage = rs.getString("p.postImage");
				long time = rs.getLong("p.time");
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
	
	public void createNewPost(String postText, String postImage, String interest) {
		// we know userID; postID is auto-generated; interestID can be found by String interest
		String queryFindInterestID = null; // TO DO: prepare query statement
		String queryInsertNewPost = null; // TO DO: prepare query statement
		// TO DO: use current system time for "time" entry in Post table in database
		
		createNewPostHelper(queryFindInterestID, queryInsertNewPost);		
	}
	
	public static synchronized void createNewPostHelper(String query1, String query2) {
		String url = null;	// change this to the URL needed to connect to database
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		try {
			conn = DriverManager.getConnection(url);
			
			// get interestID
			ps1 = conn.prepareStatement(query1);
			rs1 = ps1.executeQuery();
			// TO DO: parse output
			
			// insert new post into database
			ps2 = conn.prepareStatement(query2);
			rs2 = ps2.executeQuery();
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
	}
	
	// when user wants to edit a post, the client side should provide the postID
	public void editExistingPost(int postID, String newPostText, String newPostImage) {
		// TO DO: prepare query statement for UPDATE
		String query = null;
		editExistingPostHelper(query);
	}
	
	// TO DO
	public void editExistingPostHelper(String query) {
		
	}
	
	// when user wants to delete a post, the client side should provide the postID
	public void deletePost(int postID) {
		// TO DO: prepare query statement for DELETE
		String query = null;
		deletePostHelper(query);
	}
	
	// TO DO
	public void deletePostHelper(String query) {
		
	}
	
	
}
