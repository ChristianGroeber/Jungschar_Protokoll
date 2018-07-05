/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

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
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jungscharprotokoll.java.dbConnection.DatabaseConnection;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLSettingsController implements Initializable {

    @FXML
    private TitledPane addLeiter;
    
    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillPane();
    }    
    
    private void fillPane(){
        HBox box = new HBox();
        addLeiter.setContent(box);
        VBox vBox = new VBox();
        box.getChildren().add(vBox);
        HBox hBoxName = new HBox();
        HBox hBoxLastName = new HBox();
        HBox hBoxEmail = new HBox();
        HBox hBoxPosition = new HBox();
        HBox hBoxGruppe = new HBox();
        
        vBox.getChildren().addAll(hBoxName,hBoxLastName, hBoxEmail, hBoxPosition, hBoxGruppe);
        
        Label name = new Label("Name: ");
        TextField txtName = new TextField();
        hBoxName.getChildren().addAll(name, txtName);
        
        Label lastName = new Label("Nachname: ");
        TextField txtLastName = new TextField();
        hBoxLastName.getChildren().addAll(lastName, txtLastName);
        
        Label email = new Label("EMail: ");
        TextField txtEmail = new TextField();
        hBoxEmail.getChildren().addAll(email, txtEmail);
        
        Label position = new Label("Position: ");
        TextField txtPosition = new TextField();
        hBoxPosition.getChildren().addAll(position, txtPosition);
        
        Label gruppe = new Label("Gruppe: ");
        TextField txtGruppe = new TextField();
        hBoxGruppe.getChildren().addAll(gruppe, txtGruppe);
        
        Button save = new Button();
        save.setText("Speichern");
        box.getChildren().add(save);
        save.setOnAction((ActionEvent e) -> {
            try {
                writeToDatabase(vBox);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLSettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    private void writeToDatabase(VBox box) throws SQLException{
        String name = ((TextField) ((HBox) box.getChildren().get(0)).getChildren().get(1)).getText();
        String lastName = ((TextField) ((HBox) box.getChildren().get(1)).getChildren().get(1)).getText();
        String email = ((TextField) ((HBox) box.getChildren().get(2)).getChildren().get(1)).getText();
        String position = ((TextField) ((HBox) box.getChildren().get(3)).getChildren().get(1)).getText();
        String gruppe = ((TextField) ((HBox) box.getChildren().get(4)).getChildren().get(1)).getText();
        
        dbConnection.connectToMysql("localhost", "jungschar", "root", "");
        dbConnection.writeNewLeiter(name, lastName, email, gruppe, position);
    }
    
}
