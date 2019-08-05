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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Address;
import models.Appointment;
import models.City;
import models.Country;
import models.Customer;
import util.DateTimeUtils;

/**
 *
 * @author jamyers
 */
public class FetchData {
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();
    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private final ObservableList<Address> addressess = FXCollections.observableArrayList();
    
    private static final String SQL_GET_USERNAMES = "SELECT * FROM users;";
    private static final String SQL_CUSTOMER = "SELECT * FROM customers WHERE ID = ";
    private static final String SQL_CUSTOMERS = "SELECT * FROM customers;";
    private static final String SQL_APPOINTMENT = "SELECT * FROM appointments WHERE ID = ";
    private static final String SQL_APPOINTMENTS = "SELECT * FROM appointments;";
    private static final String SQL_APPOINTMENTS_BY_REP = "SELECT * FROM appointments WHERE customerRep = '";
    private static final String SQL_ADDRESS = "SELECT * FROM addresses WHERE customersId = ";
    private static final String SQL_ADDRESSES = "SELECT * FROM addresses";
    private static final String SQL_COUNTRY = "SELECT * FROM country WHERE countryId = ";
    private static final String SQL_COUNTRIES = "SELECT * FROM country";
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
    
    public ArrayList fetchUserNames() throws SQLException, ClassNotFoundException{
        ArrayList<String> userNames = new ArrayList<>();
        
        conn = DBConnect.makeConnection();
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_GET_USERNAMES);
        
        int index;
        
        while(resultSet.next()) {
            index = resultSet.findColumn("username");
            String username = resultSet.getString(index);
            
            userNames.add(username);
        }
        
        conn.close();
        
