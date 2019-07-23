/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.FetchData;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Appointment;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class AppointmentsController implements Initializable {
    @FXML private TableView appointmentsTable;
    @FXML private Button btnView;
    @FXML private Button btnDelete;
    @FXML private Button btnFilter;
    
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();;
    
    private void populateTable() {
        TableColumn<Appointment, Integer> id = new TableColumn("ID");
        id.setMinWidth(20);
        id.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn<Appointment, String> subject = new TableColumn<>("Subject");
        subject.setMinWidth(175);
        subject.setCellValueFactory(new PropertyValueFactory("subject"));
        
        TableColumn<Appointment, String> location = new TableColumn<>("Location");
        location.setMinWidth(175);
        location.setCellValueFactory(new PropertyValueFactory("location"));
        
        TableColumn<Appointment, String> start = new TableColumn<>("Start Date");
        start.setMinWidth(175);
        start.setCellValueFactory(new PropertyValueFactory("start"));
        
        TableColumn<Appointment, String> end = new TableColumn<>("End Date");
        end.setMinWidth(175);
        end.setCellValueFactory(new PropertyValueFactory("end"));
        
        appointmentsTable.setItems(appointments);
        appointmentsTable.getColumns().setAll(id, subject, location, start, end);
    }
    
    private void setEventHandlers() {
        btnView.setOnMouseClicked((MouseEvent e) -> {
            
        });
        
        btnDelete.setOnMouseClicked((MouseEvent e) -> {
        
        });
        
        btnFilter.setOnMouseClicked((MouseEvent e) -> {
        
        });
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
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        }
        
        populateTable();
    }    
    
}
