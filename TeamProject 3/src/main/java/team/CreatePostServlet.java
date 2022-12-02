
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
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("In create post");
			PrintWriter out = response.getWriter();
			//TODO change parameter 
			String image= null;
			String text=null;
			image = request.getParameter("image");
			text = request.getParameter("text");
			String interest = request.getParameter("interestList");
			
			System.out.println(image);
			System.out.println(text);
			
			String destPage=null;
			if(image==null ||text==null) {
				destPage="CreatePost.html";
			}else {
				//https://www.dariawan.com/tutorials/java/java-sql-timestamp-examples/
				long now = System.currentTimeMillis();
				Timestamp sqlTimestamp = new Timestamp(now);
				
				
				Post post = new Post();
				post.setPostText(text);
				post.setPostImage(image);
				post.setTimeStamp(sqlTimestamp);
				post.setInterest(interest);	
	//
//				HttpSession session = request.getSession(false);
//				User user = (User) session.getAttribute("user");
				
				User user = new User();
				user.setUserID(3);
				
				JDBCConnector.createPost(user, post);
				destPage= "HomePage.html";
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
			dispatcher.forward(request, response);
			
		} catch (IOException e) {
			System.out.println("IOException in CreatePostServlet: "+ e);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
