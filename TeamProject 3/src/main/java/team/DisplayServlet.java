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

@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	//get the posts to display
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			PrintWriter pw = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);

			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			System.out.println("Edit profile:"+user.getUsername());
		
			
			String result = gson.toJson(user);
			
			pw.write(result);
			pw.flush();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}	
}
