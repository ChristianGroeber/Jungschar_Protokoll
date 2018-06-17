/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author chris
 */
public class Model {

    private final Protokoll protokoll = Protokoll.getInstance();

    public void openNewWindow(String file, String title) throws IOException {
        Stage stage = Starter.getStage();
        Parent root = FXMLLoader.load(getClass().getResource("/jungscharprotokoll/fxml/view/" + file));
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public ArrayList<String> toArray(String text) {
        ArrayList<String> ret;
        String[] arr = text.split(protokoll.getNewLine());
        ret = new ArrayList<>(Arrays.asList(arr));
        return ret;
    }
    
    public  String editHtml(String html) {
        String[] arrString = html.split("");
        ArrayList<String> arrHtml = new ArrayList<>(Arrays.asList(arrString));
        for(int i = 0; i < 61; i++){
            arrHtml.remove(0);
        }
        int arrLength = arrHtml.size() - 1;
        int x = 0;
        for(int i = arrLength; x < 14; arrLength--){
            arrHtml.remove(arrLength);
            x++;
        }
        StringBuilder sb = new StringBuilder();
        for(String i : arrHtml){
            sb.append(i);
        }
        return sb.toString();
    }
}
