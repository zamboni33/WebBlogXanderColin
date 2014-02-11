<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.googlecode.objectify.Objectify" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="forum.Subscribe" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="forum.Greeting"%>
<%@ page import="forum.NewPosts"%>


<html>

<head>
   <link type="text/css" rel="stylesheet" href="Forum.css" />
   <title>Starter Page for Web Blog</title>
   
<script type="text/Javascript"> 

function expandcollapse (postid) { 

   whichpost = document.getElementById(postid); 
   
   if (whichpost.className=="postshown") { 
      whichpost.className="posthidden"; 
   } 
   else { 
      whichpost.className="postshown"; 
   } 
} 
</script>
   
</head>

 <body>


	
<div class="banner">
    <div class="wrapper">
        <h1>Creepy Guy Forums</h1>
    </div>
</div>

	<p> This is a starter forum concerning creepy men. You can post after signing in with a google ID. </p>	


	
<%

    String guestbookName = request.getParameter("guestbookName");

    if (guestbookName == null) {
        guestbookName = "default";
    }

    pageContext.setAttribute("guestbookName", guestbookName);
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    if (user != null) {
      pageContext.setAttribute("user", user);
%>
<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>

<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to include your name with greetings you post.</p>
<%
   }
%>

<%

ObjectifyService.register(Greeting.class);
ObjectifyService.register(NewPosts.class);
ObjectifyService.register(Subscribe.class);

List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();
List<NewPosts> newPosts = ObjectifyService.ofy().load().type(NewPosts.class).list();
List<Subscribe> subscribers = ObjectifyService.ofy().load().type(Subscribe.class).list();


Collections.sort(greetings); 
  
  

    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.

    if (greetings.isEmpty()) {
        %>
        <p>Guestbook '${fn:escapeXml(guestbookName)}' has no messages.</p>
        <%
    } else {
        %>
        <p>Messages in Guestbook '${fn:escapeXml(guestbookName)}'.</p>
        <%
        for (Greeting greeting : greetings) {
            pageContext.setAttribute("greeting_title",
                                     greeting.getTitle());
            pageContext.setAttribute("greeting_content",
                                     greeting.getContent());
            pageContext.setAttribute("greeting_date",
                    greeting.getDate());

            
            if (greeting.getUser() == null) {
                %>
                <p>A very stupid person wrote:</p>
                <%
            } else {
                pageContext.setAttribute("greeting_user",
                                         greeting.getUser());

                %>
                <p><b>${fn:escapeXml(greeting_user.nickname)}</b> wrote:</p>
                <%
            }
            %>
            <blockquote style=font-family:verdana; font-size:32px>${fn:escapeXml(greeting_title)}</blockquote>
            <blockquote>${fn:escapeXml(greeting_content)}</blockquote>
            <blockquote>${fn:escapeXml(greeting_date)}</blockquote>
            <%
        }
    }

%>





 <% if(user != null){ %>

	<a href="post.jsp">Create Post</a>

    
    <%
    Subscribe dummy = new Subscribe();
    dummy.setEmailAddress(user.getEmail());
     if(subscribers.contains(dummy) ) { %>
	    <form action="/subscribe" method="toggle">
	      <div><input type="submit" value="Unsubscribe" /></div>
	    </form>
	    <%}else { %>
	    <form action="/subscribe" method="toggle">
      		<div><input type="submit" value="Subscribe" /></div>
    	</form>    
    	<%  } 
    } %>
 
 

  </body>

</html>