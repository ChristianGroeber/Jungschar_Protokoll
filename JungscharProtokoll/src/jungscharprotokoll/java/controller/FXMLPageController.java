/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import jungscharprotokoll.java.model.Model;
import jungscharprotokoll.java.model.Protokoll;
import jungscharprotokoll.java.model.Starter;
import jungscharprotokoll.java.model.Timetable;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLPageController implements Initializable {

    private final Protokoll protokoll = Protokoll.getInstance();

    @FXML
    private AnchorPane scrHead;
    @FXML
    private AnchorPane scrProtokoll;
    @FXML
    private AnchorPane scrRunde;

    private ArrayList<String> textProgram;

    private String head;
    private String protokol;
    private String runde;
    private String anderes;

    private final Timetable table = Timetable.getInstance();

    private String htmlHead;
    private String htmlFoot;
    @FXML
    private Button btnHeadBearbeiten;
    @FXML
    private Button btnProgrammBearbeiten;
    @FXML
    private Button btnRundeBearbeiten;
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
        try {
            textProgram = protokoll.getFileText();
//        vView.getChildren().add(view);
//        try {
//            view.getEngine().loadContent(protokoll.getFileString());
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLPageController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (IOException ex) {
            Logger.getLogger(FXMLPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        createHeadFoot();

        fillHead();
        fillProtokol();
        fillRunde();

        System.out.println(head);
        System.out.println(protokol);
        System.out.println(runde);
    }

    private void fillHead() {
        head = fill("<!--Tabelle Kopf-->", "<!--ENDE Tabelle Kopf-->");
        WebView headView = new WebView();
        scrHead.getChildren().add(headView);
        headView.getEngine().loadContent(head);
    }

    private void fillProtokol() {
        protokol = fill("<!--Protokoll-->", "<!--ENDE Protokoll-->");
        createProtokollPunkte();
    }

    private void createProtokollPunkte() {
        WebView view = new WebView();
        protokol = fill("<!--Protokoll-->", "<!--ENDE Protokoll-->");
        scrProtokoll.getChildren().add(view);
        view.getEngine().loadContent(protokol);
    }

    private void fillRunde() {
        runde = fill("<!--Runde-->", "<!--ENDE Runde-->");
        WebView rundeView = new WebView();
        scrRunde.getChildren().add(rundeView);
        rundeView.getEngine().loadContent(runde);
    }

    private String fill(String begin, String end) {
        String ret = "";
        boolean write = false;
        boolean stopWrite = false;
        for (int i = 0; i < textProgram.size(); i++) {
            write = textProgram.get(i).equals(begin);
            if (write) {
                for (int j = i; j < textProgram.size(); j++) {
                    stopWrite = textProgram.get(j).equals(end);
                    if (!stopWrite) {
                        try {
                            ret += textProgram.get(j);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        break;
                    }
                }
                break;
            }
        }
        ret = htmlHead + ret + htmlFoot;
        return ret;
    }

    private void createHeadFoot() {
        htmlHead += "<!DOCTYPE html><html><head><linkt rel=\"stylesheet\" href=\"style.css\"</head><body>";
        htmlFoot += "</body></html>";
    }

    @FXML
    private void edit(ActionEvent event) throws IOException {
        new Model().openNewWindow("FXMLMainProtokollPunkte.fxml", "Protokoll - Bearbeiten");
    }

    @FXML
    private void upload(ActionEvent event) {
        
    }

    @FXML
    private void goTo(MouseEvent event) throws IOException {
        Model m = Starter.getModel();
        String string = ((ImageView) event.getSource()).getId();
        Starter.setLastWindow("FXMLPage.fxml");
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
