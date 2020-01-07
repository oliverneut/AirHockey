package gui;

import static gui.Main.httpController;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

/**
 * Controller for the loginscreen.
 */
public class LoginScreenController {

    @FXML
    private transient TextField userNameField;

    @FXML
    private transient PasswordField passWordField;

    @FXML
    private Button loginButton2;

    @FXML
    private transient Label errorLabel;

    @FXML
    private Button goBackButton;

    private transient Parent menuScreen = null;

    private transient Parent main = null;


    /**
     * Checks the login credentials.
     * @param event mouseclick on the login button.
     */
    @FXML
    private void checkLogin(ActionEvent event) {
        /*userNameField.getText().equals("user") && passWordField.getText().equals("user")*/
        if (checkCredentials(userNameField.getText(), passWordField.getText())) {
            try {
                menuScreen = FXMLLoader.load(
                        Thread.currentThread().getContextClassLoader()
                                .getResource("menuScreen.fxml"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(menuScreen));
        }

    }

    /**
     * Goes back to the previous scene, when the back button is pressed.
     * @param event mouseclick on the back button.
     */
    @FXML
    private void goBack(ActionEvent event) {
        try {
            main = FXMLLoader.load(
                    Thread.currentThread().getContextClassLoader()
                            .getResource("main.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(main));
    }

    /**
     * Checks the credentials if they're not null, and if the credentials exist in the database.
     * @param username the username entered.
     * @param password the password entered.
     * @return
     */
    private boolean checkCredentials(String username, String password) {
        if (username.length() == 0 || password.length() == 0) {
            errorLabel.setText("please fill in credentials");
            return false;
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("user", username);
        param.put("password", password);

        HttpRequest httpRequest = httpController.makeGetRequest("/user/login", param);

        HttpResponse<String> response = httpController.sendRequest(httpRequest);

        return response.statusCode() == 200;
    }


}
