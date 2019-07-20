/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jamyers
 */
public class MainSceneController implements Initializable {

    Parent root;
    private final Stage stage = new Stage();
    
    @FXML GridPane display;
    
    @FXML
    private void onHomeClick(ActionEvent event) throws IOException {
        display.getChildren().clear();
        GridPane newPane = FXMLLoader.load(getClass().getResource("home.fxml"));
        display.add(newPane, 0, 0);
    }
    
    @FXML
    private void onCustomersClick(ActionEvent event) throws IOException {
        display.getChildren().clear();
        GridPane newPane = FXMLLoader.load(getClass().getResource("Customers.fxml"));
        display.add(newPane, 0, 0);
    }
    
    @FXML
    private void onAppointmentsClick(ActionEvent event) {
        
    }
    
    @FXML
    private void onCalendarClick(ActionEvent event) {
        
    }
    
    public static void loadCustomerAppointments(int index) throws IOException {
        
    }
    
    public void openWindow() throws Exception {
        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        
        Scene scene = new Scene(root);
       
        stage.setScene(scene);
        stage.show();
    }
    
    private void loadOnInit() throws IOException {
        GridPane newPane = FXMLLoader.load(getClass().getResource("home.fxml"));
        display.add(newPane, 0, 0);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadOnInit();
        } catch(IOException ex) {
            
        }
    }    
    
}
