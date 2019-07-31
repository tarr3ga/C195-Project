/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.DeleteData;
import data.FetchData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableCell;
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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a");
    
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
        firstName.setMinWidth(175);
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        TableColumn<Customer, String> lastName = new TableColumn<>("Last Name");
        lastName.setMinWidth(175);
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        TableColumn<Customer, Timestamp> addedOn = new TableColumn<>("Added On");
        addedOn.setMinWidth(250);
        addedOn.setCellValueFactory(new PropertyValueFactory<>("addedOn"));
        /*addedOn.setCellFactory(col -> new TableCell<Customer, Timestamp>(){
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                LocalDateTime ldt = item.toLocalDateTime();
                
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(String.format(ldt.format(formatter)));
            }
        });*/
        
        
        TableColumn<Customer, String> customerRep = new TableColumn<>("Customer Rep");
        customerRep.setMinWidth(175);
        customerRep.setCellValueFactory(new PropertyValueFactory<>("customerRep"));
        
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
        displayTable.getColumns().setAll(id, firstName, lastName, addedOn, customerRep);
        
        displayTable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount() >= 2) {
                Customer c = (Customer)displayTable.getSelectionModel().getSelectedItem();           
                //int index = c.getId();
                
                try {
                    loadCustomerSpecificAppointments(c);
                } catch(IOException | SQLException ex) {
                    System.err.println(ex.toString());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
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
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
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
                    
                    populateTable();
                } catch(IOException ex) {

                }
            }
        });
        
        // TODO Impliment delete customer code
        btnDelete.setOnMouseClicked((MouseEvent e) -> {
            if(displayTable.getSelectionModel().getSelectedItem() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning!");
                alert.setHeaderText("Delete Record?");
                alert.setContentText("Are you sure you wan to delete this record?");
                
                Optional<ButtonType> result = alert.showAndWait();
                
                if(result.get() == ButtonType.OK) {
                    Customer c = (Customer)displayTable.getSelectionModel().getSelectedItem();
                
                    try {
                        if(checkIfCustomerHasAppointments(c)) {
                            alert.close();
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Error!");
                            errorAlert.setContentText(("Customer has associated appointments.  Please delete appointments first."));
                            
                            Optional<ButtonType>errorResult = errorAlert.showAndWait();
                        } else {
                            deleteCustomer(c);
                        }
                    } catch(SQLException ex) {
                        System.err.println(ex.toString());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    alert.close();
                }
                           
                /*if(result.get() == ButtonType.OK) {
                    
                } else {
                    alert.close();
                }*/
            }
        });
    }
    
    private void loadCustomerSpecificAppointments(Customer customer) throws SQLException, IOException, ClassNotFoundException {
        
        FetchData data = new FetchData();
        appointments = data.fetchAppointmentsForCustomerData(customer.getId());
        
        adjustTimeZones();
        
        displayTable.getItems().clear();
       
        TableColumn<Appointment, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(20);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
       
        TableColumn<Appointment, String> subject = new TableColumn<>("Subject");
        subject.setMinWidth(175);
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        TableColumn<Appointment, String> location = new TableColumn<>("Location");
        location.setMinWidth(175);
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        TableColumn<Appointment, ZonedDateTime> time = new TableColumn<>("Date and Time");
        time.setMinWidth(250);
        time.setCellValueFactory(new PropertyValueFactory<>("start"));
        time.setCellFactory(col -> new TableCell<Appointment, ZonedDateTime>(){
            @Override
            protected void updateItem(ZonedDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(String.format(item.format(formatter)));
            }
        });
        
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
        displayTable.getColumns().setAll(id, subject, location, time);
        
        displayTable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount() >= 2) {
                Appointment a = (Appointment)displayTable.getSelectionModel().getSelectedItem();           
                int index = a.getId();
                
                try {                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                    Parent root = loader.load();
                    
                    DetailsController controller = loader.getController();
                    controller.getAppointment(a);
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    
                    controller.getData();
                    
                    stage.showAndWait();
                } catch(IOException | SQLException ex) {
                    System.err.println(ex.toString());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
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
                
                loadCustomerSpecificAppointments(customer);
            } catch(SQLException | IOException ex) {
                System.err.println(ex.toString());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }) ;
        
        btnViewAppointmentDetails.setOnMouseClicked((MouseEvent e) -> {
            if(displayTable.getSelectionModel().getSelectedItem() != null) {
                Appointment a = (Appointment)displayTable.getSelectionModel().getSelectedItem();           
                int index = a.getId();
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
                    Parent root = loader.load();
                    
                    DetailsController controller = loader.getController();
                    controller.getAppointment(a);
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    
                    controller.getData();
                    
                    stage.showAndWait();
                } catch(SQLException | IOException ex) {
                    System.err.println(ex.toString());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Appointment a = (Appointment)displayTable.getSelectionModel().getSelectedItem();
                    try {
                        deleteAppointment(a, customer);
                    } catch(SQLException | IOException ex) {
                        
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    alert.close();
                }
            }
        });
    }
    
    private boolean checkIfCustomerHasAppointments(Customer c) throws SQLException, ClassNotFoundException {
        boolean hasAppoinyments = true;
        
        FetchData data = new FetchData();
        int count = data.getAppointmentCountPerCustomer(c);
        
        if(count == 0)
            hasAppoinyments = false;
        
        return hasAppoinyments;
    }
    
    private void deleteAppointment(Appointment a, Customer c) throws SQLException, IOException, ClassNotFoundException {
        DeleteData delete= new DeleteData();
        delete.deleteAppointment(a.getId());
        
        File dir = new File("logs/");
        boolean success =  dir.mkdir();

        if(success)
            System.out.println("Directory created");
        else
            System.out.println("Directory already exists");

        File file = new File("logs/transactions.txt");

        String message = "Appointment ID: " + a.getId() + " Deleted by " + 
                        FXMLDocumentController.authorizedUser + " on " + ZonedDateTime.now().toString();

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.newLine();
            bufferedWriter.append(message);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch(IOException ex) {

        } 
        
        loadCustomerSpecificAppointments(c);
    }
    
    private void deleteCustomer(Customer c) throws SQLException, ClassNotFoundException {
        DeleteData data = new DeleteData();
        data.DeleteCustomer(c);
        
        File dir = new File("logs/");
        boolean success =  dir.mkdir();

        if(success)
            System.out.println("Directory created");
        else
            System.out.println("Directory already exists");

        File file = new File("logs/transactions.txt");

        String message = "Customer ID: " + c.getId() + " Deleted by " + 
                        FXMLDocumentController.authorizedUser + " on " + ZonedDateTime.now().toString();

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.newLine();
            bufferedWriter.append(message);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch(IOException ex) {

        } 
    }
    
    private void adjustTimeZones() {
        TimeZone defaultTimeZone = TimeZone.getDefault();
        ZoneId defaultZoneId = ZoneId.of(defaultTimeZone.getID());
        
        for(Appointment a : appointments) {
            ZonedDateTime zdtStart = DateTimeUtils.getUnalteredZonedDateTimeFromString(String.valueOf(a.getStart()));
            ZoneId customerZoneId =  zdtStart.getZone();
            
            ZonedDateTime zdtEnd = DateTimeUtils.getUnalteredZonedDateTimeFromString(String.valueOf(a.getEnd()));
            
            if(!customerZoneId.equals(defaultZoneId)) {
                TimeZone customerTimeZone = TimeZone.getTimeZone(customerZoneId);
                zdtStart = DateTimeUtils.adjustForTimeZones(zdtStart, customerTimeZone);
                
                zdtEnd = DateTimeUtils.adjustForTimeZones(zdtEnd, customerTimeZone);
            }
            
            a.setStart(zdtStart);
            a.setEnd(zdtEnd);
        }
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        populateTable();  
    }     
}