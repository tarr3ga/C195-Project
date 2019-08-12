/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import models.Appointment;
import models.User;

/**
 *
 * @author jamyers
 */
public class AppointmentRowData {
    private final Appointment appointment;
    private final User user;
    
    public Appointment getAppointment() { return appointment; }
    public User getUser() { return user; }
    
    public AppointmentRowData(Appointment appointment, User user) {
        this.appointment = appointment;
        this.user = user;
    }
}
