

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateNewUserServlet
 */
@WebServlet("/CreateNewUserServlet")
public class CreateNewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewUserServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get parameters for user authentication
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String profilePicture = request.getParameter("profilePicture");
		// could have many interests
		String[] interests = request.getParameterValues("interests");
		
		PrintWriter out = response.getWriter();
		
		// query to database
		// if creating new user is successful, userID will be returned
		// front end will be responsible to keep the userID
		// if the user is created, "error" is returned
		int userID = UserMethods.createNewUser(username, password, profilePicture, interests);
		if(userID == -1) out.println("error");
		else out.println(userID);
	}
}
