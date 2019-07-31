/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.mysql.cj.log.Log;
import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jamyers
 */
public class DBConnect {
    private static final String DB_NAME = "U03pYa";
    private static final String DB_URL = "jdbc:mysql//52.206.157.109/" + DB_NAME;
    private static final String USER = "U03pYa";
    private static final String PASSWORD = "53688049291";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    //static Driver conn;
    static Connection conn;
    
    public static Connection makeConnection() throws SQLException, ClassNotFoundException {
        /*Class.forName(DRIVER);
        
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        System.out.println("Connected");
        
        return conn;*/
        Connection conn = null;
      
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to database : " + DB_NAME);
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        
        return conn;
    }
    
     public static void closeonnection() throws ClassNotFoundException, SQLException {
         conn.close();
     }
}
