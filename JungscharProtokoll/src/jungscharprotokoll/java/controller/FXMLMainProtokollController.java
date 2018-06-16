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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private Button btnNewLine;
    @FXML
    private Button btnWeiter;
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
        TitledPane newPane = new TitledPane();
        accordion.getPanes().add(newPane);
        fillPane(newPane);
        panes.add(newPane);
        newPane.setText("newPane");
    }

    private void speichern(TitledPane pane) {
        VBox myPanes = (VBox) pane.getContent();
        VBox box = (VBox) myPanes.getChildren();
        ObservableList<Node> panes2 = box.getChildren();
        HBox hBoxSpinners = (HBox) panes2.get(0);
        ObservableList<Node> spinner = hBoxSpinners.getChildren();
        for (Node i : spinner) {
            System.out.println(i.getClass());
        }
    }

    private void fillPane(TitledPane newPane) {
        HBox box = new HBox();
        VBox myPanes = new VBox();
        box.getChildren().add(myPanes);
        TitledPane time = new TitledPane();
        TitledPane taetigkeit = new TitledPane();
        TitledPane zustaendig = new TitledPane();
        TitledPane material = new TitledPane();
        //add all the new Panes to newPane
        myPanes.getChildren().addAll(time, taetigkeit, zustaendig, material);
        newPane.setContent(box);

        time.setText("Zeit");
        taetigkeit.setText("Tätigkeit");
        zustaendig.setText("Zuständig");
        material.setText("Material");

        //add spinner
        fillSpinner(time);
        //fill taetigkeit
        HTMLEditor html = new HTMLEditor();
        AnchorPane anch = new AnchorPane();
        html.setMaxSize(550, 200);
        anch.getChildren().add(html);
        taetigkeit.setContent(anch);
        //fill zustaendig
        fillComboBoxZustaendig(zustaendig);
        //fill material
        fillMaterial(material);
        Button btn = new Button();

        btn.setText("Speichern");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                speichern(newPane);
            }
        });

        box.getChildren().add(btn);
    }

    private void fillMaterial(TitledPane materialPane) {
        VBox box = new VBox();
        HBox hBox = new HBox();
        hBox.getChildren().add(box);
        materialPane.setContent(hBox);
        Button btn = new Button("Neu");
        hBox.getChildren().add(btn);
        btn.setOnAction((ActionEvent e) -> {
            box.getChildren().add(new TextField());
        });
    }

    /**
     * This Methode creates one ComboBox for every Leiter that is in the
     * ArrayList. Currently, these Leiter are only created manually by the
     * Starter, but the idea is, that they will be read from the MySQL Server.
     *
     * @param zustaendigPane
     */
    private void fillComboBoxZustaendig(TitledPane zustaendigPane) {
        VBox box = new VBox();
        ArrayList<CheckBox> boxes = new ArrayList<>();
        for (Leiter i : leiter) {
            CheckBox ckBox = new CheckBox(i.getName());
            box.getChildren().add(ckBox);
            boxes.add(ckBox);
        }
        zustaendigPane.setContent(box);
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
        timePane.setContent(box);

        ArrayList<Spinner<Integer>> arrSpinners = new ArrayList<>();
        arrSpinners.add(spnVonH);
        arrSpinners.add(spnVonM);
        arrSpinners.add(spnBisH);
        arrSpinners.add(spnBisM);

        for (Spinner<Integer> i : arrSpinners) {
            i.setMaxWidth(100);
            i.setEditable(true);
        }

        spinners.add(arrSpinners);
    }

    @FXML
    private void weiter(ActionEvent event) throws IOException {
        speichern();
        m.openNewWindow("FXMLRunde.fxml", "Runde");
    }

    private void speichern() throws UnsupportedEncodingException, IOException {
//        p.addZustaendig(getZustaendigLeiter());
//        p.setBeginnH(spnVonH.getValue());
//        p.setBeginnM(spnVonM.getValue());
//        p.setEndeH(spnBisH.getValue());
//        p.setEndeM(spnBisM.getValue());
//        p.setTaetigkeit(txtTaetigkeit.getHtmlText());
//        table.setProgrammpunkt(p);
//        createHtml();
//        protokoll.writeToFile(text, protokoll.getNextLine2());
//        protokoll.setNextLine2(protokoll.getNextLine2() + m.toArray(text).size());
    }

    private void getTime() {

    }

    private Leiter getZustaendigLeiter() {
//        for (Leiter i : leiter) {
////            for (CheckBox box : boxLeiter) {
////                if (i.getName().equals(box.getText()) && box.isSelected()) {
////                    return i;
////                }
//            }
//        }
        return null;
    }

    private void createHtml() {
//        int line = protokoll.getLine();
//        text += EINSCHUB + "<!--Start line " + line + "-->" + NEWLINE;
//        p.setPunkt(line);
//        text += EINSCHUB + EINSCHUB + EINSCHUB + "<tr>" + NEWLINE;
//        text += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th>" + spnVonH.getValue() + ":" + spnVonM.getValue() + " - "
//                + spnBisH.getValue() + ":" + spnBisM.getValue() + "</th>" + NEWLINE;
//        text += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th>" + txtTaetigkeit.getHtmlText() + "</th>" + NEWLINE;
//        text += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th>" + getZustaendig() + "</th>" + NEWLINE;
//        text += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th>" + getMaterial() + "</th>" + NEWLINE;
//        text += EINSCHUB + EINSCHUB + EINSCHUB + "</tr>" + NEWLINE;
//        text += EINSCHUB + "<!--End line " + protokoll.getLine() + "-->" + NEWLINE;
//
//        p.setHtmlText(text);
//        protokoll.addLine();
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
//        for (int i = 0; i < boxLeiter.size(); i++) {
//            if (boxLeiter.get(i).isSelected()) {
//                if (!ret.equals("")) {
//                    ret += ", ";
//                }
//                ret += boxLeiter.get(i).getText();
//            }
//        }
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
