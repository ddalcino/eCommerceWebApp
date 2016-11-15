package edu.csueb.cs6320.controller;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.csueb.cs6320.bean.User;
import edu.csueb.cs6320.utils.UrlNames;
import edu.csueb.cs6320.utils.UserUtils;

/**
 * Handles requests for the application Admin page.
 * The @Controller annotation turns it into a servlet
 */
@Controller
public class AdminController {
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
	@RequestMapping(value = "/admin", method=RequestMethod.GET)
	public String admin(Locale locale, Model model, HttpServletRequest request) {
		Logger.getAnonymousLogger().log(Level.INFO, "serving admin page");
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
		//logger.info("Updating a user");
		User user = (User) request.getSession().getAttribute("user");
		if (user == null || !user.hasAdminPrivileges()) {
			return "redirect:/";
		} else {
	        String fName = request.getParameter("firstname");
	        String lName = request.getParameter("lastname");
	        String email = request.getParameter("email");
	        String struserid = request.getParameter("userid");
	        String role = request.getParameter("role");
	        User newUser = User.makeUserFromStringParams(fName, lName, email, struserid, role);
	        if (newUser != null && newUser.isValid()) {
	        	UserUtils.updateUser(newUser.getUserid(), newUser);
	    		//logger.info("Successfully updated user "+ newUser);
	        } else {
	    		//logger.info("Failed to update user "+ newUser);
	        }
			return "redirect:/admin";
		}
	}

	/**
	 * Handles updates to users
	 * @param jsonUser
	 * @return	true if it was able to update a valid user, false if not
	 */
    @RequestMapping(value="/admin/update/jsonUser", method=RequestMethod.POST)
    @ResponseBody
    public boolean updateUserAjax(User jsonUser, HttpServletRequest request) {
		User admin = (User) request.getSession().getAttribute("user");
		if (admin == null || !admin.hasAdminPrivileges()) {
    		Logger.getAnonymousLogger().log(Level.INFO, "User withour admin "+
    				"privileges has attempted to modify a user, but failed!");

			return false;
		} else {
			if (jsonUser != null) {
	    		Logger.getAnonymousLogger().log(Level.INFO, "Hey, I think I received"+
	    				" a JSON user object! This is what I got: " + jsonUser);
	    		if (!jsonUser.isValid()) { return false; }
		        return UserUtils.updateUser(jsonUser.getUserid(), jsonUser); // jsonUser.isValid();
	    	} else {
	    		Logger.getAnonymousLogger().log(Level.INFO, "Received a null JSON user object!");
	    		return false;
	    	}
		}
    }

}
