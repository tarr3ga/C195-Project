/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import data.FetchData;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Appointment;

/**
 *
 * @author jamyers
 */
public class AppointmentDetail {
    private final Appointment appointment;
    
    public AppointmentDetail(Appointment appointment) {
        this.appointment = appointment;
    }
    
    public GridPane getGridPane() throws SQLException {
        AppointmentDetail detail = new AppointmentDetail(appointment);
        
        FetchData data = new FetchData(); 
        data.fetchAppointmentData();
        
        GridPane newPane = new GridPane();
       
        newPane.setPadding(new Insets(20, 20, 20, 20));
        newPane.setVgap(10);
        newPane.setHgap(10);
        newPane.setMouseTransparent(true);
        newPane.setFocusTraversable(false);
        newPane.setMinHeight(650);
        newPane.setMinWidth(1200);
        
        // ROW 1
        Label title = new Label("Appointment Details");
        title.setMinWidth(200);
        title.setMinHeight(45);
        title.setMaxWidth(200);
        title.setMaxHeight(45);
        newPane.add(title, 0, 0);

        // ROW 2
        Label lblFName = new Label("First Name:");
        lblFName.setMinWidth(200);
        lblFName.setMinHeight(45);
        lblFName.setMaxWidth(200);
        lblFName.setMaxHeight(45);
        lblFName.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblFName, 0, 1);
        
        TextArea taFName = new TextArea();
        taFName.setMinWidth(200);
        taFName.setMinHeight(45);
        taFName.setMaxWidth(200);
        taFName.setMaxHeight(45);
        newPane.add(taFName, 1, 1);
        
        Label lblLName = new Label("Last Name:");
        lblLName.setMinWidth(110);
        lblLName.setMinHeight(45);
        lblLName.setMaxWidth(110);
        lblLName.setMaxHeight(45);
        lblLName.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblLName, 2, 1);
        
        TextArea taLName = new TextArea();
        taLName.setMinWidth(200);
        taLName.setMinHeight(45);
        taLName.setMaxWidth(200);
        taLName.setMaxHeight(45);
        newPane.add(taLName, 3, 1);
        
        // Row 2
        Label lblSubject = new Label("Subject:");
        lblSubject.setMinWidth(200);
        lblSubject.setMinHeight(45);
        lblSubject.setMaxWidth(200);
        lblSubject.setMaxHeight(45);
        lblSubject.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblSubject, 0, 2);
        
        TextArea taSubject = new TextArea();
        taSubject.setMinWidth(200);
        taSubject.setMinHeight(45);
        taSubject.setMaxWidth(200);
        taSubject.setMaxHeight(45);
        newPane.add(taSubject, 1, 2);
        
        // Row 3
        Label lblTime = new Label("Time:");
        lblTime.setMinWidth(200);
        lblTime.setMinHeight(45);
        lblTime.setMaxWidth(200);
        lblTime.setMaxHeight(45);
        lblTime.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblTime, 0, 3);
        
        TextArea taTime = new TextArea();
        taTime.setMinWidth(200);
        taTime.setMinHeight(45);
        taTime.setMaxWidth(200);
        taTime.setMaxHeight(45);
        newPane.add(taTime, 1, 3);
        
        // Row 4
        Label lblStreet = new Label("Street:");
        lblStreet.setMinWidth(200);
        lblStreet.setMinHeight(45);
        lblStreet.setMaxWidth(200);
        lblStreet.setMaxHeight(45);
        lblStreet.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblStreet, 0, 4);
        
        TextArea taStreet = new TextArea();
        taStreet.setMinWidth(200);
        taStreet.setMinHeight(45);
        taStreet.setMaxWidth(200);
        taStreet.setMaxHeight(45);
        newPane.add(taStreet, 1, 4);
        
        // Row 5
        Label lblCity = new Label("City:");
        lblCity.setMinWidth(200);
        lblCity.setMinHeight(45);
        lblCity.setMaxWidth(200);
        lblCity.setMaxHeight(45);
        lblCity.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblCity, 0, 5);
        
        TextArea taCity = new TextArea();
        taCity.setMinWidth(200);
        taCity.setMinHeight(45);
        taCity.setMaxWidth(200);
        taCity.setMaxHeight(45);
        newPane.add(taCity, 1, 5);
        
        Label lblState = new Label("State:");
        lblState.setMinWidth(110);
        lblState.setMinHeight(45);
        lblState.setMaxWidth(110);
        lblState.setMaxHeight(45);
        lblState.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblState, 2, 5);
        
        TextArea taState = new TextArea();
        taState.setMinWidth(200);
        taState.setMinHeight(45);
        taState.setMaxWidth(200);
        taState.setMaxHeight(45);
        newPane.add(taState, 3, 5);
        
        Label lblZip = new Label("ZIP:");
        lblZip.setMinWidth(110);
        lblZip.setMinHeight(45);
        lblZip.setMaxWidth(110);
        lblZip.setMaxHeight(45);
        lblZip.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblZip, 4, 5);
        
        TextArea taZip = new TextArea();
        taZip.setMinWidth(200);
        taZip.setMinHeight(45);
        taZip.setMaxWidth(200);
        taZip.setMaxHeight(45);
        newPane.add(taZip, 5, 5);
        
        // Row 6
        Label lblPhone = new Label("Phone:");
        lblPhone.setMinWidth(200);
        lblPhone.setMinHeight(45);
        lblPhone.setMaxWidth(200);
        lblPhone.setMaxHeight(45);
        lblPhone.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblPhone, 0, 6);
        
        TextArea taPhone = new TextArea();
        taPhone.setMinWidth(200);
        taPhone.setMinHeight(45);
        taPhone.setMaxWidth(200);
        taPhone.setMaxHeight(45);
        newPane.add(taPhone, 1, 6);
        
        Label lblPhoneType = new Label("Type:");
        lblPhoneType.setMinWidth(110);
        lblPhoneType.setMinHeight(45);
        lblPhoneType.setMaxWidth(110);
        lblPhoneType.setMaxHeight(45);
        lblPhoneType.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblPhoneType, 2, 6);
        
        TextArea taPhoneType = new TextArea();
        taPhoneType.setMinWidth(200);
        taPhoneType.setMinHeight(45);
        taPhoneType.setMaxWidth(200);
        taPhoneType.setMaxHeight(45);
        newPane.add(taPhoneType, 3, 6);
        
        // Row 7
        Label lblDesc = new Label("Description:");
        lblDesc.setMinWidth(200);
        lblDesc.setMinHeight(45);
        lblDesc.setMaxWidth(200);
        lblDesc.setMaxHeight(45);
        lblDesc.setAlignment(Pos.CENTER_RIGHT);
        newPane.add(lblDesc, 0, 7);
        
        TextArea taDesc = new TextArea();
        taDesc.setMinWidth(800);
        taDesc.setMinHeight(200);
        taDesc.setMaxWidth(800);
        taDesc.setMaxHeight(200);
        newPane.add(taDesc, 1, 7, 6, 1);
        
        // Row 8
        HBox box = new HBox();
        Button edit = new Button("Edit");
        edit.setMinWidth(100);
        edit.setMinHeight(45);
        Button cancel = new Button("Cancel");
        cancel.setMinWidth(100);
        cancel.setMinHeight(45);
        box.getChildren().addAll(edit, cancel);
        newPane.add(box, 0, 8);
        
        cancel.setOnMouseClicked((e) -> {
            System.out.println("Click");
        });
        
        edit.setOnMouseClicked((e) -> {
            newPane.setMouseTransparent(false);
            newPane.setFocusTraversable(true);
        });
        
        return newPane;
    }
}