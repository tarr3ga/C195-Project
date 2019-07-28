/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.DeleteData;
import data.FetchData;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Appointment;
import util.DateTimeUtils;

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
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a", Locale.US);
    
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
        
        TableColumn<Appointment, ZonedDateTime> start = new TableColumn<>("Start Date");
        start.setMinWidth(225);
        start.setCellValueFactory(new PropertyValueFactory("start"));
        start.setCellFactory(col -> new TableCell<Appointment, ZonedDateTime>(){
            @Override
            protected void updateItem(ZonedDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(String.format(item.format(formatter)));
            }
        });
        
        TableColumn<Appointment, String> customerRep = new TableColumn<>("Customer Rep");
        customerRep.setMinWidth(175);
        customerRep.setCellValueFactory(new PropertyValueFactory("customerRep"));
        
        TableColumn<Appointment, ZonedDateTime> end = new TableColumn<>("End Date");
        end.setMinWidth(225);
        end.setCellValueFactory(new PropertyValueFactory("end"));
        end.setCellFactory(col -> new TableCell<Appointment, ZonedDateTime>(){
            @Override
            protected void updateItem(ZonedDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(String.format(item.format(formatter)));
            }
        });
        
        appointmentsTable.setItems(appointments);
        appointmentsTable.getColumns().setAll(id, subject, location, start, end, customerRep);
    }
    
    private void setEventHandlers() {
        appointmentsTable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount() >= 2) {
                Appointment a = (Appointment)appointmentsTable.getSelectionModel().getSelectedItem();           
                int index = a.getId();
                
                try {                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                    Parent root = loader.load();
                    
                    DetailsController controller = loader.getController();
                    controller.getAppointment(a);
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    
                    controller.getData();
                    
                    stage.showAndWait();
                } catch(IOException | SQLException ex) {
                    System.err.println(ex.toString());
                }      
            }       
        });
        
        btnView.setOnMouseClicked((MouseEvent e) -> {
           if(appointmentsTable.getSelectionModel().getSelectedItem() != null) {
                Appointment a = (Appointment)appointmentsTable.getSelectionModel().getSelectedItem();           
                int index = a.getId();
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                    Parent root = loader.load();
                    
                    DetailsController controller = loader.getController();
                    controller.getAppointment(a);
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    
                    controller.getData();
                    
                    stage.showAndWait();
                } catch(SQLException | IOException ex) {
                    System.err.println(ex.toString());
                }
            }           
        });
        
        btnDelete.setOnMouseClicked((MouseEvent e) -> {
             if(appointmentsTable.getSelectionModel().getSelectedItem() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning!");
                alert.setHeaderText("Delete Record?");
                alert.setContentText("Are you sure you wan to delete this record?");
                
                Optional<ButtonType> result = alert.showAndWait();
                
                if(result.get() == ButtonType.OK) {
                    try {
                        Appointment a = (Appointment)appointmentsTable.getSelectionModel().getSelectedItem();
                        
                        DeleteData delete= new DeleteData();
                        delete.deleteAppointment(a.getId());
                    } catch(SQLException ex) {
                        
                    }
                
                } else {
                    alert.close();
                }
            }
        });
        
        btnFilter.setOnMouseClicked((MouseEvent e) -> {
            if(startDate.getValue() != null && endDate.getValue() != null) {
                LocalDate start = startDate.getValue();
                LocalDate end = endDate.getValue();
                
                String zone = TimeZone.getDefault().getID();
                
                ZonedDateTime startDateTime = ZonedDateTime.of(LocalDateTime.of(start.getYear(), 
                        start.getMonth(), start.getDayOfMonth(), 0, 0), ZoneId.of(zone));
                startDateTime.format(formatter);
                ZonedDateTime endDateTime = ZonedDateTime.of(LocalDateTime.of(end.getYear(), 
                        end.getMonth(), end.getDayOfMonth(), 23, 59), ZoneId.of(zone));
                startDateTime.format(formatter);
                
                appointments.clear();
                
                try {
                    FetchData data = new FetchData();
                    appointments = data.fetchAppointmentsInDateRange(startDateTime, endDateTime);
                    adjustForTimezones();
                    populateTable();
                } catch(SQLException ex) {
                    
                }
            }
        });
    }
    
    private void adjustForTimezones() {
        for(Appointment a : appointments) {
            String start = DateTimeUtils.getFormatedDateTimeString(a.getStart());
            String end = DateTimeUtils.getFormatedDateTimeString(a.getEnd());
            
            DateTimeUtils.adjustTimeForTimezone(start, end, a.getTimezone());
        }
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
        
        setEventHandlers();
    } 
}