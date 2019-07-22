/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.FetchData;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Appointment;
import models.Customer;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class AppointmentsController implements Initializable {
    @FXML private TableView appointmentsTable;
    
    private ObservableList<Appointment> appointments;
    private ObservableList<Customer> customers;
    private ArrayList<ArrayList<Integer>> combinedList;
    
    private void populateTable() {
       
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FetchData data = new FetchData();
            appointments = data.fetchAppointmentData();
            
            data = new FetchData();
            customers = data.fetchCustomerData();
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        populateTable();
    }    
    
}
