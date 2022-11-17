package teamAssignment;

import java.sql.Statement;
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
	public Server(int port) {
		try {
			ServerSocket ss= new ServerSocket(port);
			conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
			serverThreads = new Vector<ServerThread>();
			this.URL= "jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!";
			while(true) {
				Socket s = ss.accept();
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
	public static User userAuthentication(String username, String password){
	   
	    try{
	    	st = conn.createStatement();
	        rs= st.executeQuery("Select * from User where userName='"+ username+"'");
	        //if now user name is located then it is an error
	        int id;
	        String userName = null;
	        String pass;
        	while(rs.next()){
        		id = rs.getInt("userID");
        		userName = rs.getString("userName");
                pass = rs.getString("password");
                
                //if password does not exist
               if(pass ==null){
                    System.out.println("Invalid username or password please try again.");
                    return null;
                }
            	//Invalid password entered
                else if(pass.equals(pass)==false) {
                
                	System.out.println("Invalid username or password please try again.");
                    return null;
                }
              //instatiate user and return
                else {
                	ArrayList<String> list = getInterestList(id);
                	//TODO->following list, photo, and url
                	user = new User(id, userName, null, list, null, URL);
                	return user;
                }
            }
        	if(userName==null) {
        		System.out.println("Invalid username or password please try again.");
                return null;
            }
	            
	        
	    } catch(SQLException sqle) {
				System.out.println ("SQLException: " + sqle.getMessage());
	    }
	    
	    return null;

	}
	
	
//TODO get info from front end
	public User createNewUser(){
		String userName=null;
		String password=null;
		int userID=-1;
		System.out.println("Name: ");
	    try {
	    	//get into
		    userName = br.readLine();
		    System.out.println("Password: ");
		    password = br.readLine();
		    //TODO get interest
		    
		    st = conn.createStatement();
		    //add user to USer Table
		 	st.executeUpdate("INSERT INTO User(userName,password) " + "VALUES ('"+ userName +"', '"+ password+"')");
		 	//TODO update interest id;
		 	st.executeUpdate("SELECT * FROM User WHERE userName='"+ userName+"'");
		 	while(rs.next()){
		 		userID = rs.getInt("userID");
		 	}
		 	//TODO fix the call below
		 	user = new User(userID, userName, null, null, null, URL);
		 	return user;
		 	
		} catch (IOException e) {
			System.out.println("IO exception in createNewUser() "+ e);
		} catch (SQLException e) {
			System.out.println("SQL exception in createNewUser() "+ e);
		}
	    return null;
	    
	}
	
	
	//done I believe
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
            psmt.setString(1, userID);
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
    
    public List<String> searchKeyword(String name){
        List<String> returnUser = new ArrayList<String>();
        try{
           
            st = conn.createStatement();
            
            //get results by username
            rs= st.executeQuery("Select * from User where userName='"+ name+"'");
                while(rs.next()){
                	int userID = rs.getInt("userID");
                    String username = rs.getString("userName");
                    //TODO get rest of information line interest ID
                    
                   
                    returnUser.add(username);
                }
  
                //check the users with similar interest
                rs= st.executeQuery("Select * from Interest where "+ name + "=1");
                List<Integer> idList= new ArrayList<Integer>();
	            while(rs.next()){
	                	int ID = rs.getInt("interestID");
	                	idList.add(ID);
	            }
	            for(Integer i: idList) {
	            	ResultSet rs2 = st.executeQuery("SELECT userName FROM User WHERE userID= " + i);
                	while(rs2.next()) {
                		String username = rs2.getString("userName");
	                	returnUser.add(username);
                	}
				}
            
        }catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
        }
    
        return returnUser;
        
    }
    
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

}

