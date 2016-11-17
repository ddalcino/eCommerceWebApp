/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csueb.cs6320.utils;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;

import edu.csueb.cs6320.bean.User;

/**
 *
 * @author dave
 */

@Service
public class UserService {
	
	@PersistenceUnit(unitName="TestPU")
	private EntityManagerFactory emf;
	
	public List<User> getUserList() {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();

		em.getTransaction().begin();
		List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		em.getTransaction().commit();
				
		return users;
	}
	
	public boolean delete(long userid) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();
		
		User u = em.find(User.class, userid);
		if (u == null) { return false; }
		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();
		
		return true;
	}
	
	/**
	 * doesnt set password!
	 * @param alteredUser
	 */
	public boolean updateUser(long userid, User alteredUser) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();
		User u = em.find(User.class, userid);
		if (u == null) { return false; }
		
		em.getTransaction().begin();
		u.setEmail(alteredUser.getEmail());
		u.setFirstName(alteredUser.getFirstName());
		u.setLastName(alteredUser.getLastName());
		u.setRole(alteredUser.getRole());
		em.getTransaction().commit();
		
		return true;
	}
	
	public boolean updatePassword(long userid, String newPassword) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();
		User u = em.find(User.class, userid);
		if (u == null) { return false; }

		em.getTransaction().begin();

		// TODO: This block of code really belongs in User.setPassword()
    	String salt = Auth.getSalt();
    	u.setSalt(salt);
    	try {
			u.setSalted_hashed_password(Auth.hashPassword(newPassword, salt));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		}
		em.getTransaction().commit();

		return true;
	}
	
	public boolean isValidPassword(String email, String password) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();
		
		
		User u = (User) 
				em.createQuery(
			    "SELECT u FROM User u WHERE u.email = :inEmail")
			    .setParameter("inEmail", email)
			    .setMaxResults(1)
			    .getSingleResult();
		
		if (u == null) { return false; }
		
		try {
			return Auth.isCorrectPassword(password, u.getSalt(), u.getSalted_hashed_password());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * If the email/password combination is valid, returns the user that belongs to it; otherwise returns null
	 * @param email		The user's email
	 * @param password	The user's password
	 * @return			The user, if authentic; otherwise null
	 */
	public User getAuthenticatedUser(String email, String password) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();
		
		
		User u = (User) 
				em.createQuery(
			    "SELECT u FROM User u WHERE u.email = :inEmail")
			    .setParameter("inEmail", email)
			    .setMaxResults(1)
			    .getSingleResult();
		
		if (u == null) { return null; }
		
		try {
			if (Auth.isCorrectPassword(password, u.getSalt(), u.getSalted_hashed_password())) {
				return u;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}


//    private static ArrayList<User> users;
//
//    static {
//        users = new ArrayList<User>();
//        users.add(User.makeUserFromStringParams("Fred", "Thompson", "fred.thompson@email.com", "1", "CUSTOMER"));
//        users.add(User.makeUserFromStringParams("Joe", "Cool", "joe.cool@email.com", "2", "ADMIN"));
//        users.add(User.makeUserFromStringParams("Batman", "Superman", "spiderman@email.com", "3", "SELLER"));
//    }
//
//    private UserService() {
//    }

//    public static ArrayList<User> getUserList() {
//    	//TODO: retrieve from database
//        return new ArrayList<User>(users);
//    }
    
    public boolean createUser(User user, String newPassword) {
    	
    	String salt = Auth.getSalt();
    	user.setSalt(salt);
    	try {
			user.setSalted_hashed_password(Auth.hashPassword(newPassword, salt));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
    	
    	
    	EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();
    	em.getTransaction().begin();
    	em.persist(user);
    	em.getTransaction().commit();
    	return true;
    }

//    public boolean removeUser(long userid) {
//    	
//    	User u = new User();
//    	u.setUserid(userid);
//    	delete(u);
//    	
//    	return true;
//    	
////    	//TODO: DBUtils.deleteUser(userid);
////    	for (User user : users) {
////    		if (user.getUserid() == userid) {
////    			System.out.println("Userid matches " + userid + ", now deleting"); 
////    			users.remove(user);
////    			return true;
////    		} else {
////    			System.out.println("Userid=" + user.getUserid() +" != " + userid + ", fail to delete"); 
////   			
////    		}
////    	}
////        return false;
//    }
//    public static boolean updateUser(long userid, User newData) {
//    	//TODO: cause changes to database
//        int index = 0;
//        for (User user : users) {
//            if (user.getUserid() == userid) {
//                user.setEmail(newData.getEmail());
//                user.setFirstName(newData.getFirstName());
//                user.setLastName(newData.getLastName());
//                user.setRole(newData.getRole());
//                users.set(index, user);
//                Logger.getAnonymousLogger().log(Level.INFO, "updated user: " + user);
//                return true;
//            } else {
//                index++;
//            }
//        }
//        return false;
//    }
}
