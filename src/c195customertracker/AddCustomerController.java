/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.FetchData;
import data.SaveData;
import java.net.URL;
import java.sql.SQLException;
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
    
    @FXML TextField firstName;
    @FXML TextField lastName;
    @FXML TextField phone;
    @FXML ComboBox phoneType;
    @FXML TextField street;
    @FXML TextField city;
    @FXML ComboBox state;
    @FXML TextField zip;
    @FXML ComboBox country;
    
    @FXML Button btnSubmit;
    @FXML Button btnCancel;
    
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
                    Customer c = new Customer();
                    c.setFirstName(firstName.getText());
                    c.setLastName(lastName.getText());
                    
                    SaveData data = new SaveData();
                    int id = data.saveNewCustomer(c);
                    
                    Address a = new Address();
                    a.setStreet(street.getText());
                    a.setCity(city.getText());
                    a.setState(state.getSelectionModel().getSelectedItem().toString());
                    a.setZip(zip.toString());
                    a.setCountryId(country.getSelectionModel().getSelectedIndex() + 1);
                    a.setCustomerId(id);
                    
                    data = new SaveData();
                    data.saveNewAddress(a);
                    
                    PhoneNumber p = new PhoneNumber();
                    p.setPhone(phone.getText());
                    p.setPhoneType(phoneType.getSelectionModel().getSelectedItem().toString());
                    p.setCustomerId(id);
                    
                    data = new SaveData();
                    data.saveNewPhone(p);
                    
                    Stage stage = (Stage)btnSubmit.getScene().getWindow();
                    stage.close();
                } catch(SQLException ex) {
                    
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
    }    
    
}