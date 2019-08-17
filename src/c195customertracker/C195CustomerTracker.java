/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.SaveAlerts;

/**
 *
 * @author jamyers
 */
public class C195CustomerTracker extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
         Runtime.getRuntime().addShutdownHook(new Thread() { 
          @Override
          public void run() 
          {   
             SaveAlerts save = new SaveAlerts();
             
             try {
                save.saveAlerts();
             } catch(IOException ex) {
                System.err.println(ex.toString());
             }
          } 
        });
    }   
}