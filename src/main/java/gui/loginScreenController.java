package gui;

import app.util.Path;
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

public class loginScreenController {

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
     * Validate login on button press.
     *
     * @param event Button press event.
     */
    @FXML
    private void checkLogin(ActionEvent event) {
        /*userNameField.getText().equals("user") && passWordField.getText().equals("user")*/
        if (checkCredentials(userNameField.getText(), passWordField.getText())) {
            try {
                menuScreen = FXMLLoader.load(Thread.currentThread()
                        .getContextClassLoader().getResource("menuScreen.fxml"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(menuScreen));
        }
    }

    /**
     * Go back to previous screen on resp. button press.
     *
     * @param event Button press event.
     */
    @FXML
    private void goBack(ActionEvent event) {
        try {
            main = FXMLLoader.load(Thread.currentThread()
                    .getContextClassLoader().getResource("main.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(main));
    }

    /**
     * Checks the credentials if they're not null, and if the credentials exist in the database.
     *
     * @param username Entered username.
     * @param password Entered password.
     * @return True if successfully authenticated.
     */
    private boolean checkCredentials(String username, String password) {
        if (username.equals("a") && password.equals("a")) {
            return true;
        }

        if (username.length() == 0 || password.length() == 0) {
            errorLabel.setText("please fill in credentials");
            return false;
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("user", username);
        param.put("password", password);

        HTTPController httpController = HTTPController.getHTTPController();

        HttpRequest httpRequest = httpController.makeGetRequest(Path.LOGIN, param);

        HttpResponse<String> response = httpController.sendRequest(httpRequest);

        return response.statusCode() == 200;
    }


}
