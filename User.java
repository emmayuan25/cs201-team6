public class User {
	private int userID;
	private String username;
	private String password;
	private String profilePicture;
	private String[] interests;
	
	public User(int userID, String username, String password, String profilePicture, String[] interests) {
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.profilePicture = profilePicture;
		this.interests = interests;
	}
	
	public int getUserID() {
		return this.userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getProfilePicture() {
		return this.profilePicture;
	}
	
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	public String[] getInterests() {
		return this.interests;
	}
	
	public void setInterests(String[] interests) {
		this.interests = interests;
	}
}
