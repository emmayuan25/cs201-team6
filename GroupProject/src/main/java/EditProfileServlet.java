
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
			System.out.println("In edit servlet");
			//TODO change parameter 
			String imageChange=null;
			String usernameChange = request.getParameter("username");
			imageChange = request.getParameter("image");
			System.out.println("new username: "+ usernameChange);
			System.out.println("new image: " + imageChange);
			//TODO Get interest list change
			String interest = request.getParameter("list");
			if(interest==null) {
				
			}
			String[]  interestChange= interest.split(",");
			
			
			List<String> interestList = Collections.synchronizedList(new ArrayList<>());
		
			
			for(String i: interestChange) {
				interestList.add(i);
			}
			
			System.out.println("Neww interest list: " + interestList);
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			
			user.setUsername(usernameChange);
			if(imageChange==null|| imageChange.equals("")) {
				imageChange="https://www.iconpacks.net/icons/2/free-user-icon-3296-thumb.png";
			}
			user.setProfilePicture(imageChange);
			
			String x="";
        	for(String p: interestList) {
        		p = p.substring(0,1).toUpperCase() + p.substring(1).toLowerCase();
        		x+=p + " ";
        	}
        	//user.setInterestID(x);
		
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

			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");
			System.out.println("Edit profile:"+user.getUsername());
		
			
			String result = gson.toJson(user);
			
			pw.write(result);
			pw.flush();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	
	}


}
