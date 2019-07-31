/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195customertracker;

import data.FetchData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.CheckCredentials;
import util.Language;

/**
 *
 * @author jamyers
 */
public class FXMLDocumentController implements Initializable {
    
    public static String authorizedUser;
    public static int authorizedUserId;
    
    private Language selectedLanguage;
    String[] english = {"Username", "Password", "Logon", "Language", "Authorized", 
        "The username and password did not match.", "All fileds are required"};
    String[] spanish = {"Nombre de usuario", "Contraseña", "Inicio de sesión", 
        "Idioma", "Autorizada", "El nombre de usuario y la contraseña no \ncoinciden.",
        "Todos los documentos son requeridos."};
    String[] french = {"Nom d'utilisateur", "Mot de passe", "La langue", "Se connecter", 
        "Autorisée", "Le nom d'utilisateur et le mot de passe ne \ncorrespondent pas.",
        "Tous les champs sont requis."};
    
    private String authorized, unauthorized, required;
    private final ObservableList<String> languages = FXCollections.observableArrayList();
    
    
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private ComboBox language;
    @FXML private Button logon;
    
    @FXML private Label labelUsername;
    @FXML private Label labelPassword;
    @FXML private Label labelLanguage;
    @FXML private Label response;

    @FXML
    private void onSelectLang(ActionEvent event) {
        String lang = language.getSelectionModel().getSelectedItem().toString();
        
        if(lang.equals("English")) {
            selectedLanguage = Language.ENGLISH;
            changeLanguage();
        } else if(lang.equals("Spanish")) {
            selectedLanguage = Language.SPANISH;
            changeLanguage();
        } else if(lang.equals("French")) {
            selectedLanguage = Language.FRENCH;
            changeLanguage();
        }
    }
    
    @FXML
    private void onUserEnter(ActionEvent event) throws SQLException, Exception {
        checkCreds();
    }
    
    @FXML 
    private void onPassEnter(ActionEvent event) throws SQLException, Exception {
        checkCreds();
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException, Exception {
        checkCreds();
    }
    
    public FXMLDocumentController() {
        authorized = english[4];
        unauthorized = english[5];
        required = english[6];
    }
    
    private void checkCreds() throws SQLException, Exception {
        if(username.getLength()== 0 || password.getLength() == 0) {
            response.setText(required);
        } else {
            CheckCredentials check = new CheckCredentials(username.getText(), password.getText());
            
            if(check.Check()) {
                response.setText(authorized);
                
                authorizedUser = username.getText();
                
                File file = new File("logs/transactions.txt");

                String message = username.getText() + " logged in at " + LocalDateTime.now();
                
                 try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                    bufferedWriter.newLine();
                    bufferedWriter.append(message);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch(IOException ex) {

                } 
                
                FetchData data = new FetchData();
                authorizedUserId = data.getUserId(authorizedUser);
                
                MainSceneController scene = new MainSceneController();
                scene.openWindow();
        }
            else
                response.setText(unauthorized);
        }
    }
    
    private void changeLanguage() {
        switch(selectedLanguage) {
            case ENGLISH:
                labelUsername.setText(english[0]);
                labelPassword.setText(english[1]);
                labelLanguage.setText(english[2]);
                logon.setText(english[3]);
                authorized = english[4];
                unauthorized = english[5];
                required = english[6];
                
                labelUsername.setLayoutX(110);
                labelPassword.setLayoutX(110);
                labelLanguage.setLayoutX(110);
                break;
            case SPANISH:
                labelUsername.setText(spanish[0]);
                labelPassword.setText(spanish[1]);
                labelLanguage.setText(spanish[2]);
                logon.setText(spanish[3]);
                authorized = spanish[4];
                unauthorized = spanish[5];
                required = spanish[6];
                
                labelUsername.setLayoutX(35);
                labelPassword.setLayoutX(35);
                labelLanguage.setLayoutX(35);
                break;
            case FRENCH:
                labelUsername.setText(french[0]);
                labelPassword.setText(french[1]);
                labelLanguage.setText(french[2]);
                logon.setText(french[3]);
                authorized = french[4];
                unauthorized = french[5];
                required = french[6];
                
                labelUsername.setLayoutX(50);
                labelPassword.setLayoutX(50);
                labelLanguage.setLayoutX(50);
                break;
        }
    }
    
    private void selectLanguageByLocale() {
        Locale locale = Locale.getDefault();
        //Locale locale = new Locale("es", "ES");
        //Locale locale = Locale.FRENCH;
        
        if(locale.getLanguage().equals("en")) {
            language.getSelectionModel().select("English");
            selectedLanguage = Language.ENGLISH;
            changeLanguage();
        } else if(locale.getLanguage().equals("es")) {
            language.getSelectionModel().select("Spanish");
            selectedLanguage = Language.SPANISH;
            changeLanguage();
        } else if(locale.getLanguage().equals("fr")) {
            language.getSelectionModel().select("French");
            selectedLanguage = Language.FRENCH;
            changeLanguage();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        languages.addAll("English", "Spanish", "French");
        language.setItems(languages);
        
        selectLanguageByLocale();
    }       
}