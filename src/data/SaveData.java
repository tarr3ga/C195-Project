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
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Address;
import models.Appointment;
import models.Country;
import models.Customer;
import models.PhoneNumber;
import util.DateTimeUtils;

/**
 *
 * @author jamyers
 */
public class SaveData {
    
    private Connection conn;
    private Statement statement;
    
    public int saveNewCustomer(Customer customer) throws SQLException {
        int id = 0;
        
         try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("saveNewCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        String sql = "INSERT INTO customers(firstName, lastName) " +
                     "VALUES('" + customer.getFirstName() +"', '" + customer.getLastName() + "');";
        
        statement = conn.createStatement();
        statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
        
        ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
            id = rs.getInt(1);
        }
        
        conn.close();
        
        return id;
    }
    
    public void saveNewAddress(Address address) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch (SQLException ex) {
            System.out.println("saveNewAddress");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "INSERT INTO addresses(street, city, state, zip, countryId, customerId) " +
                     "VALUES('" + address.getStreet() + "', '" + address.getCity() + "', '" + address.getState() +"', '" +
                     address.getZip() + "', " + address.getCountryId() + ", " + address.getCustomerId() +");";
        
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void saveNewPhone(PhoneNumber phoneNumber) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("saveNewPhone");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "INSERT INTO phoneNumbers(phone, phoneType, customerId) " +
                     "VALUES('" + phoneNumber.getPhone() + "', '" + phoneNumber.getPhoneType() +"', " + 
                     phoneNumber.getCustomerId() + ");";
        
        statement = conn.createStatement();
        
    }
    
    public void updateFullRecord(Appointment appointment, Customer customer, Address address,
            Country country, PhoneNumber phoneNumber) throws SQLException {
        
        updateAppointment(appointment);
        updateCustomer(customer);
        updateAddress(address);
        updateCountry(country);
        updatePhoneNumber(phoneNumber);
    }
    
    public void updateAppointment(Appointment appointment) throws SQLException{
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchSingleCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String start = DateTimeUtils.getStorableDateTimeString(appointment.getStart());
        String end = DateTimeUtils.getStorableDateTimeString(appointment.getEnd());
        
        String sql = "UPDATE appointments " +
                     "SET subject = '" + appointment.getSubject() + "', " + 
                     "    location = '" + appointment.getLocation() + "', " +
                     "    description = '" + appointment.getDescription() + "', " +
                     "    start = '" + start + "', " +
                     "    end = '" + end + "' " +
                     "WHERE ID = " + appointment.getId() + ";";
                                              
        System.out.println("data.SaveData.saveAppointment()" + sql);
        
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void updateCustomer(Customer customer) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchSingleCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "UPDATE customers " +
              "SET firstName = '" + customer.getFirstName() + "', " +
              "    lastName = '"  + customer.getLastName() + "' " +
              "WHERE ID = " + customer.getId() + ";";
                                              
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void updateAddress(Address address) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchSingleCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "UPDATE addresses " +
              "SET street = '"  + address.getStreet() + "', " +
              "    city = '" + address.getCity() + "', " +
              "    state = '" + address.getState() + "', " +                 
              "    zip = '" + address.getZip() + "' " +                   
              "WHERE ID = " + address.getId() + ";";
                                              
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void updateCountry(Country country) throws SQLException{
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchSingleCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "UPDATE countries " + 
              "SET country = '" + country.getCountry() + "' " +
              "WHERE ID = " + country.getId() + ";";
                                              
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    } 
    
    public void updatePhoneNumber(PhoneNumber phoneNumber) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchSingleCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "UPDATE phoneNumbers " +
              "SET phone = '" + phoneNumber.getPhone() + "', " +
              "    phoneType = '" + phoneNumber.getPhoneType() + "' " +
              "WHERE ID = " + phoneNumber.getId() + ";";
                                              
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
}