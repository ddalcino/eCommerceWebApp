/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csueb.cs6320.utils;

import edu.csueb.cs6320.bean.User;
import java.util.ArrayList;
//import java.util.function.Predicate;

/**
 *
 * @author dave
 */
public class UserUtils {

    private static ArrayList<User> users;

    static {
        users = new ArrayList<User>();
        users.add(new User("Fred", "Thompson", "fred.thompson@email.com", 1, User.Roles.CUSTOMER));
        users.add(new User("Joe", "Cool", "joe.cool@email.com", 2, User.Roles.ADMIN));
        users.add(new User("Batman", "Superman", "spiderman@email.com", 3, User.Roles.SELLER));
    }

    private UserUtils() {
    }

    public static ArrayList<User> getUserList() {
        return new ArrayList<User>(users);
    }
    
    public static boolean createUser(User user, String newPassword) {
    	return DBUtils.createUser(user, newPassword);
    }

    public static boolean removeUser(long userid) {
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
    public static boolean updateUser(String email, User newData) {
        int index = 0;
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                user.setEmail(newData.getEmail());
                user.setFirstName(newData.getFirstName());
                user.setLastName(newData.getLastName());
                user.setRole(newData.getRole());
                users.set(index, user);
                return true;
            } else {
                index++;
            }
        }
        return false;
    }
}
