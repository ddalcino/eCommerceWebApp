package edu.csueb.cs6320.controller;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.csueb.cs6320.bean.SaleItem;
import edu.csueb.cs6320.bean.SaleItemOffer;
import edu.csueb.cs6320.bean.User;
import edu.csueb.cs6320.utils.NavbarMaker;
import edu.csueb.cs6320.utils.SaleItemOfferService;
import edu.csueb.cs6320.utils.SaleItemService;
import edu.csueb.cs6320.utils.UrlNames;

@Controller
public class ItemController {
	
	@Autowired
	private SaleItemService saleItemService;
	@Autowired
	private SaleItemOfferService saleItemOfferService;
	
	@RequestMapping(value="/item/{itemid}", method=RequestMethod.GET)
	public String item(
			@PathVariable long itemid,
			Locale locale, 
			Model model, 
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("navbarItems", 
				NavbarMaker.getNavbarItems(user, null));
		if (user != null) {
			model.addAttribute("user", user);
		}
		System.out.println("Requesting item #" + itemid);
		
		SaleItem item = saleItemService.getSaleItemWithId(itemid);
		Collection<SaleItemOffer> offers = 
				saleItemOfferService.getOffersBySaleItemId(itemid);
				//item.getOffersForItem();
		if (offers == null) {
			System.out.println("Offers are null!");
		} else {
			System.out.println("Offers has " + offers.size() + " entries");
			for(SaleItemOffer offer : offers) {
				System.out.println(offer);
			}
		}
		model.addAttribute("saleItem", item);
		model.addAttribute("offers", offers);
		return UrlNames.ITEM_JSP;
	}

}
