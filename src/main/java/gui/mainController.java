package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


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
            loginScreen = FXMLLoader.load(
                    getClass().getClassLoader().getResource("loginScreen.fxml"));
        } catch (Exception e) {
            System.out.println("KKR1");
            //e.printStackTrace();
        }
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(loginScreen));
    }

    @FXML
    private void loadRegisterScreen(ActionEvent event) {
        Parent registerScreen = null;
        try {
            registerScreen = FXMLLoader.load(
                    getClass().getClassLoader().getResource("registerScreen.fxml"));
        } catch (Exception e) {
            System.out.println("KKR1");
            //e.printStackTrace();
        }
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(registerScreen));
    }


}
