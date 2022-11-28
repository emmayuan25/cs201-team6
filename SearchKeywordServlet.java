

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class SearchKeywordServlet
 */
@WebServlet("/SearchKeywordServlet")
public class SearchKeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchKeywordServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get parameters for user authentication
		String keyword = request.getParameter("keyword");
		
		PrintWriter out = response.getWriter();
		
		// query to database
		// if there is a user whose username matches the keyword, return a User object that contains information regarding this user
		// note that although password is a data member, the front end should not expose this information to the user who was doing the search
		// if there is no such user, return "error"
		User user = UserMethods.searchKeyword(keyword);
		if(user == null) out.println("error");
		else {
			// convert to JSON String
			// adapted from https://jenkov.com/tutorials/java-json/gson.html (suggested by Professor Papa)
			// reference: Piazza post @146
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			String userInJSON = gson.toJson(user);
			out.println(userInJSON);
		}
	}
}
