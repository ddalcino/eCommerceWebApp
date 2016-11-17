package edu.csueb.cs6320.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.web.util.HtmlUtils;


@Entity
public class User implements Serializable {
	
	/////////////////////////////////////////////////////////////////
	// STATIC DATA
	private static final long serialVersionUID = -5489635368639207770L;
	
	public enum Roles {
		CUSTOMER, SELLER, ADMIN
	}
	private static final Map<String, Roles> MAP_STRING_TO_ROLE =
			new HashMap<String, Roles>();
	private static final Roles[] MAP_INT_TO_ROLE = 
			{Roles.CUSTOMER, Roles.SELLER, Roles.ADMIN};

	static {
		// initialize MAP_STRING_TO_ROLE:
		MAP_STRING_TO_ROLE.put("CUSTOMER", Roles.CUSTOMER);
		MAP_STRING_TO_ROLE.put("SELLER", Roles.SELLER);
		MAP_STRING_TO_ROLE.put("ADMIN", Roles.ADMIN);
	}
	
	public static Roles DEFAULT_ROLE = Roles.CUSTOMER;
	public static long UNINITIALIZED_USER_ID = -1;
	
	
	/////////////////////////////////////////////////////////////////
	// DATA MEMBERS
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userid;
	private Roles role;
	private String firstName;
	private String lastName;
	private String email;
	private String salt;
	private String saltedHashedPassword;
	
	
	
	/////////////////////////////////////////////////////////////////
	// STATIC METHODS: CONVENIENCE FUNCTIONS
	/**
	 * Returns a Roles enum corresponding to an integer or a string, 
	 * depending on what's in the argument
	 * @param str	A string that holds either a number ("1") or text 
	 * ("CUSTOMER") that corresponds to a particular role, as defined in 
	 * MAP_INT_TO_ROLE or MAP_STRING_TO_ROLE above
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
				if (MAP_STRING_TO_ROLE.containsKey(str)) {
					return MAP_STRING_TO_ROLE.get(str);
				}
			}
	   	}
		// none of that worked, so fall back on the default
		return DEFAULT_ROLE;
	}

	/**
	 * Turns an integer into a Role
	 * @param intRole	integer 0-2
	 * @return			Role
	 */
	public static Roles int2role(int intRole) {
		if (intRole >= 0 && intRole < MAP_INT_TO_ROLE.length){
			return MAP_INT_TO_ROLE[intRole];
		} else {
			return DEFAULT_ROLE;
		}
	}
	
	/**
	 * Turns a role into an integer
	 * @param role	Role
	 * @return		integer 0-2
	 */
	public static int role2int(Roles role) {
		for (int i = 0; i < MAP_INT_TO_ROLE.length; i++) {
			if (role == MAP_INT_TO_ROLE[i]) { return i; }
		}
		// If we reached this point, then either the Roles enum or 
		// the MAP_INT_TO_ROLE array is broken
		assert(false);
		return -1;
	}
	
	
	/////////////////////////////////////////////////////////////////
	// MEMBER METHODS
	
	/**
	 * Default Constructor
	 */
	public User() {
		this("", "", "", UNINITIALIZED_USER_ID, DEFAULT_ROLE);
	}
	
	/**
	 * Constructor with full parameter list
	 * @param firstName	first name
	 * @param lastName	last name
	 * @param email		email address
	 * @param userid	user id number; should be defined by database
	 * @param role		user type
	 */
	private User(String firstName, String lastName, String email, long userid, Roles role) {
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
			Logger.getAnonymousLogger().log(Level.INFO, "Tried to parse long from uid " + strUserId);
			userid = Long.parseLong(strUserId);
			Logger.getAnonymousLogger().log(Level.INFO, "Got uid " + userid);
		} catch (NumberFormatException e) {
			Logger.getAnonymousLogger().log(Level.INFO, "Failed to parse long from uid " + strUserId);
			return null;
		}
		return new User(firstName, lastName, email, userid, str2role(strRole));
	}
	
	/////////////////////////////////////////////////////////////////
	// GETTERS AND SETTERS
		
	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name; html escapes the value to prevent XSS
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = HtmlUtils.htmlEscape(firstName);
	}

	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets last name; html escapes the value to prevent XSS
	 * @param lastName
	 */
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
		Logger.getAnonymousLogger().log(Level.INFO, "Somebody set my role to: " + role);
		this.role = role;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getSalted_hashed_password() {
		return saltedHashedPassword;
	}
	public void setSalted_hashed_password(String salted_hashed_password) {
		this.saltedHashedPassword = salted_hashed_password;
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
	
	public boolean hasAdminPrivileges() {
		return role == Roles.ADMIN;
	}
	
	public boolean hasSellerPrivileges() {
		return role == Roles.SELLER; // || role == Roles.ADMIN;
	}
	
	@Override
	public String toString() {
		return  firstName + " " +
				lastName + ", " +
				email + ", " +
				role + ", UID=" + userid;
	}
}