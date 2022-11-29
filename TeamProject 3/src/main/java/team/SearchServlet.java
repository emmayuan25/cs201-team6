package team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	//get list
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
//		try {
			
			try {
				PrintWriter out = response.getWriter();
				Gson gson = new Gson();
				//TODO change parameter 
				String search = request.getParameter("search");
				HttpSession session = request.getSession(false);
				User user = (User) session.getAttribute("user");
				System.out.println("user id: "+user.getUserID());
				
				List<User> searchList = JDBCConnector.searchKeyword(search, user.getUserID());
				response.setContentType("application/json"); 
				out.print(gson.toJson(searchList));	
				out.flush();
				out.close();
				
	
			} catch (IOException e) {
				System.out.println("IOException in search servlet: "+ e);
			}
			
//			//TODO send to jsp file
//			
//			request.setAttribute("list", searchList);
//			//TODO add JSP file name
//			request.getRequestDispatcher("JSP FILE NAME").forward(request, response);
			
			
			
//		} catch (IOException | ServletException e) {
//			System.out.println("IOException in searchServlet: "+ e);
//		}
		
		
	}
	
}
