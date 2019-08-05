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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Appointment;
import util.DateTimeUtils;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class HomeController implements Initializable {

    @FXML GridPane display;
    @FXML private Label welcome;
    @FXML private Label location;
    @FXML private Label customerCount;
    @FXML private Label appointmentCount;
    @FXML private Button btnTypeReport;
    @FXML private Button btnAppointments;
    @FXML private Button btnAppointmentsPerMonth;
    @FXML private ComboBox cbUsers;
    
    private void setEventHandlers() {
        btnTypeReport.setOnMouseClicked((MouseEvent e) -> {
            int countConsult = 0;
            int countPlan = 0;
            int countWork = 0;
            int countCasual = 0;
            int countOther = 0;
            
            try {
                FetchData data = new FetchData();
                countConsult = data.getConsultationCount();
                
                data = new FetchData();
                countPlan = data.getPlanningCount();
                
                data = new FetchData();
                countWork = data.getWorkingCount();
                
                data = new FetchData();
                countCasual = data.getCasualCount();
                countOther = data.getOtherCount();
            } catch(SQLException ex) {
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 20, 20, 20));
            
            grid.add(new Label("Consulting: "), 0, 0);
            grid.add(new Label(String.valueOf(countConsult)), 1, 0);
            grid.add(new Label("Planning: "), 0, 1);
            grid.add(new Label(String.valueOf(countPlan)), 1, 1);
            grid.add(new Label("Working: "), 0, 2);
            grid.add(new Label(String.valueOf(countWork)), 1, 2);
            grid.add(new Label("Casual: "), 0, 3);
            grid.add(new Label(String.valueOf(countCasual)), 1, 3);
            grid.add(new Label("Other: "), 0, 4);
            grid.add(new Label(String.valueOf(countOther)), 1, 4);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.DECORATED);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setHeaderText("Appointment Type Report");
            alert.getDialogPane().setContent(grid);
            
            DialogPane pane = alert.getDialogPane();
            pane.getStylesheets().add(getClass().getResource("/styles/myDialogs.css").toExternalForm());
            pane.getStyleClass().add("myDialog");
            
            alert.showAndWait();
        });
        
        btnAppointments.setOnMouseClicked((MouseEvent e) -> {
            String rep = (String)cbUsers.getSelectionModel().getSelectedItem();
            
            try {                    
                    FetchData data = new FetchData();
                    int repId = data.getUserId(rep);
                
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentsForRep.fxml"));
                    AppointmentsForRepController controller = loader.getController();
                    controller.rep = repId;
                    Parent root = loader.load();
             
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                                 
                    stage.showAndWait();
                } catch(SQLException | ClassNotFoundException | IOException ex) {
                    System.err.println(ex.toString());
                }      
        });
        
        btnAppointmentsPerMonth.setOnMouseClicked((MouseEvent e) -> {
            ObservableList<Appointment> list = FXCollections.observableArrayList();
            ArrayList<ZonedDateTime> dates = new ArrayList<>();
            
            try {
                FetchData data = new FetchData();
                list = data.fetchAppointmentData();
            } catch(SQLException ex) {
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(Appointment a : list) {
                ZonedDateTime z = a.getStart();
                dates.add(z);
            }
            
            HashMap<String, Integer> monthCount = DateTimeUtils.getAppointmentPerMonth(dates);
            
            int index = 0;
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 20, 20, 20));
            
            for(Map.Entry<String, Integer> entry : monthCount.entrySet()) {
                grid.add(new Label(entry.getKey()), 0, index);
                grid.add(new Label(String.valueOf(entry.getValue())), 1, index);
                
                index++;
            }
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.DECORATED);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setHeaderText("Appointment by Month Report");
            alert.getDialogPane().setContent(grid);
            
            DialogPane pane = alert.getDialogPane();
            pane.getStylesheets().add(getClass().getResource("/styles/myDialogs.css").toExternalForm());
            pane.getStyleClass().add("myDialog");
            
            alert.showAndWait();
        });
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String user = FXMLDocumentController.authorizedUser;
        ZonedDateTime time = ZonedDateTime.now();
        
        String message = "Welcome " + user + ", you are logged in at " + time.toString();
        welcome.setText(message);
        
        String zone = TimeZone.getDefault().getDisplayName();
        String locale = "You're current Time Zone is: " + zone;
        
        location.setText(locale);
        
        try {
            FetchData data = new FetchData();
            int appointments = data.getAppointmentCount();
            
            data = new FetchData();
            int customers = data.getCustomerCount();
            
            customerCount.setText("Customers stored: " + customers);
            appointmentCount.setText("Appointments stored: " + appointments);
            
            ArrayList<String> userNames = data.fetchUserNames();
            
            for(String u : userNames) {
                cbUsers.getItems().add(u);
            }
            
            cbUsers.getSelectionModel().selectFirst();
        } catch(SQLException ex) {
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setEventHandlers();
    }    
    
}
