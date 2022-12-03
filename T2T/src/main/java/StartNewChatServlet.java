

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StartNewChatServlet
 */
@WebServlet("/StartNewChatServlet")
public class StartNewChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StartNewChatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String userIDString = request.getParameter("userID");
		int input_userID = Integer.parseInt(userIDString);
		String friendIDString = request.getParameter("friendID");
		int input_friendID = Integer.parseInt(friendIDString);
		String messageText = request.getParameter("message");
		
		JDBCConnector.addMessage(input_userID, input_friendID, messageText);

		response.setStatus(HttpServletResponse.SC_OK);
		String r = "success";
		pw.write(r);

		System.out.println("in serarch servlet post");

		pw.flush();
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
