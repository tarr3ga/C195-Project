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
public class Country {
    private int id;
    private String countryAbreviation;
    private String country;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCountryAbreviation() { return countryAbreviation; }
    public void setCountryAbreviation(String countryAbreviation) { this.countryAbreviation = countryAbreviation; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Country() {
        
    }
    
    public Country(int id, String countryAbreviation, String country) {
        this.id = id;
        this.countryAbreviation = countryAbreviation;
        this.country = country;
    }
}
