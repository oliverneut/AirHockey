package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.File;
import java.net.URL;

public class loginScreenController {

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passWordField;

    @FXML
    private Button loginButton2;

    @FXML
    private Label errorLabel;

    @FXML
    private Button goBackButton;


    /**
     *
     * @param event
     */
    @FXML
    private void checkLogin(ActionEvent event){
        /*userNameField.getText().equals("user") && passWordField.getText().equals("user")*/
        if(checkCredentials(userNameField.getText(), passWordField.getText())) {
            Parent menuScreen = null;
            try {
                URL url = new File("/Users/oliverneut/Desktop/template/src/main/resources/menuScreen.fxml").toURI().toURL();
                menuScreen = FXMLLoader.load(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(menuScreen));
        }

    }

    /**
     *
     * @param event
     */
    @FXML
    private void goBack(ActionEvent event){
        Parent main = null;
        try {
            URL url = new File("../template/src/main/resources/main.fxml").toURI().toURL();
            main = FXMLLoader.load(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(main));
    }

    /**
     * Checks the credentials if they're not null, and if the credentials exist in the database
     * @param username
     * @param password
     * @return
     */
    private boolean checkCredentials(String username, String password){
        if(username.length() == 0 || password.length() == 0){
            errorLabel.setText("please fill in credentials");
            return false;
        }
        /**
         *  IMPLEMENT DATABASE QUERY.
         */

        return true;
    }


}
