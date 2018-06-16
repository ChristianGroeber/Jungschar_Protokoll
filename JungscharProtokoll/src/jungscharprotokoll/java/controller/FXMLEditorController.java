/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jungscharprotokoll.java.model.Protokoll;
import jungscharprotokoll.java.model.Starter;
import static jungscharprotokoll.java.model.Starter.getScene;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLEditorController implements Initializable {

    @FXML
    private VBox boxView;
    @FXML
    private TextField txtEingabe;
    @FXML
    private CheckBox btnFett;
    @FXML
    private CheckBox btnKursiv;
    @FXML
    private CheckBox btnUnterstrichen;
    @FXML
    private Button btnGrösser;
    @FXML
    private Button btnKleiner;

    private final Protokoll protokoll = Protokoll.getInstance();

    private int nextLine = 42;
    @FXML
    private Button btnHTML;
    @FXML
    private TextField txtSize;

    private boolean bold = false;
    private boolean kursive = false;
    private boolean underline = false;

    private int textSize = 10;
    @FXML
    private Button neueTabellenzeile;
    
    private final String EINSCHUB = protokoll.getEinschub();
    private final String NEWLINE = protokoll.getNewLine();
    @FXML
    private AnchorPane anchTime;
    @FXML
    private Spinner<?> spnVonH;
    @FXML
    private Spinner<?> spnVonM;
    @FXML
    private Spinner<?> spnBisH;
    @FXML
    private Spinner<?> spnBisM;
    @FXML
    private Button btnEinfuegen;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(protokoll.getFile().getPath());
        System.out.println(boxView.getChildren());
        txtSize.setText("" + textSize);
    }

    @FXML
    private void enterText(ActionEvent event) throws UnsupportedEncodingException, IOException {
        nextLine = nextLine + 1;
        String text = "<p><font size=\"" + textSize + "\">" + txtEingabe.getText() + "</font></p>";

        //text formatting
        if (bold) {
            text = "<b>" + text + "</b>";
        }
        if (kursive) {
            text = "<i>" + text + "</i>";
        }
        if (underline) {
            text = "<u>" + text + "</u>";
        }
        protokoll.writeToFile(text, nextLine);
        txtEingabe.setText("");

//        putText();

        txtEingabe.positionCaret(0);
    }

    public void putText() {
        WebView view = new WebView();
        view.setMaxSize(getScene().getWidth(), getScene().getHeight());
        view.maxWidthProperty().bind(getScene().widthProperty());
        view.maxHeightProperty().bind(getScene().heightProperty());
        try {
            boxView.getChildren().remove(0);
            boxView.getChildren().addAll(view);
            System.out.println(boxView.getChildren());
        } catch (Exception e) {
            System.out.println(e);
            view = new WebView();
            boxView.getChildren().addAll(view);
        }
        WebEngine engine = view.getEngine();
        URL urlHello = getClass().getResource(protokoll.getFile().getPath());
        try {
            engine.load(urlHello.toExternalForm());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void change(ActionEvent event) {
        CheckBox changed = (CheckBox) event.getSource();
        String strChanged = changed.getId();
        switch (strChanged) {
            case "btnFett":
                bold = !bold;
                break;
            case "btnKursiv":
                kursive = !kursive;
                break;
            case "btnUnterstrichen":
                underline = !underline;
                break;
            case "btnKleiner":
                textSize = textSize - 1;
                break;
            case "btnGrösser":
                textSize = textSize + 1;
                break;
        }
    }
    
    int line = 0;

    @FXML
    private void change2(ActionEvent event) throws UnsupportedEncodingException, IOException {
        Button changed = (Button) event.getSource();
        String strChanged = changed.getId();
        switch (strChanged) {
            case "btnKleiner":
                textSize = textSize - 1;
                break;
            case "btnGrösser":
                textSize = textSize + 1;
                break;
            case "neueTabellenzeile":
                System.out.println();
                protokoll.writeToFile(EINSCHUB + "<!--Start Line " + line + "-->" + NEWLINE + EINSCHUB + EINSCHUB + 
                        EINSCHUB + "<tr>" + NEWLINE + EINSCHUB + EINSCHUB + EINSCHUB + "</tr>" + NEWLINE
                        + EINSCHUB + "<!--End Line " + line + "-->" + NEWLINE, protokoll.getNextLine(line));
                nextLine++;
                break;
        }
        txtSize.setText("" + textSize);
    }

    @FXML
    private void editHTML(ActionEvent event) throws IOException {
        Starter.getModel().openNewWindow("FXMLHtmlEditor.fxml", "HTML");
    }

    @FXML
    private void changeSize(ActionEvent event) {
        try {
            textSize = Integer.parseInt(txtSize.getText());
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void einfuegen(ActionEvent event) {
    }

}
