package forum;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;

public class ForumServlet extends HttpServlet {

	public static boolean validUser = false;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)

	throws IOException {

		UserService userService = UserServiceFactory.getUserService();

		User user = userService.getCurrentUser();

		if (user != null) {
			validUser = true;
			resp.setContentType("text/plain");

			resp.getWriter().println("Hello, " + user.getNickname());

		} else {
			validUser = false;
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));

		}

	}

}