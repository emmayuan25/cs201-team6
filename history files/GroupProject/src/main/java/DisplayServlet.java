
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
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			System.out.println("in display");
			PrintWriter pw = response.getWriter();
			Gson gson = new Gson();
			
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			
			System.out.println("poster name: " + user.getUsername());

			
			List<Post> postList = JDBCConnector.displayPosts(user);
			
			System.out.println("does displaypost jdbc work");

			String result = gson.toJson(postList);
			response.setContentType("application/json");
			
//			System.out.println(result);
			
			pw.print(result);
			
			System.out.println("is it with gson");

			pw.flush();
			pw.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
