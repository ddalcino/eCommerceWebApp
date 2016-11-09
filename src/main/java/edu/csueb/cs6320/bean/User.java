package edu.csueb.cs6320.bean;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.util.HtmlUtils;

public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5489635368639207770L;
	private String firstName;
    private String lastName;
    private String email;
    private long userid;
    private Roles role;
    
    public static long UNINITIALIZED_USER_ID = -1;
    public enum Roles {
        CUSTOMER, SELLER, ADMIN
    }
    private static final EnumMap<Roles,Integer> MAP_ROLES_TO_INTEGERS = 
    		new EnumMap<Roles, Integer>(Roles.class);
    private static final EnumMap<Roles,String> MAP_ROLES_TO_STRINGS = 
    		new EnumMap<Roles, String>(Roles.class);
    static {
    	// initialize MAP_ROLES_TO_INTEGERS:
    	MAP_ROLES_TO_INTEGERS.put(Roles.CUSTOMER, 0);
    	MAP_ROLES_TO_INTEGERS.put(Roles.SELLER, 1);
    	MAP_ROLES_TO_INTEGERS.put(Roles.ADMIN, 2);
    	
    	// initialize MAP_ROLES_TO_STRINGS:
    	MAP_ROLES_TO_STRINGS.put(Roles.CUSTOMER, "CUSTOMER");
    	MAP_ROLES_TO_STRINGS.put(Roles.SELLER, "SELLER");
    	MAP_ROLES_TO_STRINGS.put(Roles.ADMIN, "ADMIN");
    }
    
    public static Roles DEFAULT_ROLE = Roles.CUSTOMER;
    
    /**
     * Returns a Roles enum corresponding to an integer or a string, 
     * depending on what's in the argument
     * @param str	A string that holds either a number ("1") or text 
     * ("CUSTOMER") that corresponds to a particular role, as defined in 
     * MAP_ROLES_TO_INTEGERS or MAP_ROLES_TO_STRINGS above
     * @return	a Roles enum corresponding to input
     */
    public static Roles str2role(String str) {
        if (str != null) {
        	// first try it as a string integer
        	try {
        		int intRole = Integer.parseInt(str);
        		return int2role(intRole);
        	} catch (NumberFormatException e) {
        		// it's not an integer, so it must be text
        		
	         	// Reverse lookup: O(n); would be O(1) with an inverse lookup table
	         	for (Roles key : MAP_ROLES_TO_STRINGS.keySet()) {
	        		if (str.equals(MAP_ROLES_TO_STRINGS.get(key))) {
	        			return key;
	        		}
	        	}
        	}
       	}
        // none of that worked, so fall back on the default
        return DEFAULT_ROLE;
    }

    public static Roles int2role(int intRole) {
		// Reverse lookup: O(n); would be O(1) with an inverse lookup table
	 	for (Roles key : MAP_ROLES_TO_INTEGERS.keySet()) {
			if (intRole == MAP_ROLES_TO_INTEGERS.get(key).intValue()) {
				return key;
			}
		}
	 	return DEFAULT_ROLE;
    }
    public static int role2int(Roles role) {
    	return MAP_ROLES_TO_INTEGERS.get(role);
    }

    public User() {
        this("", "", "", UNINITIALIZED_USER_ID, DEFAULT_ROLE);
    }
    
    public User(String firstName, String lastName, String email, long userid, Roles role) {
        this.role = role;
        this.firstName = HtmlUtils.htmlEscape(firstName);
        this.lastName = HtmlUtils.htmlEscape(lastName);
        this.email = HtmlUtils.htmlEscape(email);
        this.userid = userid;
    }
    
    public static User makeUserFromNameEmail(String firstName, String lastName, String email) {
    	return new User(firstName, lastName, email, UNINITIALIZED_USER_ID, DEFAULT_ROLE);
    }
    
    public static User makeUserFromStringParams (String firstName, String lastName, String email, 
    		String strUserId, String strRole) {
    	long userid = UNINITIALIZED_USER_ID;
    	try {
    		Logger.getAnonymousLogger().log(Level.INFO, "User.makeUserFromStringParams() tried to parse long from uid " + strUserId);
    		userid = Long.parseLong(strUserId);
    		Logger.getAnonymousLogger().log(Level.INFO, "User: Got uid " + userid);
    	} catch (NumberFormatException e) {
    		Logger.getAnonymousLogger().log(Level.INFO, "User: Failed to parse long from uid " + strUserId);
    		return null;
    	}
    	return new User(firstName, lastName, email, userid, str2role(strRole));
    }
        
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = HtmlUtils.htmlEscape(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = HtmlUtils.htmlEscape(lastName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = HtmlUtils.htmlEscape(email);
    }    

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
    
    public boolean isValid() {
    	return 	firstName != null && !firstName.equals("") &&
    			lastName != null && !lastName.equals("") &&
    			email != null && !email.equals("") &&
    			isUseridValid();    			
    }
    /**
     * Convenience function
     * @return
     */
    public boolean isUseridValid() {
    	return userid > UNINITIALIZED_USER_ID;
    }
    
    @Override
    public String toString() {
    	return  firstName + " " +
    			lastName + ", " +
    			email + ", " +
    			role + ", UID=" + userid;
    }
}