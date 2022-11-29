package team;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/DisplayChatsServlet")
public class DisplayChatsServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			
			//get user
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			Gson gson = new Gson();
			Set<User> conversationSet = JDBCConnector.getChatList(user.getUserID());
			
			
			response.setContentType("application/json"); 
			out.print(gson.toJson(conversationSet));	
			out.flush();
			out.close();
//			//TODO send out postList to js
//			//this will send the list of post objects to the js function calling this class
//			//TODO change "list" name
//			request.setAttribute("set", conversationSet);
//			//TODO add JSP file name
//			request.getRequestDispatcher("JSP FILE NAME").forward(request, response);
			
			
		} catch (IOException e) {
			System.out.println("IOException in conversationServlet: "+ e);
		} 
		
		
	}
	
}
