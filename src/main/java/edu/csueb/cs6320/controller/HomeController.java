package edu.csueb.cs6320.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.log.Log;

import edu.csueb.cs6320.bean.User;
import edu.csueb.cs6320.utils.Auth;
import edu.csueb.cs6320.utils.DBUtils;
import edu.csueb.cs6320.utils.UrlNames;
import edu.csueb.cs6320.utils.UserUtils;

/**
 * Handles requests for the application home page.
 * The @Controller annotation turns it into a servlet
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)	//If you leave method out of the annotation, by default it handles all requests
	public String home(Locale locale, Model model) {	//Model: used to map name-value pairs & pass bw request and response
		// We can add HttpServletRequest to the arg list; Maven will compensate appropriately
//		logger.info("Welcome home! The client locale is {}.", locale);
		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
		
		return UrlNames.LOGIN_JSP;		// accesses home.jsp
	}
	

	
	@RequestMapping(value="/admin/user", method=RequestMethod.GET)
	public @ResponseBody User getUser(Locale locale, Model model) {
		System.out.println("Ajax request for a user occurred");
		return User.makeUserFromStringParams("John", "Smith", "jsmith@gmail.com", "5", "ADMIN");
	}
	
	@RequestMapping(value="/admin/deleteUser/{userid}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody boolean deleteUser(@PathVariable long userid, HttpServletRequest request) {
		System.out.println("Ajax request to delete a user with userid="+ userid +" occurred");
		User admin = (User) request.getSession().getAttribute("user");
		if (admin != null && admin.hasAdminPrivileges()) {
		
			//UserUtils.removeUser(userid);
			return UserUtils.removeUser(userid);
			//return new User("John", "Smith", "jsmith@gmail.com", 5l, User.Roles.ADMIN);
		} else {
			System.out.println("Ajax request to delete a user cannot be serviced because user is not an admin.");
			return false;
		}
	}
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/auth/login", method = RequestMethod.POST)
	public String login(Locale locale, Model model, HttpServletRequest request) {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// do some logic to validate credentials
		User user = Auth.authenticateLogin(email, password);
		// if we retrieved a user object, we are logged in
		if (user != null) {
			user.setEmail(email);
			user.setRole(User.Roles.ADMIN);
			request.getSession().setAttribute("user", user);
			return "redirect:/admin";
			//loginSuccess=true;
//          response.sendRedirect(UrlNames.LANDING_PAGE);
//          return;
		}
		
		// TODO: input validation, decide login or not
		ArrayList<User> users = UserUtils.getUserList();
		model.addAttribute("users", users);
		model.addAttribute("user", user);
		return UrlNames.ADMIN_JSP;
	}
	@RequestMapping(value = "/auth/register", method = RequestMethod.POST)
	public String register(Locale locale, Model model, HttpServletRequest request) {
		// TODO: input validation, registration
//        boolean success = false;
        
        String fName = request.getParameter("firstname");
        String lName = request.getParameter("lastname");
        String email = request.getParameter("email1");
        String email2 = request.getParameter("email2");
        String password = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        if(     fName != null && lName != null &&
                email != null && email2 != null &&
                password != null && password2 != null && 
                !email.equals("") && !password.equals("") &&
                email.equals(email2) && password.equals(password2)) {
            if (Auth.isDuplicateEmailAddress(email)) {
                request.setAttribute("statusMsg",
                        "Registration failed; there is already a " +
                        "user with that email address. Did you " +
                        "forget your password? Please use the " +
                        "password recovery feature, as soon as" +
                        "we implement one.");
//                success = false;
            } else {
            	User user = User.makeUserFromNameEmail(fName, lName, email);
            	if (UserUtils.createUser(user, password)) {
	                request.setAttribute("statusMsg",
	                        "Successfully made new user with email=" +
	                        email + "; now try to log in!");
//	                success = true;
            	} else {
            		request.setAttribute("statusMsg",
	                        "Failed to make new user with email=" + email + 
	                        "; please send complaints to /dev/null!");
            	}
            }
            
        } else {
            request.setAttribute("statusMsg", 
                    "Registration failed; emails must match each " +
                    "other, and passwords must match each other.");
//            success = false;
        }
	
		
		return UrlNames.LOGIN_JSP;
	}
	
//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public String register(Locale locale, Model model) {
//		
//		return UrlNames.LOGIN_JSP;
//	}
	
	@RequestMapping(value = "/admin", method=RequestMethod.GET)
	public String admin(Locale locale, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null || !user.hasAdminPrivileges()) {
			return "redirect:/";
		} else {
			model.addAttribute("user", user);
			ArrayList<User> users = UserUtils.getUserList();
			model.addAttribute("users", users);
			return UrlNames.ADMIN_JSP;
		}
	}
	
	/**
	 * Servlet for updating a User. 
	 * Request must contain string values for firstname, lastname, email1, and role, 
	 * as well as a correct long for userid; otherwise, the operation will fail.
	 * If the user recorded in the session does not have the Admin role, the operation
	 * will fail, and the user will be redirected to the login page.
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method=RequestMethod.POST)
	public String updateUser(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Updating a user");
		User user = (User) request.getSession().getAttribute("user");
		if (user == null || !user.hasAdminPrivileges()) {
			return "redirect:/";
		} else {
	        String fName = request.getParameter("firstname");
	        String lName = request.getParameter("lastname");
	        String email = request.getParameter("email1");
	        String struserid = request.getParameter("userid");
	        String role = request.getParameter("role");
	        User newUser = User.makeUserFromStringParams(fName, lName, email, struserid, role);
	        if (newUser != null && newUser.isValid()) {
	        	UserUtils.updateUser(newUser.getUserid(), newUser);
	    		logger.info("Successfully updated user "+ newUser);
	        } else {
	    		logger.info("Failed to update user "+ newUser);
	        }
			return "redirect:/admin";
		}
	}
	
}
