/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import java.io.IOException;
import java.net.URL;
import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.AppointmentAlert;
import util.DateTimeUtils;
import util.Scan;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class MainSceneController implements Initializable {

    Parent root;
    private final Stage stage = new Stage();
    
    @FXML GridPane display;
    
    @FXML
    private void onHomeClick(ActionEvent event) throws IOException {
        display.getChildren().clear();
        GridPane newPane = FXMLLoader.load(getClass().getResource("home.fxml"));
        display.add(newPane, 0, 0);
    }
    
    @FXML
    private void onCustomersClick(ActionEvent event) throws IOException {
        display.getChildren().clear();
        GridPane newPane = FXMLLoader.load(getClass().getResource("Customers.fxml"));
        display.add(newPane, 0, 0);
    }
    
    @FXML
    private void onAppointmentsClick(ActionEvent event) throws IOException {
        display.getChildren().clear();
        GridPane newPane = FXMLLoader.load(getClass().getResource("Appointments.fxml"));
        display.add(newPane, 0, 0);
    }
    
    private static void startTimer() {
        Date start = new Date();
        start = DateTimeUtils.getTimeWithSecondsZeroed(start);
        
        TimerTask task = new Scan();
        Timer t = new Timer();
        t.scheduleAtFixedRate(task, start, 60000);
    }
    
    private static void scan() {
        
    } 
    
    public static void setAlert(int id, ZonedDateTime time, String name, String title) {        
        AppointmentAlert alert = new AppointmentAlert(id, time, name, title);
        Scan.alerts.add(alert);
    }
    
    public static void cancelAlert(int id) {
        
    }
    
    public void openWindow() throws Exception {
        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        
        Scene scene = new Scene(root);
       
        stage.setScene(scene);
        stage.show();
    }
    
    private void loadOnInit() throws IOException {
        GridPane newPane = FXMLLoader.load(getClass().getResource("home.fxml"));
        display.add(newPane, 0, 0);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadOnInit();
        } catch(IOException ex) {
            System.err.println(ex.toString());
        }
        
        startTimer();
    }    
}