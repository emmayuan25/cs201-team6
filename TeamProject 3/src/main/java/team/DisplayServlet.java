
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

@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	//get the posts to display
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			System.out.println("here");
			
			List<Post> postList = JDBCConnector.displayPosts(user);
			response.setContentType("application/json"); 
			out.print(gson.toJson(postList));	
			out.flush();
			out.close();
			
			
//			//TODO send out postList to js
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
