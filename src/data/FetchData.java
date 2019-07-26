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
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Address;
import models.Appointment;
import models.Country;
import models.Customer;
import models.PhoneNumber;

/**
 *
 * @author jamyers
 */
public class FetchData {
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();
    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private final ObservableList<Address> addressess = FXCollections.observableArrayList();
    private final ObservableList<PhoneNumber> phoneNumber = FXCollections.observableArrayList();
    
    private static final String SQL_CUSTOMER = "SELECT * FROM customers WHERE ID = ";
    private static final String SQL_CUSTOMERS = "SELECT * FROM customers;";
    private static final String SQL_APPOINTMENT = "SELECT * FROM appointments WHERE ID = ";
    private static final String SQL_APPOINTMENTS = "SELECT * FROM appointments;";
    private static final String SQL_ADDRESS = "SELECT * FROM addresses WHERE customersId = ";
    private static final String SQL_ADDRESSES = "SELECT * FROM addresses";
    private static final String SQL_PHONENUMBER = "SELECT * FROM phoneNumbers WHERE customersId = ";
    private static final String SQL_PHONENUMBERS = "SELECT * FROM phoneNumbers";
    private static final String SQL_COUNTRY = "SELECT * FROM countries WHERE ID = ";
    private static final String SQL_COUNTRIES = "SELECT * FROM countries";
    private static final String SQL_CUSTOMER_SPECIFIC_APPOINTMENTS = "SELECT * FROM appointments WHERE customersId = ";
    
    
    private Connection conn;
    private Statement statement;
    private ResultSet resultSet;
    
    // <editor-fold defaultstate="collapsed" desc=" TEST CODE ">
    public ObservableList fetchData(String type) {
        switch(type){
            case "Customer":
                
                break;
        }
        
        switch(type){
            case "Customer":
                return customers;
            default:
                return null;
        }
    }
    
    private String[] getProperties() {
        String[] properties = {};
        
        
        
        return properties;
    }
    // </editor-fold>
    
    public Customer fetchSingleCustomer(int customerId) throws SQLException {
        Customer c = new Customer();
        
         try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchSingleCustomer");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_CUSTOMER + customerId + ";");
        
        int index;
        
        resultSet.first();
        
        index = resultSet.findColumn("ID");
        c.setId(resultSet.getInt(index));
        
        index = resultSet.findColumn("firstName");
        c.setFirstName(resultSet.getString(index));
        
        index = resultSet.findColumn("lastName");
        c.setLastName(resultSet.getString(index));
        
        return c;
    }
    
    public ObservableList fetchCustomerData() throws SQLException {
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchCustomerData");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_CUSTOMERS);
        
        int index;
        
        while(resultSet.next()) {
            index = resultSet.findColumn("ID");
            int id = resultSet.getInt(index);
            
            index = resultSet.findColumn("firstName");
            String firstName = resultSet.getString(index);
            
            index = resultSet.findColumn("lastName");
            String lastName = resultSet.getString(index);
            
            index = resultSet.findColumn("addedOn");
            Timestamp addedOn = (Timestamp)resultSet.getObject(index);
            
            Customer c = new Customer();
            c.setId(id);
            c.setFirstName(firstName);
            c.setLastName(lastName);
            c.setAddedOn(addedOn);
            
            customers.add(c);
        }
        
        conn.close();
        
        return customers;
    }
    
    public ObservableList fetchAddresses() throws SQLException {
        
         try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchAddresses");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_ADDRESSES);
        
        int index;
        
        while(resultSet.next()) {
            index = resultSet.findColumn("ID");
            int id = resultSet.getInt(index);
            
            index = resultSet.findColumn("street");
            String street = resultSet.getString(index);
            
            index = resultSet.findColumn("city");
            String city = resultSet.getString(index);
            
            index = resultSet.findColumn("state");
            String state = resultSet.getString(index);
            
            index = resultSet.findColumn("zip");
            String zip = resultSet.getString(index);
            
            index = resultSet.findColumn("countryId");
            int countryId = resultSet.getInt(index);
            
            index = resultSet.findColumn("customersId");
            int customersId = resultSet.getInt(index);
            
            Address a = new Address();
            a.setId(id);
            a.setStreet(street);
            a.setCity(city);
            a.setState(state);
            a.setZip(zip);
            a.setCountryId(countryId);
            a.setCustomerId(customersId);
            
            addressess.add(a);
        }
        
        conn.close();
        
        return addressess;
    }
    
