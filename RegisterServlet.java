package team;

import java.io.IOException;

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

		//sign up 
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			
			//TODO change names
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String userImage = request.getParameter("image");
			
			//TODO get interest list
			
			User user = (User) JDBCConnector.createNewUser(username, password, userImage, null);
			
			
			try {
				//TODO get login JS page
				String destPage ="";
				
				if(user!=null) {
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					//TODO get home page jsp
					destPage =""; 
				}else {
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
