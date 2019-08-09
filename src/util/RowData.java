/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import models.Customer;
import models.User;

/**
 *
 * @author jamyers
 */
public class RowData {    
    private Customer customer;
    private User user;
    
    public Customer getCustomer() { return customer; }
    public User getUser() { return user; }
    
    public RowData(Customer cusotmer, User user) {
        this.customer = cusotmer;
        this.user = user;  
    }   
}
