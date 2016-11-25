package edu.csueb.cs6320.controller;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
//import javax.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.csueb.cs6320.bean.CartItem;
import edu.csueb.cs6320.bean.SaleItemOffer;
import edu.csueb.cs6320.bean.User;
import edu.csueb.cs6320.utils.CartService;
import edu.csueb.cs6320.utils.UrlNames;
import edu.csueb.cs6320.utils.SaleItemOfferService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private SaleItemOfferService saleItemOfferService;
	
	@RequestMapping(value="/cart/", method=RequestMethod.GET)
	public String cart(
			Locale locale, 
			Model model, 
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null && user.isUseridValid()) {
			model.addAttribute("user", user);
			model.addAttribute("cart", cartService.getCartContents(user.getUserid()));
			return UrlNames.CART_JSP;
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/cart/addItem/", method = RequestMethod.POST)
	@ResponseBody
	public CartItem addItemToCartAjax(
			@RequestParam(value = "saleItemOfferID") long saleItemOfferID, 
			@RequestParam(value = "quantity") int quantity, 
			HttpServletRequest request) {
		System.out.println("User trying to buy saleItemOfferID=" + 
			saleItemOfferID + ", qty=" + quantity);
		User user = (User) request.getSession().getAttribute("user");
		SaleItemOffer offer = saleItemOfferService.getOfferById(saleItemOfferID);
		if (user != null && user.isUseridValid()) {
			CartItem item = cartService.addItemToCart(user.getUserid(), 
					offer, quantity);
			List<CartItem> cart = (List<CartItem>) request.getSession()
					.getAttribute("cart");
			cart.add(item);
			System.out.println("User added item with saleItemOfferID=" + 
					saleItemOfferID + ", qty=" + quantity);
//			JsonArray jo = Json.createObjectBuilder()
//					  .add("employees", Json.createArrayBuilder()
//					    .add(Json.createObjectBuilder()
//					      .add("firstName", "John")
//					      .add("lastName", "Doe")))
//					  .build();
			return item;
		}
		return null;
	}

	@RequestMapping(value = "/cart/changeQuantity/", method = RequestMethod.POST)
	@ResponseBody
	public boolean changeQtyCartItemAjax(
			@RequestParam(value = "cartItemID") long cartItemID, 
			@RequestParam(value = "quantity") int quantity, 
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null && user.isUseridValid()) {
			if(cartService.changeQuantity(cartItemID, quantity)){
				// find item in sessionscope cart and change its value accordingly
				List<CartItem> cart = (List<CartItem>) request.getSession()
						.getAttribute("cart");
				for (CartItem item : cart){
					if(item.getId() == cartItemID) {
						if (quantity == 0) {
							cart.remove(item);
						} else {
							item.setQuantity(quantity);
						}
						break;
					}
				}
				// TODO: think about what to do if cartService.changeQuantity() 
				// returns true, but sessionScope.cart can't find the CartItem
				return true;
			}
		}
		return false;
	}
}
