package com.coursework;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainWindow extends Application {

    static Stage stage;

    public void start(Stage primarystage) throws IOException {

        stage = primarystage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 600.0, 400.0);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch(new String[0]);
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
