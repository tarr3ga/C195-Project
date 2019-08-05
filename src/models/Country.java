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
public class Country {
    private int countryId;
    private String countryAbreviation;
    private String country;
    private Timestamp createDate;
    private int createdBy;
    private Timestamp lastUpdate;
    private int updatedBy;
    
    
    public int getCountryId() { return countryId; }
    public void setCountryId(int countryId) { this.countryId = countryId; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCountryAbreviation() { return countryAbreviation; }
    public void setCountryAbreviation(String countryAbreviation) { this.countryAbreviation = countryAbreviation; }

    public Timestamp getCreateDate() { return createDate; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }

    public int getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(int updatedBy) { this.updatedBy = updatedBy; }
    
    public Country() {
        
    }

    public Country(int countryId, String countryAbreviation, String country, Timestamp createDate, int createdBy, Timestamp lastUpdate, int updatedBy) {
        this.countryId = countryId;
        this.countryAbreviation = countryAbreviation;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    }
}
