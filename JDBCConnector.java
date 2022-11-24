package team;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;




public class JDBCConnector {
	
	//check user log in
	//TODO figure out if I return user or int
	//https://www.codejava.net/coding/how-to-code-login-and-logout-with-java-servlet-jsp-and-mysql
	synchronized static public User userAuthentication(String username, String password){
		try {
			Class.forName("com.mysql.cj.jdbc.driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
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
	synchronized static public User createNewUser(String userName, String password, String userImage, ArrayList<String> interestList) {
		
		
		Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
		User user = null;
	    try {
	    	
	    	
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
		    st = conn.createStatement();
		    
		    String username = null;
		    rs = st.executeQuery("SELECT * FROM User WHERE userName='"+userName+"')");
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
		
		
	synchronized static	public List<String> searchKeyword(String name, Integer myID){
	        List<String> returnUser = new ArrayList<String>();
	        Connection conn = null;
			Statement st = null;  
			ResultSet rs = null;
	        
	        try{
	        	conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
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
	synchronized public static ArrayList<String> getInterestList(int id) {
    	ArrayList<String> interestList = new ArrayList<String>();
    	Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
    	
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
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
   synchronized public static int updateProfile(User user, ArrayList<String> interestList) {
    	Connection conn = null;
		Statement st = null;  
		ResultSet rs = null;
		 int interestID = 0;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/T2T?user=root&password=Cupcake1!");
			
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



    
}
