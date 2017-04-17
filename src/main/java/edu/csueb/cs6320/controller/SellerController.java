package edu.csueb.cs6320.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.csueb.cs6320.bean.SaleItemOffer;
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
			request.setAttribute("sellItemOffers", 
					saleItemOfferService.getOffersBySeller(user));
			
			return UrlNames.SELL_JSP;
		}
	}
	
	@RequestMapping(value = "/sell/updateListing/", method = RequestMethod.POST)
	public @ResponseBody boolean updateListingAjax(
			@RequestParam(value = "sellItemOfferId") int sellItemOfferId, 
			@RequestParam(value = "newPrice") double newPrice, 
			@RequestParam(value = "newQty") int newQty, 
			HttpServletRequest request) {
		System.out.println("Updating sellItemOfferId #" + sellItemOfferId +
				" to price=" + newPrice + ", quantity=" + newQty);
		User user = (User) request.getSession().getAttribute("user");
		if (	user != null 		&& user.isUseridValid() && user.hasSellerPrivileges() &&
				//newEmail != null 	&& !newEmail.equals("")) {
				newQty >= 0 && newPrice > 0) {
//			user.setEmail(newEmail);
//			userService.updateUser(user.getUserid(), user);
			SaleItemOffer offer = saleItemOfferService.updateOfferById(sellItemOfferId, newPrice, newQty);
			if (offer != null) {
				System.out.println("Updated sellItemOfferId " + sellItemOfferId);
			return true;
			} else {
				System.out.println("Failure to updated nonexistend sellItemOffer!");
				return false;
			}		
		} else {
			System.out.println("Didn't attempt to update sellItemOfferId " + sellItemOfferId);
			return false;
		}
	}

}
