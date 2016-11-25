package edu.csueb.cs6320.utils;

import edu.csueb.cs6320.bean.NavbarItem;
import edu.csueb.cs6320.bean.User;

import java.util.EnumMap;


/**
 *
 * @author dave
 */
public class NavbarMaker {
    public static enum Names {
    	HOME, CART, SELL, ADMIN, SETTINGS
//    	ENUM_NAME
    };
    
    public static final EnumMap<Names,NavbarItem> NAVBAR_ITEM_MAP = 
            new EnumMap<Names, NavbarItem>(Names.class);
    public static final EnumMap<Names,NavbarItem> NAVBAR_HIDDEN_ITEM_MAP = 
            new EnumMap<Names, NavbarItem>(Names.class);
    static {
        NAVBAR_ITEM_MAP.put(Names.HOME, 
                new NavbarItem(UrlNames.ROOT_URL+UrlNames.HOME_JSP, "Home", false));
//        NAVBAR_ITEM_MAP.put(Names.ENUM_NAME, 
//                new NavbarItem("url","Displayed Name", false));
    }
    public static final NavbarItem ADMIN_NAV_ITEM = 
    		new NavbarItem(UrlNames.ROOT_URL+UrlNames.ADMIN_URL, "Admin Utils", false);
    public static final NavbarItem SELL_NAV_ITEM = 
    		new NavbarItem(UrlNames.ROOT_URL+UrlNames.SELL_URL, "Sell New Item", false);

    public static NavbarItem[] getNavbarItems(
//            boolean isUserLoggedIn, 
            User user,
            Names currentPage) {
        NavbarItem[] items = new NavbarItem[NAVBAR_ITEM_MAP.size() + 2];
        int i = 0;
       
        if (user == null) {
            //currentPage = Names.LOGIN;
            //items = new NavbarItem[1];
            //items[0] = new NavbarItem(NAVBAR_HIDDEN_ITEM_MAP.get(Names.LOGIN));
        } else {
            //items = new NavbarItem[NAVBAR_ITEM_MAP.size() + NAVBAR_HIDDEN_ITEM_MAP.size()];
            // Show all the items that show up no matter what when the user is logged in
            for (Names key : NAVBAR_ITEM_MAP.keySet()) {
                    // add the item
                    items[i] = new NavbarItem(NAVBAR_ITEM_MAP.get(key));
                    if (currentPage==key) {
                        items[i].setIsActive(true);
                    }
                    i++;
            }
	        // Show items that should only show up if they are selected as the current page
	        for (Names key : NAVBAR_HIDDEN_ITEM_MAP.keySet()) {
	            if (currentPage==key) {
	                items[i] = new NavbarItem(NAVBAR_HIDDEN_ITEM_MAP.get(key));
	                items[i].setIsActive(true);
	            }
	        }
	        
	        // Show items based on user privileges
	        if (user.hasSellerPrivileges()) { 
	        	items[i++]= new NavbarItem(SELL_NAV_ITEM); 
	        	if (currentPage == Names.SELL) items[i-1].setIsActive(true);
	        }
	        if (user.hasAdminPrivileges()) { 
	        	items[i++] = new NavbarItem(ADMIN_NAV_ITEM); 
	        	if (currentPage == Names.ADMIN) items[i-1].setIsActive(true);
	        	
	        }
        }
        
//        }
        return items;
    }
}
