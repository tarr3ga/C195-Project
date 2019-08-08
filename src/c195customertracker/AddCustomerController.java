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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Address;
import models.City;
import models.Country;
import models.Customer;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class AddCustomerController implements Initializable {
    
    public static boolean isEditing = false;
    public static Customer customerToEdit;
    
    @FXML private TextField name;
    @FXML private TextField phone;
    @FXML private TextField address;
    @FXML private TextField address2;
    @FXML private TextField city;
    @FXML private TextField postalCode;
    //@FXML private ComboBox state;
    @FXML private ComboBox country;
    @FXML private Button btnSubmit;
    @FXML private Button btnCancel;
    
    private final String[] states = {"AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", 
                                     "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", 
                                     "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", 
                                     "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", 
                                     "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", 
                                     "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", 
                                     "UM", "UT", "VA", "VI", "VT", "WA", "WI", "WV", 
                                     "WY"};
    
    private Customer c;
    //private Customer cto;
    private Address a;
    private City ci;
    private Country co;
    
    private void setEventHandlers() {
        btnSubmit.setOnMouseClicked((MouseEvent e) -> {
            try {
                if(validateForm()) {

                    System.out.println("c195customertracker.AddCustomerController.setEventHandlers() isEditing = " + isEditing);
                    
                    int id = -1;

                    //c = new Customer();
                    if(isEditing) {
                        c.setCustomerId(customerToEdit.getCustomerId());
                    }
                    c.setName(name.getText());
                    c.setActive(true);
                    
                     if(isEditing) {
                        //c.setCreateDate(c.getCreateDate());
                        //c.setCreatedBy(c.getCreatedBy());
                        c.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
                        c.setUpdatedBy(FXMLDocumentController.authorizedUserId);
                    } else {
                        c.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
                        c.setCreatedBy(FXMLDocumentController.authorizedUserId);
                        c.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
                        c.setUpdatedBy(FXMLDocumentController.authorizedUserId);
                    }
                    
                    //a = new Address();
                    a.setCustomerId(c.getCustomerId());
                    a.setAddress(address.getText());
                    a.setAddress2(address2.getText());
                    a.setPostalCode(postalCode.getText());
                    a.setPhone(phone.getText());
                    
                    if(isEditing) {
                        //a.setCreateDate(c.getCreateDate());
                        //a.setCreatedBy(c.getCreatedBy());
                        a.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
                        a.setUpdatedBy(FXMLDocumentController.authorizedUserId);
                    } else {
                        a.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
                        a.setCreatedBy(FXMLDocumentController.authorizedUserId);
                        a.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
                        a.setUpdatedBy(FXMLDocumentController.authorizedUserId);
                    }
                    
                    int countryId = country.getSelectionModel().getSelectedIndex() -1;
                    //City ci = new City();
                    ci.setCity(city.getText());
                    ci.setCountryId(countryId);
                    
                    if(isEditing) {
                        ci.setCreateDate(ci.getCreateDate());
                        ci.setCreatedBy(ci.getCreatedBy());
                        ci.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
                        ci.setUpdatedBy(FXMLDocumentController.authorizedUserId);
                    } else {
                        ci.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
                        ci.setCreatedBy(FXMLDocumentController.authorizedUserId);
                        ci.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
                        ci.setUpdatedBy(FXMLDocumentController.authorizedUserId);
                    }
                    
                    SaveData data = new SaveData();
                    
                    int cityId;
                    
                    if(isEditing) {
                        
                    } else {
                        cityId = data.saveNewCity(ci);  
                        a.setCityId(cityId);
                    }
                  
                    if(isEditing) {
                        data.updateCustomerRecord(c, ci, a);
                    } else {
                        id = data.saveNewAddress(a);
                        data.close();
                    }
                    
                    c.setAddressId(id);
                    
                    if(isEditing == false) {
                        id = data.saveNewCustomer(c);
                        data.close();
                    }

                    File dir = new File("logs/");
                    boolean success = dir.mkdir();

                    if(success)
                        System.out.println("Directory created");
                    else
                        System.out.println("Directory already exists");

                    File file = new File("logs/transactions.txt");

                    String message;

                    if(isEditing) {
                    message = "Updated Customer ID: " + c.getCustomerId() + " Updated by " +        
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
                }
            } catch(SQLException ex) {
                System.out.println("Update Customer" + ex.toString());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }        
        });
        
        btnCancel.setOnMouseClicked((MouseEvent e) -> {
            Stage stage = (Stage)btnCancel.getScene().getWindow();
            stage.close();
        });
    }
    
    private boolean validateForm() {
        boolean isValid = false;
        
        if(!name.getText().isEmpty() &&
           !phone.getText().isEmpty() &&
           !address.getText().isEmpty() &&
           !city.getText().isEmpty() &&
           !postalCode.getText().isEmpty() &&
          // !state.getSelectionModel().isEmpty() &&
           !country.getSelectionModel().isEmpty()) {
            isValid = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("All Fields Are Required.");
            
            alert.showAndWait();
        }
        
        return isValid;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setEventHandlers();
         
        /*for(String s: states) {
            state.getItems().add(s);
        }*/
        
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
        
        country.getSelectionModel().select("US | United States");
        
        c = new Customer();
        a = new Address();
        ci = new City();
        co = new Country();
        
        if(isEditing == true) {
            FetchData data = new FetchData();
            c = new Customer();
            a = new Address();
            ci = new City();
            co = new Country();
            
            try {
                c = data.fetchSingleCustomer(customerToEdit.getCustomerId());
                
                data = new FetchData();
                a = data.fetchAddress(customerToEdit.getAddressId());
                
                ci = data.fetchCity(a.getCityId());
                
                data = new FetchData();
                co = data.fetchCountry(ci.getCountryId());
            } catch(SQLException ex) {
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            name.setText(c.getName());
            phone.setText(a.getPhone());
            address.setText(a.getAddress());
            address2.setText(a.getAddress2());
            postalCode.setText(a.getPostalCode());
            city.setText(ci.getCity());
            
            String countryFull = co.getCountryAbreviation() + " | " + co.getCountry();
            country.getSelectionModel().select(countryFull);
        }
    }       
}