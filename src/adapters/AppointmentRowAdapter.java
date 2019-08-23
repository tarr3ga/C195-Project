/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adapters;

import data.FetchData;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ListIterator;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import models.AppointmentRow;
import models.Customer;
import models.User;
import util.DateTimeUtils;

/**
 *
 * @author jamyers
 */
public class AppointmentRowAdapter {
    private final ObservableList<AppointmentRow> appointmentRows = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    
    private final Customer customer;
    public ObservableList getAppointmentRows() { return appointmentRows; }
    
    public AppointmentRowAdapter(Customer customer) throws SQLException, ClassNotFoundException {
        this.customer = customer;
       
        fetchData();
        buildList();
        adjustTime();
    }
    
    public ObservableList getAdapter() {
        return appointmentRows;
    }
    
    private void adjustForTimeZone() {
        ZoneId zoneId = ZoneId.systemDefault();
        
        for(Appointment a : appointments) {            
             ZonedDateTime startTime = a.getStart();
             ZonedDateTime endTime = a.getEnd();
             
             System.out.println("");
             System.out.println("");
             System.out.println("startTime = " + startTime);
             System.out.println("endTime   = " + endTime);
             
             //ZonedDateTime adjustedStartTime = startTime.withZoneSameInstant(zoneId);
             //ZonedDateTime adjustedEndTime = endTime.withZoneSameInstant(zoneId);
             
             ZonedDateTime adjustedStartTime = startTime.withZoneSameInstant(zoneId);
             ZonedDateTime adjustedEndTime = endTime.withZoneSameInstant(zoneId);
             
             a.setStart(adjustedStartTime);
             a.setEnd(adjustedEndTime);
             
             System.out.println("adjustedStartTime = " + adjustedStartTime);
             System.out.println("adjustedEndTime   = " + adjustedEndTime);
             System.out.println("");
             System.out.println("");
        }
    }
    
    private void fetchData() throws SQLException, ClassNotFoundException {
        FetchData data = new FetchData();
        
        appointments = data.fetchAppointmentsForCustomerData(customer);
        adjustForTimeZone();
        
        users = data.fetchUsers();
    }
    
    private void buildList() {
        
        ListIterator<Appointment> itr = appointments.listIterator();
        
        while(itr.hasNext()) {
            AppointmentRow row = new AppointmentRow();
            Appointment element = itr.next();
            
            row.setId(element.getAppointmentId());
            row.setTitle(element.getTitle());
            row.setLocation(element.getLocation());
            row.setStart(element.getStart());
            row.setEnd(element.getEnd());
            row.setCreatedBy(element.getCreatedBy());
            
            appointmentRows.add(row);
        }
        
        ListIterator<AppointmentRow> rows = appointmentRows.listIterator();
        
        while(rows.hasNext()) {
            ListIterator<User> u = users.listIterator();
            AppointmentRow element = rows.next();

            while(u.hasNext()) {
                User user = u.next();
                
                if(element.getCreatedBy() == user.getUserId()) {
                    element.setUser(user.getUsername());
                }
            }
        }
    }
    
    private void adjustTime() {
        for(AppointmentRow a : appointmentRows) {
            ZonedDateTime start = a .getStart().plusHours(1);
            ZonedDateTime end = a.getEnd().plusHours(1);
            
            a.setStart(start);
            a.setEnd(end);
        }
    }
}