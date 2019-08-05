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
    @FXML private ComboBox state;
    @FXML private TextField zip;
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
    
    private void setEventHandlers() {
        btnSubmit.setOnMouseClicked((MouseEvent e) -> {
            try {
                if(validateForm()) {

                    int id = -1;

                    Customer c = new Customer();
                    if(isEditing == true) {
                        c.setCustomerId(customerToEdit.getCustomerId());
                    }
                    c.setName(name.getText());

                    SaveData data = new SaveData();

                    if(isEditing == false) {
                        id = data.saveNewCustomer(c);
                        data.close();
                    }

                    Address a = new Address();
                    a.setAddress(address.getText());
                    a.setAddress2(address2.getText());
                    //TODO Need to convert to cityId
                    
                    int countryId = country.getSelectionModel().getSelectedIndex() -1;
                    
                    City ci = new City();
                    ci.setCity(city.getText());
                    ci.setCountryId(countryId);
                    ci.setCreatedBy(FXMLDocumentController.authorizedUserId);
                    
                    int cityId = data.saveNewCity(ci);
                    
                    a.setCityId(cityId);

                    data = new SaveData();

                    if(isEditing == false) {
                        id = data.saveNewAddress(a);
                        data.close();
                    } else {
                        data.updateCustomerRecord(c, a);
                    }

                    data = new SaveData();

                    File dir = new File("logs/");
                    boolean success =  dir.mkdir();

                    if(success)
                        System.out.println("Directory created");
                    else
                        System.out.println("Directory already exists");

                    File file = new File("logs/transactions.txt");

                    String message = "";

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
           !zip.getText().isEmpty() &&
           !state.getSelectionModel().isEmpty() &&
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
        
        country.getSelectionModel().select("US | United States");
        
        if(isEditing == true) {
            FetchData data = new FetchData();
            Customer cto = new Customer();
            Address a = new Address();
            City ci = new City();
            Country co = new Country();
            
            try {
                cto = data.fetchSingleCustomer(customerToEdit.getCustomerId());
                
                data = new FetchData();
                a = data.fetchAddress(customerToEdit.getAddressId());
                
                ci = data.fetchCity(a.getCityId());
                
                data = new FetchData();
                co = data.fetchCountry(ci.getCountryId());
            } catch(SQLException ex) {
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            name.setText(cto.getName());
            address.setText(a.getAddress());
            city.setText(ci.getCity());
            
            String countryFull = co.getCountryAbreviation() + " | " + co.getCountry();
            country.getSelectionModel().select(countryFull);
        }
    }       
}