import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;




public class JDBCConnector {
	private static String sql_user = "root";
	private static String sql_password = "mysql@cs201"; // change this before submission
	
	//check user log in
	//TODO figure out if I return user or int
	//https://www.codejava.net/coding/how-to-code-login-and-logout-with-java-servlet-jsp-and-mysql
	synchronized static public User userAuthentication(String username, String password){
		try {

		    Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException cnfe) {

		    System.out.println(cnfe.getMessage());

		}
		
		Connection conn = null;
		ResultSet rs = null;
		User user = null;
		
	    try{
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=" + JDBCConnector.sql_password);
	    	String line = "SELECT * FROM User WHERE userName=? and password = ?";
	    	PreparedStatement statement = conn.prepareStatement(line);
	    	statement.setString(1, username);
	    	statement.setString(2, password);	    	
	    	rs = statement.executeQuery();

        	while(rs.next()){
        		user = new User();
        		user.setUserID(rs.getInt("userID"));
        		user.setUsername(rs.getString("userName"));
        		user.setProfilePicture(rs.getString("userImage"));
        		user.setInterestID(rs.getInt("interestID"));
        	}   


  
	    } catch(SQLException sqle) {
				System.out.println ("SQLException: " + sqle.getMessage());
	    }finally {
	    	try {
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle: "+ sqle.getMessage());
	    	}
	    }
	    
	    return user;
	}
	
	
	//TODO what should this return
	synchronized static public User createNewUser(String userName, String password, String userImage, List<String> interestList) {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
		
		Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
		User user = null;
	    try {
	    	
	    	
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=" + JDBCConnector.sql_password);
		    st = conn.createStatement();
		    
		    String username = null;
		    rs = st.executeQuery("SELECT * FROM User WHERE userName='"+userName+"'");
		    while(rs.next()) {
		    	username= rs.getString("userName");
		    }
		    
		    //username already taken
		    if(username!=null) {
		    	return null;
		    }
		    
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
		    st.executeUpdate(listExecute);
		    
		    //get interestID
		    int interestID = 0;
		 	
		 	rs= st.executeQuery("SELECT MAX(interestID) FROM Interest");
		 	while(rs.next()) {
		 		interestID=rs.getInt(1);
		 	}
		 	
		 	//insert into user table
		 	st.executeUpdate("INSERT INTO USER(userName,userImage,password,interestID) VALUES('"+userName+"', '"+userImage+"', '"+ password+"', '"+ interestID+"')" );
		 	
		 	//get userID
		 	rs= st.executeQuery("SELECT * FROM User WHERE userName='"+ userName+"'");
		 	int userID = 0;
		 	while(rs.next()){
		 		userID = rs.getInt("userID");
		 	}
		 	
		 	user = new User();
		 	
		 	user.setUserID(userID);
		 	user.setInterestID(interestID);
		 	user.setUsername(userName);
		 	user.setProfilePicture(userImage);
		 	
		} catch (SQLException e) {
			System.out.println("SQL exception in createNewUser() "+ e);
		}finally {
	    	try {
	    		if(st!=null) {
	    			st.close();
	    		}
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    		if(rs !=null) {
	    			conn.close();
	    		}
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle: "+ sqle.getMessage());
	    	}
	    }
	    return user;
	    
	}
	
	//TODO Chat and post table 

