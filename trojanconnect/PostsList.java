package eryu_CSCI201_GroupProject;

import java.util.ArrayList;

// wrapper class for a list of posts
public class PostsList {
	private ArrayList<Post> postsList = null;
	
	public PostsList(ArrayList<Post> postsList) {
		this.postsList = postsList;
	}
	
	public ArrayList<Post> getPostsList() {
		return this.postsList;
	}
	
	public void setPostsList(ArrayList<Post> postsList) {
		this.postsList = postsList;
	}
}
