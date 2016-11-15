/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csueb.cs6320.utils;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.function.Predicate;

import edu.csueb.cs6320.bean.User;

/**
 *
 * @author dave
 */
public class UserUtils {

    private static ArrayList<User> users;

    static {
        users = new ArrayList<User>();
        users.add(User.makeUserFromStringParams("Fred", "Thompson", "fred.thompson@email.com", "1", "CUSTOMER"));
        users.add(User.makeUserFromStringParams("Joe", "Cool", "joe.cool@email.com", "2", "ADMIN"));
        users.add(User.makeUserFromStringParams("Batman", "Superman", "spiderman@email.com", "3", "SELLER"));
    }

    private UserUtils() {
    }

    public static ArrayList<User> getUserList() {
        return new ArrayList<User>(users);
    }
    
    public static boolean createUser(User user, String newPassword) {
    	return users.add(user);
    	//return DBUtils.createUser(user, newPassword);
    }

    public static boolean removeUser(long userid) {
    	//DBUtils.deleteUser(userid);
    	for (User user : users) {
    		if (user.getUserid() == userid) {
    			System.out.println("Userid matches " + userid + ", now deleting"); 
    			users.remove(user);
    			return true;
    		} else {
    			System.out.println("Userid=" + user.getUserid() +" != " + userid + ", fail to delete"); 
   			
    		}
    	}
        return false;
    }
    public static boolean updateUser(long userid, User newData) {
        int index = 0;
        for (User user : users) {
            if (user.getUserid() == userid) {
                user.setEmail(newData.getEmail());
                user.setFirstName(newData.getFirstName());
                user.setLastName(newData.getLastName());
                user.setRole(newData.getRole());
                users.set(index, user);
                Logger.getAnonymousLogger().log(Level.INFO, "updated user: " + user);
                return true;
            } else {
                index++;
            }
        }
        return false;
    }
}
