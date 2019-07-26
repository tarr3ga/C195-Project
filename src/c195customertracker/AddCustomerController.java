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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Address;
import models.Country;
import models.Customer;
import models.PhoneNumber;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class AddCustomerController implements Initializable {
    
    public static boolean isEditing = false;
    public static Customer customerToEdit;
    
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField phone;
    @FXML private ComboBox phoneType;
    @FXML private TextField street;
    @FXML private TextField city;
    @FXML private ComboBox state;
    @FXML private TextField zip;
    @FXML private ComboBox country;
    
    @FXML private Button btnSubmit;
    @FXML private Button btnCancel;
    
    private final String[] phoneTypes = {"Home", "Cell", "Work"};
    private final String[] states = {"AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", 
                                     "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", 
                                     "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", 
                                     "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", 
                                     "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", 
                                     "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", 
                                     "UM", "UT", "VA", "VI", "VT", "WA", "WI", "WV", 
                                     "WY"};
    
    private void setEventHandlers() {
        btnSubmit.setOnMouseClicked((MouseEvent e) -> {
            if(firstName.lengthProperty().getValue() > 0 && lastName.lengthProperty().getValue() > 0) {
                try {
                    int id = -1;
                    
                    Customer c = new Customer();
                    if(isEditing == true) {
                        c.setId(customerToEdit.getId());
                    }
                    c.setFirstName(firstName.getText());
                    c.setLastName(lastName.getText());
                    
                    c.setCustomerRep(FXMLDocumentController.authorizedUser);
                    
                    SaveData data = new SaveData();
                    
                    if(isEditing == false) {
                        id = data.saveNewCustomer(c);
                        data.close();
                    }
                    
                    Address a = new Address();
                    a.setStreet(street.getText());
                    a.setCity(city.getText());
                    a.setState(state.getSelectionModel().getSelectedItem().toString());
                    a.setZip(zip.getText());
                    a.setCountryId(country.getSelectionModel().getSelectedIndex() + 1);
                    a.setCustomerId(id);
                    
                    data = new SaveData();
                    
                    if(isEditing == false) {
                        id = data.saveNewAddress(a);
                        data.close();
                    }
                    
                    PhoneNumber p = new PhoneNumber();
                    p.setPhone(phone.getText());
                    p.setPhoneType(phoneType.getSelectionModel().getSelectedItem().toString());
                    p.setCustomerId(id);
                    
                    data = new SaveData();
                    
                    if(isEditing == false) {
                        data.saveNewPhone(p);
                    } else {
                        data.updateCustomerRecord(c, a, p);
                    }
                    
                    File dir = new File("logs/");
                    boolean success =  dir.mkdir();
                    
                    if(success)
                        System.out.println("Directory created");
                    else
                        System.out.println("Directory already exists");
                    
                    File file = new File("logs/transactions.txt");
                    
                    String message = "";
                    
                    if(isEditing) {
                    message = "Updated Customer ID: " + c.getId() + " Updated by " +        
                            FXMLDocumentController.authorizedUser + " on " + ZonedDateTime.now().toString();    
                    } else {
                    message = "New Customer ID: " + id + " Created by " +        
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
                    
                    Stage stage = (Stage)btnSubmit.getScene().getWindow();
                    stage.close();
                } catch(SQLException ex) {
                    System.out.println("Update Customer" + ex.toString());
                }
            }
        });
        
        btnCancel.setOnMouseClicked((MouseEvent e) -> {
            Stage stage = (Stage)btnCancel.getScene().getWindow();
            stage.close();
        });
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setEventHandlers();
         
        for(String s: phoneTypes) {
            phoneType.getItems().add(s);
        }
        
        for(String s: states) {
            state.getItems().add(s);
        }
        
        try {
            FetchData data = new FetchData();
            ArrayList<Country> countriesList = data.fetchCountries();
            
            for(Country c : countriesList) {
                String entry = c.getCountryAbreviation() + " | " + c.getCountry();
                country.getItems().add(entry);
            }
        } catch(SQLException ex) {
            System.err.println(ex.toString());
        } catch(Exception ex) {
            System.err.println(ex.toString());
        }
        
        if(isEditing == true) {
            FetchData data = new FetchData();
            Customer cto = new Customer();
            Address a = new Address();
            PhoneNumber p = new PhoneNumber();
            Country co = new Country();
            
            try {
                cto = data.fetchSingleCustomer(customerToEdit.getId());
                
                data = new FetchData();
                a = data.fetchAddress(customerToEdit.getId());
                
                data = new FetchData();
                p = data.fetchPhoneNumber(customerToEdit.getId());
                
                data = new FetchData();
                co = data.fetchCountry(a.getCountryId());
            } catch(SQLException ex) {
                
            }
            
            firstName.setText(cto.getFirstName());
            lastName.setText(cto.getLastName());
            phone.setText(p.getPhone());
            phoneType.getSelectionModel().select(p.getPhoneType());
            street.setText(a.getStreet());
            city.setText(a.getCity());
            state.getSelectionModel().select(a.getState());
            zip.setText(a.getZip());
            
            String countryFull = co.getCountryAbreviation() + " | " + co.getCountry();
            country.getSelectionModel().select(countryFull);
        }
    }       
}