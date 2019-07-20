/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.FetchData;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class HomeController implements Initializable {

    @FXML private Label welcome;
    @FXML private Label customerCount;
    @FXML private Label appointmentCount;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String user = FXMLDocumentController.authorizedUser;
        LocalDateTime time = LocalDateTime.now();
        
        String message = "Welcome " + user + ", you are logged in at " + time.toString();
        welcome.setText(message);
        
        try {
            FetchData data = new FetchData();
            int appointments = data.getAppointmentCount();
            
            data = new FetchData();
            int customers = data.getCustomerCount();
            
            customerCount.setText("Users stored: " + customers);
            appointmentCount.setText("Appointments stored: " + appointments);
        } catch(SQLException ex) {
            
        }
    }    
    
}
