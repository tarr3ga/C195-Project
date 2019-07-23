/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import models.AppointmentAlert;

/**
 *
 * @author jamyers
 */
public class Scan extends TimerTask {
        public static List<AppointmentAlert> alerts = new ArrayList<>();
        
        @Override
        public void run() {
            Platform.runLater(() ->  {
                LocalDateTime time = LocalDateTime.now();

                System.out.println("Scan at" + LocalDateTime.now());

                for(AppointmentAlert a : alerts) {
                    LocalDateTime due = a.getAlertTime();

                    if(time.isAfter(due.minusMinutes(15))){
                        String message = "The appoint with " + a.getCustomerName() +
                                " is in 15 minutes.  The subject of the meeting is " + a.getSubject();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("15 minutes till appointment!");
                        alert.setContentText(message);
                        alert.show();
                    }
                }
            }) ;
        }
    }
