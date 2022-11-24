

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get parameters for user authentication
		String userIDString = request.getParameter("userID");
		int userID = Integer.parseInt(userIDString);
		
		PrintWriter out = response.getWriter();
		
		// query to database
		// if user deletion is successful, return "success"
		// otherwise, return "error" (should not happen)
		int success = UserMethods.deleteUser(userID);
		if(success == 0) out.println("error");
		else out.println("success");
}
}
