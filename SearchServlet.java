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

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	//get list
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			
			//TODO change parameter 
			String search = request.getParameter("search");
			
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			System.out.println("user id: "+user.getUserID());
			//TODO might have to change it so it can be a list of User, so there is the functionality to open user profile and message them
			List<String> searchList = JDBCConnector.searchKeyword(search, user.getUserID());

			
			//TODO send out postList to js
			//this will send the list of post objects to the js function calling this class
			//TODO change "list" name
			request.setAttribute("list", searchList);
			//TODO add JSP file name
			request.getRequestDispatcher("JSP FILE NAME").forward(request, response);
			
			
			
		} catch (IOException | ServletException e) {
			System.out.println("IOException in searchServlet: "+ e);
		}
		
		
	}
	
}
