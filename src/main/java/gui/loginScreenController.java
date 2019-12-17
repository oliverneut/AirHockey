package gui;

import static gui.Main.httpController;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
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
    private void checkLogin(ActionEvent event) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("user", userNameField.getText());
        param.put("password", passWordField.getText());

        HttpRequest httpRequest = httpController.makeRequest("/user/login", param);

        String response = httpController.sendRequest(httpRequest);

        if (response != null && response.equals("Successful")) {
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
        } else {
            errorLabel.setText("wrong credentials");
        }
    }
}
