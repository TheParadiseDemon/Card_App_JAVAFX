package com.coursework;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class MakeNewModuleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField def1;

    @FXML
    private TextField def2;

    @FXML
    private TextField term1;

    @FXML
    private TextField term2;

    private int modulenumber = 1;
    private static final String ModulePath = "C:\\java\\CourseWork\\src\\main\\resources\\com\\coursework\\Module";
    private static final String UpdatePath = "C:\\java\\CourseWork\\src\\main\\resources\\com\\coursework\\Update.txt";

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

    public void SaveData() throws SQLException {
        ResultSet res = null;
        String delete = "DELETE from module" + modulenumber;
        String add = "insert into module" + modulenumber + " (Слово, Перевод) values (?,?)";

        try{
            prSt = getDbConnection().prepareStatement(delete);
            prSt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        for (Pair<String, String> i : list) {
            try{
                prSt = getDbConnection().prepareStatement(add);
                prSt.setString(1, i.getKey());
                prSt.setString(2, i.getValue());
                prSt.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    //Текстовые файлы-----------------------------------------------------------

    public int Load() throws IOException{

        Path file = Paths.get(UpdatePath);
        int result = 1;
        String s = "";
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            s = reader.readLine();
            result = Integer.parseInt(s);
        } catch (IOException x) {
            System.err.println(x);
        }
        return result;
    }

    public void ChangeUpdateNumber(int number) throws IOException{
        Path file = Paths.get(UpdatePath);

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(file, StandardOpenOption.TRUNCATE_EXISTING))) {
            out.write(String.valueOf(number).getBytes());
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    //-----------------------------------------------------------------------------

    public List<Pair<String, String>> GetData(){

        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();

        result.add(new Pair<String, String>(term1.getText(), def1.getText()));
        result.add(new Pair<String, String>(term2.getText(), def2.getText()));

        return result;
    }

    @FXML
    TextField ModuleName;
    public void Save(int number) throws IOException, SQLException {

        String s = ModuleName.getText();
        if (s.length() > 20){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Название должно не превышать 20 символов.");

            alert.showAndWait();
        }
        else if (s.length() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Введите название модуля.");

            alert.showAndWait();
        }
        else {

            byte data[] = s.getBytes();
            modulenumber = Load();

            Path file = Paths.get(ModulePath + modulenumber + ".txt");

            try (OutputStream out = new BufferedOutputStream(
                    Files.newOutputStream(file, StandardOpenOption.TRUNCATE_EXISTING))) {
                out.write(data, 0, data.length);
            } catch (IOException x) {
                System.err.println(x);
            }
            list = GetData();
            SaveData();
            if (modulenumber != 3)
                modulenumber++;
            else
                modulenumber = 1;
            ChangeUpdateNumber(modulenumber);
        }
    }

    @FXML
    void initialize() {

    }

    public void abortNewModule(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 600.0, 400.0);
        MainWindow.setScene(scene);
    }

    public void SavemoduleClicked(ActionEvent actionEvent) throws IOException, SQLException {
        if ((term1.getText() != "")&&(term2.getText() != "")&&(def1.getText() != "")&&(def2.getText() != ""))
            Save(modulenumber);
    }
}