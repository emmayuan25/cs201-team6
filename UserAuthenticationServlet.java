

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserAuthenticationServlet
 */
@WebServlet("/UserAuthenticationServlet")
public class UserAuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAuthenticationServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get parameters for user authentication
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		PrintWriter out = response.getWriter();
		
		// query to database
		// if the user is authenticated, userID will be returned
		// front end will be responsible to keep the userID
		// if the user is not authenticated, either the username does not exist, or the password does not match the username
		// in this case, "error" is returned
		int userID = UserMethods.userAuthentication(username, password);
		if(userID == -1) out.println("error");
		else out.println(userID);
	}
}
