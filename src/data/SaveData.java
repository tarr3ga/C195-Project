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
import models.City;
import models.Country;
import models.Customer;
import models.PhoneNumber;

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
    
    public int saveNewCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        int id = 0;
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("saveNewCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        String sql = "INSERT INTO customers(name, addressId, active, createdBy) " +
                     "VALUES('" + customer.getName() + ", " + customer.getAddressId() + ", '" + 
                     customer.getIsActive() + "', " + customer.getCreatedBy() + ";" ;
        
        statement = conn.createStatement();
        statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
        
        ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
            id = rs.getInt(1);
        }
        
        conn.close();
        
        return id;
    }
    
    public void updateCustomerRecord(Customer customer, Address address) throws SQLException, ClassNotFoundException {
        
        updateCustomer(customer);
        updateAddress(address, customer.getCustomerId());
    }
    
    public int saveNewAddress(Address address) throws SQLException, ClassNotFoundException {
        int id = 0;
        
        try {
            conn = DBConnect.makeConnection();
        } catch (SQLException ex) {
            System.err.println("saveNewAddress");
            System.err.println(ex.toString());
        }
        
        String sql = "INSERT INTO addresses(address, address2,  cityId, createdBy) " +
                     "VALUES('" + address.getAddress()+ "', '" + address.getAddress2()+ "', " + address.getCityId() + 
                     ", " + address.getCreatedBy()  + ");";
        
        statement = conn.createStatement();
        statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
      
         ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
            id = rs.getInt(1);
        }
        
        conn.close();
        
        return id;
    }
    
    public int saveNewAppointment(Appointment appointment) throws SQLException, ClassNotFoundException {
        int id = 0;
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "INSERT INTO appointments(customerId, usersId, title, description, location, contact, type, url, start, end, createdBy) " +
                     "VALUES(" + appointment.getCustomerId() + ", " + 
                     appointment.getUserId() + ", '" +
                     appointment.getTitle() + "', '" +
                     appointment.getDescription() + "', '" +
                     appointment.getLocation() + "', '" +
                     appointment.getContact() + "', '" +
                     appointment.getType() + "', '" +
                     appointment.getUrl() + "', '" +
                     appointment.getStart().toString() + "', " +
                     appointment.getEnd().toString() + 
                     appointment.getCreatedBy() + ");";
        
        statement = conn.createStatement();
        statement.execute(sql, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
            id = rs.getInt(1);
        }

        System.out.println("ID = " + id);
        
        conn.close();
        
        return id;
    }
    
    public int saveNewCity(City city) throws SQLException, ClassNotFoundException {
        int id = 0;
        
        conn = DBConnect.makeConnection();

        String sql = "INSERT INTO city(city, countryId, createdBy) " +
                     "VALUES(" +
                     "  city = '" + "', " +
                     "  countryId =" + ", " +
                     "  createdBy = " +
                     ");";
        
        statement = conn.createStatement();
        statement.execute(sql, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
            id = rs.getInt(1);
        }

        System.out.println("ID = " + id);
        
        conn.close();
        
        return id;
    }
    
    public void updateFullRecord(Appointment appointment, Customer customer, Address address,
            Country country) throws SQLException, ClassNotFoundException {
        
        updateAppointment(appointment);
        updateCustomer(customer);
        updateAddress(address, customer.getAddressId());
    }
    
    public void updateAppointment(Appointment appointment) throws SQLException, ClassNotFoundException{
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("updateFullRecord");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "UPDATE appointments " +
                     "SET userId = " + appointment.getUserId() + ", " + 
                     "    title = '" + appointment.getTitle() + "', " +
                     "    description = '" + appointment.getDescription() + "', " +
                     "    location = '" + appointment.getLocation() + "', " +
                     "    contact = '" + appointment.getContact() + "' " +
                     "    type = '" + appointment.getType() + "', " +
                     "    url'" + appointment.getUrl() + "', '" +
                     "    start'" + appointment.getStart().toString() + "', '" +
                     "    end'" + appointment.getEnd().toString() + "', '" +
                     "    lastUpdate" + appointment.getLastUpdate() + "', '" +
                     "    updatedBy" + appointment.getUpdatedBy() + "', '" +
                     "WHERE ID = '" + appointment.getAppointmentId() + "';";
                                              
        System.out.println("data.SaveData.saveAppointment()" + sql);
        
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchSingleCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "UPDATE customers " +
              "SET firstName = '" + customer.getName() + "', " +
              "    addressId = "  + customer.getAddressId()+ " " +
              "WHERE ID = " + customer.getCustomerId()+ ";";
                                              
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void updateAddress(Address address, int customerId) throws SQLException, ClassNotFoundException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchSingleCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "UPDATE addresses " +
              "SET address = '"  + address.getAddress() + "', " +
              "    address2 = '" + address.getAddress2() + "', " +
              "    cityd = " + address.getCityId() + ", " +                 
              "    lastUpdate = " + address.getLastUpdate() + " " +  
              "    updatedBy = " + address.getUpdatedBy() + " " +    
              "WHERE ID = " + customerId + ";";
                                              
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
}