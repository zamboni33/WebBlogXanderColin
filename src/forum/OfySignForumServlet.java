package forum;

 


import com.google.appengine.api.datastore.Entity;

import com.google.appengine.api.datastore.Key;

import com.google.appengine.api.datastore.KeyFactory;

import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;

import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy; 

import java.io.IOException;

import java.util.Date;

 

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

 

public class OfySignForumServlet extends HttpServlet {
	
	static {
		ObjectifyService.register(Greeting.class);
	}
	
    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException { 

        // We have one entity group per Guestbook with all Greetings residing
        // in the same entity group as the Guestbook to which they belong.
        // This lets us run a transactional ancestor query to retrieve all
        // Greetings for a given Guestbook.  However, the write rate to each
        // Guestbook should be limited to ~1/second.

    	UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

//        String guestbookName = req.getParameter("guestbookName");
//        Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Greeting greeting = new Greeting(user, title, content);
        NewPosts newPost = new NewPosts(user, title, content);
        ofy().save().entity(greeting).now();
        ofy().save().entity(newPost).now();
        resp.sendRedirect("/ofyforum.jsp");
        
        
//        Date date = new Date();
//        Entity greeting = new Entity("Greeting", guestbookKey);
//        greeting.setProperty("user", user);
//        greeting.setProperty("date", date);
//        greeting.setProperty("content", content);

 



 

        resp.sendRedirect("/ofyforum.jsp");

    }

}