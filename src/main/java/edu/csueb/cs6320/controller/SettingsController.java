package edu.csueb.cs6320.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.csueb.cs6320.bean.User;
import edu.csueb.cs6320.utils.NavbarMaker;
import edu.csueb.cs6320.utils.UrlNames;
import edu.csueb.cs6320.utils.UserService;

/**
 * Handles requests for the application settings page.
 * The @Controller annotation turns it into a servlet
 */
@Controller
public class SettingsController {
	
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);

	
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String settings(Locale locale, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("navbarItems", 
				NavbarMaker.getNavbarItems(user, NavbarMaker.Names.SETTINGS));
		return UrlNames.SETTINGS_JSP;
	}
	

	/**
	 * Receives an array of two strings, the first and last name. Replaces the 
	 * first and last names of the user with the new names, and stores these 
	 * names to the database.
	 * @return	True if the user was updated, otherwise false.
	 */
	@RequestMapping(value = "/settings/changeName/fullname", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateUserNameAjax(
			@RequestParam(value = "firstname") String firstname, 
			@RequestParam(value = "lastname")  String lastname, 
			HttpServletRequest request) {
		System.out.println("Updating user name to " + firstname + " " + lastname);

		User user = (User) request.getSession().getAttribute("user");
		if (	user != null 		&& user.isUseridValid() &&
				firstname != null	&& !firstname.equals("") &&
				lastname != null	&& !lastname.equals("")) {
			user.setFirstName(firstname);
			user.setLastName(lastname);
			userService.updateUser(user.getUserid(), user);
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/settings/changeEmail/", method = RequestMethod.POST)
	public @ResponseBody boolean updateUserEmailAjax(
			@RequestParam(value = "newEmail") String newEmail, 
			HttpServletRequest request) {
		System.out.println("Updating user email to " + newEmail);
		User user = (User) request.getSession().getAttribute("user");
		if (	user != null 		&& user.isUseridValid() &&
				newEmail != null 	&& !newEmail.equals("")) {
			user.setEmail(newEmail);
			userService.updateUser(user.getUserid(), user);
			System.out.println("Updated user email to " + newEmail);
			return true;
		} else {
			System.out.println("Didn't update user email to " + newEmail);
			return false;
		}
	}

	@RequestMapping(value = "/settings/changePassword/", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateUserPasswordAjax(
			@RequestParam(value = "oldPassword") String oldPassword, 
			@RequestParam(value = "newPassword") String newPassword, 
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null && user.isUseridValid() && user.getEmail() != null &&
				oldPassword != null && !oldPassword.equals("") &&
				newPassword != null && !newPassword.equals("")) {
			// verify the old password
			if (userService.isValidPassword(user.getEmail(), oldPassword)) {
				// set the new password
				userService.updatePassword(user.getUserid(), newPassword);
				return true;
			};
		}
		return false;
	}

	/**
	 * Sets a user's role to SELLER if becomeSeller is true, otherwise sets 
	 * role to CUSTOMER; then it saves the changes to the database.
	 * This call will do nothing if the user is already an admin! 
	 * @param becomeSeller	Boolean: if true, sets user to SELLER, else CUSTOMER
	 * @param request
	 * @return				True if any changes were saved, else false.
	 */
	@RequestMapping(value = "/settings/becomeSeller/", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateUserRoleAjax(
			@RequestParam(value = "setSeller") boolean setSeller, 
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null && user.isUseridValid() && !user.hasAdminPrivileges()) {
			user.setRole(setSeller ? User.Roles.SELLER : User.Roles.CUSTOMER);
			userService.updateUser(user.getUserid(), user);
			return true;
		}
		return false;
	}
	
}
