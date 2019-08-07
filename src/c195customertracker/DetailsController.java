/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.FetchData;
import data.SaveData;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Address;
import models.Appointment;
import models.City;
import models.Country;
import models.Customer;
import util.DateTimeUtils;
import util.TextSanitizer;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class DetailsController implements Initializable {

    @FXML private TextField name;
    @FXML private TextField subject;
    @FXML private TextField location;
    @FXML private TextField type;
    @FXML private TextField dateTime;
    @FXML private TextField endTime;
    @FXML private TextArea details;
    @FXML private TextField tfAddress;
    @FXML private TextField tfAddress2;
    @FXML private TextField tfCity;
   
    @FXML private TextField country;
    @FXML private TextField phone;
    @FXML private TextField phoneType;
    
    @FXML private GridPane gpBoxButtons;
    @FXML private Button btnEdit;
    @FXML private Button btnClose;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Button onBtnCustomerDetailsClick;
    
    private Appointment appointment;
    private Customer customer;
    private Address address;
    private Country countryData;
    private City city;
    
    @FXML 
    private void onBtnCustomerDetailsClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerDetails.fxml"));
        Parent root = loader.load();
        
        CustomerDetailsController controller = loader.getController();
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        controller.customer = customer;

        stage.showAndWait();
    }
    
    @FXML
    private void onBtnEditClick() {
        makeEditable();
    }
    
    @FXML 
    private void onBtnCloseClick() {
       Stage stage = (Stage)btnClose.getScene().getWindow();
       stage.close();
    }
    
    @FXML
    private void onBtnSaveClick() throws SQLException, ClassNotFoundException {
        setData();  
        //checkText();
        saveChanges();
        
        enableEditClose();
    }
    
    @FXML 
    private void onBtnCancelClick() throws SQLException, ClassNotFoundException {
        disableEditing();
        getData();
    }
    
    private void checkText() {
        if(appointment.getDescription().length() > 10) {
            String text = TextSanitizer.sanitizeTextBlock(appointment.getDescription());
        }
    }
    
    private void enableEditClose() {
        btnEdit.setDisable(false);
        btnClose.setDisable(false);
        btnSave.setDisable(true);
        btnCancel.setDisable(true);
        
        btnEdit.setVisible(true);
        btnClose.setVisible(true);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
    }
    
    private void enableSaveCancel() {
        btnEdit.setDisable(true);
        btnClose.setDisable(true);
        btnSave.setDisable(false);
        btnCancel.setDisable(false);
        
        btnEdit.setVisible(false);
        btnClose.setVisible(false);
        btnSave.setVisible(true);
        btnCancel.setVisible(true);
    }
    
    private void makeEditable() {
        enableSaveCancel();
        
        name.setEditable(true);
        subject.setEditable(true);
        location.setEditable(true);
        dateTime.setEditable(true);
        endTime.setEditable(true);
        details.setEditable(true);
        tfAddress.setEditable(true);
        tfAddress2.setEditable(true);
        tfCity.setEditable(true);
        country.setEditable(true);
        phone.setEditable(true);
        
        name.setFocusTraversable(true);
        subject.setFocusTraversable(true);
        location.setFocusTraversable(true);
        dateTime.setFocusTraversable(true);
        endTime.setFocusTraversable(true);
        details.setFocusTraversable(true);
        tfAddress.setFocusTraversable(true);
        tfAddress2.setFocusTraversable(true);
        country.setFocusTraversable(true);
        phone.setFocusTraversable(true);
    }
    
    private void disableEditing() {
        enableEditClose();
        
        name.setEditable(false);
        subject.setEditable(false);
        location.setEditable(false);
        dateTime.setEditable(false);
        endTime.setEditable(false);
        details.setEditable(false);
        tfAddress.setEditable(false);
        tfAddress2.setEditable(false);
        country.setEditable(false);
        phone.setEditable(false);
        phoneType.setEditable(false);
        
        name.setFocusTraversable(false);
        subject.setFocusTraversable(false);
        location.setFocusTraversable(false);
        dateTime.setFocusTraversable(false);
        endTime.setFocusTraversable(false);
        details.setFocusTraversable(false);
        tfAddress.setFocusTraversable(false);
        tfAddress2.setFocusTraversable(false);
        country.setFocusTraversable(false);
        phone.setFocusTraversable(false);
        phoneType.setFocusTraversable(false);
    }
    
    private void saveChanges() throws SQLException, ClassNotFoundException {
        SaveData data = new SaveData();
        data.updateFullRecord(appointment, customer, city, address, countryData);
    }
    
    private void setData() {
        appointment.setTitle(subject.getText());
        appointment.setLocation(location.getText());
        appointment.setDescription(details.getText());
        
    }
    
    public void getAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    public void getData() throws SQLException, ClassNotFoundException {
        subject.setText(appointment.getTitle());
        location.setText(appointment.getLocation());
        
        String formattedStartDate = DateTimeUtils.getFormatedDateTimeString(appointment.getStart());
        String formattedEndDate = DateTimeUtils.getFormatedDateTimeString(appointment.getEnd());
        
        dateTime.setText(formattedStartDate);
        endTime.setText(formattedEndDate);
        details.setText(appointment.getDescription());
        type.setText(appointment.getType());
        
        FetchData data = new FetchData();
        customer = data.fetchSingleCustomer(appointment.getCustomerId());
        name.setText(customer.getName());
        
        address = data.fetchAddress(customer.getAddressId());
        tfAddress.setText(address.getAddress());
        tfAddress2.setText(address.getAddress2());
       
        data = new FetchData();
        city = data.fetchCity(address.getCityId());
        
        countryData = data.fetchCountry(city.getCountryId());
        country.setText(countryData.getCountry());
        
        tfCity.setText(city.getCity());
        
        phone .setText(address.getPhone());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enableEditClose();
    }    
}