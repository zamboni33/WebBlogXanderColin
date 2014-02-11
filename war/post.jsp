<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <link type="text/css" rel="stylesheet" href="Forum.css" />
   <title>Make A Post</title>
   
</head>
<body>

<%String guestbookName = request.getParameter("guestbookName"); 
pageContext.setAttribute("guestbookName", guestbookName); %>

<form action="/ofysign" method="post">
      <p> Title: <div><textarea name="title" rows="1" cols="60"></textarea></div> </p>
      <p> Content: <div><textarea name="content" rows="3" cols="60"></textarea></div> </p>
      <div><input type="submit" value="Post" /></div>
      <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
    </form>
    
 <form action="/cancel">
      <div><input type="submit" value="Cancel" /></div>
    </form>


</body>
</html>