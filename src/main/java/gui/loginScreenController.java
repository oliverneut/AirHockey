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
     *
     * @param event
     */
    @FXML
    private void goBack(ActionEvent event){
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
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
        Map<String, String> param = new HashMap<String, String>();
        param.put("user", username);
        param.put("password", password);

        HttpRequest httpRequest = httpController.makeRequest("/user/login", param);

        String response = httpController.sendRequest(httpRequest);

        return response != null && response.contains("Successful");
    }


}
