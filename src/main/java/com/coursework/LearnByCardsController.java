package com.coursework;

import java.io.IOException;
import java.net.URL;


import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javafx.scene.shape.Circle;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

import static com.coursework.MainWindowController.*;

public class LearnByCardsController implements Initializable {

    //Базы данных---------------------------------------------------------------
    public static Connection con;
    public static PreparedStatement prSt;
    public static final String user = "Denis";
    public static final String password = "PrestigioPlaza10(";
    public static final String url = "jdbc:mysql://localhost:3306/coursework";

    public Connection getDbConnection() throws ClassCastException, SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
        return con;
    }

    List<Pair<String, String>> list = new ArrayList<Pair<String, String>>();

    public void GetData() throws SQLException {
        ResultSet res = null;
        String select = "SELECT * FROM module" + getModulenumber();
        try{
            prSt = getDbConnection().prepareStatement(select);
            res = prSt.executeQuery();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        while(res.next()){
            list.add(new Pair<String, String>(res.getString(2),res.getString(3)));
        }
    }

    public int currentIndex = 0;

    //---------------------------------------------------------------

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void CloseModuleClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 600.0, 400.0);
        MainWindow.setScene(scene);
    }

    @FXML
    private Label Card;

    @FXML
    void SwitchCard(MouseEvent event) throws IOException {
        if (Card.getText().equals(list.get(currentIndex).getKey()))
            Card.setText(list.get(currentIndex).getValue());
        else
            Card.setText(list.get(currentIndex).getKey());
    }

    @FXML
    private Label ModuleName;

    private int listlength;

    @FXML
    private Label Counter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            GetData();
        } catch (SQLException e) {
            System.err.println("Проблемы с базой данных" + e);
        }
        Card.setText(list.get(0).getKey());
        listlength = list.size();
        ModuleName.setText(getModulename());
        Counter.setText((currentIndex + 1) + "/" + listlength);
    }

    @FXML
    private Circle NextCard;

    @FXML
    private Circle PreviousCard;

    @FXML
    void NextCardClicked(MouseEvent event) {
        if (currentIndex == list.size() - 1)
            currentIndex = 0;
        else
            currentIndex++;
        Card.setText(list.get(currentIndex).getKey());
        Counter.setText((currentIndex + 1) + "/" + listlength);
    }

    @FXML
    void PreviousCardClicked(MouseEvent event) {
        if (currentIndex == 0)
            currentIndex = list.size() - 1;
        else
            currentIndex--;
        Card.setText(list.get(currentIndex).getKey());
        Counter.setText((currentIndex + 1) + "/" + listlength);
    }
}
