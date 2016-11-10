package edu.csueb.cs6320.utils;

import edu.csueb.cs6320.bean.NavbarItem;
import java.util.EnumMap;


/**
 *
 * @author dave
 */
public class NavbarMaker {
    public static enum Names {
    	ENUM_NAME
    };
    
    public static final EnumMap<Names,NavbarItem> NAVBAR_ITEM_MAP = 
            new EnumMap<Names, NavbarItem>(Names.class);
    public static final EnumMap<Names,NavbarItem> NAVBAR_HIDDEN_ITEM_MAP = 
            new EnumMap<Names, NavbarItem>(Names.class);
    static {
        NAVBAR_ITEM_MAP.put(Names.ENUM_NAME, 
                new NavbarItem("url","Displayed Name", false));
    }

    public static NavbarItem[] getNavbarItems(
            boolean isUserLoggedIn, 
            Names currentPage) {
        NavbarItem[] items = new NavbarItem[NAVBAR_ITEM_MAP.size() + 1];
        int i = 0;
       
        if (!isUserLoggedIn) {
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
        }
        // Show items that should only show up if they are selected as the current page
        for (Names key : NAVBAR_HIDDEN_ITEM_MAP.keySet()) {
            if (currentPage==key) {
                items[i] = new NavbarItem(NAVBAR_HIDDEN_ITEM_MAP.get(key));
                items[i].setIsActive(true);
            }
        }
//        }
        return items;
    }
}
