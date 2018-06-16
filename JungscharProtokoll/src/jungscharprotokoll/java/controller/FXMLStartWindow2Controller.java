/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.IOException;
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
public class FXMLStartWindow2Controller implements Initializable {

    private final ArrayList<Leiter> leiter = Starter.getLeiter();
    @FXML
    private Label lblDatum;
    @FXML
    private ComboBox<String> cmbZustaendig;
    @FXML
    private VBox vBoxAnwesend;
    @FXML
    private VBox vBoxAnwesendNachmittag;
    @FXML
    private ImageView btnStart;
    @FXML
    private ImageView btnProtokoll;
    @FXML
    private ImageView btnRunde;
    @FXML
    private ImageView btnAnsicht;

    private Protokoll prot = Protokoll.getInstance();

    private final Anwesenheitskontrolle kont = Anwesenheitskontrolle.getInstance();

    private final ArrayList<CheckBox> boxAnwesend = new ArrayList<>();
    private final ArrayList<CheckBox> boxAbwesendNachmittag = new ArrayList<>();

    private final ArrayList<Leiter> anwesend = kont.getAnwesend();
    private final ArrayList<Leiter> abwesend = kont.getAbwesend();
    private final ArrayList<Leiter> abwesendNachmittag = kont.getAbwesendNachmittag();

    private Leiter zustaendig = kont.getZustaendig();

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
            CheckBox box = createCheckBox(i.getName(), anwesend.contains(i));
            vBoxAnwesend.getChildren().add(box);
            boxAnwesend.add(box);
        }
    }

    private void createAbwesendNachmittag() {
        for (Leiter i : leiter) {
            CheckBox box = createCheckBox(i.getName(), abwesendNachmittag.contains(i));
            vBoxAnwesendNachmittag.getChildren().add(box);
            boxAbwesendNachmittag.add(box);
        }
    }

    private CheckBox createCheckBox(String title, boolean checked) {
        CheckBox box = new CheckBox(title);
        box.setSelected(checked);
        return box;
    }

    private void fillComboBox() {
        ObservableList<String> options = FXCollections.observableArrayList();
        for (Leiter i : leiter) {
            options.add(i.getName());
        }
        cmbZustaendig.setItems(options);
        cmbZustaendig.setValue(kont.getZustaendig().getName());
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
        ArrayList<String> text = prot.getFileText();
        String einschub = prot.getEinschub();
        String newLine = prot.getNewLine();
        int x = 0;
        for (String i : text) {
            x++;
            if (i.contains("<!--Sitzung vom-->")) {
                text.set(x, einschub + einschub + einschub + einschub + einschub + einschub + einschub + "<th><p>" + getDate() + "</p></th>" + newLine);
            } else if (i.contains("<!--Zustaendig-->")) {
                text.set(x, einschub + einschub + einschub + einschub + einschub + einschub + einschub + "<th><p>" + k.getZustaendig().getName() + "</p></th>" + newLine);
            } else if (i.contains("<!--Anwesend-->")) {
                text.set(x, einschub + einschub + einschub + einschub + "<th><p>" + getString(k.getAnwesend()) + "</p></th>" + newLine);
            } else if (i.contains("<!--Abwesend-->")) {
                text.set(x, einschub + einschub + einschub + einschub + "<th><p>" + getString(k.getAbwesend()) + "</p></th>" + newLine);
            } else if (i.contains("<!--Abwesend Nachmittag-->")) {
                text.set(x, einschub + einschub + einschub + einschub + "<th><p>" + getString(k.getAbwesendNachmittag()) + "</p></th>" + newLine);
            }
        }
        
        prot.writeToFile(text.toString(), 0);
        Starter.getModel().openNewWindow(Starter.getLastWindow(), Starter.getLastWindow());
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private Leiter getLeiter(String name) {
        for (Leiter l : leiter) {
            if (l.getName().equals(name)) {
                return l;
            }
        }
        return null;
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

    @FXML
    private void goTo(MouseEvent event) throws IOException {
        Model m = Starter.getModel();
        String string = ((ImageView) event.getSource()).getId();
        Starter.setLastWindow("FXMLStartWindow2.fxml");
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
