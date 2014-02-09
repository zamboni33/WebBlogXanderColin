package forum;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Subscribe extends HttpServlet{
	public static boolean subscribed;
	
	
	void Subscribe(){
		subscribed = false;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {  
			doToggle(request, response);  
	}
	
	public void doToggle(HttpServletRequest req, HttpServletResponse resp)

            throws IOException { 
	
		if(subscribed){
			subscribed = false;
		}
		else{
			subscribed = true;
			// append user name to list for chron job
		}
	    resp.sendRedirect("/ofyforum.jsp?guestbookName=");
	}

	
}