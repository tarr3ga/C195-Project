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
import models.City;
import models.Country;
import models.Customer;
import models.User;
import util.DateTimeUtils;
import util.RowData;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class CustomersController implements Initializable {

    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<RowData> rowData = FXCollections.observableArrayList();
    
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
        //TableColumn<Customer, Integer> id = new TableColumn<>("ID");
        //id.setMinWidth(20);
        //id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        //TableColumn<Customer, String> name = new TableColumn<>("Name");
        //name.setMinWidth(175);
        //name.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<RowData, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(20);
        id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        TableColumn<RowData, String> name = new TableColumn<>("Name");
        name.setMinWidth(175);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<RowData, String> userName = new TableColumn<>("Created By");
        userName.setMinWidth(175);
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        
        /*userName.setCellFactory(col -> new TableCell<RowData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                if(empty) 
                    setText(null);
                else {
                    
                }                    
            }
        });*/
        
        TableColumn<RowData, Timestamp> addedOn = new TableColumn<>("Added On");
        addedOn.setMinWidth(250);
        addedOn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        
        //TableColumn<Customer, Timestamp> addedOn = new TableColumn<>("Added On");
        //addedOn.setMinWidth(250);
        //addedOn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        
        
        //TableColumn<Customer, String> customerRep = new TableColumn<>("Customer Rep");
        //customerRep.setMinWidth(175);
        //customerRep.setCellValueFactory(new PropertyValueFactory<>("customerRep"));
        
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
        displayTable.getColumns().setAll(id, name, addedOn, userName);
        
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
                System.err.println(ex.toString());
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
                    
                    AddCustomerController.customerToEdit = null;
                    AddCustomerController.isEditing = false;
                    
                    populateTable();
                } catch(IOException ex) {

                } finally {
                    AddCustomerController.customerToEdit = null;
                    AddCustomerController.isEditing = false;
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
        appointments = data.fetchAppointmentsForCustomerData(customer);
        
        adjustTimeZones();
        
        displayTable.getItems().clear();
       
        TableColumn<Appointment, Integer> id = new TableColumn<>("ID");
        id.setMinWidth(20);
        id.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
       
        TableColumn<Appointment, String> subject = new TableColumn<>("Title");
        subject.setMinWidth(175);
        subject.setCellValueFactory(new PropertyValueFactory<>("title"));

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
        customerLabel.setText("Details for " + customer.getName());
        
        Label customerDate = new Label();
        String formattedDateTime = DateTimeUtils.getFormatedDateTimeStringFromTimestamp(customer.getCreateDate());
        customerDate.setText("Added on " + formattedDateTime);
        
        Address add = new Address();
        
        try {
            data = new FetchData();
            add = data.fetchAddress(customer.getAddressId());
        } catch(SQLException ex) {
            
        }
        
        Label address = new Label();
        address.setText(add.getAddress());
        
        Label address2 = new Label();
        address2.setText(add.getAddress2());
        
        City city = new City();
        
        try {
            data = new FetchData();
            city = data.fetchCity(add.getCityId());
        } catch(SQLException ex) {
            
        }
        
        Country co = new Country();
        
        try {
            data = new FetchData();
            co = data.fetchCountry(city.getCountryId());
        } catch(SQLException ex) {
            
        }
        
        Label country = new Label();
        country.setText(co.getCountryAbreviation() + " | " + co.getCountry());
        
        Label phone = new Label();
        phone.setText(add.getPhone());
        
        hBoxCustomer.getChildren().clear();
        hBoxCustomer.getChildren().addAll(customerLabel, customerDate, address, address2, country, phone);
        
        displayTable.setItems(appointments);
        displayTable.getColumns().setAll(id, subject, location, time);
        
        displayTable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount() >= 2) {
                Appointment a = (Appointment)displayTable.getSelectionModel().getSelectedItem();           
                int index = a.getAppointmentId();
                
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
                int index = a.getAppointmentId();
                
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
        delete.deleteAppointment(a.getAppointmentId());
        
        File dir = new File("logs/");
        boolean success =  dir.mkdir();

        if(success)
            System.out.println("Directory created");
        else
            System.out.println("Directory already exists");

        File file = new File("logs/transactions.txt");

        String message = "Appointment ID: " + a.getAppointmentId()+ " Deleted by " + 
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

        String message = "Customer ID: " + c.getCustomerId()+ " Deleted by " + 
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
                //TimeZone customerTimeZone = TimeZone.getTimeZone(customerZoneId);
                zdtStart = DateTimeUtils.adjustForTimeZones(zdtStart);
                
                zdtEnd = DateTimeUtils.adjustForTimeZones(zdtEnd);
            }
            
            a.setStart(zdtStart);
            a.setEnd(zdtEnd);
        }
    }
    
    /*private void getData() throws SQLException, ClassNotFoundException {
        FetchData data = new FetchData();
        appointments = data.fetchAppointmentData();
        
        data = new FetchData();
        users = data.fetchUsers();
    }*/
    
    private void getUserData() throws SQLException, ClassNotFoundException {
        for(Customer c : customers) {
            for(User u : users){
                if(u.getUserId() == c.getCreatedBy()){
                    RowData rowDataTemp = new RowData(c, u);
                    rowData.add(rowDataTemp);
                }
            }
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
            users = data.fetchUsers();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        populateTable();  
    }     
}