package teamAssignment;

import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class Server {
	
	private Vector<ServerThread> serverThreads;
	private static  ResultSet rs;
	private static Statement st;
	private static Connection conn;
	private BufferedReader br;
	private static User user;
	private static String URL;
	
	
	//server constructor
	//TODO Serverlet and Web socket and change vector to collection lsit
	//open and close connection to table
	public Server(int port) {
		try {
			ServerSocket ss= new ServerSocket(port);
			conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
			serverThreads = new Vector<ServerThread>();
			this.URL= "jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!";
			while(true) {
				Socket s = ss.accept();
				System.out.println("Accepted connection");
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
			}
		} catch (IOException e) {
			System.out.println("IO exception in server constructor "+ e);
		} catch (SQLException e) {
			System.out.println("SQL exception in server constructor" + e);
		}
	}
	
	public Vector<ServerThread> getVectorThread(){
		return this.serverThreads;
	}
	
	//done
	public static void main(String [] args) {
		Server s = new Server(1023);
		
	}
	
	
	//TODO -> fix connection to front end, figure out how to return user class
	public User userAuthentication(String username, String password){
	   
	    try{
	    	System.out.println("Username: "+ username + " Passowrd: "+ password);
	    	st = conn.createStatement();
	        rs= st.executeQuery("Select * from User where userName='"+ username+"'");
	        //if now user name is located then it is an error
	        int userID;
	        String userName = null;
	        String pass;
	        String userImage;
	        int interestID;
	        
        	while(rs.next()){
        		userID = rs.getInt("userID");
        		userName = rs.getString("userName");
                pass = rs.getString("password");
                userImage = rs.getString("userImage");
                interestID = rs.getInt("interestID");
                
            //if password does not exist
               if(pass ==null){
                    return null;
                }
               
            	//instantiate user
                else if(pass.equals(password)== true) {
                	System.out.println("Correct log in");
                	
                	ArrayList<String> list = getInterestList(interestID);
                	for(String i: list) {
                		System.out.println(i);
                	}
                	//TODO->following list
                	user = new User(userID, userName, userImage, list, null, URL);
                	return user;
                	
                }
               
             //Invalid password entered
                else {
                    return null;
                }
            }
        	
        	//username not found in database
        	if(userName==null) {
                return null;
            }
	            
	        
	    } catch(SQLException sqle) {
				System.out.println ("SQLException: " + sqle.getMessage());
	    }
	    
	    return null;

	}
	
	
//TODO get info from front end and fix interestID
	synchronized public User createNewUser(String userName, String password, String userImage, ArrayList<String> interestList){
	
		int userID=-1;
		int interestID = 0;
	
		
	
	    try {
	 
		    //TODO get interest and followers
		    System.out.println("Username: "+userName+" Password: "+ password+ "Image lin: " + userImage);
		    st = conn.createStatement();
		  
		    //add to interest table
		    String listExecute = "INSERT INTO Interest(";
		    for(int i=0; i<interestList.size()-1;i++) {
		    	listExecute +=  interestList.get(i)+", ";
		    }
		    listExecute +=interestList.get(interestList.size()-1) +") VALUES(";
		    for(int j=0; j<interestList.size()-1;j++) {
		    	listExecute += "1, ";
		    }
		    listExecute += "1)";
		    System.out.println("INSERT EXECUTION: "+ listExecute);
		    
		 	st.executeUpdate(listExecute);
		 	rs= st.executeQuery("SELECT MAX(interestID) FROM Interest");
		 	while(rs.next()) {
		 		interestID=rs.getInt(1);
		 	}
		 	
		 	//insert into user table
		 	st.executeUpdate("INSERT INTO USER(userName,userImage,password,interestID) VALUES('"+userName+"', '"+userImage+"', '"+ password+"', '"+ interestID+"')" );
		 	
		 
		 	rs= st.executeQuery("SELECT * FROM User WHERE userName='"+ userName+"'");
		 	while(rs.next()){
		 		userID = rs.getInt("userID");
		 	}
		 	//TODO Need to do followers table
		 	user = new User(userID, userName, userImage, interestList , null, URL);
		 	return user;
		 	
		} catch (SQLException e) {
			System.out.println("SQL exception in createNewUser() "+ e);
		}
	    return null;
	    
	}
	
	
	//TODO Chat and post table and followers table
	public void deleteUser(User profile){
        try{
            st = conn.createStatement();
            String username = profile.getUsername();
            rs= st.executeQuery("Select * from User where userName='"+ username+"'");
            String user;
            String pass;
            String userID = null;
            String interestID = null;
            while(rs.next()){
                user = rs.getString("userName");
                pass = rs.getString("password");
                userID = rs.getString("userID");
                interestID = rs.getString("interestID");
            }
            //delete from user
            PreparedStatement psmt = conn.prepareStatement("DELETE FROM User where userID = ?");
            psmt.setString(1, userID);
            psmt.executeUpdate();
            
            //delete from interest
            psmt = conn.prepareStatement("DELETE FROM Interest where interestID = ?");
            psmt.setString(1, interestID);
            psmt.executeUpdate();
            
            //delete from post
            psmt = conn.prepareStatement("DELETE FROM Post where userID = ?");
            psmt.setString(1, userID);
            psmt.executeUpdate();
            
            //delete from chat message
            psmt = conn.prepareStatement("DELETE FROM ChatMessage where fromUID = ?");
            psmt.setString(1, userID);
            psmt.executeUpdate();
            
    
        }catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
        }
    }
    
	//get list of search results
    public List<String> searchKeyword(String name, Integer myID){
        List<String> returnUser = new ArrayList<String>();
        try{
           
            st = conn.createStatement();
            
            //get results by username
            System.out.println("checking to find username");
            rs= st.executeQuery("Select * from User WHERE userName='"+ name+"'");
            System.out.println("User SQL okay");
                while(rs.next()){
                	int userID = rs.getInt("userID");
                    String username = rs.getString("userName");
                    //TODO get rest of information line interest ID
                    System.out.println(userID);
                   
                    returnUser.add(username);
                }
  
                //check the users with similar interest
                System.out.println("Checking to find interest");
                rs= st.executeQuery("SELECT * FROM Interest WHERE "+ name + "=1");
                System.out.println("Interest table okay");
                List<Integer> idList= new ArrayList<Integer>();
	            while(rs.next()){
	                	int ID = rs.getInt("interestID");
	                	System.out.println(ID);
	                	idList.add(ID);
	            }
	            for(Integer i: idList) {
	            	ResultSet rs2 = st.executeQuery("SELECT userName FROM User WHERE interestID= "+ i);
                	while(rs2.next()) {
                		if(i!=myID) {
                			String username = rs2.getString("userName");
                    		System.out.println(username);
    	                	returnUser.add(username);
                		}
                	}
				}
            
        }catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
        }
    
        return returnUser;
        
    }
    
    //get list of all interest
    public static ArrayList<String> getInterestList(int id) {
    	ArrayList<String> interestList = new ArrayList<String>();
    	try {
    		
			rs=st.executeQuery("SELECT * FROM Interest WHERE interestID=" +id);
			while(rs.next()) {
				int viterbi= rs.getInt("viterbi");
				if(viterbi ==1) {
					interestList.add("viterbi");
				}
				int dornsife= rs.getInt("dornsife");
				if(dornsife ==1) {
					interestList.add("dornsife");
				}
				int marshall= rs.getInt("marshall");
				if(marshall ==1) {
					interestList.add("marshall");
				}
				int computerScience= rs.getInt("computerScience");
				if(computerScience ==1) {
					interestList.add("computerScience");
				}
				int csba= rs.getInt("csba");
				if(csba ==1) {
					interestList.add("csba");
				}
				int businessAdmin= rs.getInt("businessAdmin");
				if(businessAdmin ==1) {
					interestList.add("businessAdmin");
				}
				int csgames= rs.getInt("csgames");
				if(csgames ==1) {
					interestList.add("csgames");
				}
				int cais= rs.getInt("cais");
				if(cais ==1) {
					interestList.add("cais");
				}
				int athenaHacks= rs.getInt("athenaHacks");
				if(athenaHacks ==1) {
					interestList.add("athenaHacks");
				}
				int scope= rs.getInt("scope");
				if(scope ==1) {
					interestList.add("scope");
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL exception in Server getInterestList() "+ e);
		}
    	return interestList;
    }
    
    public void newMessage(int fromUID, int toUID, String messageText) {
    	 try {
			st = conn.createStatement();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();   
			st.executeUpdate("INSERT INTO ChatMessage(fromUID,toUID,messageText,createdAt) VALUES('"+fromUID+"', '"+ toUID+"', '"+messageText+"', '"+dtf.format(now)+"')");
		} catch (SQLException e) {
			System.out.println ("SQLException: " + e.getMessage());
		}
    }

}
