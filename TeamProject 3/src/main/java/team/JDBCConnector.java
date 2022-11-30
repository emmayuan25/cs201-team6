package team;

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
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
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
	    	
	    	
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
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

   synchronized public static Set<User> getChatList(int id) {
	   try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		    System.out.println(cnfe.getMessage());
		} 
	   
	   	Set<Integer> chatlist = new HashSet<Integer>();
		Set<Integer> chatOther = new HashSet<Integer>();
		Set<User> userOther = new HashSet<User>();
	
		Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
		User user = new User();
		   
		   try {
		   conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
		   st=conn.createStatement();
		   	rs=st.executeQuery("SELECT * FROM ChatMessage WHERE fromUID ="+id );
			while(rs.next()) {
				int messageID = rs.getInt("messageID");
				int toUID = rs.getInt("toUID");
				chatlist.add(messageID);
				chatOther.add(toUID);
								   
			}
			rs=st.executeQuery("SELECT * FROM ChatMessage WHERE toUID ="+id );
			while(rs.next()) {
				int messageID = rs.getInt("messageID");
				int fromUID = rs.getInt("fromUID");
				chatlist.add(messageID);
				chatOther.add(fromUID);
								   
			}
			
	
	
			for (int s : chatOther) {
				rs=st.executeQuery("SELECT * FROM User WHERE userID="+s);
				while(rs.next()) {
					user = new User();
					user.setUserID(rs.getInt("userID"));
					user.setUsername(rs.getString("userName"));
					user.setProfilePicture(rs.getString("userImage"));
					user.setInterestID(rs.getInt("interestID"));
					userOther.add(user);
				}
				
			}
	
		} catch (SQLException e) {
			System.out.println("SQL exception in Server getChatList() "+ e);
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
		   return userOther;
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

   
   
 //get list of all chats b/w 2 people ordered by time
 		//id1 is our identity, id2 is the person who's text we want to pull up
			synchronized public static Map<Timestamp, String> getMessageList(int id1, int id2) { 
 			Map<Timestamp, String> messagelist = new HashMap<Timestamp, String>();
 			Map<Timestamp, String> tm = new TreeMap<Timestamp, String>();
 			Connection conn = null;
 			Statement st = null;  
 			ResultSet rs = null;
 			
 			try {
 				conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
 				st=conn.createStatement();
 				rs=st.executeQuery("SELECT * FROM ChatMessage WHERE fromUID ="+id1);
 				while(rs.next()) {
 					int toUID = rs.getInt("toUID");
 					int fromUID = rs.getInt("fromUID");
					String msg = rs.getString("messageText");
					String inputVal = fromUID.toString()+"_"+msg;
 					if (toUID == id2) {
 						Timestamp createdAt = rs.getTimestamp("createdAt");
 						messagelist.put(createdAt, inputVal);
 					}              
 				}
 				rs=st.executeQuery("SELECT * FROM ChatMessage WHERE toUID ="+id1);
 				while(rs.next()) {
 					int fromUID = rs.getInt("fromUID");
					String msg = rs.getString("messageText");
					String inputVal = fromUID.toString()+"_"+msg;
 					if (fromUID == id2) {
 						Timestamp createdAt = rs.getTimestamp("createdAt");
 						messagelist.put(createdAt, inputVal);
 					}
 				}
 				Map<Timestamp, String> treeMap = new TreeMap<Timestamp, String>((Comparator<? super Timestamp>) messagelist);
 				tm = treeMap;
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
 			return tm;
 		}
 		

    
}
