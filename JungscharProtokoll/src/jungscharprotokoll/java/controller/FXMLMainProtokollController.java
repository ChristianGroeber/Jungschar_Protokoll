/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
    private final Protokoll protokoll = Protokoll.getInstance();

    private final ArrayList<Leiter> leiter = Starter.getLeiter();
    private final ArrayList<ArrayList<CheckBox>> boxLeiter = new ArrayList<>();
    private final ArrayList<TitledPane> panes = new ArrayList<>();
    private final ArrayList<ArrayList<Spinner<Integer>>> spinners = new ArrayList<>();
    private final ArrayList<ArrayList<TextField>> textField = new ArrayList<>();
    private final ArrayList<HTMLEditor> htmlText = new ArrayList<>();

    private final Materialliste liste = new Materialliste();

    private String text = "";
    private final String EINSCHUB = protokoll.getEinschub();
    private final String NEWLINE = protokoll.getNewLine();

    private final int items = 0;

    private final ArrayList<Integer> alreadyWritten = new ArrayList<>();

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
        ArrayList<Programmpunkt> punkte = table.getProgrammpunkt();
        if (!punkte.isEmpty()) {
            loadOlderProgrammpunkte(punkte);
        }
        try {
            doNewLine();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void newLine(ActionEvent event) throws IOException {
        speichern();
        doNewLine();
    }

    private void doNewLine() throws IOException {
        ArrayList<Programmpunkt> punkte = table.getProgrammpunkt();
        Programmpunkt p = new Programmpunkt();
        table.setProgrammpunkt(p);
        p.setPunkt(protokoll.getLine());
        protokoll.addLine();

        //Setting the titles for all panes - which is the time.
        for (int i = 0; i < panes.size(); i++) {
            panes.get(i).setText(spinners.get(i).get(0).getValue() + ":" + spinners.get(i).get(1).getValue()
                    + " - " + spinners.get(i).get(2).getValue() + ":" + spinners.get(i).get(3).getValue());
        }
        TitledPane newPane = new TitledPane();
        accordion.getPanes().add(newPane);
        textField.add(new ArrayList<>());
        fillPane(newPane);
        panes.add(newPane);
        newPane.setText("newPane");
        listToEdit++;
    }

    private void loadOlderProgrammpunkte(ArrayList<Programmpunkt> punkte) {
        Collections.reverse(punkte);
        for (Programmpunkt i : punkte) {
            System.out.println("Loading older Programmpunkt N°" + punkte.indexOf(i));
            TitledPane pane = new TitledPane();
            HBox box = createTitledPanes();
            VBox vBox = (VBox) box.getChildren().get(0);
            pane.setText(i.getBeginnH() + ":" + i.getBeginnM() + " - " + i.getEndeH() + ":" + i.getEndeM());
            //fill Spinners
            TitledPane timePane = (TitledPane) vBox.getChildren().get(0);
            ArrayList<Integer> times = new ArrayList<>();
            times.add(i.getBeginnH());
            times.add(i.getBeginnM());
            times.add(i.getEndeH());
            times.add(i.getEndeM());
            fillSpinner(timePane, times);

            //fill Tätigkeit
            HTMLEditor html = new HTMLEditor();
            htmlText.add(html);
            AnchorPane anch = new AnchorPane();
            html.setMaxSize(550, 200);
            anch.getChildren().add(html);
            TitledPane taetigkeit = (TitledPane) vBox.getChildren().get(1);
            taetigkeit.setContent(anch);
            html.setHtmlText(i.getHtmlTaetigkeit());

            //fill Comboboxes
            ArrayList<CheckBox> checkBoxes = new ArrayList<>();
            VBox vBox1000 = new VBox();
            for (Leiter x : leiter) {
                CheckBox checkBox = new CheckBox();
                checkBoxes.add(checkBox);
                checkBox.setText(x.getName());
                for (Leiter p : i.getZustaendig()) {
                    if (p.getName().equals(checkBox.getText())) {
                        checkBox.setSelected(true);
                    }
                }
                vBox1000.getChildren().add(checkBox);
            }
            boxLeiter.add(checkBoxes);
            TitledPane zustaendigPane = (TitledPane) vBox.getChildren().get(2);
            zustaendigPane.setContent(vBox1000);
            //fill Material
            TitledPane materialPane = (TitledPane) vBox.getChildren().get(3);
            VBox vBox2 = new VBox();
            HBox hBox = new HBox();
            hBox.getChildren().add(vBox2);
            materialPane.setContent(hBox);
            Button btn = new Button("Neu");
            hBox.getChildren().add(btn);
            btn.setOnAction((ActionEvent e) -> {
                TextField field = new TextField();
                vBox2.getChildren().add(field);
                textField.get(punkte.indexOf(i)).add(field);
            });

            textField.add(new ArrayList<>());
            for (String str : i.getMaterial()) {
                TextField alreadyField = new TextField();
                alreadyField.setText(str);
                vBox2.getChildren().add(alreadyField);
                textField.get(punkte.indexOf(i)).add(alreadyField);
            }
            accordion.getPanes().add(pane);
            pane.setContent(box);
            pane.setCollapsible(true);
        }
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        speichern();
    }

    private void speichern() throws IOException {
        ArrayList<Programmpunkt> punkte = table.getProgrammpunkt();
        //this is so that every punkt is only once in the table.
        int counter = 0;
        for (Programmpunkt x : punkte) {
            if (!alreadyWritten.contains(x.getPunkt())) {
                System.out.println("Now importing already existing Programmpunkte Counter: " + counter);
                String html = "";
                int line = x.getPunkt();
                html += EINSCHUB + "<!--Start line " + line + "-->" + NEWLINE;
                html += EINSCHUB + EINSCHUB + EINSCHUB + "<tr>" + NEWLINE
                        + saveTime(counter, x) + saveTaetigkeit(counter, x) + saveZustaendig(counter, x) + saveMaterial(counter, x)
                        + EINSCHUB + EINSCHUB + EINSCHUB + "</tr>" + NEWLINE;
                html += EINSCHUB + "<!--ENDE line " + line + "-->" + NEWLINE;
                x.setHtmlText(html);
                alreadyWritten.add(x.getPunkt());
                x.setHtmlTaetigkeit(saveTaetigkeit(counter, x));
            }
            counter++;
        }
        table.sort();

        //here I need to make it so that every punkt only appears once in the protokol.
        //Approach: checking if the punkt is written already in the protokoll itself.
        protokoll.deleteProtokolRows();
        ArrayList<Programmpunkt> punkteSorted = table.getProgrammpunkt();
        Collections.reverse(punkteSorted);
        for (Programmpunkt p : punkteSorted) {
            protokoll.writeToFile(p.getHtmlText(), 59);
        }
    }

    private String saveTime(int index, Programmpunkt p) {
        String htmlRet = "";
        ArrayList<Spinner<Integer>> spinner = spinners.get(index);
        p.setBeginnH(spinner.get(0).getValue());
        p.setBeginnM(spinner.get(1).getValue());
        System.out.println("Spinner getValue2 " + spinner.get(2).getValue());
        p.setEndeH(spinner.get(2).getValue());
        System.out.println("Spinner getValue3 " + spinner.get(3).getValue());
        p.setEndeM(spinner.get(3).getValue());
        htmlRet += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th><p>" + spinner.get(0).getValue()
                + ":" + spinner.get(1).getValue() + " - " + spinner.get(2).getValue()
                + ":" + spinner.get(3).getValue() + "</p></th>" + NEWLINE;
        System.out.println("Saved Time");
        return htmlRet;
    }

    private String saveTaetigkeit(int index, Programmpunkt p) {
        String htmlRet = "";
        String strHtmlText = "";
        try {
            strHtmlText = new Model().editHtml(htmlText.get(index).getHtmlText());
        } catch (Exception e) {
            System.out.println(e);
        }
        htmlRet += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th><p>"
                + strHtmlText + "</th>" + NEWLINE;
        return htmlRet;
    }

    private String saveZustaendig(int index, Programmpunkt p) {
        String htmlRet = "";
        ArrayList<CheckBox> box = boxLeiter.get(index);
        ArrayList<String> zustaendig = new ArrayList<>();
        for (CheckBox i : box) {
            if (i.isSelected()) {
                zustaendig.add(i.getText());
            }
        }
        htmlRet += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th><p>";
        for (int i = 0; i < zustaendig.size(); i++) {
            if (i != 0) {
                htmlRet += ", ";
            }
            htmlRet += zustaendig.get(i);
        }
        htmlRet += "</p></th>" + NEWLINE;
        return htmlRet;
    }

    private String saveMaterial(int index, Programmpunkt p) {
        String htmlRet = "";
        System.out.println("ArrayList<TextField> size: " + textField.size() + " index: " + index);
        ArrayList<TextField> fields = new ArrayList<>();
        try {
            fields = textField.get(index);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Index Out of Bounds: fucked up Material.");
        }
        htmlRet += EINSCHUB + EINSCHUB + EINSCHUB + EINSCHUB + "<th><p>";
        for (int i = 0; i < fields.size(); i++) {
            if (i != 0) {
                htmlRet += ", ";
            }
            if (!p.matExists(fields.get(i).getText())) {
                htmlRet += fields.get(i).getText();
                p.setMaterial(fields.get(i).getText());
            }
        }
        htmlRet += "</p></th>" + NEWLINE;
        return htmlRet;
    }

    private void fillPane(TitledPane newPane) {
        HBox box = createTitledPanes();
        VBox vBox = (VBox) box.getChildren().get(0);
        //add spinner
        fillSpinner((TitledPane) vBox.getChildren().get(0));
        //fill taetigkeit
        HTMLEditor html = new HTMLEditor();
        htmlText.add(html);
        AnchorPane anch = new AnchorPane();
        html.setMaxSize(550, 200);
        anch.getChildren().add(html);
        TitledPane taetigkeit = (TitledPane) vBox.getChildren().get(1);
        taetigkeit.setContent(anch);
        //fill zustaendig
        fillComboBoxZustaendig((TitledPane) vBox.getChildren().get(2));
        //fill material
        fillMaterial((TitledPane) vBox.getChildren().get(3));

        newPane.setContent(box);
    }

    private HBox createTitledPanes() {
        HBox box = new HBox();
        VBox myPanes = new VBox();
        box.getChildren().add(myPanes);
        TitledPane time = new TitledPane();
        TitledPane taetigkeit = new TitledPane();
        TitledPane zustaendig = new TitledPane();
        TitledPane material = new TitledPane();

        time.setExpanded(false);
        taetigkeit.setExpanded(false);
        zustaendig.setExpanded(false);
        material.setExpanded(false);

        time.setText("Zeit");
        taetigkeit.setText("Tätigkeit");
        zustaendig.setText("Zuständig");
        material.setText("Material");

        Button delete = new Button();
        box.getChildren().add(delete);
        delete.setText("Löschen");
        delete.setOnAction((ActionEvent e) -> {
            
        });

        myPanes.getChildren().addAll(time, taetigkeit, zustaendig, material);
        return box;
    }

    private int listToEdit = 0;

    private void fillMaterial(TitledPane materialPane) {
        VBox box = new VBox();
        HBox hBox = new HBox();
        hBox.getChildren().add(box);
        materialPane.setContent(hBox);
        Button btn = new Button("Neu");
        hBox.getChildren().add(btn);
        btn.setOnAction((ActionEvent e) -> {
            TextField field = new TextField();
            box.getChildren().add(field);
            textField.get(listToEdit - 1).add(field);
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

    private void fillSpinner(TitledPane timePane, ArrayList<Integer> times) {
        HBox box = new HBox();
        Programmpunkt p = table.getLastProgrammpunkt();
        SpinnerValueFactory<Integer> vonH = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> vonM = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);
        SpinnerValueFactory<Integer> bisH = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> bisM = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);

        vonH.setValue(times.get(0));
        vonM.setValue(times.get(1));
        bisH.setValue(times.get(2));
        bisM.setValue(times.get(3));

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

    private void fillSpinner(TitledPane timePane) {
        HBox box = new HBox();
        Programmpunkt p = table.getLastProgrammpunkt();
        SpinnerValueFactory<Integer> vonH = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> vonM = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);
        SpinnerValueFactory<Integer> bisH = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> bisM = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);

        vonH.setValue(getNewestPunkt().get(0));
        vonM.setValue(getNewestPunkt().get(1));
        bisH.setValue(getNewestPunkt().get(0));
        bisM.setValue(getNewestPunkt().get(1));

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

    private ArrayList<Integer> getNewestPunkt() {
        ArrayList<Integer> ret = new ArrayList<>();
        int newestH = 0;
        int newestM = 0;
        for (ArrayList<Spinner<Integer>> i : spinners) {
            if (i.get(2).getValue() > newestH) {
                newestH = i.get(2).getValue();
            }
            if (i.get(3).getValue() > newestM) {
                newestM = i.get(3).getValue();
            }
        }
        ret.add(newestH);
        ret.add(newestM);
        return ret;
    }

    @FXML
    private void weiter(ActionEvent event) throws IOException {
        speichern();
        m.openNewWindow("FXMLRunde.fxml", "Runde");
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
