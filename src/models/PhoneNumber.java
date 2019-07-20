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
public class PhoneNumber {
    private int id;
    private String phone;
    private String phoneType;
    private int customerId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPhoneType() { return phoneType; }
    public void setPhoneType(String phoneType) { this.phoneType = phoneType; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public PhoneNumber() {
        
    }
    
    public PhoneNumber(int id, String phone, String phoneType, int customerId) {
        this.id = id;
        this.phone = phone;
        this.phoneType = phoneType;
        this.customerId = customerId;
    }
    
    
}
