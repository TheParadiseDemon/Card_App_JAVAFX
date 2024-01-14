package com.coursework;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginController {

    public TextField Password;
    public TextField Login;

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

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    public boolean CheckEmtyFields(){

        if ((Objects.equals(Password.getText(), "")) ||
                (Objects.equals(Login.getText(), ""))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Заполните все поля.");

            alert.showAndWait();
            return true;
        } else
            return false;
    }

    public boolean CheckData() throws SQLException {

        ResultSet res = null;
        String searchpassword = "select * from passwords where логин = ?";

        try{
            prSt = getDbConnection().prepareStatement(searchpassword);
            prSt.setString(1, Login.getText());
            res = prSt.executeQuery();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        if (!res.next()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Пользователя с таким логином не существует.");

            alert.showAndWait();
            return true;
        }
        if (!Objects.equals(res.getString(1), Password.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Неверный пароль.");

            alert.showAndWait();
            return true;
        }
//        System.out.println("Неучтенная ошибка.");
        return false;
    }
    private boolean Checked() throws SQLException {

        return ((CheckEmtyFields())||(CheckData()));
    }

    @FXML
    void EnterProfileClicked(ActionEvent event) throws IOException, SQLException {
        if (!Checked()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"));
            Scene scene = new Scene((Parent) fxmlLoader.load(), 600.0, 400.0);
            MainWindow.setScene(scene);
        }
    }

    @FXML
    void RegisterClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("SingIn.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 701.0, 478.0);
        MainWindow.setScene(scene);
    }
}
