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

import com.google.gson.Gson;

@WebServlet("/DisplayAllPostsServlet")
public class DisplayAllPostsServlet extends HttpServlet{
	
	//get the posts to display
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			//get a list of Post class which are the post 
			List<Post> postList = JDBCConnector.allPosts();
			response.setContentType("application/json"); 
			out.print(gson.toJson(postList));	
			out.flush();
			out.close();
//			//TODO send out list to js
//			//this will send the list of post objects to the js function calling this class
//			//TODO change "list" name
//			request.setAttribute("list", postList);
//			//TODO add JSP file name
//			request.getRequestDispatcher("JSP FILE NAME").forward(request, response);
			
		} catch (IOException e) {
			System.out.println("IOException in DisplayServlet: "+ e);
		} 
		
		
	}
}
