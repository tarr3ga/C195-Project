/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 *
 * @author jamyers
 */
public class Appointment {
    private int id;
    private String subject;
    private String location;
    private String description;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private String timezone;
    private String customerRep;
    private Date createDate;
    private String createdBy;
    private int userId;
    private int customerId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public ZonedDateTime getStart() { return start; }
    public void setStart(ZonedDateTime start) { this.start = start; }

    public ZonedDateTime getEnd() { return end; }
    public void setEnd(ZonedDateTime end) { this.end = end; }
    
    public void setTimezone(String timezone) { this.timezone = timezone; }
    public String getTimezone() { return timezone; }
    
    public void setCustomerRep(String customerRep) { this.customerRep = customerRep; }
    public String getCustomerRep() { return customerRep; }
    
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public Appointment() {
        
    }
    
    public Appointment(int id, String subject, String location, String description, 
            ZonedDateTime start, ZonedDateTime end, String timezone, String customerRep,  Date createDate, String createdBy, int userId, int customerId) {
        this.id = id;
        this.subject = subject;
        this.location = location;
        this.description = description;
        this.start = start;
        this.end = end;      
        this.timezone = timezone;
        this.customerRep = customerRep;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.userId = userId;
        this.customerId = customerId;
    }
    
}
