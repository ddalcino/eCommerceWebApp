package edu.csueb.cs6320.utils;

import java.security.NoSuchAlgorithmException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.csueb.cs6320.bean.User;
import edu.csueb.cs6320.bean.User.Roles;

/**
 *
 * @author dave
 */
public class DBUtils {
    private static Connection conn = null;
    private static final String url = "jdbc:mysql://localhost:3306/cs6320";
    private static final String username = "cs6320Project";
    private static final String password = "stringbeanSPRING";
    
    /**
     * Inner class Contract: defines a contract for a table called 'user'
     * and provides static variable names for each column of data in the table
     */
    class Contract {
    	static final String	TABLE_NAME = "user",
    						F_NAME = "firstname",	// String
    						L_NAME = "lastname",	// String
    						EMAIL = "email",		// String
    						ROLE = "role_id",		// int
    						USER_ID = "uid",		// long
    						SALT = "salt",			// 32-char String
    						SALT_HASH_PASS = "salted_hashed_password";	// String

        /** Definitions to help convert Java datatypes to MySQL datatypes */
        private static final String
        		NAME_TYPE = " VARCHAR(40)",
        		LONG_NAME_TYPE = " VARCHAR(80)",
                TEXT_TYPE = " TEXT",
                LONG_TYPE = " BIGINT",
                DOUBLE_TYPE = " REAL",
                BOOLEAN_TYPE = " INTEGER",
        /** Comma separator */
                COMMA_SEP = ",";
        
        /** SQL statement to create the SQL table */
        private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
        		USER_ID + " BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY" + COMMA_SEP +
        		F_NAME + NAME_TYPE + COMMA_SEP +
        		L_NAME + NAME_TYPE + COMMA_SEP +
        		EMAIL + LONG_NAME_TYPE + COMMA_SEP +
        		ROLE + " TINYINT UNSIGNED," +
        		SALT + NAME_TYPE + COMMA_SEP +
        		SALT_HASH_PASS + LONG_NAME_TYPE + " )";
        /** SQL statement to delete the SQL table */
        private static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + Contract.TABLE_NAME;
    }
    public static String getCreateTableStmt() {
    	return Contract.SQL_CREATE_TABLE;
    }
    
    /**
     * Convenience function that makes a user out of a result set
     * @param rs	result set
     * @return		corresponding user object
     * @throws SQLException
     */
    public static User getUserFromResultSet(ResultSet rs) throws SQLException {
    	return User.makeUserFromStringParams(
                rs.getString(Contract.F_NAME),
                rs.getString(Contract.L_NAME),
                rs.getString(Contract.EMAIL),
                rs.getString(Contract.USER_ID),	// user_id is stored as a long
                rs.getString(Contract.ROLE));	// role is stored as an int value (0-2)
    }
    
    /**
     * Creates a fresh SQL connection each time it is called
     * @return
     */
    public static Connection getConnection() {
        //System.out.println("Trying to get connection!");
        try {        
            //if (null == conn) {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            //} 
            System.out.println("Connection established ok!");
        } catch (SQLException ex) {
            //System.err.println(ex.getMessage());
            //ex.printStackTrace();
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            System.err.println("Failed attempt at connection.");
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static ArrayList<User> getUsers() {
        Connection connection = getConnection();
        ArrayList<User> users = new ArrayList<User>();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + Contract.TABLE_NAME);
            
            //User user;
            //rs.
            while(rs.next()) {
            	User user = getUserFromResultSet(rs);	// can throw a SQLException
            	if (user != null) users.add(user);
            	else Logger.getLogger(DBUtils.class.getName()).log(Level.WARNING,
            			"Received invalid user from resultset");
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.last();
                System.out.println("Result size is " + rs.getRow());
                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                
            }
        }
        System.out.println("Done with attempt to retrieve users, with size " + users.size());
        return users;
    }
    
