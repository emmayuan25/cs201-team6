package team;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ConvoServlet")
public class ConvoServlet extends HttpServlet{
	
	//get chats
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		int toID = Integer.parseInt(request.getParameter("fromID"));  
		//get user
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		
		int userID=user.getUserID();
		
		//TODO get return
		JDBCConnector.getMessageList(userID, toID);
		
	}
	
	//add new chats
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String message = request.getParameter("message");
		int toID = Integer.parseInt(request.getParameter("fromID"));  
		
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		
		JDBCConnector.createMessage(user.getUserID(), toID,message);
		
	}

}
