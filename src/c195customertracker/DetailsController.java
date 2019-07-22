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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Address;
import models.Appointment;
import models.Country;
import models.Customer;
import models.PhoneNumber;
import util.DateTimeUtils;
import util.TextSanitizer;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class DetailsController implements Initializable {

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField subject;
    @FXML private TextField location;
    @FXML private TextField dateTime;
    @FXML private TextField endTime;
    @FXML private TextArea details;
    @FXML private TextField street;
    @FXML private TextField city;
    @FXML private TextField state;
    @FXML private TextField zip;
    @FXML private TextField country;
    @FXML private TextField phone;
    @FXML private TextField phoneType;
    
    @FXML private GridPane gpBoxButtons;
    @FXML private Button btnEdit;
    @FXML private Button btnClose;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    
    private Appointment appointment;
    private Customer customer;
    private Address address;
    private Country countryData;
    private PhoneNumber phoneNumber;
    
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
    private void onBtnSaveClick() throws SQLException {
        setData();  
        //checkText();
        saveChanges();
        
        enableEditClose();
    }
    
    @FXML 
    private void onBtnCancelClick() throws SQLException {
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
        
        firstName.setEditable(true);
        lastName.setEditable(true);
        subject.setEditable(true);
        location.setEditable(true);
        dateTime.setEditable(true);
        endTime.setEditable(true);
        details.setEditable(true);
        street.setEditable(true);
        city.setEditable(true);
        state.setEditable(true);
        zip.setEditable(true);
        country.setEditable(true);
        phone.setEditable(true);
        phoneType.setEditable(true);
        
        firstName.setFocusTraversable(true);
        lastName.setFocusTraversable(true);
        subject.setFocusTraversable(true);
        location.setFocusTraversable(true);
        dateTime.setFocusTraversable(true);
        endTime.setFocusTraversable(true);
        details.setFocusTraversable(true);
        street.setFocusTraversable(true);
        city.setFocusTraversable(true);
        state.setFocusTraversable(true);
        zip.setFocusTraversable(true);
        country.setFocusTraversable(true);
        phone.setFocusTraversable(true);
        phoneType.setFocusTraversable(true);
    }
    
    private void disableEditing() {
        enableEditClose();
        
        firstName.setEditable(false);
        lastName.setEditable(false);
        subject.setEditable(false);
        location.setEditable(false);
        dateTime.setEditable(false);
        endTime.setEditable(false);
        details.setEditable(false);
        street.setEditable(false);
        city.setEditable(false);
        state.setEditable(false);
        zip.setEditable(false);
        country.setEditable(false);
        phone.setEditable(false);
        phoneType.setEditable(false);
        
        firstName.setFocusTraversable(false);
        lastName.setFocusTraversable(false);
        subject.setFocusTraversable(false);
        location.setFocusTraversable(false);
        dateTime.setFocusTraversable(false);
        endTime.setFocusTraversable(false);
        details.setFocusTraversable(false);
        street.setFocusTraversable(false);
        city.setFocusTraversable(false);
        state.setFocusTraversable(false);
        zip.setFocusTraversable(false);
        country.setFocusTraversable(false);
        phone.setFocusTraversable(false);
        phoneType.setFocusTraversable(false);
    }
    
    private void saveChanges() throws SQLException {
        SaveData data = new SaveData();
        data.updateFullRecord(appointment, customer, address, countryData, phoneNumber);
    }
    
    private void setData() {
        appointment.setSubject(subject.getText());
        appointment.setLocation(location.getText());
        appointment.setDescription(details.getText());
        
    }
    
    public void getAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    public void getData() throws SQLException {
        subject.setText(appointment.getSubject());
        location.setText(appointment.getLocation());
        
        String formattedStartDate = DateTimeUtils.getFormatedDateTimeString(appointment.getStart());
        String formattedEndDate = DateTimeUtils.getFormatedDateTimeString(appointment.getEnd());
        
        dateTime.setText(formattedStartDate);
        endTime.setText(formattedEndDate);
        details.setText(appointment.getDescription());
        
        
        FetchData data = new FetchData();
        customer = data.fetchSingleCustomer(appointment.getCustomerId());
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        
        address = data.fetchAddress(customer.getId());
        street.setText(address.getStreet());
        city.setText(address.getCity());
        state.setText(address.getState());
        zip.setText(address.getZip());
        
        countryData = data.fetchCountry(address.getCountryId());
        country.setText(countryData.getCountry());
        
        phoneNumber = data.fetchPhoneNumber(customer.getId());
        phone.setText(phoneNumber.getPhone());
        phoneType.setText(phoneNumber.getPhoneType());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enableEditClose();
    }    
}