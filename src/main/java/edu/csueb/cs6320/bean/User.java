package edu.csueb.cs6320.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    
    public static long INVALID_USER_ID = -1;
    public enum Roles {
        CUSTOMER, SELLER, ADMIN
    }
    public static Roles DEFAULT_ROLE = Roles.CUSTOMER;
    public static Roles str2role(String str) {
        if (str == null) {
            return Roles.CUSTOMER;
        } else if (str.equals("CUSTOMER")) {
            return Roles.CUSTOMER;
        } else if (str.equals("SELLER")) {
            return Roles.SELLER;
        } else if (str.equals("ADMIN")) {
            return Roles.ADMIN;
        } else {
            return Roles.CUSTOMER;
        }
    }
    public static Roles int2role(String strInt) {
        int intRole = 0;
        try {
            intRole = Integer.parseInt(strInt);
        } catch (NumberFormatException e) {}
        if (intRole == 0) {
            return Roles.CUSTOMER;
        } else if (intRole == 1) {
            return Roles.SELLER;
        } else if (intRole == 2) {
            return Roles.ADMIN;
        } else {
            return null; // Roles.CUSTOMER;
        }
    }
    public static int role2int(Roles role) {
    	switch (role){
    	case CUSTOMER:	return  0;
    	case SELLER:	return  1;
    	case ADMIN:		return  2;
    	default:		return -1;
    	}
    }

    public User() {
        this("", "", "", INVALID_USER_ID, DEFAULT_ROLE);
    }
    
    public User(String firstName, String lastName, String email, long userid, Roles role) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userid = userid;
    }
    
    public static User makeUserFromNameEmail(String firstName, String lastName, String email) {
    	return new User(firstName, lastName, email, INVALID_USER_ID, DEFAULT_ROLE);
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
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}