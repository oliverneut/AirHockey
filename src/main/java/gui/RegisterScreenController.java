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


public class RegisterScreenController {

    @FXML
    private transient TextField userName;

    @FXML
    private transient PasswordField passWord1;

    @FXML
    private transient PasswordField passWord2;

    @FXML
    private Button goBackButton;

    @FXML
    private transient Label passWordError;

    private transient Parent menuScreen = null;

    private transient Parent main = null;

    private transient int statusCode = 201;

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


    @FXML
    private void register(ActionEvent event) {
        if (!passWord1.getText().equals(passWord2.getText())) {
            passWordError.setText("Passwords are not the same");
            return;
        }

        if (registerCredentials(userName.getText(), passWord1.getText())) {
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
     * Registers the username and password in the database.
     * also checks if it isn't registered already.
     * If the username or password is null then return false.
     *
     * @param username the filled in username.
     * @param password the filled in password.
     * @return False if registration unsuccessful.
     */
    private boolean registerCredentials(String username, String password) {
        if (username.length() == 0 || password.length() == 0) {
            passWordError.setText("Please fill in the fields.");
            return false;
        }

        Map<String, String> params = new HashMap<>();
        params.put("user", username);
        params.put("password", password);

        HttpRequest httpRequest = httpController.makeGetRequest("/user/register", params);

        HttpResponse<String> httpResponse = httpController.sendRequest(httpRequest);

        if (httpResponse.statusCode() == statusCode) {
            return true;
        }

        passWordError.setText(httpResponse.body());
        return false;
    }


}