    public Address fetchAddress(int id) throws SQLException {
        
        Address a = new Address();
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchAddress");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_ADDRESS + id + ";");
        
        int index;
        
        resultSet.next();
        
        index = resultSet.findColumn("street");
        a.setStreet(resultSet.getString(index));

        index = resultSet.findColumn("city");
        a.setCity(resultSet.getString(index));

        index = resultSet.findColumn("state");
        a.setState(resultSet.getString(index));           

        index = resultSet.findColumn("ZIP");
        a.setZip(resultSet.getString(index));
        
        index = resultSet.findColumn("countryId");
        a.setCountryId(resultSet.getInt(index));
        
        conn.close();
        
        return a;
    }
    
    public PhoneNumber fetchPhoneNumber(int id) throws SQLException {
        PhoneNumber p = new PhoneNumber();
        
         try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("ffetchPhoneNumber");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_PHONENUMBER + id + ";");
        
        int index;
        
        resultSet.next();
        
        index = resultSet.findColumn("phone");
        p.setPhone(resultSet.getString(index));

        index = resultSet.findColumn("phoneType");
        p.setPhoneType(resultSet.getString(index));
          
        conn.close();
        
        return p;
    }
    
    public Country fetchCountry(int countryId) throws SQLException {
        
        Country c = new Country();
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchCountry");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_COUNTRY + countryId + ";");
              
        int index;
        
        resultSet.next();
        
        index = resultSet.findColumn("countryAbreviation");
        c.setCountryAbreviation(resultSet.getString(index));
        
        index = resultSet.findColumn("countryName");
        c.setCountry(resultSet.getString(index));
              
        conn.close();
        
        return c;
    }
    
    public ArrayList<Country> fetchCountries() throws SQLException {
        
        ArrayList<Country> countries = new ArrayList<>();
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchCountry");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_COUNTRIES);
              
        int index;
        
        while(resultSet.next()) {
            index = resultSet.findColumn("countryAbreviation");
            String countryAbreviation = resultSet.getString(index);
        
            index = resultSet.findColumn("countryName");
            String country = resultSet.getString(index);
            
            Country c = new Country();
            c.setCountryAbreviation(countryAbreviation);
            c.setCountry(country);
            
            countries.add(c);
        }
           
        conn.close();
        
        return countries;
    }
    
    public ObservableList fetchAppointmentsForCustomerData(int userId) throws SQLException {
        
         try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchAppointmentsForCustomerData");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_CUSTOMER_SPECIFIC_APPOINTMENTS + userId + ";");
        
        int index;
        
        while(resultSet.next()) {
            index = resultSet.findColumn("ID");
            int id = resultSet.getInt(index);
            
            index = resultSet.findColumn("subject");
            String subject = resultSet.getString(index);
            
            index = resultSet.findColumn("location");
            String location = resultSet.getString(index);
            
            index = resultSet.findColumn("description");
            String description = resultSet.getString(index);
            
            index = resultSet.findColumn("start");
            String start = resultSet.getString(index);
                    
            index = resultSet.findColumn("end");
            String end = resultSet.getString(index);
            
            index = resultSet.findColumn("customersId");
            int customersId = resultSet.getInt(index);
            
            ZonedDateTime startDateTime = ZonedDateTime.parse(start);
            ZonedDateTime endDateTime = ZonedDateTime.parse(end);
            
            /*try {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                startDateTime = ZonedDateTime.parse(start, formatter);
                endDateTime = ZonedDateTime.parse(end, formatter);
            } catch(DateTimeParseException ex) {
                System.err.println("fetchAppointmentsForCustomerData");
                System.err.println(ex.toString());
            }*/
            
            Appointment a = new Appointment();
            a.setId(id);
            a.setSubject(subject);
            a.setLocation(location);
            a.setDescription(description);
            a.setStart(startDateTime);
            a.setEnd(endDateTime);
            a.setCustomerId(customersId);
            
            appointments.add(a);
        }
        
        conn.close();
        
        return appointments;
    }
    
    public ObservableList fetchAppointmentData() throws SQLException {
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchAppointmentData");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_APPOINTMENTS);
        
        int index;
        
        while(resultSet.next()) {
            index = resultSet.findColumn("ID");
            int id = resultSet.getInt(index);
            
            index = resultSet.findColumn("subject");
            String subject = resultSet.getString(index);
            
            index = resultSet.findColumn("location");
            String location = resultSet.getString(index);
            
            index = resultSet.findColumn("description");
            String description = resultSet.getString(index);
            
            index = resultSet.findColumn("start");
            String start = resultSet.getString(index);
            
            index = resultSet.findColumn("end");
            String end = resultSet.getString(index);
            
            index = resultSet.findColumn("customersId");
            int customersId = resultSet.getInt(index);
            
            ZonedDateTime startDateTime = ZonedDateTime.parse(start);
            ZonedDateTime endDateTime = ZonedDateTime.parse(end);
            
            /*try {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                formatter.ofPattern("yyyy-MM-dd HH:mm z");
                startDateTime = ZonedDateTime.parse(start, formatter);
                endDateTime = ZonedDateTime.parse(end, formatter);
            } catch(DateTimeParseException ex) {
                System.err.println(ex.toString());
            }*/
            
            Appointment a = new Appointment();
            a.setId(id);
            a.setSubject(subject);
            a.setLocation(location);
            a.setDescription(description);
            a.setStart(startDateTime);
            a.setEnd(endDateTime);
            a.setCustomerId(customersId);
            
            appointments.add(a);
        }
        
        conn.close();
        
        return appointments;
    }
    
    public ObservableList fetchAppointmentDetails(Appointment a) throws SQLException {
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchAppointmentDetails");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_APPOINTMENT + a.getId() + ";");
        
        int index;
        
        while(resultSet.next()) {
            
        }
        
        return appointments;
    }
    
    public ObservableList fetchAppointmentsInDateRange(ZonedDateTime startDate, ZonedDateTime endDate) throws SQLException {
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        String sql = "SELECT * FROM appointments";
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        int index;
        
        while(resultSet.next()) {
            index = resultSet.findColumn("ID");
            int id = resultSet.getInt(index);
            
            index = resultSet.findColumn("subject");
            String subject = resultSet.getString(index);
            
            index = resultSet.findColumn("location");
            String location = resultSet.getString(index);
            
            index = resultSet.findColumn("description");
            String description = resultSet.getString(index);
            
            index = resultSet.findColumn("start");
            String start = resultSet.getString(index);
            
            index = resultSet.findColumn("end");
            String end = resultSet.getString(index);
            
            index = resultSet.findColumn("customersId");
            int customersId = resultSet.getInt(index);
            
            ZonedDateTime startDateTime = ZonedDateTime.parse(start);
            ZonedDateTime endDateTime = ZonedDateTime.parse(end);
            
            if(startDateTime.isAfter(startDate) && startDateTime.isBefore(endDate)) {
                Appointment a = new Appointment();
                a.setId(id);
                a.setSubject(subject);
                a.setLocation(location);
                a.setDescription(description);
                a.setStart(startDateTime);
                a.setEnd(endDateTime);
                a.setCustomerId(customersId);
                
                appointments.add(a);
            }
        }
        
        return appointments;
    }
    
    public int getUserId(String userName) throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "SELECT ID FROM users WHERE username = '" + userName + "';";
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        int index = resultSet.findColumn("ID");
        int id = resultSet.getInt(index);
        
        return id;
    }
    
    public int getCustomerCount() throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "SELECT COUNT(*) FROM customers";
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        int count = resultSet.getInt(1);
        
        conn.close();
        
        return count;
    }
    
    public int getAppointmentCount() throws SQLException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "SELECT COUNT(*) FROM appointments";
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        int count = resultSet.getInt(1);
        
        conn.close();
        
        return count;
    }
    
    public int getAppointmentCountPerCustomer(Customer customer) throws SQLException{        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        String sql = "SELECT COUNT(*) FROM appointments WHERE customersId = " + customer.getId();
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        int count = resultSet.getInt(1);
        
        System.out.println("Appointment count = " + count);
        
        return count;
    }
}