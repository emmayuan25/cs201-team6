package team;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	//login
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		try{
		
			//TODO change names
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			//TODO decide if the next line is necesary???
//			JDBCConnector connect = new JDBCConnector();
			
			User user = JDBCConnector.userAuthentication(username, password);
			
			//TODO get login JS page
			String destPage ="";
			
			if(user!=null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				//TODO get home page jsp
				destPage ="";
			}else {
				String message = "Invalid email/password";
				request.setAttribute("message", message);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
			dispatcher.forward(request, response);
			
		} catch (ServletException e) {
			System.out.println("Servlet Exception in Login Servlet: "+ e);
		} catch (IOException e) {
			System.out.println("IOException in Login Servlet: "+e);
		}
	}
	
	
}
