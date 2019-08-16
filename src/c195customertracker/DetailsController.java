/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import com.sun.prism.paint.Color;
import data.FetchData;
import data.SaveData;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Address;
import models.Appointment;
import models.City;
import models.Country;
import models.Customer;
import util.DateTimeUtils;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class DetailsController implements Initializable {

    @FXML private GridPane appointmentDetails;
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
    
    @FXML private Button btnEdit;
    @FXML private Button btnClose;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    
    @FXML private Label lblStart;
    @FXML private Label lblEnd;
    
    private Appointment appointment;
    private Customer customer;
    private Address address;
    private Country countryData;
    private City city;
    
    private DatePicker startDate ;
    private ComboBox cbType;
    private ComboBox cbStartTime;
    private ComboBox cbEndTime;
    private ComboBox cbTimezone;
    private ComboBox cbStartAmPm;
    private ComboBox cbEndAmPm;
    private HBox left;
    private HBox leftMid;
    private HBox rightMid;
    private HBox right;
    private Label start;
    private Label end;
    
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

        saveChanges();
        
        enableEditClose();
        
        getData();
    }
    
    @FXML 
    private void onBtnCancelClick() throws SQLException, ClassNotFoundException {
        disableEditing();
        getData();
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
        
        subject.setEditable(true);
        location.setEditable(true);
        dateTime.setEditable(true);
        endTime.setEditable(true);
        details.setEditable(true);

        subject.setFocusTraversable(true);
        location.setFocusTraversable(true);
        dateTime.setFocusTraversable(true);
        endTime.setFocusTraversable(true);
        details.setFocusTraversable(true);
        
        //Grey out uneditable controlls
        name.setStyle("-fx-background-color: #5c687a;");
        tfAddress.setStyle("-fx-background-color: #5c687a;");
        tfAddress2.setStyle("-fx-background-color: #5c687a;");
        tfCity.setStyle("-fx-background-color: #5c687a;");
        country.setStyle("-fx-background-color: #5c687a;");
        phone.setStyle("-fx-background-color: #5c687a;");
        
        setEditControls();
    }
    
    private void disableEditing() {
        enableEditClose();
       
        subject.setEditable(false);
        location.setEditable(false);
        dateTime.setEditable(false);
        endTime.setEditable(false);
        details.setEditable(false);
        
        subject.setFocusTraversable(false);
        location.setFocusTraversable(false);
        dateTime.setFocusTraversable(false);
        endTime.setFocusTraversable(false);
        details.setFocusTraversable(false);
        
        //Set back to white
        name.setStyle("-fx-background-color: #fff;");
        tfAddress.setStyle("-fx-background-color: #fff;");
        tfAddress2.setStyle("-fx-background-color: #fff;");
        tfCity.setStyle("-fx-background-color: #fff;");
        country.setStyle("-fx-background-color: #fff;");
        phone.setStyle("-fx-background-color: #fff;");
        
        hideEditControls();
    }
 
    private void setEditControls() {
        type.setVisible(false);
        dateTime.setVisible(false);
        endTime.setVisible(false);
        lblStart.setVisible(false);
        lblEnd.setVisible(false);
        
        left = new HBox();
        leftMid = new HBox();
        rightMid = new HBox();
        right = new HBox();
        start = new Label("Start: ");
        end = new Label("End: ");
        
        cbType = new ComboBox();
        cbStartTime = new ComboBox();
        cbEndTime = new ComboBox();
        cbTimezone = new ComboBox();
        cbStartAmPm = new ComboBox();
        cbEndAmPm = new ComboBox(); 
        startDate = new DatePicker();
        
        for(String s : DateTimeUtils.TIMES) {
            cbStartTime.getItems().add(s);
            cbEndTime.getItems().add(s);
        }
        
        for(String s : DateTimeUtils.TIMEZONES ) {
            cbTimezone.getItems().add(s);
        }
        
        cbTimezone.getSelectionModel().selectFirst();
        
        cbStartAmPm.getItems().add("AM");
        cbStartAmPm.getItems().add("PM");
        cbEndAmPm.getItems().add("AM");
        cbEndAmPm.getItems().add("PM");
        if(appointment.getStart().getHour() > 11) 
            cbStartAmPm.getSelectionModel().select("PM");
        else
            cbStartAmPm.getSelectionModel().select("AM");
        if(appointment.getEnd().getHour() > 11) 
            cbEndAmPm.getSelectionModel().select("PM");
        else
            cbEndAmPm.getSelectionModel().select("AM");
        
        cbType.getItems().add("Consultation");
        cbType.getItems().add("Planning");
        cbType.getItems().add("Working");
        cbType.getItems().add("Casual");
        cbType.getItems().add("Other");
        cbType.getSelectionModel().select(appointment.getType());
        
        String[] startDateTimeParts = DateTimeUtils.getDatePartsFromZonedDateTime(appointment.getStart());
        String[] endDateTimeParts = DateTimeUtils.getDatePartsFromZonedDateTime(appointment.getEnd());
        
        if(startDateTimeParts[1].length() == 1) 
            startDateTimeParts[1] = "0" + startDateTimeParts[1];
        
        if(startDateTimeParts[2].length() == 1) 
            startDateTimeParts[2] = "0" + startDateTimeParts[2];
        
        String date = startDateTimeParts[2] + "-" + startDateTimeParts[1] + "-" + startDateTimeParts[0];
        
        startDate.setValue(DateTimeUtils.LOCAL_DATE(date)); 
       
        if(startDateTimeParts[5].equals("AM"))
            cbStartAmPm.getSelectionModel().select("AM");
        else
            cbStartAmPm.getSelectionModel().select("PM");
        
        if(endDateTimeParts[5].equals("AM"))
            cbEndAmPm.getSelectionModel().select("AM");
        else
            cbEndAmPm.getSelectionModel().select("PM");
       
        cbStartTime.getSelectionModel().select(startDateTimeParts[3] + ":" + startDateTimeParts[4]);
        cbEndTime.getSelectionModel().select(endDateTimeParts[3] + ":" + endDateTimeParts[4]);
        
        System.out.println("c195customertracker.DetailsController.setEditControls() startDateTimeParts[3] = " + startDateTimeParts[3]);
        System.out.println("c195customertracker.DetailsController.setEditControls() startDateTimeParts[4] = " + startDateTimeParts[4]);
        
        startDate.setMaxWidth(150);
        
        left.setMinWidth(120);
        left.getChildren().add(start);
        leftMid.getChildren().addAll(startDate, cbTimezone);
        rightMid.getChildren().addAll(cbStartTime, cbStartAmPm);
        right.getChildren().addAll(end, cbEndTime, cbEndAmPm);
        
        //appointmentDetails.setGridLinesVisible(true);
        
        appointmentDetails.add(left, 0, 4, 4, 1);
        appointmentDetails.add(leftMid, 1, 4, 4, 1);
        appointmentDetails.add(rightMid, 2, 4, 4, 1);
        appointmentDetails.add(right, 3, 4, 4, 1);
        appointmentDetails.add(cbType, 1, 3);
    }
    
    private void hideEditControls() {
        cbType.setVisible(false);
        left.setVisible(false);
        leftMid.setVisible(false);
        rightMid.setVisible(false);
        right.setVisible(false);
        start.setVisible(false);
        end.setVisible(false);
        
        type.setVisible(true);
        dateTime.setVisible(true);
        endTime.setVisible(true);
        lblStart.setVisible(true);
        lblEnd.setVisible(true);
    }
    
    private void saveChanges() throws SQLException, ClassNotFoundException {
        SaveData data = new SaveData();
        
        data.updateFullRecord(appointment, customer, city, address, countryData);
    }
    
    private void setData() {
        appointment.setTitle(subject.getText());
        appointment.setLocation(location.getText());
        appointment.setDescription(details.getText());      
        appointment.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
        appointment.setUpdatedBy(FXMLDocumentController.authorizedUserId);
        
        String timeZoneName = DateTimeUtils.getTimeZoneName((String)cbTimezone.getSelectionModel().getSelectedItem());
        String date = startDate.getValue().toString();
        String time = (String)cbStartTime.getSelectionModel().getSelectedItem();
        String amPm = (String)cbStartAmPm.getSelectionModel().getSelectedItem();
        
        appointment.setStart(DateTimeUtils.getZonedDateTimeFromDateParts(timeZoneName, date, time, amPm));
        
        time = (String)cbEndTime.getSelectionModel().getSelectedItem();
        amPm = (String)cbEndAmPm.getSelectionModel().getSelectedItem();
        
        appointment.setEnd(DateTimeUtils.getZonedDateTimeFromDateParts(timeZoneName, date, time, amPm));
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