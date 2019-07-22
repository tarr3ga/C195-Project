/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.FetchData;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Address;
import models.Appointment;
import models.Country;
import models.Customer;
import models.PhoneNumber;
import util.DateTimeUtils;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class CustomersController implements Initializable {

    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    
    public ObservableList<Customer> getCustomers() {
        return customers;
    } 
    
    @FXML private TableView displayTable;
    @FXML private HBox hBoxButtons;
    @FXML private VBox hBoxCustomer;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnView;
    private Button btnEdit;
    private Button btnViewAppointmentDetails;
    private Button btnDeleteAppointment;
         
    private void populateTable() {
        TableColumn<Customer, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(20);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Customer, String> firstName = new TableColumn<>("First Name");
        firstName.setMinWidth(150);
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        TableColumn<Customer, String> lastName = new TableColumn<>("Last Name");
        lastName.setMinWidth(150);
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        TableColumn<Customer, Timestamp> addedOn = new TableColumn<>("Added On");
        addedOn.setMinWidth(150);
        addedOn.setCellValueFactory(new PropertyValueFactory<>("addedOn"));
        
        btnAdd = new Button();
        btnAdd.setPrefWidth(200);
        btnAdd.setText("Add Customer");
        
        btnView = new Button();
        btnView.setPrefWidth(200);
        btnView.setText("View Customer");
        
        btnEdit = new Button();
        btnEdit.setPrefWidth(200);
        btnEdit.setText("Edit Customer");
        
        btnDelete = new Button();
        btnDelete.setPrefWidth(200);
        btnDelete.setText("Delete Customer");
       
        hBoxButtons.getChildren().clear();
        hBoxButtons.getChildren().addAll(btnAdd, btnView, btnEdit, btnDelete);
        
        displayTable.setItems(customers);
        displayTable.getColumns().setAll(id, firstName, lastName, addedOn);
        
        displayTable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount() >= 2) {
                Customer c = (Customer)displayTable.getSelectionModel().getSelectedItem();           
                //int index = c.getId();
                
                try {
                    loadCustomerSpecificAppointments(c);
                } catch(IOException | SQLException ex) {
                    System.err.println(ex.toString());
                }
            }       
        });
         
        btnAdd.setOnMouseClicked((MouseEvent e) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
                Parent root = loader.load();
                    
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                    
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            } catch(IOException ex) {
                
            }
            
            initialize(null, null);
        });
                
        
        btnView.setOnMouseClicked((MouseEvent e) -> {
            if(displayTable.getSelectionModel().getSelectedItem() != null) {
                Customer c = (Customer)displayTable.getSelectionModel().getSelectedItem();           
                
                try {
                    loadCustomerSpecificAppointments(c);
                } catch(IOException | SQLException ex) {
                    System.err.println(ex.toString());
                }
            }           
        });
        
        btnEdit.setOnMouseClicked((MouseEvent e) -> {
            if(displayTable.getSelectionModel().getSelectedItem() != null){
                Customer c = (Customer)displayTable.getSelectionModel().getSelectedItem();
                AddCustomerController.customerToEdit = c;
                AddCustomerController.isEditing = true; 
                
                 try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
                    Parent root = loader.load();

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.showAndWait();
                } catch(IOException ex) {

                }
            }
        });
        
        btnDelete.setOnMouseClicked((MouseEvent e) -> {
            if(displayTable.getSelectionModel().getSelectedItem() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning!");
                alert.setHeaderText("Delete Record?");
                alert.setContentText("Are you sure you wan to delete this record?");
                
                Optional<ButtonType> result = alert.showAndWait();
                
                if(result.get() == ButtonType.OK) {
                    
                } else {
                    alert.close();
                }
            }
        });
    }
    
    private void loadCustomerSpecificAppointments(Customer customer) throws SQLException, IOException {
        
        FetchData data = new FetchData();
        appointments = data.fetchAppointmentsForCustomerData(customer.getId());
        
        displayTable.getItems().clear();
       
        TableColumn<Customer, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(20);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
       
        TableColumn<Customer, String> subject = new TableColumn<>("Subject");
        subject.setMinWidth(150);
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        TableColumn<Customer, String> location = new TableColumn<>("Location");
        location.setMinWidth(150);
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        btnAdd = new Button();
        btnAdd.setPrefWidth(200);
        btnAdd.setText("Add Appointment");
        
        btnViewAppointmentDetails = new Button();
        btnViewAppointmentDetails.setPrefWidth(200);
        btnViewAppointmentDetails.setText("View Appointment");
        
        btnDeleteAppointment = new Button();
        btnDeleteAppointment.setPrefWidth(200);
        btnDeleteAppointment.setText("Delete Appointment");
        
        hBoxButtons.getChildren().clear();
        hBoxButtons.getChildren().addAll(btnAdd, btnViewAppointmentDetails, btnDeleteAppointment);
        
        Label customerLabel = new Label();
        customerLabel.setText("Details for " + customer.getFirstName() + " " + customer.getLastName());
        
        Label customerDate = new Label();
        String formattedDateTime = DateTimeUtils.getFormatedDateTimeStringFromTimestamp(customer.getAddedOn());
        customerDate.setText("Added on " + formattedDateTime);
        
        Address add = new Address();
        
        try {
            data = new FetchData();
            add = data.fetchAddress(customer.getId());
        } catch(SQLException ex) {
            
        }
        
        Label street = new Label();
        street.setText(add.getStreet());
        
        Label address = new Label();
        address.setText(add.getCity() + ", " + add.getState() + " " + add.getZip());
        
        Country c = new Country();
        
        try {
            data = new FetchData();
            c = data.fetchCountry(add.getCountryId());
        } catch(SQLException ex) {
            
        }
        
        Label country = new Label();
        country.setText(c.getCountryAbreviation() + " | " + c.getCountry());
        
        PhoneNumber p = new PhoneNumber();
        
        try {
            data = new FetchData();
            p = data.fetchPhoneNumber(customer.getId());
        } catch(SQLException ex) {
            
        }
        
        Label phone = new Label();
        phone.setText(p.getPhone() + " (" + p.getPhoneType() + ")");
        
        hBoxCustomer.getChildren().clear();
        hBoxCustomer.getChildren().addAll(customerLabel, customerDate, street, address, country, phone);
        
        displayTable.setItems(appointments);
        displayTable.getColumns().setAll(id, subject, location);
        
        displayTable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount() >= 2) {
                Appointment a = (Appointment)displayTable.getSelectionModel().getSelectedItem();           
                int index = a.getId();
                
                try {
                    //loadCustomerSpecificAppointments(index);
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                    Parent root = loader.load();
                    
                    DetailsController controller = loader.getController();
                    controller.getAppointment(a);
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.setScene(scene);
                    stage.show();
                    
                    controller.getData();
                } catch(IOException | SQLException ex) {
                    System.err.println(ex.toString());
                }      
            }       
        });
         
        btnAdd.setOnMouseClicked((MouseEvent e) -> {
            try {
                AddAppointmentController.customerIsSelected = true;
                AddAppointmentController.customerSelected = customer;
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
                Parent root = loader.load();
                
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            } catch(IOException ex) {
                
            }
        }) ;
        
        btnViewAppointmentDetails.setOnMouseClicked((MouseEvent e) -> {
            if(displayTable.getSelectionModel().getSelectedItem() != null) {
                Appointment a = (Appointment)displayTable.getSelectionModel().getSelectedItem();           
                int index = a.getId();
                
                try {
                    //loadCustomerSpecificAppointments(index);
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                    Parent root = loader.load();
                    
                    DetailsController controller = loader.getController();
                    controller.getAppointment(a);
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.showAndWait();
                } catch(IOException ex) {
                    System.err.println(ex.toString());
                }
            }           
        });
        
        btnDeleteAppointment.setOnMouseClicked((MouseEvent e) -> {
            if(displayTable.getSelectionModel().getSelectedItem() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning!");
                alert.setHeaderText("Delete Record?");
                alert.setContentText("Are you sure you wan to delete this record?");
                
                Optional<ButtonType> result = alert.showAndWait();
                
                if(result.get() == ButtonType.OK) {
                    
                } else {
                    alert.close();
                }
            }
        });
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FetchData data = new FetchData();
        
        try {
            customers = data.fetchCustomerData();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
        populateTable();  
    }     
}