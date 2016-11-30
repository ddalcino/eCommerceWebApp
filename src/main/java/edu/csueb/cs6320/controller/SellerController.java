package edu.csueb.cs6320.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.csueb.cs6320.bean.User;
import edu.csueb.cs6320.utils.NavbarMaker;
import edu.csueb.cs6320.utils.SaleItemOfferService;
import edu.csueb.cs6320.utils.SaleItemService;
import edu.csueb.cs6320.utils.UrlNames;

@Controller
public class SellerController {
	@Autowired
	private SaleItemService saleItemService;
	
	@Autowired
	private SaleItemOfferService saleItemOfferService;

	@RequestMapping(value = "/sell", method=RequestMethod.GET)
	public String admin(Locale locale, Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null || !user.hasSellerPrivileges()) {
			return "redirect:/";
		} else {
			request.setAttribute("navbarItems", 
					NavbarMaker.getNavbarItems(user, NavbarMaker.Names.SELL));
			return UrlNames.SELL_JSP;
		}
	}
}
