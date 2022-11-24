package team;

import java.io.IOException;
import java.io.PrintWriter;

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
			
			
			
		} catch (IOException e) {
			System.out.println("IOException in DisplayServlet: "+ e);
		}
		
		
	}
}
