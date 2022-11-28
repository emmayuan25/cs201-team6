package team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet{
	
	//get the posts to display
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			PrintWriter out = response.getWriter();
		
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");

			List<Post> postList = JDBCConnector.displayPosts(user);
			
			//TODO send out postList to js
			//this will send the list of post objects to the js function calling this class
			//TODO change "list" name
			request.setAttribute("list", postList);
			//TODO add JSP file name
			request.getRequestDispatcher("JSP FILE NAME").forward(request, response);
			
			
			
		} catch (IOException e) {
			System.out.println("IOException in DisplayServlet: "+ e);
		} catch (ServletException e) {
			System.out.println("Server Exception in DisplayServlet: "+ e);
		}
		
		
	}
}
