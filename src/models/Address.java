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
public class Address {
    private int customerId;
    private int addressId;
    private String address;
    private String address2;
    private String postalCode;
    private String phone;
    private int cityId;
    private Timestamp createDate;
    private int createdBy;
    private Timestamp lastUpdate;
    private int updatedBy;

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }

    public Timestamp getCreateDate() { return createDate; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }

    public int getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(int updatedBy) { this.updatedBy = updatedBy; }
    
    public Address() {
        
    }
    
    public Address(int customerId, int addressId, String address, String postalCode, String phone, 
            int cityId, Timestamp createDate, int createdBy, Timestamp lastUpdate, int updatedBy) {
        this.customerId = customerId;
        this.addressId = addressId;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.cityId = cityId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    }   
}