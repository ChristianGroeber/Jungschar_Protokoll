/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jungscharprotokoll.java.dbConnection.DatabaseConnection;

/**
 *
 * @author chris
 */
public class Starter extends Application {

    private static Stage stage;
    private static Scene scene;
    private WebView view;
    private WebEngine engine;
    private static final Model model = new Model();
    private static ArrayList<Leiter> leiter = new ArrayList<>();
    private static String lastWindow;
    private static DatabaseConnection connection = new DatabaseConnection();

    @Override
    public void start(Stage primaryStage) throws IOException {
//        erstelleLeiter();
        Starter.stage = primaryStage;
        model.openNewWindow("FXMLStart.fxml", "Setup");
    }

    public static Stage getStage() {
        return stage;
    }

    public WebEngine getEngine() {
        return engine;
    }

    public static Model getModel() {
        return model;
    }

    public static Scene getScene() {
        return scene;
    }

    private void erstelleLeiter() {
        addLeiter("Simon", "Schimmer");
        addLeiter("Jolanda", "Weiss ich doch ned");
        addLeiter("Nicola", "Neukom");
    }

    public static ArrayList<Leiter> getLeiter() {
        return leiter;
    }

    public void addLeiter(String name, String nachname) {
        leiter.add(new Leiter(name, nachname));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        connection.connectToMysql("localhost", "jungschar", "root", "");
        leiter = connection.getLeiter();
        launch(args);
    }

    public static HBox getLeiste() {
        HBox leiste = new HBox();
        String path = "/jungscharprotokoll/resources/images/";
        ImageView start = new ImageView(path + "arrowStart.png");
        ImageView protokoll = new ImageView(path + "arrowProtokoll.png");
        ImageView runde = new ImageView(path + "arrowRunde.png");
        ImageView ansicht = new ImageView(path + "arrowAnsicht.png");

        ArrayList<ImageView> arrImage = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            arrImage.add(start);
            arrImage.add(protokoll);
            arrImage.add(runde);
            arrImage.add(runde);
        }
        leiste.getChildren().addAll(start, protokoll, runde, ansicht);
        return leiste;
    }

    public static String getLastWindow() {
        return lastWindow;
    }

    public static void setLastWindow(String lastWindow) {
        Starter.lastWindow = lastWindow;
    }

    public static DatabaseConnection getDatabaseConnection() {
        return connection;
    }
}
