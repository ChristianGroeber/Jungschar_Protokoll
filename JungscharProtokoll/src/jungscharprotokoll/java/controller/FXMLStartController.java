/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jungscharprotokoll.java.model.Protokoll;
import jungscharprotokoll.java.model.Starter;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLStartController implements Initializable {

    @FXML
    private TextField txtNeu;

    private final Protokoll protokoll = Protokoll.getInstance();
    @FXML
    private Button btnNeu;
    @FXML
    private Button btnStart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reset();
    }

    private void reset() {
        txtNeu.setDisable(true);
        btnStart.setDisable(true);
    }

    @FXML
    private void oeffnen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        protokoll.setFile(file);
        btnStart.setDisable(false);

    }

    @FXML
    private void neu(ActionEvent event) throws IOException {
        if (btnNeu.getText().equals("Neu")) {
            txtNeu.setDisable(false);
            btnNeu.setText("Speichern");
            btnNeu.setDisable(true);
            txtNeu.positionCaret(0);
        } else if (btnNeu.getText().equals("Speichern")) {
            File f = new File(txtNeu.getText() + ".html");
            f.createNewFile();
            protokoll.setFile(f);
            btnStart.setDisable(false);
        }
    }

    @FXML
    private void start(ActionEvent event) throws IOException {
        Starter.getModel().openNewWindow("FXMLStartWindow.fxml", "Editor");
    }

    @FXML
    private void changed(KeyEvent event) {
        if (!txtNeu.getText().equals("")) {
            btnNeu.setDisable(false);
        }
    }

   

}
