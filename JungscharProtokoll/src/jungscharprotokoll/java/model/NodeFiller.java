/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.model;

import java.util.ArrayList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author chris
 */
public class NodeFiller {

    private final Timetable table = Timetable.getInstance();

    public ArrayList<ArrayList<Spinner<Integer>>> fillSpinner(TitledPane timePane, ArrayList<ArrayList<Spinner<Integer>>> spinners) {
        HBox box = new HBox();
        Programmpunkt p = table.getLastProgrammpunkt();
        SpinnerValueFactory<Integer> vonH = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> vonM = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);
        SpinnerValueFactory<Integer> bisH = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> bisM = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60);

        vonH.setValue(getNewestPunkt(spinners).get(0));
        vonM.setValue(getNewestPunkt(spinners).get(1));
        bisH.setValue(getNewestPunkt(spinners).get(0));
        bisM.setValue(getNewestPunkt(spinners).get(1));

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
        return spinners;
    }

    private ArrayList<Integer> getNewestPunkt(ArrayList<ArrayList<Spinner<Integer>>> spinners) {
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

    public HBox createTitledPanes() {
        HBox box = new HBox();
        VBox myPanes = new VBox();
        box.getChildren().add(myPanes);
        TitledPane time = new TitledPane();
        TitledPane taetigkeit = new TitledPane();
        TitledPane zustaendig = new TitledPane();
        TitledPane material = new TitledPane();

        time.setText("Zeit");
        taetigkeit.setText("Tätigkeit");
        zustaendig.setText("Zuständig");
        material.setText("Material");

        myPanes.getChildren().addAll(time, taetigkeit, zustaendig, material);
        return box;
    }
}
