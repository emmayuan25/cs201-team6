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
		
			for(String i: interest){
				interestList.add(i);
			}
			
			User user = (User) JDBCConnector.createNewUser(username, password, userImage, interestList);
			
			
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
					//TODO Change the string for to match JS request -> this is if the js is request a message to know if registration worked
					request.setAttribute("message", message);
				}
				//TODO will move the user to the homepage
				//TODO might have to use respnse.sendRedirect("location") instead of forward dispatcher
				RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				System.out.println("Servlet Exception: "+ e);
			} catch (IOException e) {
				System.out.println("IOException: " + e);
			}
			
		}
		
	
}