        return userNames;
    }
    
    public Customer fetchSingleCustomer(int customerId) throws SQLException, ClassNotFoundException {
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
        
        index = resultSet.findColumn("customerId");
        c.setCustomerId(resultSet.getInt(index));
        
        index = resultSet.findColumn("name");
        c.setName(resultSet.getString(index));
        
        index = resultSet.findColumn("name");
            String name = resultSet.getString(index);
            
            index = resultSet.findColumn("addressId");
            c.setAddressId(resultSet.getInt(index));
            
            index = resultSet.getInt("isActive");
            c.setActive(resultSet.getBoolean(index));
            
            index = resultSet.findColumn("ceatedDate");
            c.setCreateDate((Timestamp)resultSet.getObject(index));
            
            index = resultSet.findColumn("ceatedBy");
            c.setCreatedBy(resultSet.getInt(index));
            
            index = resultSet.findColumn("lastUpdate");
            Timestamp lastUpdate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("updatedBy");
            int updatedBy = resultSet.getInt(index);
        
        return c;
    }
    
    public ObservableList fetchCustomerData() throws SQLException, ClassNotFoundException {
        
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
            index = resultSet.findColumn("customerId");
            int customerId = resultSet.getInt(index);
            
            index = resultSet.findColumn("name");
            String name = resultSet.getString(index);
            
            index = resultSet.findColumn("addressId");
            int addressId = resultSet.getInt(index);
            
            index = resultSet.getInt("isActive");
            boolean isActive = resultSet.getBoolean(index);
            
            index = resultSet.findColumn("ceatedDate");
            Timestamp createdDate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("ceatedBy");
            int createdBy = resultSet.getInt(index);
            
            index = resultSet.findColumn("lastUpdate");
            Timestamp lastUpdate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("updatedBy");
            int updatedBy = resultSet.getInt(index);
            
            Customer c = new Customer();
            c.setCustomerId(customerId);
            c.setName(name);
            c.setAddressId(addressId);
            c.setActive(isActive);
            c.setCreateDate(createdDate);
            c.setCreatedBy(createdBy);
            c.setLastUpdate(lastUpdate);
            c.setUpdatedBy(updatedBy);
            
            customers.add(c);
        }
        
        conn.close();
        
        return customers;
    }
    
    public ObservableList fetchAddresses() throws SQLException, ClassNotFoundException {
        
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
            index = resultSet.findColumn("addressId");
            int addressId = resultSet.getInt(index);
            
            index = resultSet.findColumn("street");
            String street = resultSet.getString(index);
            
            index = resultSet.findColumn("street2");
            String street2 = resultSet.getString(index);
            
            index = resultSet.findColumn("cityId");
            int cityId = resultSet.getInt(index);
                        
            index = resultSet.findColumn("createDate");
            Timestamp createDate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("createdBy");
            int createdBy = resultSet.getInt(index);
            
            index = resultSet.findColumn("lastUpdate");
            Timestamp lastUpdate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("updatedBy");
            int updatedBy = resultSet.getInt(index);
            
            Address a = new Address();
            a.setAddressId(addressId);
            a.setAddress(street);
            a.setAddress2(street2);
            a.setCityId(cityId);
            a.setCreateDate(createDate);
            a.setCreatedBy(createdBy);
            a.setLastUpdate(lastUpdate);
            a.setUpdatedBy(updatedBy);
            
            addressess.add(a);
        }
        
        conn.close();
        
        return addressess;
    }
    
    public Address fetchAddress(int id) throws SQLException, ClassNotFoundException {
        
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
        
        index = resultSet.findColumn("addressId");
        a.setAddressId(resultSet.getInt(index));
        
        index = resultSet.findColumn("address");
        a.setAddress(resultSet.getString(index));

        index = resultSet.findColumn("address2");
        a.setAddress2(resultSet.getString(index));
        
        index = resultSet.findColumn("cityId");
        a.setCityId(resultSet.getInt(index));

        index = resultSet.findColumn("createDate");
        a.setCreateDate((Timestamp)resultSet.getObject(index));           

        index = resultSet.findColumn("createdBy");
        a.setCreatedBy(resultSet.getInt(index));
        
        index = resultSet.findColumn("lestUpdate");
        a.setLastUpdate((Timestamp)resultSet.getObject(index));
        
        index = resultSet.findColumn("updatedBy");
        a.setUpdatedBy(resultSet.getInt(index));
        
        conn.close();
        
        return a;
    }
    
    public City fetchCity(int id) throws SQLException, ClassNotFoundException {
        City city = new City();
        
        String sql = "SELECT * FROM city WHERE cityId = " + id + ";";
        
        conn = DBConnect.makeConnection();
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        int index = resultSet.findColumn("cityId");
        city.setCityId(resultSet.getInt(index));
        
        index = resultSet.findColumn("city");
        city.setCity(resultSet.getString(index));
        
        index = resultSet.findColumn("countryId");
        city.setCountryId(resultSet.getInt(index));
        
        index = resultSet.findColumn("createDate");
        String createString = resultSet.getString(index);
        city.setCreateDate(DateTimeUtils.getTimeStampFromStrng(createString));
        
        index = resultSet.findColumn("createdBy");
        city.setCreatedBy(resultSet.getInt(index));
        
        index = resultSet.findColumn("lastUpdate");
        String endString = resultSet.getString(index);
        city.setLastUpdate(DateTimeUtils.getTimeStampFromStrng(endString));
        
        index = resultSet.findColumn("updatedBt");
        city.setUpdatedBy(resultSet.getInt(index));
        
        return city;
    }
    
    public Country fetchCountry(int countryId) throws SQLException, ClassNotFoundException {
        
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
        
        index = resultSet.findColumn("country");
        c.setCountry(resultSet.getString(index));
              
        conn.close();
        
        return c;
    }
    
    public ArrayList<Country> fetchCountries() throws SQLException, ClassNotFoundException {
        
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
            index = resultSet.findColumn("countryName");
            String country = resultSet.getString(index);
            
            Country c = new Country();
            c.setCountry(country);
            
            countries.add(c);
        }
           
        conn.close();
        
        return countries;
    }
    
    public ObservableList fetchAppointmentsForCustomerData(Customer c) throws SQLException, ClassNotFoundException {
        
         try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchAppointmentsForCustomerData");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_CUSTOMER_SPECIFIC_APPOINTMENTS + c.getCustomerId() + ";");
        
        int index;
        
        while(resultSet.next()) {
            index = resultSet.findColumn("appointmentId");
            int appointmentId = resultSet.getInt(index);
            
            index = resultSet.findColumn("customersId");
            int customerId = resultSet.getInt(index);
            
            index = resultSet.findColumn("userId");
            int userId = resultSet.getInt(index);
            
            index = resultSet.findColumn("title");
            String title = resultSet.getString(index);
                        
            index = resultSet.findColumn("description");
            String description = resultSet.getString(index);
            
            index = resultSet.findColumn("location");
            String location = resultSet.getString(index);

            index = resultSet.findColumn("contact");
            String contact = resultSet.getString(index);
            
            index = resultSet.findColumn("type");
            String type = resultSet.getString(index);
            
            index = resultSet.findColumn("createDate");
            Timestamp createDate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("start");
            String start = resultSet.getString(index);
            
            index = resultSet.findColumn("end");
            String end = resultSet.getString(index);
            
            index = resultSet.findColumn("createdBy");
            int createdBy = resultSet.getInt(index);
            
            index = resultSet.findColumn("lastUpdate");
            Timestamp lastUpdate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("updatedBy");
            int updatedBy = resultSet.getInt(index);
            
            ZonedDateTime startDateTime = ZonedDateTime.parse(start);
            ZonedDateTime endDateTime = ZonedDateTime.parse(end);
            
            Appointment a = new Appointment();
            a.setAppointmentId(appointmentId);
            a.setCustomerId(customerId);
            a.setUserId(userId);
            a.setTitle(title);
            a.setDescription(description);
            a.setLocation(location);
            a.setContact(contact);
            a.setType(type);
            a.setStart(startDateTime);
            a.setEnd(endDateTime);
            a.setCreateDate(createDate);
            a.setCreatedBy(createdBy);
            a.setLastUpdate(lastUpdate);
            a.setUpdatedBy(updatedBy);
            
            appointments.add(a);
        }
        
        conn.close();
        
        return appointments;
    }
    
    public ObservableList fetchAppointmentsForCustomerRep(int userId) throws SQLException, ClassNotFoundException {
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        String sql = SQL_APPOINTMENTS_BY_REP + userId + "';";
        
        System.out.println(sql);
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        int index;
        
        while(resultSet.next()) {
            index = resultSet.findColumn("appointmentId");
            int appointmentId = resultSet.getInt(index);
            
            index = resultSet.findColumn("customerId");
            int customerId  = resultSet.getInt(index);
            
            //index = resultSet.findColumn("userId");
            //int userId  = resultSet.getInt(index);
            
            index = resultSet.findColumn("title");
            String subject = resultSet.getString(index);
            
            index = resultSet.findColumn("location");
            String location = resultSet.getString(index);
            
            index = resultSet.findColumn("description");
            String description = resultSet.getString(index);
            
            
            index = resultSet.findColumn("start");
            String start = resultSet.getString(index);
                    
            index = resultSet.findColumn("end");
            String end = resultSet.getString(index);
            
            ZonedDateTime startDateTime = ZonedDateTime.parse(start);
            ZonedDateTime endDateTime = ZonedDateTime.parse(end);
            
            Appointment a = new Appointment();
            a.setAppointmentId(appointmentId);
            a.setTitle(subject);
            a.setLocation(location);
            a.setDescription(description);
            a.setStart(startDateTime);
            a.setEnd(endDateTime);
            a.setCustomerId(customerId);
            
            appointments.add(a);
        }
        
        conn.close();
        
        return appointments;
    }
    
    public ObservableList fetchAppointmentData() throws SQLException, ClassNotFoundException {
        
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
             index = resultSet.findColumn("appointmentId");
            int appointmentId = resultSet.getInt(index);
            
            index = resultSet.findColumn("customersId");
            int customerId = resultSet.getInt(index);
            
            index = resultSet.findColumn("userId");
            int userId = resultSet.getInt(index);
            
            index = resultSet.findColumn("title");
            String title = resultSet.getString(index);
                        
            index = resultSet.findColumn("description");
            String description = resultSet.getString(index);
            
            index = resultSet.findColumn("location");
            String location = resultSet.getString(index);

            index = resultSet.findColumn("contact");
            String contact = resultSet.getString(index);
            
            index = resultSet.findColumn("type");
            String type = resultSet.getString(index);
            
            index = resultSet.findColumn("createDate");
            Timestamp createDate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("start");
            String start = resultSet.getString(index);
            
            index = resultSet.findColumn("end");
            String end = resultSet.getString(index);
            
            index = resultSet.findColumn("createdBy");
            int createdBy = resultSet.getInt(index);
            
            index = resultSet.findColumn("lastUpdate");
            Timestamp lastUpdate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("updatedBy");
            int updatedBy = resultSet.getInt(index);
                
            ZonedDateTime startDateTime = ZonedDateTime.parse(start);
            ZonedDateTime endDateTime = ZonedDateTime.parse(end);
            
            Appointment a = new Appointment();
            a.setAppointmentId(appointmentId);
            a.setCustomerId(customerId);
            a.setUserId(userId);
            a.setTitle(title);
            a.setDescription(description);
            a.setLocation(location);
            a.setContact(contact);
            a.setType(type);
            a.setStart(startDateTime);
            a.setEnd(endDateTime);
            a.setCreateDate(createDate);
            a.setCreatedBy(createdBy);
            a.setLastUpdate(lastUpdate);
            a.setUpdatedBy(updatedBy);
            
            appointments.add(a);
        }
        
        conn.close();
        
        return appointments;
    }
    
    public ObservableList fetchAppointmentDetails(Appointment a) throws SQLException, ClassNotFoundException {
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.out.println("fetchAppointmentDetails");
            Logger.getLogger(data.FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL_APPOINTMENT + a.getAppointmentId() + ";");
        
        int index;
        
        while(resultSet.next()) {
            
        }
        
        return appointments;
    }
    
    public ObservableList fetchAppointmentsInDateRange(ZonedDateTime startDate, ZonedDateTime endDate) throws SQLException, ClassNotFoundException {
        
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
           index = resultSet.findColumn("appointmentId");
            int appointmentId = resultSet.getInt(index);
            
            index = resultSet.findColumn("customersId");
            int customerId = resultSet.getInt(index);
            
            index = resultSet.findColumn("userId");
            int userId = resultSet.getInt(index);
            
            index = resultSet.findColumn("title");
            String title = resultSet.getString(index);
                        
            index = resultSet.findColumn("description");
            String description = resultSet.getString(index);
            
            index = resultSet.findColumn("location");
            String location = resultSet.getString(index);

            index = resultSet.findColumn("contact");
            String contact = resultSet.getString(index);
            
            index = resultSet.findColumn("type");
            String type = resultSet.getString(index);
            
            index = resultSet.findColumn("createDate");
            Timestamp createDate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("start");
            String start = resultSet.getString(index);
            
            index = resultSet.findColumn("end");
            String end = resultSet.getString(index);
            
            index = resultSet.findColumn("createdBy");
            int createdBy = resultSet.getInt(index);
            
            index = resultSet.findColumn("lastUpdate");
            Timestamp lastUpdate = (Timestamp)resultSet.getObject(index);
            
            index = resultSet.findColumn("updatedBy");
            int updatedBy = resultSet.getInt(index);
           
            ZonedDateTime startDateTime = ZonedDateTime.parse(start);
            ZonedDateTime endDateTime = ZonedDateTime.parse(end);
            
            if(startDateTime.isAfter(startDate) && startDateTime.isBefore(endDate)) {
                Appointment a = new Appointment();
                a.setAppointmentId(appointmentId);
                a.setCustomerId(customerId);
                a.setUserId(userId);
                a.setTitle(title);
                a.setDescription(description);
                a.setLocation(location);
                a.setContact(contact);
                a.setType(type);
                a.setStart(startDateTime);
                a.setEnd(endDateTime);
                a.setCreateDate(createDate);
                a.setCreatedBy(createdBy);
                a.setLastUpdate(lastUpdate);
                a.setUpdatedBy(updatedBy);
                
                appointments.add(a);
            }
        }
        
        return appointments;
    }
    
    public int getUserId(String userName) throws SQLException, ClassNotFoundException {
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
    
    public int getCustomerCount() throws SQLException, ClassNotFoundException {
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
    
    public int getAppointmentCount() throws SQLException, ClassNotFoundException {
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
    
    public int getAppointmentCountPerCustomer(Customer customer) throws SQLException, ClassNotFoundException{        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        String sql = "SELECT COUNT(*) FROM appointment WHERE customersId = " + customer.getCustomerId();
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        int count = resultSet.getInt(1);
        
        System.out.println("Appointment count = " + count);
        
        return count;
    }
    
    public int getConsultationCount() throws SQLException, ClassNotFoundException {
        int count;
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "SELECT COUNT(*) FROM appointments WHERE type = 'Consultation'";
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        count = resultSet.getInt(1);
        
        conn.close();
        
        return count;
    }
    
    public int getPlanningCount() throws SQLException, ClassNotFoundException {
        int count = 0;
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "SELECT COUNT(*) FROM appointments WHERE type = 'Planning'";
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        count = resultSet.getInt(1);
        
        conn.close();
        
        return count;
    }
    
    public int getWorkingCount() throws SQLException, ClassNotFoundException {        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        int count = 0;
        
        String sql = "SELECT COUNT(*) FROM appointments WHERE type = 'Working'";
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        count = resultSet.getInt(1);
        
        conn.close();
        
        return count;
    }
    
    public int getCasualCount() throws SQLException, ClassNotFoundException {
        int count = 0;
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "SELECT COUNT(*) FROM appointments WHERE type = 'Casual'";
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        count = resultSet.getInt(1);
        
        conn.close();
        
        return count;
    }
    
    public int getOtherCount() throws SQLException, ClassNotFoundException {
        int count = 0;
        
        try {
            conn = DBConnect.makeConnection();
        } catch(SQLException ex) {
            
        }
        
        String sql = "SELECT COUNT(*) FROM appointments WHERE type = 'Other'";
        
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        resultSet.next();
        
        count = resultSet.getInt(1);
        
        conn.close();
        
        return count;
    }
}