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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Customer;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class CustomerDetailsController implements Initializable {

    public Customer customer;
    
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField street;
    @FXML private TextField city;
    @FXML private TextField state;
    @FXML private TextField zip;
    @FXML private TextField country;
    @FXML private TextField phone;
    @FXML private TextField phoneType;
    @FXML private TextField rep;
    @FXML private Button btnClose;
    
    @FXML
    private void onCloseClick() {
        
    }
    
    private void populateTable() throws SQLException {
        FetchData data = new FetchData();
        
        Customer c = new Customer();
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            populateTable();
        } catch(SQLException ex) {
            
        }
    }    
    
}
