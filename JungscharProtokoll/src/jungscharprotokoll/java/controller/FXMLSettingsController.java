/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jungscharprotokoll.java.dbConnection.DatabaseConnection;
import jungscharprotokoll.java.model.Model;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLSettingsController implements Initializable {

    private AnchorPane addLeiter;
    
    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    @FXML
    private AnchorPane leiterView;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPosition;
    @FXML
    private TextField txtGruppe;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    private void writeToDatabase() throws SQLException{
        String name = txtName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String position = txtPosition.getText();
        String gruppe = txtGruppe.getText();
        
        dbConnection.connectToMysql("localhost", "jungschar", "root", "");
        dbConnection.writeNewLeiter(name, lastName, email, gruppe, position);
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        new Model().openNewWindow("FXMLStart.fxml", "Start");
    } 

    @FXML
    private void newLeiter(ActionEvent event) {
    }

    @FXML
    private void save(ActionEvent event) throws SQLException {
        writeToDatabase();
    }
    
}