	synchronized static	public void deleteUser(User profile){
			
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
		Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
		PreparedStatement psmt =null;
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
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
            psmt = conn.prepareStatement("DELETE FROM User where userID = ?");
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
        }finally {
	    	try {
	    		if(st!=null) {
	    			st.close();
	    		}
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    		if(rs !=null) {
	    			conn.close();
	    		}if(psmt != null) {
	    			psmt.close();
	    		}
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle: "+ sqle.getMessage());
	    	}
	    }
	}
		
		
	synchronized static	public List<User> searchKeyword(String name, Integer myID){
	       
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
		
		List<User> returnUser = Collections.synchronizedList(new ArrayList<>());
	    Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
	    
	    try{
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
	        st = conn.createStatement();
	        
	        
	       
	        rs= st.executeQuery("Select * from User WHERE userName='"+ name+"'");
       
            while(rs.next()){
            	User user = new User();
            	int userID = rs.getInt("userID");
                String username = rs.getString("userName");
                String userImage= rs.getString("userImage");
                user.setUserID(userID);
                user.setUsername(username);
                user.setProfilePicture(userImage); 
                    returnUser.add(user);
                }
	  
	                //check the users with similar interest
	            
	            rs= st.executeQuery("SELECT * FROM Interest WHERE "+ name + "=1");
	            
	            List<Integer> idList= new ArrayList<Integer>();
	            while(rs.next()){
	                	int ID = rs.getInt("interestID");
	                	idList.add(ID);
	            }
	            for(Integer i: idList) {
	            	ResultSet rs2 = st.executeQuery("SELECT u.userName, u.userID, u.userImage, i.interestID FROM User u, Interest i WHERE u.interestID=i.interestID and i.interestID="+i);
	            	while(rs2.next()) {
	            		if(i!=myID) {
	            			User user = new User();
	            			String username = rs2.getString("userName");
	            			String userImage = rs2.getString("userImage");
	            			int userID = rs2.getInt("userID");
		                	user.setUserID(userID);
		                	user.setUsername(username);
		                	user.setProfilePicture(userImage);
		                	returnUser.add(user);
	            		}
	            	}
				}
	        
	    }catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
	    }finally {
	    	try {
	    		if(st!=null) {
	    			st.close();
	    		}
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    		if(rs !=null) {
	    			conn.close();
	    		}
	    		
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle: "+ sqle.getMessage());
	    	}
	    }
	
	    return returnUser;
	    
	}
	
	//get list of all interest
	synchronized public static List<String> getInterestList(int id) {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
		
		List<String> interestList = Collections.synchronizedList(new ArrayList<>());
    	Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
    	
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
    		st=conn.createStatement();
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
		}finally {
	    	try {
	    		if(st!=null) {
	    			st.close();
	    		}
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    		if(rs !=null) {
	    			conn.close();
	    		}
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle: "+ sqle.getMessage());
	    	}
	    }
    	return interestList;
    }
    
	
    //return interestID
   synchronized public static int updateProfile(User user, List<String> interestList) {
	   try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
	   
	   	Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
		 int interestID = user.getInterestID();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
			st=conn.createStatement();
			//update user table
			st.executeUpdate("UPDATE User SET userName='"+user.getUsername()+"', userImage='"+user.getProfilePicture()+"' WHERE userID="+user.getUserID());
			
			
			//Add new row to interest table and update interest id for user (easier than updating the entire row)
	
		    String listExecute = "INSERT INTO Interest(";
		    for(int i=0; i<interestList.size()-1;i++) {
		    	listExecute +=  interestList.get(i)+", ";
		    }
		    listExecute +=interestList.get(interestList.size()-1) +") VALUES(";
		    for(int j=0; j<interestList.size()-1;j++) {
		    	listExecute += "1, ";
		    }
		    listExecute += "1)";
		    st.executeUpdate(listExecute);
		    
		    //get interestID
		 	rs= st.executeQuery("SELECT MAX(interestID) FROM Interest");
		 	while(rs.next()) {
		 		interestID=rs.getInt(1);
		 	}
			
		 	//update interest
		 	st.executeUpdate("UPDATE User SET interestID="+interestID+" WHERE userID="+user.getUserID());
			
			
		} catch (SQLException e) {
			System.out.println("SQL exception in Server getInterestList() "+ e);
		}finally {
	    	try {
	    		if(st!=null) {
	    			st.close();
	    		}
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    		if(rs !=null) {
	    			conn.close();
	    		}
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle: "+ sqle.getMessage());
	    	}
	    }
		return interestID;
		
    }
   
   
   synchronized public static List<Post> allPosts(){
	   try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
	   
	   List<Post> postList = Collections.synchronizedList(new ArrayList<>());
	   Connection conn = null;
	   Statement st = null; 
	   ResultSet rs = null;
	  
	   try {
		   conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
		   st=conn.createStatement();
		   rs= st.executeQuery("SELECT p.postID, p.postText, p.postImage, p.interestID, u.userName, u.userImage FROM Post p, User u WHERE p.userID = U.userID");
		   while(rs.next()) {
			   Post post = new Post();
			   post.setPostID(rs.getInt("postID"));
			   post.setPostText(rs.getString("postText"));
			   post.setPostImage(rs.getString("postImage"));
			   post.setUsername(rs.getString("userName"));
			   post.setProfilePicture(rs.getString("userImage"));
			   postList.add(post);
		   }
	   }catch (SQLException e) {
			System.out.println("sqle in displayPost: "+ e.getMessage());
		}finally {
	    	try {
	    		if(st!=null) {
	    			st.close();
	    		}
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    		if(rs !=null) {
	    			conn.close();
	    		}
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle in displayPost: "+ sqle.getMessage());
	    	}
	    }
	   
	   return postList;
	   
   }
   
   //TODO shuffle the post list
   //get posts
   synchronized public static List<Post> displayPosts(User user) {
	   try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
	   
	   //get user interest
	   
	   int interestID=user.getInterestID();
	   
	   //get from interest table which interest the user has
	  List<String> interestList = getInterestList(interestID);
	   
	   List<Post> postList = Collections.synchronizedList(new ArrayList<>());
	   
	   Connection conn = null;
		Statement st = null; 
		Statement st2= null;
		ResultSet rs = null;
		ResultSet rs2 = null;
	  
	   
		   try {
			   conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
			   st=conn.createStatement();
			   st2=conn.createStatement();
			   //go through each interest in post interest table 
			   for(String x: interestList) {
				   String line = "SELECT "+ x + ",interestID FROM PostInterest";
				   rs= st.executeQuery(line);
				   while(rs.next()) {
					   int n = rs.getInt(x);
					   //if the value is 1
					   if(n!=0) {
						   //get post associated to that id
						   int p = rs.getInt("interestID");
						   String excute = "SELECT p.postID, p.postText, p.postImage, p.interestID, u.userName, u.userImage FROM Post p, User u where p.userID = u.userID and p.interestID ="+p;
						   rs2= st2.executeQuery(excute);
						   while(rs2.next()) {
							   //get post
							   Post post = new Post();
							   post.setPostID(rs2.getInt("postID"));
							   post.setPostText(rs2.getString("postText"));
							   post.setPostImage(rs2.getString("postImage"));
							   post.setUsername(rs2.getString("userName"));
							   post.setProfilePicture(rs2.getString("userImage"));
							   postList.add(post);
						   }
					   }
				   }
			
			   }
		} catch (SQLException e) {
			System.out.println("sqle in displayPost: "+ e.getMessage());
		}finally {
	    	try {
	    		if(st!=null) {
	    			st.close();
	    		}
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    		if(rs !=null) {
	    			conn.close();
	    		}
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle in displayPost: "+ sqle.getMessage());
	    	}
	    }
		   return postList;
		   
	   }
	
   
   
   synchronized public static void createPost(User user, Post post) {
	   try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
	   
	   Connection conn = null;
	   Statement st = null;  
	   ResultSet rs = null;
	   int userID = user.getUserID();
	   
	   String text = post.getPostText();
	   String image = post.getPostImage();
//	   String time = post.getTimestampString();
	   String interest = post.getInterest();
	   
	   try {
		   conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
		   st=conn.createStatement();
	   //insert post to postinterest table
	 	st.executeUpdate("INSERT INTO PostInterest("+interest+") VALUES(1)");
	 	int interestID = 0;
		 	
	 	//get interestID
	 	rs= st.executeQuery("SELECT MAX(interestID) FROM PostInterest");
	 	while(rs.next()) {
	 		interestID=rs.getInt(1);
	 	}
	 	
	 	//add post to post table
	 	st.executeUpdate("INSERT INTO Post(postText,postImage,userID,interestID) VALUES('"+text+"', '"+image+"', "+userID+", "+interestID+")");
	 	
	   } catch (SQLException e) {
			System.out.println("SQL exception in createPost: "+ e);
		}finally {
			try {
	    		if(st!=null) {
	    			st.close();
	    		}
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    		if(rs !=null) {
	    			conn.close();
	    		}
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle: "+ sqle.getMessage());
	    	}
	
		}
	   
   }
   
   // get everyone whom the input user has sent or received messages with
   synchronized public static ChatsList getChatsList(int input_userID) {
	   try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
	   
	   HashSet<Integer> friendIDSet = new HashSet<Integer>();
	   ArrayList<Chat> chatsArrayList = new ArrayList<Chat>();
	
	   Connection conn = null;
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   PreparedStatement ps2 = null;
	   ResultSet rs2 = null;
		   
	   try {
		   	conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=" + JDBCConnector.sql_password);
		   	
		   	// get the receiverID for the chat messages where senderID == input_userID
			ps = conn.prepareStatement("SELECT toUID FROM ChatMessage WHERE fromUID=?");
			ps.setInt(1, input_userID);
			rs = ps.executeQuery();
		   
			while(rs.next()) {
				int receiverID = rs.getInt("toUID");
				if(!friendIDSet.contains(receiverID)) {
					friendIDSet.add(receiverID);
				}				   
			}
			
			// get the senderID for the chat messages where receiverID == input_userID
			ps2 = conn.prepareStatement("SELECT fromUID FROM ChatMessage WHERE toUID=?");
			ps2.setInt(1, input_userID);
			rs2 = ps2.executeQuery();

			while(rs2.next()) {
				int senderID = rs2.getInt("fromUID");
				if(!friendIDSet.contains(senderID)) {
					friendIDSet.add(senderID);
				}				   
			}
		
			for(Integer friendID : friendIDSet) {
				PreparedStatement ps3 = null;
				ResultSet rs3 = null;
				try {
					// get the userName and userImage of those in friendIDSet
					ps3 = conn.prepareStatement("SELECT userName, userImage FROM User WHERE userID=?");
					ps3.setInt(1, friendID);
					rs3 = ps3.executeQuery();
					
					while(rs3.next()) {
						String userName = rs3.getString("userName");
						String userImage = rs3.getString("userImage");
						
						Chat current_chat = new Chat(friendID, userName, userImage);
						chatsArrayList.add(current_chat);
					}
				} catch (SQLException e) {
					System.out.println("SQL exception in Server getChatsList() "+ e);
				} finally {
					try {
						if(rs3 != null) rs3.close();
						if(ps3 != null) ps3.close();
					} catch(SQLException sqle) {
						   System.out.println("sqle: "+ sqle.getMessage());
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL exception in Server getChatsList() "+ e);
		} finally {
			try {
				if(rs2 != null) rs2.close();
				if(ps2 != null) ps2.close();
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException sqle) {
				   System.out.println("sqle: "+ sqle.getMessage());
			   }
		}
	   
		ChatsList chatsList = new ChatsList(chatsArrayList);
		return chatsList;
	}
   
 
   
 	
   synchronized public static void createMessage(int userID, int toID, String message){
	   try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
	   Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
			st=conn.createStatement();
			st.executeUpdate("INSERT INTO ChatMessage(fromUID,toUID,messageText,createdAt) VALUES("+userID+", "+toID+", '"+message+"', CURRENT_TIMESTAMP)");
		} catch (SQLException e) {
			System.out.println("sqle: "+ e.getMessage());
		}finally {
			try {
	    		if(st!=null) {
	    			st.close();
	    		}
	    		if(conn!=null) {
	    			conn.close();
	    		}
	    		if(rs !=null) {
	    			conn.close();
	    		}
	    	} catch(SQLException sqle) {
	    		System.out.println("sqle: "+ sqle.getMessage());
	    	}
	
		}
	  
   }

   // get a specific list of chat messages
   
   synchronized public static ChatMessagesList getMessagesList(int input_userID, int input_friendID) { 
	   try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
	   } catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
	   } 
	   
	   ArrayList<ChatMessage> messagesList = new ArrayList<ChatMessage>();
	   HashMap<Integer, String> imagesMap = new HashMap<Integer, String>();
	
	   Connection conn = null;
	   PreparedStatement ps = null;
	   ResultSet rs = null;
		   
	   try {
		   	conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=" + JDBCConnector.sql_password);
		   	
		   	// get the chat messages between input_userID and input_friendID
			ps = conn.prepareStatement("SELECT fromUID, toUID, messageText, createdAt FROM ChatMessage WHERE (fromUID=? AND toUID=?) OR (fromUID=? AND toUID=?)");
			ps.setInt(1, input_userID);
			ps.setInt(2, input_friendID);
			ps.setInt(3, input_friendID);
			ps.setInt(4, input_userID);
			rs = ps.executeQuery();
		   
			while(rs.next()) {
				int senderID = rs.getInt("fromUID");
				int receiverID = rs.getInt("toUID");
				String messageText = rs.getString("messageText");
				Timestamp timestamp = rs.getTimestamp("createdAt");
				
				// create ChatMessage objects and store in ArrayList
				ChatMessage message = new ChatMessage(senderID, receiverID, null, null, messageText, timestamp);
				messagesList.add(message);
			}
			
			// get the profile pictures
			for(ChatMessage message : messagesList) {
				int senderID = message.getSenderID();
				if(imagesMap.containsKey(senderID)) {
					String profilePicture = imagesMap.get(senderID);
					message.setSenderProfilePicture(profilePicture);
				}
				else {
					PreparedStatement ps2 = null;
					ResultSet rs2 = null;
					
					try {
						ps2 = conn.prepareStatement("SELECT userImage FROM User WHERE userID=?");
						ps2.setInt(1, senderID);
						rs2 = ps2.executeQuery();
						while(rs2.next()) {
							String senderProfilePicture = rs2.getString("userImage");
							message.setSenderProfilePicture(senderProfilePicture);
							imagesMap.put(senderID, senderProfilePicture);
						}
					} catch (SQLException e) {
						System.out.println("SQL exception in Server getMessagesList() "+ e);
					} finally {
						try {
							if(rs2 != null) rs2.close();
							if(ps2 != null) ps2.close();
						} catch(SQLException sqle) {
							System.out.println("sqle: "+ sqle.getMessage());
						}
					}
				}
				
				int receiverID = message.getReceiverID();
				if(imagesMap.containsKey(receiverID)) {
					String profilePicture = imagesMap.get(receiverID);
					message.setReceiverProfilePicture(profilePicture);
				}
				else {
					PreparedStatement ps2 = null;
					ResultSet rs2 = null;
					
					try {
						ps2 = conn.prepareStatement("SELECT userImage FROM User WHERE userID=?");
						ps2.setInt(1, receiverID);
						rs2 = ps2.executeQuery();
						while(rs2.next()) {
							String receiverProfilePicture = rs2.getString("userImage");
							message.setReceiverProfilePicture(receiverProfilePicture);
							imagesMap.put(receiverID, receiverProfilePicture);
						}
					} catch (SQLException e) {
						System.out.println("SQL exception in Server getMessagesList() "+ e);
					} finally {
						try {
							if(rs2 != null) rs2.close();
							if(ps2 != null) ps2.close();
						} catch(SQLException sqle) {
							System.out.println("sqle: "+ sqle.getMessage());
						}
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL exception in Server getMessagesList() "+ e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException sqle) {
				System.out.println("sqle: "+ sqle.getMessage());
			}
		}
	   
	   	//sort the messages by timestamp
	   	messagesList.sort(new ChatMessageComparator());
	   	
		ChatMessagesList chat_messages_list = new ChatMessagesList(messagesList);
		return chat_messages_list;
	}
   
   public static User getUser(int input_userID) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException cnfe) {
	        System.out.println(cnfe.getMessage());
	    } 
	   
	    ArrayList<ChatMessage> messagesList = new ArrayList<ChatMessage>();
	    HashMap<Integer, String> imagesMap = new HashMap<Integer, String>();

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	       
	    try {
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=" + JDBCConnector.sql_password);
	           
	           // get the chat messages between input_userID and input_friendID
	        ps = conn.prepareStatement("SELECT userName, userImage, interestID FROM User WHERE userID=?");
	        ps.setInt(1, input_userID);
	        rs = ps.executeQuery();
	        
	        String userName = "";
	        String userImage = "";
	        int interestID = 0;
	        while(rs.next()) {
	            userName = rs.getString("userName");
	            userImage = rs.getString("userImage");
	            interestID = rs.getInt("interestID");
	        }

	        User u = new User();
	        u.setUserID(input_userID);
	        u.setProfilePicture(userImage);
	        u.setUsername(userName);
	        u.setInterestID(interestID);

	        return u;
	    } catch (SQLException e) {
			System.out.println("SQL exception in Server getMessagesList() "+ e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch(SQLException sqle) {
				System.out.println("sqle: "+ sqle.getMessage());
			}
		}
	    
		return null;
	}
 		

    
}