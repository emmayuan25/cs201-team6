package team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

		public RegisterServlet() {
			super();
		}
		//sign up 
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
			PrintWriter out = response.getWriter();
			
			//TODO change names
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String userImage = request.getParameter("image");
			
			
			//TODO get interest list
			String[] interest = request.getParameterValues("interest");
			List<String> interestList = Collections.synchronizedList(new ArrayList<>());
			
			for(String i: interest) {
				interestList.add(i);
			}
			
			User user = (User) JDBCConnector.createNewUser(username, password, userImage, interestList);
			
			
			try {
				
				String destPage ="Register.html";
				
				if(user!=null) {
					HttpSession session = request.getSession(true);
					session.setAttribute("user", user);
					destPage ="HomePage.jsp"; 
				}else {
					//TODO send response message
					String message = "Username already taken";
					request.setAttribute("message", message);
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				System.out.println("Servlet Exception: "+ e);
			} catch (IOException e) {
				System.out.println("IOException: " + e);
			}
			
		}
		
	
}
