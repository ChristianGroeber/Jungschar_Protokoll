/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import jungscharprotokoll.java.model.Protokoll;
import jungscharprotokoll.java.model.Starter;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLHtmlEditorController implements Initializable {

    private final Protokoll protokoll = Protokoll.getInstance();
    @FXML
    private TextArea txtFile;
    @FXML
    private Button btnBold;
    @FXML
    private Button btnKursive;
    @FXML
    private Button btnUnderline;
    @FXML
    private Button btnBigger;
    @FXML
    private Button btnSmaller;
    @FXML
    private Button btnNewLine;
    @FXML
    private Label lblFontSize;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadContent();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        lblFontSize.setText("" + fontSize);
    }

    private void loadContent() throws IOException {
        ArrayList<String> str = protokoll.getFileText();

        String text = "";
        for (String i : str) {
            text += i + "\n";
        }

        txtFile.setText(text);
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        protokoll.writeToFile(txtFile.getText(), 0);
        Starter.getModel().openNewWindow("FXMLEditor.fxml", "Editor");
    }

    private int fontSize = 10;

    @FXML
    private void change(ActionEvent event) {
        String str = txtFile.getText();
        Button changed = (Button) event.getSource();
        String strChanged = changed.getId();
        String enter = "";
        switch (strChanged) {
            case "btnBold":
                enter = "<b></b>";
                break;
            case "btnKursive":
                enter = "<i></i>";
                break;
            case "btnUnderline":
                enter = "<u></u>";
                break;
            case "btnBigger":
                fontSize++;
                lblFontSize.setText("" + fontSize);
                break;
            case "btnSmaller":
                fontSize = fontSize - 1;
                lblFontSize.setText("" + fontSize);
                break;
            case "btnNewLine":
                enter = "<p font-size=\"" + fontSize + "\"></p>";
                break;
        }
        str += enter;
        txtFile.setText(str);
    }

    Dragboard db;
    ClipboardContent content;
    ArrayList<String> text;

    @FXML
    private void drag(MouseEvent event) throws IOException {
//        String str = txtFile.getSelectedText();

//        content.putString(str);
//        db = txtFile.startDragAndDrop(TransferMode.ANY);
//        db.setContent(content);
//        event.consume();
        String str = txtFile.getSelectedText();
        text = protokoll.getFileText(txtFile.getText(), "\n");

        int counter = 0;
        for (String i : text) {
            if (i.contains(str)) {
                counter++;
            }
            if (counter == 1) {
                content = new ClipboardContent();
                content.putString(str);
                break;
            }
        }

    }

    @FXML
    private void dragDone(DragEvent event) {
        System.out.println("done");
    }

}
