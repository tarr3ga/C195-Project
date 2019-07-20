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
    
    private static final String CLASS_ID = "DBConnect.java";
    
    private static final String DB_NAME = "mydb";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String USER = "root";
    private static final String PASSWORD = "J0529jam1!";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    //static Driver conn;
    static Connection conn;
    
    public static Connection makeConnection() throws SQLException {
        //Class.forName(DRIVER);
        
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        System.out.println("Connected");
        
        return conn;
    }
    
     public static void closeonnection() throws ClassNotFoundException, SQLException {
         conn.close();
     }
}
