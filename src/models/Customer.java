/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author jamyers
 */
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String customerRep;

    
    private Timestamp addedOn;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setCustomerRep(String customerRep) { this.customerRep = customerRep; }
    public String getCustomerRep() { return customerRep; }
    
    public Timestamp getAddedOn() { return addedOn; }
    public void setAddedOn(Timestamp addedOn) { this.addedOn = addedOn; }

    public Customer() {
        
    }
    
    public Customer(int id, String firstName, String lastName, String customerRep, Timestamp addedOn) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerRep = customerRep;
        this.addedOn = addedOn;
    }   
}
