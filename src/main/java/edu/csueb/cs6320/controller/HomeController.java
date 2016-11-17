package edu.csueb.cs6320.controller;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.csueb.cs6320.bean.User;
import edu.csueb.cs6320.utils.Auth;
import edu.csueb.cs6320.utils.DBUtils;
import edu.csueb.cs6320.utils.UrlNames;
import edu.csueb.cs6320.utils.UserService;

/**
 * Handles requests for the application home page.
 * The @Controller annotation turns it into a servlet
 */
@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)	//If you leave method out of the annotation, by default it handles all requests
	public String home(Locale locale, Model model) {	//Model: used to map name-value pairs & pass bw request and response
		// We can add HttpServletRequest to the arg list; Maven will compensate appropriately
		
		return UrlNames.LOGIN_JSP;		// accesses home.jsp
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
		User user = userService.getAuthenticatedUser(email, password);
				
				//Auth.authenticateLogin(email, password);
		// if we retrieved a user object, we are logged in
		if (user != null) {
			//user.setEmail(email);
			user.setRole(User.Roles.ADMIN);
			request.getSession().setAttribute("user", user);
			return "redirect:/admin";
			//loginSuccess=true;
//          response.sendRedirect(UrlNames.LANDING_PAGE);
//          return;
		}
		
		// TODO: input validation, decide login or not
		ArrayList<User> users = (ArrayList<User>) userService.getUserList();
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
            	if (userService.createUser(user, password)) {
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
}
