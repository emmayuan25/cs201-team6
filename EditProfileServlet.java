package team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			
			//TODO change parameter 
			String usernameChange = request.getParameter("search");
			String imageChange = request.getParameter("");
			
			//TODO Get interest list change
			
			
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			
			user.setUsername(usernameChange);
			user.setProfilePicture(imageChange);
			
			
			//TODO get list
			ArrayList<String> list = null;
			int id=JDBCConnector.updateProfile(user, list);
			
			
			user.setInterestID(id);
			
			session.setAttribute("user", user);
			
			//TODO send out updated profile 
			
			
		} catch (IOException e) {
			System.out.println("IOException in ProfileServlet: "+ e);
		}
		
		
	}
	

}
