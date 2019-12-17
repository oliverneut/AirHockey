package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;


import java.io.File;
import java.net.URL;


public class mainController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label gameTitle;

    @FXML
    private Button loginButton1;

    @FXML
    private Button registerButton;

    @FXML
    private void loadLoginScreen(ActionEvent event) {
        Parent loginScreen = null;
        try {
            URL url = new File("../template/src/main/resources/loginScreen.fxml").toURI().toURL();
            loginScreen = FXMLLoader.load(url);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(loginScreen));
    }

    @FXML
    private void loadRegisterScreen(ActionEvent event){
        Parent registerScreen = null;
        try {
            URL url = new File("../template/src/main/resources/registerScreen.fxml").toURI().toURL();
            registerScreen = FXMLLoader.load(url);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(registerScreen));
    }



}
