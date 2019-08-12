/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.ZonedDateTime;

/**
 *
 * @author jamyers
 */
public class AppointmentRow {
    private int id;
    private String title;
    private String location;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private String user;
    private int createdBy;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public ZonedDateTime getStart() { return start; }
    public void setStart(ZonedDateTime start) { this.start = start; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public ZonedDateTime getEnd() { return end; }
    public void setEnd(ZonedDateTime end) { this.end = end; }
    
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }   

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    
    public AppointmentRow() {
        
    }
    
    public AppointmentRow(int id, String title, String location, ZonedDateTime start, ZonedDateTime end, String user, int createdBy) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.start = start;
        this.end = end;
        this.user = user;
        this.createdBy = createdBy;
    }
}