    public static boolean createUser(User newUser, String newPassword) {
        Connection connection = getConnection();
        //ArrayList<User> users = new ArrayList<User>();
        //ResultSet rs = null;
        PreparedStatement stmt = null;
        //int rowCount = 0;
        boolean userCreatedSuccessfully = false;
        try {
            String salt = Auth.getSalt();
            stmt = connection.prepareStatement(
                    "INSERT INTO " + Contract.TABLE_NAME + " (" +
                    Contract.F_NAME + Contract.COMMA_SEP +
                    Contract.L_NAME + Contract.COMMA_SEP +
                    Contract.EMAIL + Contract.COMMA_SEP +
                    Contract.ROLE + Contract.COMMA_SEP +
                    Contract.SALT + Contract.COMMA_SEP +
                    Contract.SALT_HASH_PASS + ") VALUES "+
                    "(?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, newUser.getFirstName());
            stmt.setString(2, newUser.getLastName());
            stmt.setString(3, newUser.getEmail());
            stmt.setInt(4, User.role2int(newUser.getRole()));
            stmt.setString(5, salt);
            stmt.setString(6, Auth.hashPassword(newPassword, salt));
            stmt.execute();
            userCreatedSuccessfully = (stmt.getUpdateCount() > 0);
        } catch (SQLException e) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            
        }
        return userCreatedSuccessfully;
    }
    
    public static User getUserData(String email, String password) {
        
        Connection connection = getConnection();
        ResultSet rs = null;
        User user = null;
        //boolean result = false;
        PreparedStatement stmt = null;
        try {
            
            stmt = connection.prepareStatement(
                    "SELECT * FROM "+ Contract.TABLE_NAME + " WHERE "+ 
                    Contract.EMAIL +" = ?");
            stmt.setString(1, email);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                String salt = rs.getString(Contract.SALT);
                String saltedHashedPassword = rs.getString(Contract.SALT_HASH_PASS);
                
                
                if (Auth.isCorrectPassword(password, salt, saltedHashedPassword)) {
                    user = getUserFromResultSet(rs);
                } else {
                    user=null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            user=null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            user=null;
        } finally {
            try {
                rs.last();
                System.out.println("Result size is " + rs.getRow());
                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        }
        System.out.println("Done with attempt to check password, with result " + user);
        //return users;
        
        
        return user;
    }
    
    public static boolean setUserPassword(long userid, String newPassword) {
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        //int rowCount = 0;
        boolean isPasswordSet = false;
        try {
            String salt = Auth.getSalt();
            stmt = connection.prepareStatement(
                    "UPDATE "+ Contract.TABLE_NAME + 
                    " SET " +
            		Contract.SALT_HASH_PASS +"=?," +
            		Contract.SALT + "=?" +
            		" WHERE " +
            		Contract.USER_ID + "=?");
            stmt.setString(1, Auth.hashPassword(newPassword, salt));
            stmt.setString(2, salt);
            stmt.setLong(3, userid);
            stmt.execute();
            isPasswordSet = (stmt.getUpdateCount() > 0);
        } catch (SQLException e) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            
        }
        return isPasswordSet;
    }
    public static boolean setUserEmail(long userid, String newEmail) {
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        //int rowCount = 0;
        boolean isEmailSet = false;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE "+ Contract.TABLE_NAME + 
                    " SET "+ 
            		Contract.EMAIL + "=?" +
            		" WHERE "+ 
            		Contract.USER_ID + "=?");
            stmt.setString(1, newEmail);
            stmt.setLong(2, userid);
            stmt.execute();
            isEmailSet = (stmt.getUpdateCount() > 0);
        } catch (SQLException e) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();

        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            
        }
        return isEmailSet;
    }
    public static boolean setUserNames(long userid, String newFirstName, String newLastName) {
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        //int rowCount = 0;
        boolean areNamesReset = false;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE "+ Contract.TABLE_NAME + 
                    " SET "+ 
            		Contract.F_NAME + "=?, "+ 
            		Contract.L_NAME + "=? WHERE "+ 
            		Contract.USER_ID + "=?");
            stmt.setString(1, newFirstName);
            stmt.setString(2, newLastName);
            stmt.setLong(3, userid);
            stmt.execute();
            areNamesReset = (stmt.getUpdateCount() > 0);
        } catch (SQLException e) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();

        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            
        }
        return areNamesReset;
    }
}
