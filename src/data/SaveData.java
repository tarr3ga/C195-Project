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
import models.Address;
import models.Appointment;
import models.City;
import models.Country;
import models.Customer;

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
        
        conn = DBConnect.makeConnection();
  
        int isActive = 0;
        
        if(customer.getIsActive()) {
            isActive = 1;
        }
        
        String sql = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                     "VALUES('" + customer.getName() + "', " + customer.getAddressId() + ", " + 
                     isActive + ", '" + customer.getCreateDate() + "', " + customer.getCreatedBy() + ", '" +
                     customer.getCreateDate() + "', " + customer.getUpdatedBy() + ");";
        
        System.out.println("data.SaveData.saveNewCustomer() sql = " + sql);
        
        try {
            statement = conn.createStatement();
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
            id = rs.getInt(1);
        }
        
        conn.close();
        
        return id;
    }
    
    public void updateCustomerRecord(Customer customer, City city, Address address) throws SQLException, ClassNotFoundException {       
        updateCustomer(customer);
        updateAddress(address);
        updateCity(city);
    }
    
    public int saveNewAddress(Address address) throws SQLException, ClassNotFoundException {
        int id = 0;
        
        conn = DBConnect.makeConnection();

        String sql = "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, " +
                     "createdBy, lastUpdate, lastUpdateBy) " +
                     "VALUES('" + address.getAddress()+ "', '" + address.getAddress2() + "', " + address.getCityId() + ", '" +
                     address.getPostalCode() + "', '" + address.getPhone() + "', '" + address.getCreateDate() + "', " + 
                     address.getCreatedBy() + ", '" + address.getLastUpdate() + "', " + address.getUpdatedBy() + ");";
        
        System.out.println("data.SaveData.saveNewAddess() sql = " + sql);
        
        try {
            statement = conn.createStatement();
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
            id = rs.getInt(1);
        }
        
        conn.close();
        
        return id;
    }
    
    public int saveNewCity(City city) throws SQLException, ClassNotFoundException {
        int id = 0;
        
        conn = DBConnect.makeConnection();

        
        String sql = "INSERT INTO city(city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                     "VALUES('" +
                     city.getCity() + "', " +
                     city.getCountryId() + ", '" +
                     city.getCreateDate() + "', " +
                     city.getCreatedBy() + ", '" +
                     city.getLastUpdate() + "', " +
                     city.getCreatedBy() +
                     ");";
        
        System.out.println("data.SaveData.saveNewCity() sql = " + sql);
        
        try {
            statement = conn.createStatement();
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        
        ResultSet rs = statement.getGeneratedKeys();
        
        if(rs.next()) {
            id = rs.getInt(1);
        }

        System.out.println("ID = " + id);
        
        conn.close();
        
        return id;
    }
    
    public int saveNewAppointment(Appointment appointment) throws SQLException, ClassNotFoundException {
        int id = 0;
        
        conn = DBConnect.makeConnection();
        
        String sql = "INSERT INTO appointment(customerId, userId, title, description, " +
                     "location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                     "VALUES(" + appointment.getCustomerId() + ", " + 
                     appointment.getUserId() + ", '" +
                     appointment.getTitle() + "', '" +
                     appointment.getDescription() + "', '" +
                     appointment.getLocation() + "', '" +
                     appointment.getContact() + "', '" +
                     appointment.getType() + "', '" +
                     appointment.getUrl() + "', '" +
                     appointment.getStart().toString() + "', '" +
                     appointment.getEnd().toString() + "', '" +
                     appointment.getCreateDate() + "', " +
                     appointment.getCreatedBy() + ", '" +
                     appointment.getLastUpdate() + "', " +
                     appointment.getUpdatedBy() + ");";
        
        System.out.println("data.SaveData.saveNewAppointment() sql = " + sql);
        
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
    
    public void updateFullRecord(Appointment appointment, Customer customer, City city, Address address,
            Country country) throws SQLException, ClassNotFoundException { 
        updateAppointment(appointment);
        updateCustomer(customer);
        updateCity(city);
        updateAddress(address);
    }
    
    public void updateAppointment(Appointment appointment) throws SQLException, ClassNotFoundException{
        conn = DBConnect.makeConnection();

        
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
                     "WHERE appointmentId = '" + appointment.getAppointmentId() + "';";
                                              
        System.out.println("data.SaveData.saveAppointment()" + sql);
        
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        conn = DBConnect.makeConnection();

        
        String sql = "UPDATE customer " +
              "SET customerName = '" + customer.getName() + "', " +
              "    createDate = '"  + customer.getCreateDate() + "', " +
              "    createdBy = "  + customer.getCreatedBy() + ", " +
              "    lastUpdate = '"  + customer.getLastUpdate() + "', " +
              "    lastUpdateBy = "  + customer.getUpdatedBy() + " " +
              "WHERE customerId = " + customer.getCustomerId()+ ";";
                        
        System.out.println("data.SaveData.updateCustomer() sql = " + sql);
        
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void updateCity(City city) throws SQLException, ClassNotFoundException {
        conn = DBConnect.makeConnection();
        
        String sql = "UPDATE city " +
                "SET city = '" + city.getCity() + "', " +
                "    createDate = '"  + city.getCreateDate() + "', " +
                "    createdBy = "  + city.getCreatedBy() + ", " +
                "    lastUpdate = '" + city.getLastUpdate() + "', " +  
                "    lastUpdateBy = " + city.getUpdatedBy() + " " +    
                "WHERE cityId = " + city.getCityId() + ";";
        
        System.out.println("data.SaveData.updateCity() sql = " + sql);
        
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
    
    public void updateAddress(Address address) throws SQLException, ClassNotFoundException {
        conn = DBConnect.makeConnection();
 
        String sql = "UPDATE address " +
              "SET address = '"  + address.getAddress() + "', " +
              "    address2 = '" + address.getAddress2() + "', " +      
              "    postalCode = '" + address.getPostalCode() + "', " +      
              "    phone = '" + address.getPhone() + "', " +      
              "    createDate = '"  + address.getCreateDate() + "', " +
              "    createdBy = "  + address.getCreatedBy() + ", " +
              "    lastUpdate = '" + address.getLastUpdate() + "', " +  
              "    lastUpdateBy = " + address.getUpdatedBy() + " " +    
              "WHERE addressId = " + address.getAddressId() + ";";
                        
        System.out.println("data.SaveData.updateAddress() sql = " + sql);
        
        statement = conn.createStatement();
        statement.execute(sql);
        
        conn.close();
    }
}