/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Calendar;

/**
 *
 * @author jamyers
 */
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private Calendar addedOn;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Calendar getAddedOn() { return addedOn; }

    public void setAddedOn(Calendar addedOn) { this.addedOn = addedOn; }

    public Customer() {
        
    }
    
    public Customer(int id, String firstName, String lastName, Calendar addedOn) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addedOn = addedOn;
    }
    
    
}
