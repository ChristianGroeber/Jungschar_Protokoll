/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import jungscharprotokoll.java.model.Anwesenheitskontrolle;
import jungscharprotokoll.java.model.Leiter;
import jungscharprotokoll.java.model.Model;
import jungscharprotokoll.java.model.Protokoll;
import jungscharprotokoll.java.model.Starter;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLStartWindowController implements Initializable {

    private final ArrayList<Leiter> leiter = Starter.getLeiter();
    @FXML
    private Label lblDatum;
    @FXML
    private VBox vBoxAnwesend;
    @FXML
    private VBox vBoxAnwesendNachmittag;

    private final ArrayList<Leiter> anwesend = new ArrayList<>();
    private final ArrayList<Leiter> abwesend = new ArrayList<>();
    private final ArrayList<Leiter> abwesendNachmittag = new ArrayList<>();

    private final ArrayList<CheckBox> boxAnwesend = new ArrayList<>();
    private final ArrayList<CheckBox> boxAbwesendNachmittag = new ArrayList<>();

    Protokoll protokoll = Protokoll.getInstance();
    @FXML
    private ComboBox<String> cmbZustaendig;
    @FXML
    private AnchorPane anch;
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
        createAnwesend();
        createAbwesendNachmittag();
        fillComboBox();
    }

    private void createAnwesend() {
        for (Leiter i : leiter) {
            CheckBox box = createCheckBox(i.getName(), true);
            vBoxAnwesend.getChildren().add(box);
            boxAnwesend.add(box);
        }
    }

    private void createAbwesendNachmittag() {
        for (Leiter i : leiter) {
            CheckBox box = createCheckBox(i.getName(), false);
            vBoxAnwesendNachmittag.getChildren().add(box);
            boxAbwesendNachmittag.add(box);
        }
    }

    private CheckBox createCheckBox(String title, boolean checked) {
        CheckBox box = new CheckBox(title);
        box.setSelected(checked);
        return box;
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        for (CheckBox box : boxAnwesend) {
            Leiter l = getLeiter(box.getText());
            if (box.isSelected()) {
                anwesend.add(l);
            } else {
                abwesend.add(l);
            }
        }

        for (CheckBox box : boxAbwesendNachmittag) {
            Leiter l = getLeiter(box.getText());
            if (box.isSelected()) {
                abwesendNachmittag.add(l);
            }
        }

        Anwesenheitskontrolle k = Anwesenheitskontrolle.getInstance();
        k.setAnwesend(anwesend);
        k.setAbwesend(abwesend);
        k.setAbwesendNachmittag(abwesendNachmittag);
        k.setZustaendig(getLeiter(cmbZustaendig.getValue()));

        outputInConsole(anwesend);
        outputInConsole(abwesend);
        outputInConsole(abwesendNachmittag);

        createFile(protokoll.getFile());

        Starter.getModel().openNewWindow("FXMLMainProtokoll.fxml", "Editor");
    }

    private void createFile(File f) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        Anwesenheitskontrolle k = Anwesenheitskontrolle.getInstance();
        String einschub = protokoll.getEinschub();
        String newLine = protokoll.getNewLine();
        String text = "<!DOCTYPE html>" + newLine;
        text += "<html>" + newLine;
        text += einschub + "<head>" + newLine;
        text += einschub + einschub + "<link rel=\"stylesheet\" href=\"style.css\">" + newLine;
        text += einschub + einschub + "<link rel=\"stylesheet\" href=\"bootstrap-grid.css\">" + newLine;
        text += einschub + "</head>" + newLine;
        text += "<!--Tabelle Kopf-->" + newLine;
        text += einschub + "<body>" + newLine;
        text += einschub + einschub + "<table border=\"1\">" + newLine;
        text += einschub + einschub + einschub + "<tr>" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><h1>Jungscharprotokoll</h1></th>" + newLine;
        text += einschub + einschub + einschub + einschub + "<th>" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + "<table>" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + einschub + "<tr>" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + einschub + einschub + "<th><p>Sitzung vom:</p></th>" + newLine;
        text += "<!--Sitzung vom-->" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + einschub + einschub + "<th><p>" + getDate() + "</p></th>" + newLine;
        text += "<!--ENDE Sitzung vom-->" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + einschub + "</tr>" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + einschub + "<tr>" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + einschub + einschub + "<th><p>Zuständig:</p></th>" + newLine;
        text += "<!--Zustaendig-->" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + einschub + einschub + "<th><p>" + k.getZustaendig().getName() + "</p></th>" + newLine;
        text += "<!--ENDE Zustaendig-->" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + einschub + "</tr>" + newLine;
        text += einschub + einschub + einschub + einschub + einschub + "</table>" + newLine;
        text += einschub + einschub + einschub + einschub + "</th>" + newLine;
        text += einschub + einschub + einschub + "</tr>" + newLine;
        text += einschub + einschub + "</table>" + newLine;
        text += einschub + einschub + "<table border=\"1\">" + newLine;
        text += einschub + einschub + einschub + "<tr>" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><p>Anwesend:</p></th>" + newLine;
        text += "<!--Anwesend-->" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><p>" + getString(k.getAnwesend()) + "</p></th>" + newLine;
        text += "<!--ENDE Anwesend-->" + newLine;
        text += einschub + einschub + einschub + "</tr>" + newLine;
        text += einschub + einschub + einschub + "<tr>" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><p>Abwesend:</p></th>" + newLine;
        text += "<!--Abwesend-->" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><p>" + getString(k.getAbwesend()) + "</p></th>" + newLine;
        text += "<!--ENDE Abwesend-->" + newLine;
        text += einschub + einschub + einschub + "</tr>" + newLine;
        text += einschub + einschub + einschub + "<tr>" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><p>Abwesend am Nachmittag:</p></th>" + newLine;
        text += "<!--Abwesend Nachmittag-->" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><p>" + getString(k.getAbwesendNachmittag()) + "</p></th>" + newLine;
        text += "<!--ENDE Abwesend Nachmittag-->" + newLine;
        text += einschub + einschub + einschub + "</tr>" + newLine;
        text += einschub + einschub + "</table>" + newLine;
        text += "<!--ENDE Tabelle Kopf-->" + newLine;
        text += einschub + einschub + "<table border=\"1\">" + newLine;
        text += einschub + einschub + einschub + "<tr>" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><b>Zeit</b></th>" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><b>Tätigkeit</b></th>" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><b>Verantwortlich</b></th>" + newLine;
        text += einschub + einschub + einschub + einschub + "<th><b>Material</b></th>" + newLine;
        text += einschub + einschub + einschub + "</tr>" + newLine;
        text += einschub + einschub + "</table>" + newLine;
        text += "<!--Protokoll-->" + newLine;
        text += "<!--ENDE Protokoll-->" + newLine;
        text += "<!--Runde-->" + newLine;
        text += "<!--ENDE Runde-->" + newLine;
        text += einschub + einschub + "<p> Some Sample Text </p>" + newLine;
        text += einschub + einschub + "<p> Another sample Text</p>" + newLine;
        text += einschub + "</body>" + newLine;
        text += "</html>";
        protokoll.writeToFile(text, 0);
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getString(ArrayList<Leiter> leiter) {
        String ret = "";
        for (Leiter i : leiter) {
            if (ret.equals("")) {
                ret = i.getName();
            } else {
                ret += ", " + i.getName();
            }
        }
        return ret;
    }

    private Leiter getLeiter(String name) {
        for (Leiter l : leiter) {
            if (l.getName().equals(name)) {
                return l;
            }
        }
        return null;
    }

    private boolean checkArray(ArrayList<Leiter> arrLeiter, Leiter leiter) {
        for (Leiter i : arrLeiter) {
            if (leiter.getName().equals(i.getName())) {
                return true;
            }
        }
        return false;
    }

    private void outputInConsole(ArrayList<Leiter> leiter) {
        for (Leiter l : leiter) {
            System.out.println(l.getName());
        }
    }

    private void fillComboBox() {
        ObservableList<String> options = FXCollections.observableArrayList();
        for (Leiter i : leiter) {
            options.add(i.getName());
        }
        cmbZustaendig.setItems(options);
    }

    @FXML
    private void goTo(MouseEvent event) throws IOException {
        System.out.println("Cannot Switch Windows here");
    }

}
