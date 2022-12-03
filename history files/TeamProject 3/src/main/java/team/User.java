
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
	private int interestID;
	private String interest;
//	private ArrayList<Integer> interestID; // maybe delete this?
//	private ArrayList<Integer> followingID; // maybe delete this?
//	private static String connectionURL;
	
	
	public void setInterests(String s) {
		this.interest=s;
	}
	
	public String getInterests() {
		return interest;
	}
	public void setUserID(int id) {
		this.userID=id;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setProfilePicture(String image) {
		this.profilePicture=image;
	}
	
	public String getProfilePicture() {
		return profilePicture;
	}
	
	public void setInterestID(int id) {
		this.interestID=id;
	}
	
	public int getInterestID() {
		return interestID;
	}

}
