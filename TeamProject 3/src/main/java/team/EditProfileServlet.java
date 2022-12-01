
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
			
	
	//TODO delete profile
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			
			//TODO change parameter 
			String usernameChange = request.getParameter("username");
			String imageChange = request.getParameter("image");
			
			//TODO Get interest list change
			String[] interestChange=request.getParameterValues("interest");
			List<String> interestList = Collections.synchronizedList(new ArrayList<>());
		
			
			for(String i: interestChange) {
				interestList.add(i);
			}
			
			
			
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			
			user.setUsername(usernameChange);
			user.setProfilePicture(imageChange);
			
			
		
			int id=JDBCConnector.updateProfile(user, interestList);
			
			
			user.setInterestID(id);
			session.setAttribute("user", user);
			
			//TODO send out updated profile 
			
			
		} catch (IOException e) {
			System.out.println("IOException in ProfileServlet: "+ e);
		}
		
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			PrintWriter pw = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);

			String userID = request.getParameter("userID");
			int id = Integer.parseInt(userID);
			
//			User user = JDBCConnector.getUser(id);
			
//			String result = gson.toJson(user);
			
//			pw.write(result);
			pw.flush();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	
	}


}

