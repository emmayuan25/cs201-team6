package eryu_CSCI201_GroupProject;

public class Post {
	private int postID;
	private String postText;
	private String postImage;
	private long time;
	private String username;
	private String profilePicture;
	
	public Post(int postID, String postText, String postImage, long time, String username, String profilePicture) {
		this.postID = postID;
		this.postText = postText;
		this.postImage = postImage;
		this.time = time;
		this.username = username;
		this.profilePicture = profilePicture;
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
	public long getTime() {
		return this.time;
	}
}
