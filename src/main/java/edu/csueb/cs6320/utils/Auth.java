/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csueb.cs6320.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import edu.csueb.cs6320.bean.user.User;

/**
 *
 * @author dave
 */
public class Auth {
    public static boolean createUser(User user) {
        if (isDuplicateEmailAddress(user.getEmail())) {
            return false;
        } else {
            //TODO: create a new user in the database
            //TODO: set user.id to whatever id the database generated for that user
            return true;
        }
    }
    public static boolean isDuplicateEmailAddress(String email) {
        // Query the database: only return true if we have a unique email address
        //TODO: query database
        return false;
    }
    public static User authenticateLogin(String email, String password) {
        //TODO: check database for login credentials: if invalid, return null; 
        //else return a User object
        return User.makeUserFromNameEmail("defaultFname", "defaultLname", "defaultEmail");
    }
    
    
    public static String getSalt() {
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[32];
        r.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    public static String hashPassword(String password, String salt) 
            throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((password + salt).getBytes());

        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
    
    public static boolean isCorrectPassword(String password, String salt, String saltedHashedPassword) 
            throws NoSuchAlgorithmException {
        
        return saltedHashedPassword.equals(
                Auth.hashPassword(password, salt));
    }

}
