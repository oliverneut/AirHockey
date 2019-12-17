package GUI;

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
    private void checkLogin(ActionEvent event){
        /*userNameField.getText().equals("user") && passWordField.getText().equals("user")*/
        if(true) {
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
        else{
            errorLabel.setText("wrong credentials");
        }
    }
}
