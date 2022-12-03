package teamAssignment;

import java.beans.Statement;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ServerThread extends Thread{
	    
	    private ObjectOutputStream output;
	    private ObjectInputStream input;
	    private Scanner in;
	    private BufferedReader br;
	    private PrintStream ps;
	    private  ResultSet rs;
	    private  Connection conn;
	    private User user;
	    private Server sr;
	    
	    //start connection
	    public ServerThread(Socket s, Server sr){
	            try {
	            	this.sr=sr;
	            	output= new ObjectOutputStream(s.getOutputStream());
		            input = new ObjectInputStream(s.getInputStream());
		            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
					ps= new PrintStream(new BufferedOutputStream(s.getOutputStream()));
		            in = new Scanner(System.in);
					this.start();
				} catch(IOException e) {
					System.out.println("IOE in serverthread constructor "+ e);
				}
	            
	        
	    }
	    

	    
	    public int getUserID() {
			int id= user.getUserID();
			return id;
		}
	    
	    public String getUserName() {
			String username= user.getUsername();
			return username;
		}

		//send messages to user
	    public void sendMessage(String message){
	        try{
	        	output.writeObject(message);
	            output.flush();


	        }catch (IOException e){
	            System.out.println("ioe in severthread sendMessage(): "+ e.getMessage());
	        }
	        
	    }
	    
	    //TODO connect to front end
	    //TODO finish all the possible tasks
	    public void run(){
	    	//Prompt users to either log in or sign up
	        
			try {
				//connect
				
			
				//if selected log in, call userAuthentication()
				while(true) {
					sendMessage("Please log in or sign up");
			       
					
					String response = (String) input.readObject();
					System.out.println(response);
					
			        
					//if user chose to log in
					if(response.equals("log in")) {
			        	 
			        	sendMessage("Username:");
			        	 String username = (String) input.readObject();
			        	 
			        	 sendMessage("Password:");
			        	 String password = (String) input.readObject();
			        	
			        	 user = sr.userAuthentication(username, password);
			        	 if(user!=null) {
			        		 
			        		 break;
			        		 
			        	 }else {
			        		 sendMessage("Invalid username or password please try again.");
			        	 }
			        }
			        
					//if selected sign up, call create new user
			        else if(response.equals("sign up")) {
			        	System.out.println("Creating new user");
			        
			        	//get username
			        	sendMessage("Username: ");
			        	String userName=(String) input.readObject();
			        	 
			        	//get password
			        	 sendMessage("Password: ");
			        	String password=(String) input.readObject();
			        	
			        	//get photo
			        	sendMessage("Link to profile photo: ");
			        	String userImage= (String) input.readObject();;
			        	
			        	sendMessage("Interest: ");
			        	String interest = (String) input.readObject();
			        	String[] arrOfInt= interest.split(" ");
			        	ArrayList<String> interestList = new ArrayList<String>();
			        	for(String i: arrOfInt) {
			        		System.out.println(i);
			        		interestList.add(i);
			        	}
			        	
			        	
			        	user = sr.createNewUser(userName, password, userImage, interestList);
			        	if(user!=null) {
			        		 break;
			        		 
			        	 }	
			        } 
					
				}
				
		        while(true) {
		        	sendMessage("What would you like to do?");
		        	String action = (String) input.readObject();
		        	
		        	//display post while on home page
		        	if(action.equals("get post")) {
		        		ArrayList<Post> postings = user.displayPosts();
		        	}
		        	
		        	
		        	//for chatting -> getting messages
		        	if(action.equals("chat")) {
		        		sendMessage("What is the person you would like to send message?");
		        		String reciever = (String) input.readObject();
		        		for(ServerThread thread: sr.getVectorThread()) {
		        			
		        			
		        			if(thread.getUserName().equals(reciever)) {
		        				System.out.print("connection found");
		        				sendMessage("What is the message? ");
		        				String message = (String) input.readObject();
		        				thread.sendMessage(user.getUsername()+": "+ message);
		        				sr.newMessage(user.getUserID(), thread.getUserID(), message);
		        				//TODO load chats
		        			}
		        		}
		        	}

		        	
		        	//search while on search page
		        	if(action.equals("search")) {
		        		sendMessage("Enter search term");
		        		
		        		String search =(String) input.readObject(); // get the search term and input into class for list of results
			        	List<String> interestList =sr.searchKeyword(search, user.getUserID());
		        	}
		        	
		        	//Delete
		        	if(action.equals("delete")) {
		        		sr.deleteUser(user);
		        	}
		        	
		        	
		        	
		        	
		        	//TODO create post
		        	if(action.equals("create")) {
		        		String postText = null;
			        	String postImage = null;
			        	String postInterest = null;
			        	Post post = user.createNewPost(postText, postImage, postInterest);
			        	
		        	}
		        	
		        	//TODO edit existing post
		        	if(action.equals("edit")) {
			        	int postID = 0;
			        	String newPostText = null;
			        	String newPostImage = null;
			        	user.editExistingPost(postID, newPostText,newPostImage);
		        	}
		        	
		        	//TODO delete post
		        	if(action.equals("delete post")) {
		        		int postID=0;
		        		user.deletePost(postID);
		        	}
		        	
		        	//TODO get chat and display
		        	if(action.equals("get chat")) {
		        		//get chats
			        	ArrayList<Chat> chat = user.getCurrentChats();
			        	
		        	}
		        	
		     
		        	
		        	
		        	
		        	

		        }
			} catch (IOException | ClassNotFoundException e) {
				System.out.print("IO exception in serverThread run() " + e );
			}
	        
			
	            
	    }
	    
	    
}







