/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jungscharprotokoll.java.model.Model;
import jungscharprotokoll.java.model.Programmpunkt;
import jungscharprotokoll.java.model.Starter;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLMainProtokollPunkteController implements Initializable {

    @FXML
    private ImageView btnStart;
    @FXML
    private ImageView btnProtokoll;
    @FXML
    private ImageView btnRunde;
    @FXML
    private ImageView btnAnsicht;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        for (Programmpunkt i : punkte) {
//            p = i;
//            TitledPane pane = new TitledPane();
//            pane.setText(i.getBeginnH() + ":" + i.getBeginnM() + " - " + i.getEndeH() + ":" + i.getEndeM());
//
//            VBox box = new VBox();
//            pane.setContent(box);
//
//            fillSpinner(s1, s2, s3, s4);
//
//            HBox spinners = new HBox();
//            Button btn = new Button("Speichern");
//            btn.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    try {
//                        speichern();
//                    } catch (IOException ex) {
//                        System.out.println(ex);
//                    }
//                }
//
//            });
//            spinners.getChildren().addAll(s1, s2, s3, s4, btn);
//            box.getChildren().add(spinners);
//        }
    }

    @FXML
    private void goTo(MouseEvent event) throws IOException {
        String string = ((ImageView) event.getSource()).getId();
        Starter.setLastWindow("FXMLMainProtokollPunkte.fxml");
        Model m = new Model();
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
}
