// File Name SendEmail.java

package forum;

import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.activation.*;

import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy; 

public class SendEmail extends HttpServlet
{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {  
		
		// Fetching list of subscribers and sending an email to each one.
		List<Subscribe> subscribers = ObjectifyService.ofy().load().type(Subscribe.class).list();
		List<NewPosts> newPosts = ObjectifyService.ofy().load().type(NewPosts.class).list();
		
		StringBuilder emailText = new StringBuilder();
		
		if(!newPosts.isEmpty()){
			for(NewPosts newPost : newPosts) {
				emailText.append("User: " + newPost.getUser() + " posted:\n" 
										+ "Title: " + newPost.getTitle() + "\n"
										+ "Content: " + newPost.getContent() + "\n" 
										+ "Date: " + newPost.getDate() + "\n\n\n");
				
				ofy().delete().type(NewPosts.class).id(newPost.getID());
			}
			
			String text = new String(emailText);
			
			for(Subscribe subscriber : subscribers){	
				sendEmail(request, response, subscriber.getEmailAddress(), text);
			}
		
			newPosts.clear();
		} else{
			response.sendRedirect("/ofyforum.jsp");
		}
		
		
	}
   public static void sendEmail(HttpServletRequest req, 
		   							HttpServletResponse resp, 
		   							String emailAddress, String text) throws IOException
   {    
		 
	   // Recipient's email ID needs to be mentioned.
      String to = emailAddress;

      // Sender's email ID needs to be mentioned
      String from = "admin@webblogxandercolin.appspotmail.com";

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("Creepy Guy Blog Activity");

         // Now set the actual message
         message.setText(text);

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
      
      resp.sendRedirect("/ofyforum.jsp");
      
   }
}