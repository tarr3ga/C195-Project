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
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    @FXML private Button submit;
    @FXML private Button cancel;
    
    private ObservableList<Customer> customers;
    private final String[] times = {"12:00", "12:15", "12:30", "12:45", "1:00", "1:15", "1:30", "1:45",
                                    "2:00", "2:15", "2:30", "2:45", "3:00", "3:15", "3:30", "3:45",
                                    "4:00", "4:15", "4:30", "4:45", "5:00", "5:15", "5:30", "5:45",
                                    "6:00", "6:15", "6:30", "6:45", "7:00", "7:15", "7:30", "7:45",
                                    "8:00", "8:15", "8:30", "8:45", "9:00", "9:15", "9:30", "9:45",
                                    "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45"};
    
    private final String[] timezones = { "EST", "CST", "MST", "AST", "HST" };
    
    private void setEventHandlers() {
        submit.setOnMouseClicked((MouseEvent e) -> {
            int id = -1;
            
            Appointment a = new Appointment();
            a.setSubject(subject.getText());
            a.setLocation(location.getText());
            a.setDescription(details.getText());
            
            String[] dateTimeParts = DateTimeUtils.getDateParts(startDate.getValue().toString(), 
                    (String)startTime.getSelectionModel().getSelectedItem(), 
                    (String)startTimeAmPm.getSelectionModel().getSelectedItem());
            LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(dateTimeParts[0]), 
                    Integer.parseInt(dateTimeParts[1]), Integer.parseInt(dateTimeParts[2]), 
                    Integer.parseInt(dateTimeParts[3]), Integer.parseInt(dateTimeParts[4]));
            a.setStart(localDateTime);
            
            dateTimeParts = DateTimeUtils.getDateParts(endDate.getValue().toString(), 
                    (String)endTime.getSelectionModel().getSelectedItem(), 
                    (String)endTimeAmPm.getSelectionModel().getSelectedItem());
            localDateTime = LocalDateTime.of(Integer.parseInt(dateTimeParts[0]), 
                    Integer.parseInt(dateTimeParts[1]), Integer.parseInt(dateTimeParts[2]), 
                    Integer.parseInt(dateTimeParts[3]), Integer.parseInt(dateTimeParts[4]));
            a.setEnd(localDateTime);
            
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
            
            String message = "New Appointment ID: " + id + " Created by " + 
                            FXMLDocumentController.authorizedUser + " on " + LocalDateTime.now().toString();
                    
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
        });
        
        cancel.setOnMouseClicked((MouseEvent e) -> {
            Stage stage = (Stage)cancel.getScene().getWindow();
            stage.close();
        });
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
        
        String zone = TimeZone.getDefault().getDisplayName();
        
        switch(zone) {
            case "Eastern Standard Time":
                timezone.getSelectionModel().select("EST");
                break;
            case "Central Standard Time":
                timezone.getSelectionModel().select("CST");
                break;
            case "Mountain Standard Time":
                timezone.getSelectionModel().select("MST");
                break;
            case "Alaska Standard Time":
                timezone.getSelectionModel().select("AST");
                break;
            case "Hawaii Standard Time":
                timezone.getSelectionModel().select("HST");
                break;
        }
        
        startTimeAmPm.getItems().add("AM");
        startTimeAmPm.getItems().add("PM");
        endTimeAmPm.getItems().add("AM");
        endTimeAmPm.getItems().add("PM");
        
        startTime.getSelectionModel().selectFirst();
        endTime.getSelectionModel().selectFirst();
        startTimeAmPm.getSelectionModel().selectFirst();
        endTimeAmPm.getSelectionModel().selectFirst();
        
        setEventHandlers();
    }     
}