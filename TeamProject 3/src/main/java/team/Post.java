package team;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Post {
	private int postID;
	private String postText;
	private String postImage;
	private Timestamp timestamp;
	private String username;
	private String profilePicture;
	private boolean edited;
	private SimpleDateFormat timeFormatter;
	private String interest;

	
//	public Post() {
////		String timePattern = "yyyy-MM-dd HH:mm:ss";
////		this.timeFormatter = new SimpleDateFormat(timePattern);
//	}
	public int getPostID() {
		return postID;
	}
	
	public void setPostID(int id) {
		this.postID=id;
	}
	
	public String getPostText() {
		return postText;
	}
	
	public void setPostText(String text) {
		this.postText=text;
	}
	
	public String getPostImage() {
		return postImage;
	}
	
	public void setPostImage(String image) {
		this.postImage=image;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String user) {
		this.username=user;
	}
	
	public String getProfilePicture() {
		return profilePicture;
	}
	
	public void setProfilePicture(String pic) {
		this.profilePicture= pic;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public void setTimeStamp(Timestamp time) {
		this.timestamp=time;
	}
	
	public String getInterest() {
		return interest;
	}
	
	public void setInterest(String interest) {
		this.interest=interest;
	}
	
	public String getTimestampString() {
		String timestampString = this.timeFormatter.format(this.timestamp);
		return timestampString;
	}
	public boolean getEdited() {
		return edited;
	}
	public void setEdited(boolean edited) {
		this.edited = edited;
	}
}
