package team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
			List<String> searchList = JDBCConnector.searchKeyword(search, user.getUserID());
			
			//TODO idk what to print or do after getting list
			out.println(searchList);
			
			
			
		} catch (IOException e) {
			System.out.println("IOException: "+ e);
		}
		
		
	}
	
}
