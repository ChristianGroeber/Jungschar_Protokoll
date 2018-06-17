/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import jungscharprotokoll.java.model.Model;
import jungscharprotokoll.java.model.Protokoll;
import jungscharprotokoll.java.model.Starter;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLRundeController implements Initializable {

    @FXML
    private ImageView btnStart;
    @FXML
    private ImageView btnProtokoll;
    @FXML
    private ImageView btnRunde;
    @FXML
    private ImageView btnAnsicht;
    @FXML
    private HTMLEditor html;

    private String rundeText;

    private Protokoll protokoll = Protokoll.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goTo(MouseEvent event) throws IOException {
        Model m = Starter.getModel();
        String string = ((ImageView) event.getSource()).getId();
        Starter.setLastWindow("FXMLRunde.fxml");
        switch (string) {
            case "btnStart":
                m.openNewWindow("FXMLStartWindow2.fxml", "Start");
                break;
            case "btnProtokoll":
                m.openNewWindow("FXMLMainProtokoll.fxml", "Protokoll");
                break;
            case "btnRunde":
                m.openNewWindow("FXMLRunde.fxml", "Runde");
                break;
            case "btnAnsicht":
                m.openNewWindow("FXMLPage.fxml", "Ansicht");
                break;
            default:
                System.out.println("Unknown Button");
        }
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        System.out.println(html.getHtmlText());
//        rundeText += html.getHtmlText();
//        protokoll.writeToFile(editHtml(rundeText), protokoll.getInsertIn("<!--Runde-->"));
    }

    

    @FXML
    private void newP(ActionEvent event) {
        
    }

}
