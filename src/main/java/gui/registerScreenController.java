package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class registerScreenController {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField passWord1;

    @FXML
    private PasswordField passWord2;

    @FXML
    private Button goBackButton;

    @FXML
    private Label passWordError;

    @FXML
    private void goBack(ActionEvent event) {
        Parent main = null;
        try {
            main = FXMLLoader.load(
                    getClass().getClassLoader().getResource("main.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(main));
    }


    @FXML
    private void register(ActionEvent event) {
        if (!passWord1.getText().equals(passWord2.getText())) {
            passWordError.setText("passwords are not the same");
            return;
        }

        if (registerCredentials(userName.getText(), passWord1.getText())) {
            Parent menuScreen = null;
            try {
                menuScreen = FXMLLoader.load(
                        getClass().getClassLoader().getResource("menuScreen.fxml"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(menuScreen));
        }


    }


    /**
     * Registers the username and password in the database,
     * also checks if it isn't registered already.
     *
     * @param username .
     * @param password .
     */
    private boolean registerCredentials(String username, String password) {
        return true;
    }


}
