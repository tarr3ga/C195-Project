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
public class City {
    private int cityId;
    private String city;
    private int countryId;
    private Timestamp createDate;
    private int createdBy;
    private Timestamp lastUpdate;
    private int updatedBy;

    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public int getCountryId() { return countryId; }
    public void setCountryId(int countryId) { this.countryId = countryId; }
    
    public Timestamp getCreateDate() { return createDate; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }

    public int getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(int updatedBy) { this.updatedBy = updatedBy; }

    public City() {
        
    }
    
    public City(int cityId, String city, int countryId, Timestamp createDate, int createdBy, 
            Timestamp lastUpdate, int updatedBy) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    }
    
    
}
