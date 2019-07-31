/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import data.DBConnect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jamyers
 */
public class CheckCredentials {
    private Connection conn;
    private Statement statement;
    private ResultSet resultSet;
    private String username;
    private String password;
    
    
    public CheckCredentials(String username, String password) throws ClassNotFoundException {
        System.out.println("c195customertracker.FXMLDocumentController.checkCreds() username = " + username);
        System.out.println("c195customertracker.FXMLDocumentController.checkCreds() password = " + password);
        
        this.username = username;
        this.password = password;
        
        try {
            conn = DBConnect.makeConnection();
            Logger.getLogger(util.CheckCredentials.class.getName()).log(Level.INFO, null, "Connected to DB");
        } catch (SQLException ex) {
            Logger.getLogger(util.CheckCredentials.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean Check() throws SQLException {
        boolean isAuthorized = false;
        
        String sql = "SELECT password FROM users WHERE username = '" + username + "';";
        
        
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        if(ChecDbResults())
            isAuthorized = true;
        
        return isAuthorized;
    }
    
    private boolean ChecDbResults() throws SQLException {
        boolean isAuthorized = false;
        
        if(resultSet.first()) {
            String passwordFromDb = resultSet.getString("password");
            
            if(password.trim().equals(passwordFromDb.trim())) 
                isAuthorized = true;
        }
        
        return isAuthorized;
    }
}
