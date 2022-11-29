package team;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CreatePostServlet")
public class CreatePostServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			PrintWriter out = response.getWriter();
			//TODO change parameter 
			String image = request.getParameter("image");
			String text = request.getParameter("text");
			String interest = request.getParameter("interestList");
			//https://www.dariawan.com/tutorials/java/java-sql-timestamp-examples/
			long now = System.currentTimeMillis();
			Timestamp sqlTimestamp = new Timestamp(now);
			
			
			Post post = new Post();
			post.setPostText(text);
			post.setPostImage(image);
			post.setTimeStamp(sqlTimestamp);
			post.setInterest(interest);	

			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			
			
			JDBCConnector.createPost(user, post);
			RequestDispatcher dispatcher = request.getRequestDispatcher("CreatePost.html");
			dispatcher.forward(request, response);
			
		} catch (IOException e) {
			System.out.println("IOException in CreatePostServlet: "+ e);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
