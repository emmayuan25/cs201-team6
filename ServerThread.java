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

		//send messages to user
	    public void sendMessage(String message){
	        try{
	            StringTokenizer st = new StringTokenizer(message, "#");
	            String MsgToSend = st.nextToken();
	            int recipient = Integer.parseInt(st.nextToken());
	            for(ServerThread x: sr.getVectorThread()) {
	            	if(x.getUserID()==recipient) {
	            		x.output.writeUTF(this.user.getUsername() +" : "+ MsgToSend);
	            		break;
	            	}
	            }
//	        	output.writeObject(message);
//	            output.flush();
	        }catch (IOException e){
	            System.out.println("ioe in severthread sendMessage(): "+ e.getMessage());
	        }
	        
	    }
	    
	    //TODO connect to front end
	    //TODO finish all the possible tasks
	    public void run(){
	    	//Prompt users to either log in or sign up
	        
			try {
				sendMessage("Please log in or sign up");
		        String response;
				response = br.readLine();
				//if selected log in, call userAuthentication()
				
		        if(response.equals("log in")) {
		        	 sendMessage("Username:");
		        	 String username = br.readLine();
		        	 sendMessage("Password:");
		        	 String password = br.readLine();
		        	 user = sr.userAuthentication(username, password);
		        }
		        //if selected sign up, call create new user
		        else if(response.equals("sign up")) {
		        	user = sr.createNewUser();
		        }
		        
		        while(true) {
		        	//displaay post while on home page
		        	ArrayList<Post> postings = user.displayPosts();
		        	
		        	//for chatting -> getting messages
		        	String received =input.readUTF();
		        	if(received!=null) {
		        		StringTokenizer st = new StringTokenizer(received, "#");
			        	
			        	String msg = st.nextToken();
			        	String sender = st.nextToken();
			        	
			        	//print msg;
		        	}
		        	
		        	//search while on search page
		        	String search = null; // get the search term and input into class for list of results
		        	List<String> interestList =sr.searchKeyword(search);
		        	
		        	//delete user
		        	sr.deleteUser(user);
		        	
		        	
		        	//create post
		        	String postText = null;
		        	String postImage = null;
		        	String postInterest = null;
		        	Post post = user.createNewPost(postText, postImage, postInterest);
		        	
		        	
		        	//edit existing post
		        	int postID = 0;
		        	String newPostText = null;
		        	String newPostImage = null;
		        	user.editExistingPost(postID, newPostText,newPostImage);
		        	
		        	
		        	//get chats
		        	ArrayList<Chat> chat = user.getCurrentChats();
		        	
		        	

		        }
			} catch (IOException e) {
				System.out.print("IO exception in serverThread run() " + e );
			}
	        
			
	            
	    }
	    
	    
}





