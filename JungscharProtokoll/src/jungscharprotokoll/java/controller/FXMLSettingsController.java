/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jungscharprotokoll.java.dbConnection.DatabaseConnection;
import jungscharprotokoll.java.model.Leiter;
import jungscharprotokoll.java.model.Model;
import jungscharprotokoll.java.model.Starter;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLSettingsController implements Initializable {

    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
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
    @FXML
    private TableColumn<Leiter, String> lastNameCol;
    @FXML
    private TableColumn<Leiter, String> firstNameCol;
    @FXML
    private TableColumn<Leiter, String> eMailCol;
    @FXML
    private TableColumn<Leiter, String> positionCol;
    @FXML
    private TableColumn<Leiter, String> gruppeCol;
    @FXML
    private TableView<Leiter> leiterTbl;
    
    private final ObservableList<Leiter> data = FXCollections.observableArrayList(Starter.getLeiter());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
    }

    private void writeToDatabase() throws SQLException {
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
        txtName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPosition.setText("");
        txtGruppe.setText("");
    }

    @FXML
    private void save(ActionEvent event) throws SQLException {
        writeToDatabase();
    }

    private void fillTable() {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        eMailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        gruppeCol.setCellValueFactory(new PropertyValueFactory<>("gruppe"));
        
        try {
            leiterTbl.setItems(data);
        } catch (Exception e) {
        }
    }

    @FXML
    private void edit(ActionEvent event) {
        Leiter l = leiterTbl.getSelectionModel().getSelectedItem();
        txtName.setText(l.getName());
        txtLastName.setText(l.getNachname());
        txtEmail.setText(l.getEmail());
        txtGruppe.setText(l.getGruppe());
        txtPosition.setText(l.getPosition());
    }

}
