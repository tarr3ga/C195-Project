/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.ResultSet;
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
    private ResultSet resultSet;
    
    public void deleteAppointment(int appointmentId) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "DELETE FROM appointments WHERE ID = " + appointmentId + ";";
        
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void DeleteCustomer(Customer customer) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql1 = "SET FOREIGN_KEY_CHECKS = 0;";
        String sql2 = "DELETE FROM customers WHERE ID = " + customer.getId() + ";";
        String sql3 = "DELETE FROM addresses WHERE customersId + " + customer.getId() + ";";
        String sql4 = "DELETE FROM pnoheNumbers WHERE customersId + " + customer.getId() + ";";
        String sql5 = "SET FOREIGN_KEY_CHECKS = 1;";
        
        System.out.println(sql2);
        
        statement = conn.createStatement();
        
        statement.execute(sql1);
        statement.execute(sql2);
        statement.execute(sql3);
        statement.execute(sql4);
        statement.execute(sql5);
        
        conn.close();
    }
}
