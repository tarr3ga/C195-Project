/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import models.AppointmentAlert;

/**
 *
 * @author jamyers
 */
public class SaveAlerts implements Serializable {
    
    private ArrayList<AppointmentAlert> alerts = new ArrayList<>();
    private final String filename = "alerts.ser";
    
    public void saveAlerts() throws IOException {
        for(AppointmentAlert a : Scan.alerts) {
            alerts.add(a);
        }
        
        FileOutputStream file = new FileOutputStream(filename); 
        ObjectOutputStream out = new ObjectOutputStream(file); 
        
        out.writeObject(alerts); 
              
        out.close(); 
        file.close(); 

        System.out.println("Object has been serialized"); 
    }   
}