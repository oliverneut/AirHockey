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

import java.io.File;
import java.net.URL;


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
    private void goBack(ActionEvent event){
        Parent main = null;
        try {
            URL url = new File("/Users/oliverneut/Desktop/template/src/main/resources/main.fxml").toURI().toURL();
            main = FXMLLoader.load(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(main));
    }


    @FXML
    private void register(ActionEvent event){
        if(!passWord1.getText().equals(passWord2.getText())){
            passWordError.setText("passwords are not the same");
            return;
        }

        if(registerCredentials(userName.getText(), passWord1.getText())){
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
     * If the username or password is null then return false.
     * Registers the username and password in the database, also checks if it isn't registered already.
     * @param username the filled in username.
     * @param password the filled in password.
     */
    private boolean registerCredentials(String username, String password){
        if(username.length() == 0 || password.length() == 0){
            passWordError.setText("please fill in credentials");
            return false;
        }

        /**
         *  IMPLEMENT DATABASE QUERY.
         */

        return true;
    }


}
