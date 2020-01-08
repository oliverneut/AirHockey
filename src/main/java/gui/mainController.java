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

    private transient Parent loginScreen = null;

    private transient Parent registerScreen = null;

    @FXML
    private void loadLoginScreen(ActionEvent event) {
        try {
            loginScreen = FXMLLoader.load(
                    Thread.currentThread().getContextClassLoader().getResource("loginScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(loginScreen));
    }

    @FXML
    private void loadRegisterScreen(ActionEvent event) {
        try {
            registerScreen = FXMLLoader.load(Thread.currentThread()
                    .getContextClassLoader().getResource("registerScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(registerScreen));
    }


}
