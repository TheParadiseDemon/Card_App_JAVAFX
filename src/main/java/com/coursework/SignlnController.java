package com.coursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class SignlnController {

    //Базы данных---------------------------------------------------------------
    public static Connection con;
    public static PreparedStatement prSt;
    public static final String user = "Denis";
    public static final String password = "PrestigioPlaza10(";
    public static final String url = "jdbc:mysql://localhost:3306/coursework";
    public TextField UserPassword;
    public TextField UserLogin;
    public TextField UserLastName;
    public TextField UserName;

    public Connection getDbConnection() throws ClassCastException, SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
        return con;
    }

    public boolean CheckExistLogin() throws SQLException {

        ResultSet res = null;
        String add = "select * from passwords where логин = ?";

        try{
            prSt = getDbConnection().prepareStatement(add);
            prSt.setString(1, UserLogin.getText());
            res = prSt.executeQuery();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            if (res.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Пользователь с таким логином уже зарегистрирован.");

                alert.showAndWait();
                return true;
            }
        } catch (SQLException e){
            System.out.println("Не прочитал строку.");
            return false;
        }
//        System.out.println("Неучтенный случай.");
        return false;
    }

    public boolean CheckEmptyFields(){

        if ((Objects.equals(UserName.getText(), "")) ||
                (Objects.equals(UserLastName.getText(), "")) ||
                (Objects.equals(UserPassword.getText(), "")) ||
                (Objects.equals(UserLogin.getText(), ""))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Заполните все поля.");

            alert.showAndWait();
            return true;
        }
        else
            return false;
    }

    private boolean Checked() throws SQLException {

        return (CheckExistLogin() || CheckEmptyFields());
    }

    public void SetNewUser() throws SQLException{

        ResultSet res = null;
        String add = "insert into passwords(пароль, логин, имя, фамилия) values (?,?,?,?)";

        try{
            prSt = getDbConnection().prepareStatement(add);
            prSt.setString(1, UserPassword.getText());
            prSt.setString(2, UserLogin.getText());
            prSt.setString(3, UserName.getText());
            prSt.setString(4, UserLastName.getText());
            prSt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void EnterProfileRegClicked(ActionEvent actionEvent) throws IOException, SQLException {
        if (!Checked()){
            SetNewUser();
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"));
            Scene scene = new Scene((Parent)fxmlLoader.load(), 600.0, 400.0);
            MainWindow.setScene(scene);
        }
    }
}
