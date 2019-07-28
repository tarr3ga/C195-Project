/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.FetchData;
import data.SaveData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;
import util.DateTimeUtils;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class AddAppointmentController implements Initializable {

    public static boolean customerIsSelected = false;
    public static Customer customerSelected;
    
    @FXML private ComboBox cbCustomers;
    @FXML private TextField subject;
    @FXML private TextField location;
    @FXML private TextArea details;
    @FXML private DatePicker startDate;
    @FXML private ComboBox startTime;
    @FXML private ComboBox startTimeAmPm;
    @FXML private DatePicker endDate;
    @FXML private ComboBox endTime;
    @FXML private ComboBox endTimeAmPm;
    @FXML private ComboBox timezone;
    @FXML private ComboBox cbType;
    
    @FXML private Button submit;
    @FXML private Button cancel;
    
    private ObservableList<Customer> customers;
    private final String[] times = {"12:00", "12:15", "12:30", "12:45", "1:00", "1:15", "1:30", "1:45",
                                    "2:00", "2:15", "2:30", "2:45", "3:00", "3:15", "3:30", "3:45",
                                    "4:00", "4:15", "4:30", "4:45", "5:00", "5:15", "5:30", "5:45",
                                    "6:00", "6:15", "6:30", "6:45", "7:00", "7:15", "7:30", "7:45",
                                    "8:00", "8:15", "8:30", "8:45", "9:00", "9:15", "9:30", "9:45",
                                    "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45"};   
    private final String[] timezones = { "GMT+1  CET", "GMT+2  EET", "GMT+3  MSK", "GMT+4  SMT", "GMT+5  PKT", "GMT+6  OMSK", 
                                         "GMT+7  CXT", "GMT+8  CST", "GMT+9  JST", "GMT+10 EAST", "GMT+11 SAKT", "GMT+12 NZT",
                                         "GMT+0  GMT", "GMT-1  WAT", "GMT-2  AT", "GMT-3  ART", "GMT-4  AST", "GMT-5  EST", 
                                         "GMT-6  CST", "GMT-7  MST", "GMT-8  PST", "GMT-9  AKST", 
                                         "GMT-10 HST", "GMT-11 NT", "GMT-12 IDLW" };
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final int OPENING_TIME = 659;
    private static final int CLOSING_TIME = 1801;
    
    private void setEventHandlers() {
        submit.setOnMouseClicked((MouseEvent e) -> {
            String timeZoneName = DateTimeUtils.getTimeZoneName((String)timezone.getSelectionModel().getSelectedItem());
            
            ZoneId zone = TimeZone.getDefault().toZoneId();
            
            ZonedDateTime localStartDateTime = ZonedDateTime.now();
            
            try {
                String[] dateTimeParts = DateTimeUtils.getDateParts(startDate.getValue().toString(), 
                    (String)startTime.getSelectionModel().getSelectedItem(), 
                    (String)startTimeAmPm.getSelectionModel().getSelectedItem());
                
                localStartDateTime = ZonedDateTime.of(LocalDateTime.of(Integer.parseInt(dateTimeParts[0]), 
                        Integer.parseInt(dateTimeParts[1]), Integer.parseInt(dateTimeParts[2]), Integer.parseInt(dateTimeParts[3]), 
                        Integer.parseInt(dateTimeParts[4]) ), zone);
                localStartDateTime.format(formatter);
            } catch(Exception ex) {
                
            }
          
            ZonedDateTime localEndDateTime = ZonedDateTime.now();
            
            try {
                String[] dateTimeParts = DateTimeUtils.getDateParts(endDate.getValue().toString(), 
                        (String)endTime.getSelectionModel().getSelectedItem(), 
                        (String)endTimeAmPm.getSelectionModel().getSelectedItem());
                
                localEndDateTime = ZonedDateTime.of(LocalDateTime.of(Integer.parseInt(dateTimeParts[0]), 
                            Integer.parseInt(dateTimeParts[1]), Integer.parseInt(dateTimeParts[2]), 
                            Integer.parseInt(dateTimeParts[3]), Integer.parseInt(dateTimeParts[4])), zone);
                localEndDateTime.format(formatter);
            } catch(Exception ex) {
                
            }
            
            boolean overlap = true;
            try {
                overlap = checkForOverLappingAppointments(localStartDateTime, localEndDateTime);
            } catch(SQLException ex) {
                
            }
            
            if(validateForm() && checkDates(localStartDateTime, localEndDateTime) && 
                    checkBusinessHours(localStartDateTime, localEndDateTime) && overlap) {
                int id = -1;

                Appointment a = new Appointment();
                a.setSubject(subject.getText());
                a.setLocation(location.getText());
                a.setDescription(details.getText());
                a.setType((String)cbType.getSelectionModel().getSelectedItem());
                
                a.setStart(localStartDateTime);
                a.setEnd(localEndDateTime);
                
                a.setCustomerRep(customerSelected.getCustomerRep());
                
                a.setUserId(FXMLDocumentController.authorizedUserId);

                String cbValue = (String)cbCustomers.getSelectionModel().getSelectedItem();
                String[] parts = cbValue.split(" ");
                a.setCustomerId(Integer.parseInt(parts[0]));

                a.setTimezone((String)timezone.getSelectionModel().getSelectedItem());

                SaveData data = new SaveData();
                try{
                    id = data.saveNewAppointment(a);
                }catch(SQLException ex) {
                    System.err.println(ex.toString());
                }

                // SET 15 MINUTE ALERT
                FetchData fetch = new FetchData();
                Customer c = new Customer();
                try {
                    c = fetch.fetchSingleCustomer(a.getCustomerId());
                } catch(SQLException ex) {
                    System.err.println(ex.toString());
                }

                MainSceneController.setAlert(a.getId(), a.getStart(), c.getFirstName(), c.getLastName(), a.getSubject());

                // WRITE TRANSACTION TO LOG FILE
                File dir = new File("logs/");
                boolean success =  dir.mkdir();

                if(success)
                    System.out.println("Directory created");
                else
                    System.out.println("Directory already exists");

                File file = new File("logs/transactions.txt");

                String message = "";

                if(customerIsSelected) {
                    message = "Updated Appointment ID: " + id + " Updated by " + 
                                FXMLDocumentController.authorizedUser + " on " + ZonedDateTime.now().toString();
                } else {
                    message = "New Appointment ID: " + id + " Created by " + 
                                FXMLDocumentController.authorizedUser + " on " + ZonedDateTime.now().toString();
                }
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                    bufferedWriter.newLine();
                    bufferedWriter.append(message);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch(IOException ex) {

                } 

                Stage stage = (Stage)submit.getScene().getWindow();
                stage.close();
            }
        });
        
        cancel.setOnMouseClicked((MouseEvent e) -> {
            Stage stage = (Stage)cancel.getScene().getWindow();
            stage.close();
        });
    }
   
    private boolean checkForOverLappingAppointments(ZonedDateTime start, ZonedDateTime end) throws SQLException {
        boolean isValid = true;
        Appointment conflict = new Appointment();
        
        FetchData data = new FetchData();
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments = data.fetchAppointmentsForCustomerRep(customerSelected.getCustomerRep());
        
        for(Appointment a : appointments) {
            if(start.isBefore(a.getEnd()) && start.isAfter(a.getEnd())) {
                isValid = false;
                conflict = a;
            }
            
            if(end.isAfter(start) && end.isBefore(end)) {
                isValid = false;
                conflict = a;
            }
        }
        
        if(isValid == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setContentText("Appointment conflics with " + conflict.getSubject() + 
                                 "\nFrom: " + conflict.getStart() + "\nTo:" + conflict.getEnd());
            
            alert.showAndWait();
        }
        
        return isValid;
    }
    
    private boolean checkBusinessHours(ZonedDateTime start, ZonedDateTime end) {
        boolean isValid = true;
        
        String startString;
        String endString ;
        
        if(start.getMinute() == 0)
            startString = "" + start.getHour() + "00";
        else
            startString = "" + start.getHour() + start.getMinute();
        
        if(end.getMinute() == 0)
            endString = "" + end.getHour() + "0";
        else
            endString = "" + end.getHour() + end.getMinute();
        
        
        int startTime = Integer.parseInt(startString);
        int endTime = Integer.parseInt(endString);
        
        if(startTime < OPENING_TIME || endTime > CLOSING_TIME) {
            isValid = false;
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setContentText("Appointment is out of business hours (7:00 AM - 6:00 PM)");
            
            alert.showAndWait();
        }
        
        return isValid;
    }
    
    private boolean checkDates(ZonedDateTime start, ZonedDateTime end) {
        boolean isValid = false;
        
        if(!start.isAfter(end))
            isValid = true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setContentText("End Date is before Start Date");
            
            alert.showAndWait();
        }
            
        return isValid;
    }
    
    private boolean validateForm() {
        boolean isValid = false;
        
        if(!subject.getText().isEmpty() &&
           !location.getText().isEmpty() &&
           !details.getText().isEmpty() &&
           startDate.getValue() != null &&
           endDate.getValue() != null) {
            isValid = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setContentText("All fileds are required.");
            
            alert.showAndWait();
        }
        
        return isValid;
    }
    
    private void setDefaultTimeZone() {
        String zone = TimeZone.getDefault().getDisplayName();
        
        switch(zone) {
            case "Eastern Standard Time":
                timezone.getSelectionModel().select("GMT-5  EST");
                break;
            case "Central Standard Time":
                timezone.getSelectionModel().select("GMT-6  CST");
                break;
            case "Mountain Standard Time":
                timezone.getSelectionModel().select("GMT-7  MST");
                break;
            case "Pacific Standard Time":
                timezone.getSelectionModel().select("GMT-9  PST");
                break;
            case "Alaska Standard Time":
                timezone.getSelectionModel().select("GMT-9 AKST");
                break;
            case "Hawaii Standard Time":
                timezone.getSelectionModel().select("GMT-10 HST");
                break;
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customers = FXCollections.observableArrayList();
        
        try {
            FetchData data = new FetchData();
            customers = data.fetchCustomerData();
        } catch(SQLException ex) {
            
        }
        
        for(Customer c : customers) {
            cbCustomers.getItems().add(c.getId() + " " + c.getFirstName() + " " + c.getLastName());
        }
        
        if(customerIsSelected) {
            int index = -1;
            
            for(Customer c : customers) {
                if(customerSelected.getId() == c.getId()) {
                    index = customers.indexOf(c);
                }
            }
            
            cbCustomers.getSelectionModel().select(index);
        }
        
        for(String s : times) {
            startTime.getItems().add(s);
            endTime.getItems().add(s);
        }
        
        for(String s : timezones) {
            timezone.getItems().add(s);
        }
        
        setDefaultTimeZone();
        
        startTimeAmPm.getItems().add("AM");
        startTimeAmPm.getItems().add("PM");
        endTimeAmPm.getItems().add("AM");
        endTimeAmPm.getItems().add("PM");
        
        startTime.getSelectionModel().selectFirst();
        endTime.getSelectionModel().selectFirst();
        startTimeAmPm.getSelectionModel().selectFirst();
        endTimeAmPm.getSelectionModel().selectFirst();
        
        cbType.getItems().add("Consultation");
        cbType.getItems().add("Planning");
        cbType.getItems().add("Working");
        cbType.getItems().add("Casual");
        cbType.getItems().add("Other");
        cbType.getSelectionModel().selectFirst();
        
        setEventHandlers();
    }      
}