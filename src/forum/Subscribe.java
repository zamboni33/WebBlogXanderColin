package forum;

import java.io.IOException;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import static com.googlecode.objectify.ObjectifyService.ofy; 

@Entity
public class Subscribe extends HttpServlet {
	 @Id @GeneratedValue Long id; // still set automatically
	private String emailAddress;
	
	
	void Subscribe(){
//		subscribed = false;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {  
			doToggle(request, response);  
	}
	
	public void doToggle(HttpServletRequest req, HttpServletResponse resp)

            throws IOException { 
	    
//	    System.out.println("Crashing within Toggle?");
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    
	    Subscribe temp = new Subscribe();
	    String emailAddress = user.getEmail();
	    temp.emailAddress = emailAddress;
	    

	  List<Subscribe> subscribers = ObjectifyService.ofy().load().type(Subscribe.class).list();
	  if(subscribers.contains(temp)){
//		  System.out.println("Right before deleting from our table");
		  ofy().delete().type(Subscribe.class).id(temp.getID(temp.emailAddress));
	  } else{
	    ofy().save().entity(temp).now();
	  }
	    resp.sendRedirect("/ofyforum.jsp");
	}
	
	public String getEmailAddress(){
		return this.emailAddress;
	}
	
	public void setEmailAddress(String fuck){
		this.emailAddress = fuck;
	}
	
	@Override
	public boolean equals(Object temp){
		System.out.println("We got into our compare!");
		return (this.emailAddress.equals(((Subscribe) temp).getEmailAddress()));
	}
	
	public Long getID(String emailAddress){
		List<Subscribe> subscribers = ObjectifyService.ofy().load().type(Subscribe.class).list();
		Subscribe temp = new Subscribe();
		temp.emailAddress = emailAddress;
		int location = subscribers.indexOf(temp);
		temp = subscribers.get(location);
		return temp.id;
	}
	


	
}