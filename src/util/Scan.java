/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import models.AppointmentAlert;

/**
 *
 * @author jamyers
 */
public class Scan extends TimerTask implements Serializable {
    
    
        private static final File FILENAME = new File("alerts.log");
        
        public static ArrayList<AppointmentAlert> alerts = new ArrayList<>();
        
        public static void serialize() {
            try {
                FileOutputStream file = new FileOutputStream(FILENAME); 
                ObjectOutputStream out = new ObjectOutputStream(file);
                
                out.writeObject(Scan.alerts);
                
                out.close();
                file.close();
                
                System.out.println("Object has been serialized");
            } catch(IOException ex) {
                System.err.println(ex.toString());
            }
        }
        
        public void load() {
            
        }
        
        @Override
        public void run() {
            Platform.runLater(() ->  {
                ZonedDateTime time = ZonedDateTime.now();

                System.out.println("Scan at" + ZonedDateTime.now());

                alerts.forEach((a) -> {
                    ZonedDateTime due = a.getAlertTime();
                    if (time.isAfter(due.minusMinutes(15))) {
                        String message = "The appoint with " + a.getCustomerName() +
                                " is in 15 minutes.  The subject of the meeting is " + a.getSubject();
                        
                        alerts.remove(a);
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("15 minutes till appointment!");
                        alert.setContentText(message);
                        alert.show();
                    }
                });
            }) ;
        }
    }
