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

public class SendEmail extends HttpServlet
{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {  
			//sendEmail(request, response);  
			// Remember to pass a list of email receivers
	}
   public static void sendEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException
   {    
      // Recipient's email ID needs to be mentioned.
      String to = "a.balette@gmail.com";

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
         message.setSubject("This is the Subject Line!");

         // Now set the actual message
         message.setText("This is actual message");

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
      
      resp.sendRedirect("/ofyforum.jsp?guestbookName=");
      
   }
}