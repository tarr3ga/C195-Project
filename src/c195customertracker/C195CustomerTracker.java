/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.AppointmentAlert;
import util.Scan;

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
                
        String filename = "transactions.log";
        
        try {
            FileInputStream file = new FileInputStream(filename); 
            ObjectInputStream in = new ObjectInputStream(file);
            
            Scan.alerts = (ArrayList)in.readObject();
            
            
        } catch(ClassNotFoundException | IOException ex) {
            
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                
                try {
                FileOutputStream file = new FileOutputStream(filename); 
                ObjectOutputStream out = new ObjectOutputStream(file);
                
                out.writeObject(Scan.alerts);
                
                out.close(); 
                file.close(); 
              
                System.out.println("Object has been serialized"); 
                } catch(IOException ex) {
                    
                }
             }
        }, "Shutdown-thread"));        
    }
    
}
