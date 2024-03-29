/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

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
public class Scan extends TimerTask {
            
    public static ArrayList<AppointmentAlert> alerts = new ArrayList<>();

    @Override
    public void run() {
        Platform.runLater(() ->  {
            ZonedDateTime time = ZonedDateTime.now();

            System.out.println("Scan at" + ZonedDateTime.now());
            
            ArrayList<AppointmentAlert> toDelete = new ArrayList<>();
            
            alerts.forEach((a) -> {
                ZonedDateTime due = a.getAlertTime();
                if (time.isAfter(due.minusMinutes(15)) && time.isBefore(due.plusMinutes(60))) {
                    String message = "The appoint with " + a.getCustomerName() +
                            " is in 15 minutes.  The subject of the meeting is " + a.getSubject();

                    toDelete.add(a);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("15 minutes till appointment!");
                    alert.setContentText(message);
                    alert.show();
                }
            });
            
            for(AppointmentAlert a : toDelete) {
                alerts.remove(a);
            }
        }) ;
    }
}