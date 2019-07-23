/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;

/**
 *
 * @author jamyers
 */
public class AppointmentAlert {
    private int id;
    private LocalDateTime alertTime;
    private String customerName;
    private String subject;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public LocalDateTime getAlertTime() { return alertTime; }
    public void setAlertTime(LocalDateTime alertTime) { this.alertTime = alertTime; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public AppointmentAlert(int id, LocalDateTime alertTime, String customerName, String subject) {
        this.id = id;
        this.alertTime = alertTime;
        this.customerName = customerName;
        this.subject = subject;
    }
}