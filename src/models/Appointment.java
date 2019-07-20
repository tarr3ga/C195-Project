/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;
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
    private LocalDateTime start;
    private LocalDateTime end;
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

    public LocalDateTime getStart() { return start; }
    public void setStart(LocalDateTime start) { this.start = start; }

    public LocalDateTime getEnd() { return end; }
    public void setEnd(LocalDateTime end) { this.end = end; }

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
    
    public Appointment(int id, String subject, String location, String description, LocalDateTime start, LocalDateTime end, Date createDate, String createdBy, int userId, int customerId) {
        this.id = id;
        this.subject = subject;
        this.location = location;
        this.description = description;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.userId = userId;
        this.customerId = customerId;
    }
    
}
