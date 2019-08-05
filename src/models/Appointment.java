/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 *
 * @author jamyers
 */
public class Appointment {
    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    //private GregorianCalendar start;
    //private GregorianCalendar end;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Timestamp createDate;
    private int createdBy;
    private Timestamp lastUpdate;
    private int updatedBy;

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public ZonedDateTime getStart() { return start; }
    public void setStart(ZonedDateTime start) { this.start = start; }

    public ZonedDateTime getEnd() { return end; }
    public void setEnd(ZonedDateTime end) { this.end = end; }  
    
    public Timestamp getCreateDate() { return createDate; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }

    public int getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(int updatedBy) { this.updatedBy = updatedBy; }

    public Appointment() {
        
    }
    
    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location,
            String contact, String type, String url, ZonedDateTime start, ZonedDateTime end, 
            Timestamp createDate, int createdBy, Timestamp lastUpdate, int updatedBy) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    } 
}
