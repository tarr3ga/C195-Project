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
    
    public void close() throws SQLException {
        conn.close();
    }
    
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
    
    public void updateCustomerRecord(Customer customer, Address address,
            PhoneNumber phoneNumber) throws SQLException {
        
        updateCustomer(customer);
        updateAddress(address, customer.getId());
        updatePhoneNumber(phoneNumber, customer.getId());
    }
    
    public void saveNewAddress(Address address) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch (SQLException ex) {
            System.err.println("saveNewAddress");
            System.err.println(ex.toString());
        }
        
        String sql = "INSERT INTO addresses(street, city, state, zip, countryId, customersId) " +
                     "VALUES('" + address.getStreet() + "', '" + address.getCity() + "', '" + address.getState() +"', '" +
                     address.getZip() + "', " + address.getCountryId() + ", " + address.getCustomerId() +");";
        
        statement = conn.createStatement();
        
        System.out.println(address.getZip());
        
        try{
            statement.execute(sql);
        } catch(SQLException ex) {
            System.err.println("saveNewAddress");
            System.err.println(ex.toString());
        }
        
        conn.close();
    }
    
    public void saveNewPhone(PhoneNumber phoneNumber) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.err.println("saveNewAddress");
            System.err.println(ex.toString());
        }
        
        String sql = "INSERT INTO phoneNumbers(phone, phoneType, customersId) " +
                     "VALUES('" + phoneNumber.getPhone() + "', '" + phoneNumber.getPhoneType() +"', " + 
                     phoneNumber.getCustomerId() + ");";
        
        statement = conn.createStatement();
        try {
            statement.execute(sql);
        } catch(SQLException ex) {
            System.err.println("saveNewAddress");
            System.err.println(ex.toString());
        }
        
        conn.close();
    }
    
    public void saveNewAppointment(Appointment appointment) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String start = DateTimeUtils.getStorableDateTimeString(appointment.getStart());
        String end = DateTimeUtils.getStorableDateTimeString(appointment.getEnd());
        
        String sql = "INSERT INTO appointments(subject, location, description, start, end, customersId, usersId) " +
                     "VALUES('" + appointment.getSubject() + "', '" + 
                     appointment.getLocation() + "', '" +
                     appointment.getDescription() + "', '" +
                     start + "', '" +
                     end + "', " +
                     appointment.getCustomerId() + ", " +
                     appointment.getUserId() + ");";
        
        System.out.println(sql);
        
        statement = conn.createStatement();
        try {
            statement.execute(sql);
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        conn.close();
    }
    
    public void updateFullRecord(Appointment appointment, Customer customer, Address address,
            Country country, PhoneNumber phoneNumber) throws SQLException {
        
        updateAppointment(appointment);
        updateCustomer(customer);
        updateAddress(address, customer.getId());
        updateCountry(country);
        updatePhoneNumber(phoneNumber, customer.getId());
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
    
    public void updateAddress(Address address, int customerId) throws SQLException {
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
              "WHERE ID = " + customerId + ";";
                                              
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
              "SET countryAbreviation = '" + country.getCountry() + "' " +
              "WHERE ID = " + country.getId() + ";";
                                              
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    } 
    
    public void updatePhoneNumber(PhoneNumber phoneNumber, int customerId) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchSingleCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "UPDATE phoneNumbers " +
              "SET phone = '" + phoneNumber.getPhone() + "', " +
              "    phoneType = '" + phoneNumber.getPhoneType() + "' " +
              "WHERE ID = " + customerId + ";";
                                              
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
}