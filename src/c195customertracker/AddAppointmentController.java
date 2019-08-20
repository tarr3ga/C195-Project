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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @FXML private TextField title;
    @FXML private TextField location;
    @FXML private TextArea details;
    @FXML private DatePicker startDate;
    @FXML private ComboBox startTime;
    @FXML private ComboBox startTimeAmPm;
    @FXML private ComboBox endTime;
    @FXML private ComboBox endTimeAmPm;
    @FXML private ComboBox timezone;
    @FXML private ComboBox cbType;
    
    @FXML private Button submit;
    @FXML private Button cancel;
    
    private ObservableList<Customer> customers;
   
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final int OPENING_TIME = 659;
    private static final int CLOSING_TIME = 1800;
    private String defaultTimeZone;
    
    private void setEventHandlers() {
        submit.setOnMouseClicked((MouseEvent e) -> {
            String timeZoneName = DateTimeUtils.getTimeZoneName((String)timezone.getSelectionModel().getSelectedItem());
            
            ZoneId zone;
            
            if(!timeZoneName.equals(defaultTimeZone)) {
                zone = ZoneId.of(timeZoneName);
            } else {
                zone = TimeZone.getDefault().toZoneId();
            }
            
            ZonedDateTime localStartDateTime = ZonedDateTime.now();
            
            try {
                String[] dateTimeParts = DateTimeUtils.getDateParts(startDate.getValue().toString(), 
                    (String)startTime.getSelectionModel().getSelectedItem(), 
                    (String)startTimeAmPm.getSelectionModel().getSelectedItem());
                
                localStartDateTime = ZonedDateTime.of(LocalDateTime.of(Integer.parseInt(dateTimeParts[0]), 
                        Integer.parseInt(dateTimeParts[1]), Integer.parseInt(dateTimeParts[2]), Integer.parseInt(dateTimeParts[3]), 
                        Integer.parseInt(dateTimeParts[4]) ), zone);
                localStartDateTime.format(formatter);
            } catch(NumberFormatException ex) {
                
            }
          
            ZonedDateTime localEndDateTime = ZonedDateTime.now();
            
            try {
                String[] dateTimeParts = DateTimeUtils.getDateParts(startDate.getValue().toString(), 
                        (String)endTime.getSelectionModel().getSelectedItem(), 
                        (String)endTimeAmPm.getSelectionModel().getSelectedItem());
                
                localEndDateTime = ZonedDateTime.of(LocalDateTime.of(Integer.parseInt(dateTimeParts[0]), 
                            Integer.parseInt(dateTimeParts[1]), Integer.parseInt(dateTimeParts[2]), 
                            Integer.parseInt(dateTimeParts[3]), Integer.parseInt(dateTimeParts[4])), zone);
                localEndDateTime.format(formatter);
            } catch(NumberFormatException ex) {
                System.err.println(ex.toString());
            }
            
            boolean overlap = true;
            try {
                overlap = checkForOverLappingAppointments(localStartDateTime, localEndDateTime);
            } catch(SQLException ex) {
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(validateForm() && checkDates(localStartDateTime, localEndDateTime) && 
                    checkBusinessHours(localStartDateTime, localEndDateTime) && overlap) {
                int id = -1;

                Appointment a = new Appointment();
                a.setTitle(title.getText());
                a.setLocation(location.getText());
                a.setDescription(details.getText());
                a.setType((String)cbType.getSelectionModel().getSelectedItem());
                
                a.setStart(localStartDateTime);
                a.setEnd(localEndDateTime);
                
                a.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
                a.setCreatedBy(FXMLDocumentController.authorizedUserId);
                a.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
                a.setUpdatedBy(FXMLDocumentController.authorizedUserId);
                
                a.setUserId(FXMLDocumentController.authorizedUserId);

                String cbValue = (String)cbCustomers.getSelectionModel().getSelectedItem();
                String[] parts = cbValue.split(" ");
                a.setCustomerId(Integer.parseInt(parts[0]));
                
                SaveData data = new SaveData();
                try{
                    id = data.saveNewAppointment(a);
                }catch(SQLException ex) {
                    System.err.println(ex.toString());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
                }

                // SET 15 MINUTE ALERT
                FetchData fetch = new FetchData();
                Customer c = new Customer();
                try {
                    c = fetch.fetchSingleCustomer(a.getCustomerId());
                } catch(SQLException ex) {
                    System.err.println(ex.toString());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
                }

                MainSceneController.setAlert(a.getAppointmentId(), a.getStart(), c.getName(), a.getTitle());

                // WRITE TRANSACTION TO LOG FILE
                File dir = new File("logs/");
                boolean success =  dir.mkdir();

                if(success)
                    System.out.println("Directory created");
                else
                    System.out.println("Directory already exists");

                File file = new File("logs/transactions.txt");

                String message;// = "";

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
   
    private boolean checkForOverLappingAppointments(ZonedDateTime start, ZonedDateTime end) throws SQLException, ClassNotFoundException {
        boolean isValid = true;
        Appointment conflict = new Appointment();
        
        FetchData data = new FetchData();
        
        ObservableList<Appointment> appointments;
        appointments = data.fetchAppointmentsForCustomerData(customerSelected);
        
        for(Appointment a : appointments) {
            ZonedDateTime t1 =  DateTimeUtils.getUnalteredZonedDateTimeFromString(String.valueOf(a.getStart()));
            ZonedDateTime t2 =  DateTimeUtils.getUnalteredZonedDateTimeFromString(String.valueOf(a.getEnd()));
            
            if(start.isBefore(t2) && start.isAfter(t1)) {
                isValid = false;
                conflict = a;
            }
            
            if(end.isAfter(t1) && end.isBefore(t2)) {
                isValid = false;
                conflict = a;
            }
            
            if(start.isBefore(t1) && end.isAfter(t2)) {
                isValid = false;
                conflict = a;
            }
            
            if(start.equals(t1) && end.equals(t2)) {
                isValid = false;
                conflict = a;
            }
        }
        
        if(isValid == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setContentText("Appointment conflics with " + conflict.getTitle() + 
                                 "\nFrom: " + conflict.getStart() + "\nTo: " + conflict.getEnd());
            
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
            endString = "" + end.getHour() + "00";
        else
            endString = "" + end.getHour() + end.getMinute();
        
        
        int startTimeInt = Integer.parseInt(startString);
        int endTimeInt = Integer.parseInt(endString);
        
        if(startTimeInt < OPENING_TIME || endTimeInt >= CLOSING_TIME) {
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
        
        if(!title.getText().isEmpty() &&
           !location.getText().isEmpty() &&
           !details.getText().isEmpty() &&
           startDate.getValue() != null) {
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
        
        System.out.println("c195customertracker.AddAppointmentController.setDefaultTimeZone()  = " + zone);
        
        switch(zone) {
            case "Atlantic Standard Time":
                timezone.getSelectionModel().select("GMT-4  EST");
                defaultTimeZone = "GMT-4  EST";
                break;
            case "Eastern Standard Time":
                timezone.getSelectionModel().select("GMT-5  EST");
                defaultTimeZone = "GMT-5  EST";
                break;
            case "Central Standard Time":
                timezone.getSelectionModel().select("GMT-6  CST");
                defaultTimeZone = "GMT-6  CST";
                break;
            case "Mountain Standard Time":
                timezone.getSelectionModel().select("GMT-7  MST");
                defaultTimeZone = "GMT-7  MST";
                break;
            case "Pacific Standard Time":
                timezone.getSelectionModel().select("GMT-9  PST");
                defaultTimeZone = "GMT-9  PST";
                break;
            case "Alaska Standard Time":
                timezone.getSelectionModel().select("GMT-9 AKST");
                defaultTimeZone = "GMT-9 AKST";
                break;
            case "Hawaii Standard Time":
                timezone.getSelectionModel().select("GMT-10 HST");
                defaultTimeZone = "GMT-10 HST";
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
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(Customer c : customers) {
            cbCustomers.getItems().add(c.getCustomerId() + " " + c.getName());
        }
        
        if(customerIsSelected) {
            int index = -1;
            
            for(Customer c : customers) {
                if(customerSelected.getCustomerId() == c.getCustomerId()) {
                    index = customers.indexOf(c);
                }
            }
            
            cbCustomers.getSelectionModel().select(index);
        }
        
        for(String s : DateTimeUtils.TIMES) {
            startTime.getItems().add(s);
            endTime.getItems().add(s);
        }
        
        for(String s : DateTimeUtils.TIMEZONES) {
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