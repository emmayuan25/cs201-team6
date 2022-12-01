

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class ConvoServlet
 */
@WebServlet("/ConvoServlet")
public class ConvoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConvoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// reference: assignment 4 recommended structure PDF
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		// get user inputs
		String userIDString = request.getParameter("userID");
		int input_userID = Integer.parseInt(userIDString);
		String friendIDString = request.getParameter("friendID");
		int input_friendID = Integer.parseInt(friendIDString);
		
		ChatMessagesList chat_messages_list = JDBCConnector.getMessagesList(input_userID, input_friendID);
		
		Gson gson = new Gson();
		
		// send back result
		response.setStatus(HttpServletResponse.SC_OK);
		String result_json = gson.toJson(chat_messages_list);
//		System.out.println(result_json);
		pw.write(result_json);
		pw.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
