package eryu_CSCI201_GroupProject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Post {
	private int postID;
	private String postText;
	private String postImage;
	private Timestamp timestamp;
	private String username;
	private String profilePicture;
	private SimpleDateFormat timeFormatter;
	
	public Post(int postID, String postText, String postImage, Timestamp timestamp, String username, String profilePicture) {
		this.postID = postID;
		this.postText = postText;
		this.postImage = postImage;
		this.timestamp = timestamp;
		this.username = username;
		this.profilePicture = profilePicture;
		
		// reference for SimpleDateFormat: https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html
		// and reference: https://www.geeksforgeeks.org/program-to-convert-milliseconds-to-a-date-format-in-java/
		// and reference: https://www.javatpoint.com/java-get-current-date
		String timePattern = "yyyy-MM-dd HH:mm:ss";
		this.timeFormatter = new SimpleDateFormat(timePattern);
	}
	public int getPostID() {
		return this.postID;
	}
	public String getPostText() {
		return this.postText;
	}
	public String getPostImage() {
		return this.postImage;
	}
	public String getUsername() {
		return this.username;
	}
	public String getProfilePicture() {
		return this.profilePicture;
	}
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	public String getTimestampString() {
		String timestampString = this.timeFormatter.format(this.timestamp);
		return timestampString;
	}
}
