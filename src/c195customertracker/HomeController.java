/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.FetchData;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class HomeController implements Initializable {

    @FXML private Label welcome;
    @FXML private Label location;
    @FXML private Label customerCount;
    @FXML private Label appointmentCount;
    @FXML private Button btnTypeReport;
    @FXML private Button btnAppointments;
    @FXML private Button btnAppointmentsPerMonth;
    @FXML private ComboBox cbUsers;
    
    private void setEventHandlers() {
        btnTypeReport.setOnMouseClicked((MouseEvent e) -> {
            int countConsult = 0;
            int countPlan = 0;
            int countWork = 0;
            int countCasual = 0;
            int countOther = 0;
            
            try {
                FetchData data = new FetchData();
                
                countConsult = data.getConsultationCount();
                countPlan = data.getPlanningCount();
                countWork = data.getWorkingCount();
                countCasual = data.getCasualCount();
                countOther = data.getOtherCount();
            } catch(SQLException ex) {
                
            }
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Appointment Type Report");
            alert.setContentText("Consulting: " + countConsult + "\nPlanning:   " + countPlan +
                    "\nWorking:    " + countWork + "\nCasual:    " + countCasual + "\nOther:      " + countOther);
            
            alert.showAndWait();
        });
        
        btnAppointments.setOnMouseClicked((MouseEvent e) -> {
        
        });
        
        btnAppointmentsPerMonth.setOnMouseClicked((MouseEvent e) -> {
        
        });
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String user = FXMLDocumentController.authorizedUser;
        ZonedDateTime time = ZonedDateTime.now();
        
        String message = "Welcome " + user + ", you are logged in at " + time.toString();
        welcome.setText(message);
        
        String zone = TimeZone.getDefault().getDisplayName();
        String locale = "You're current Time Zone is: " + zone;
        
        location.setText(locale);
        
        try {
            FetchData data = new FetchData();
            int appointments = data.getAppointmentCount();
            
            data = new FetchData();
            int customers = data.getCustomerCount();
            
            customerCount.setText("Customers stored: " + customers);
            appointmentCount.setText("Appointments stored: " + appointments);
            
            ArrayList<String> userNames = data.fetchUserNames();
            
            for(String u : userNames) {
                cbUsers.getItems().add(u);
            }
        } catch(SQLException ex) {
            
        }
        
        setEventHandlers();
    }    
    
}
