
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
			
			PrintWriter pw = response.getWriter();
			Gson gson = new Gson();
			
			List<Post> postList = JDBCConnector.allPosts();
			String result = gson.toJson(postList);
			
			response.setContentType("application/json");	
			pw.print(result);
			pw.flush();
			pw.close();

			
		} catch (IOException e) {
			System.out.println("IOException in DisplayServlet: "+ e);
		} 
		
		
	}
}
