package com.coursework;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    //Текстовые файлы-----------------------------------------------------------
    public static String ModulePath = "C:\\java\\CourseWork\\src\\main\\resources\\com\\coursework\\Module";

    public String Load(int number) throws IOException{

        String result = "";

        Path file = Paths.get(ModulePath + number + ".txt");
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            result = reader.readLine();
        } catch (IOException x) {
            System.err.println(x);
        }
        return result;
    }

    //-----------------------------------------------------------------------------

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void CreateNewModuleClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MakeNewModule.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 701.0, 478.0);
        MainWindow.setScene(scene);
    }

    @FXML
    private Label ChangeProfile;

    @FXML
    private Label Module1_name;

    @FXML
    private Label Module2_name;

    @FXML
    private Label Module3_name;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        String name = "Модуль 1";
        String temp = "";
        try {
            temp = Load(1);
        } catch (IOException e) {
            System.err.println(e);
        }
        if (temp != "")
            name = temp;
        Module1_name.setText(name);
        name = "Модуль 2";
        try {
            temp = Load(2);
        } catch (IOException e) {
            System.err.println(e);
        }
        if (temp != "")
            name = temp;
        Module2_name.setText(name);
        name = "Модуль 3";
        temp = "";
        try {
            temp = Load(3);
        } catch (IOException e) {
            System.err.println(e);
        }
        if (temp != "")
            name = temp;
        Module3_name.setText(name);
    }

    private static int modulenumber;

    private static String modulename;

    @FXML
    void EnterModule1Clicked(MouseEvent event) throws IOException {
        modulenumber = 1;
        modulename = Module1_name.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("LearnByCards.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 700, 450);
        MainWindow.setScene(scene);
    }

    @FXML
    void EnterModule2Clicked(MouseEvent event) throws IOException {
        modulenumber = 2;
        modulename = Module2_name.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("LearnByCards.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 700, 450);
        MainWindow.setScene(scene);
    }

    @FXML
    void EnterModule3Clicked(MouseEvent event) throws IOException {
        modulenumber = 3;
        modulename = Module3_name.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("LearnByCards.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 700, 450);
        MainWindow.setScene(scene);
    }

    @FXML
    void ChangeProfileClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("LogIn.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 700, 450);
        MainWindow.setScene(scene);
    }

    public static int getModulenumber(){
        return modulenumber;
    }
    public static String getModulename(){
        return modulename;
    }
}

