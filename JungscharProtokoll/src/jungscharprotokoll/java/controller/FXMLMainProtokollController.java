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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import jungscharprotokoll.java.model.Leiter;
import jungscharprotokoll.java.model.Materialliste;
import jungscharprotokoll.java.model.Model;
import jungscharprotokoll.java.model.Programmpunkt;
import jungscharprotokoll.java.model.Protokoll;
import jungscharprotokoll.java.model.Starter;
import jungscharprotokoll.java.model.Timetable;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLMainProtokollController implements Initializable {

    @FXML
    private AnchorPane anchTime;
    @FXML
    private Button btnNewLine;
    @FXML
    private Button btnWeiter;
    @FXML
    private VBox vZustaendig;
    @FXML
    private VBox vMaterial;

    private Programmpunkt p = new Programmpunkt();
    private final Timetable table = Timetable.getInstance();
    private Protokoll protokoll = Protokoll.getInstance();

    private final ArrayList<Leiter> leiter = Starter.getLeiter();
    private ArrayList<ArrayList<CheckBox>> boxLeiter = new ArrayList<>();
    private final ArrayList<TitledPane> panes = new ArrayList<>();
    private final ArrayList<ArrayList<Spinner<Integer>>> spinners = new ArrayList<>();
    private final ArrayList<ArrayList<TextField>> textField = new ArrayList<>();

    private Materialliste liste = new Materialliste();

    private String text = "";
    private final String EINSCHUB = protokoll.getEinschub();
    private final String NEWLINE = protokoll.getNewLine();
    @FXML
    private HTMLEditor txtTaetigkeit;

    private int items = 0;

    private final Model m = new Model();
    @FXML
    private ImageView btnStart;
    @FXML
    private ImageView btnProtokoll;
    @FXML
    private ImageView btnRunde;
    @FXML
    private ImageView btnAnsicht;
    @FXML
    private Accordion accordion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            doNewLine();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void newLine(ActionEvent event) throws IOException {
        doNewLine();
    }

    private void doNewLine() throws IOException {
        speichern();
        TitledPane newPane = new TitledPane();
        accordion.getPanes().add(newPane);
        fillPane();
        panes.add(newPane);
    }

    private void fillPane() {
        //get Newest Pane
        TitledPane newPane = null;
        for (TitledPane i : panes) {
            newPane = i;
        }

        TitledPane time = new TitledPane();
        TitledPane taetigkeit = new TitledPane();
        TitledPane zustaendig = new TitledPane();
        TitledPane material = new TitledPane();
        //add all the new Panes to newPane
        newPane.setContent(time);
        newPane.setContent(taetigkeit);
        newPane.setContent(zustaendig);
        newPane.setContent(material);

        //add spinner
        fillSpinner(time);
        //fill taetigkeit
        HTMLEditor html = new HTMLEditor();
        taetigkeit.setContent(html);
        //fill zustaendig
        fillComboBoxZustaendig(zustaendig);
        //fill material

    }

    private void fillMaterial(TitledPane materialPane) {
        VBox box = new VBox();
        HBox hBox = new HBox();
        hBox.getChildren().add(box);
        materialPane.setContent(box);
        Button btn = new Button("Neu");
        hBox.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                box.getChildren().add(new TextField());
            }
        });
    }

    private void fillComboBoxZustaendig(TitledPane zustaendigPane) {
        VBox box = new VBox();
        ArrayList<CheckBox> boxes = new ArrayList<>();
        zustaendigPane.setContent(box);
        for (Leiter i : leiter) {
            CheckBox ckBox = new CheckBox(i.getName());
            box.getChildren().add(ckBox);
            boxes.add(ckBox);
        }
        boxLeiter.add(boxes);
    }

    private void fillSpinner(TitledPane timePane) {
        HBox box = new HBox();
        Programmpunkt p = table.getLastProgrammpunkt();
        SpinnerValueFactory<Integer> vonH = new SpinnerValueFactory.IntegerSpinnerValueFactory(p.getEndeH(), 24);
        SpinnerValueFactory<Integer> vonM = new SpinnerValueFactory.IntegerSpinnerValueFactory(p.getEndeM(), 60);
        SpinnerValueFactory<Integer> bisH = new SpinnerValueFactory.IntegerSpinnerValueFactory(p.getEndeH(), 24);
        SpinnerValueFactory<Integer> bisM = new SpinnerValueFactory.IntegerSpinnerValueFactory(p.getEndeM(), 60);

        Spinner<Integer> spnVonH = new Spinner();
        Spinner<Integer> spnVonM = new Spinner();
        Spinner<Integer> spnBisH = new Spinner();
        Spinner<Integer> spnBisM = new Spinner();

        spnVonH.setValueFactory(vonH);
        spnVonM.setValueFactory(vonM);
        spnBisH.setValueFactory(bisH);
        spnBisM.setValueFactory(bisM);

        box.getChildren().addAll(spnVonH, spnVonM, spnBisH, spnBisM);

        ArrayList<Spinner<Integer>> arrSpinners = new ArrayList<>();
        arrSpinners.add(spnVonH);
        arrSpinners.add(spnVonM);
        arrSpinners.add(spnBisH);
        arrSpinners.add(spnBisM);

        spinners.add(arrSpinners);
    }

    @FXML
    private void weiter(ActionEvent event) throws IOException {
        speichern();
        m.openNewWindow("FXMLRunde.fxml", "Runde");
    }

    @FXML
    private void neu(ActionEvent event) {
        items++;
        TextField field = new TextField();
        vMaterial.getChildren().add(field);
    }

    private void speichern() throws UnsupportedEncodingException, IOException {
        p.addZustaendig(getZustaendigLeiter());
        p.setBeginnH(spnVonH.getValue());
        p.setBeginnM(spnVonM.getValue());
        p.setEndeH(spnBisH.getValue());
        p.setEndeM(spnBisM.getValue());
        p.setTaetigkeit(txtTaetigkeit.getHtmlText());
        table.setProgrammpunkt(p);
        createHtml();
        protokoll.writeToFile(text, protokoll.getNextLine2());
        protokoll.setNextLine2(protokoll.getNextLine2() + m.toArray(text).size());
    }

    private Leiter getZustaendigLeiter() {
        for (Leiter i : leiter) {
            for (CheckBox box : boxLeiter) {
                if (i.getName().equals(box.getText()) && box.isSelected()) {
                    return i;
                }
            }
        }
        return null;
    }

    private void createHtml() {
        int line = protokoll.getLine();
        text += EINSCHUB + "<!--Start line " + line + "-->" + NEWLINE;
        p.setPunkt(line);
        text += EINSCHUB + EINSCHUB + EINSCHUB + "<tr>" + NEWLINE;
        text += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th>" + spnVonH.getValue() + ":" + spnVonM.getValue() + " - "
                + spnBisH.getValue() + ":" + spnBisM.getValue() + "</th>" + NEWLINE;
        text += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th>" + txtTaetigkeit.getHtmlText() + "</th>" + NEWLINE;
        text += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th>" + getZustaendig() + "</th>" + NEWLINE;
        text += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th>" + getMaterial() + "</th>" + NEWLINE;
        text += EINSCHUB + EINSCHUB + EINSCHUB + "</tr>" + NEWLINE;
        text += EINSCHUB + "<!--End line " + protokoll.getLine() + "-->" + NEWLINE;

        p.setHtmlText(text);
        protokoll.addLine();
    }

    private String getMaterial() {
        String ret = "";
        for (int i = 0; i < items; i++) {
            if (i != 0) {
                ret += ", ";
            }
            TextField field = (TextField) vMaterial.getChildren().get(i);
            ret += field.getText();
        }
        return ret;
    }

    private String getZustaendig() {
        String ret = "";
        for (int i = 0; i < boxLeiter.size(); i++) {
            if (boxLeiter.get(i).isSelected()) {
                if (!ret.equals("")) {
                    ret += ", ";
                }
                ret += boxLeiter.get(i).getText();
            }
        }
        return ret;
    }

    @FXML
    private void goTo(MouseEvent event) throws IOException {
        String string = ((ImageView) event.getSource()).getId();
        Starter.setLastWindow("FXMLMainProtokoll.fxml");
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
