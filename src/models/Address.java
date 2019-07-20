/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author jamyers
 */
public class Address {
    private int id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private int countryId;
    private int customerId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public int getCountryId() { return countryId; }
    public void setCountryId(int countryId) { this.countryId = countryId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public Address() {
        
    }
    
    public Address(int id, String street, String city, String state, String zip, int countryId, int customerId) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.countryId = countryId;
        this.customerId = customerId;
    }
    
    
}
