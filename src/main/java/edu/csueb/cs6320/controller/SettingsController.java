package edu.csueb.cs6320.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.csueb.cs6320.utils.UrlNames;

/**
 * Handles requests for the application settings page.
 * The @Controller annotation turns it into a servlet
 */
@Controller
public class SettingsController {

	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/settings/changeName", method = RequestMethod.GET)	//If you leave method out of the annotation, by default it handles all requests
	public String changeName(Locale locale, Model model) {	//Model: used to map name-value pairs & pass bw request and response
		// We can add HttpServletRequest to the arg list; Maven will compensate appropriately
		
		return UrlNames.SETTINGS_JSP;		// accesses home.jsp
	}
}
