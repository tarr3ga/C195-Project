/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import models.Customer;

/**
 *
 * @author jamyers
 */
public class DeleteData {
    private Connection conn;
    private Statement statement;
    
    public void deleteAppointment(int appointmentId) throws SQLException, ClassNotFoundException {
        conn = DBConnect.makeConnection();
   
        String sql = "DELETE FROM appointment WHERE appointmentId = " + appointmentId + ";";
        
        try {
        statement = conn.createStatement();
        statement.execute(sql);
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        conn.close();
    }
    
    public void DeleteCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql1 = "SET FOREIGN_KEY_CHECKS = 0;";
        String sql2 = "DELETE FROM customer WHERE customerId = " + customer.getCustomerId() + ";";
        String sql3 = "DELETE FROM address WHERE addressId = " + customer.getAddressId() + ";";
        String sql4 = "SET FOREIGN_KEY_CHECKS = 1;";
        
        System.out.println(sql2);
        
        statement = conn.createStatement();
        
        statement.execute(sql1);
        statement.execute(sql2);
        statement.execute(sql3);
        statement.execute(sql4);
        
        conn.close();
    }
}