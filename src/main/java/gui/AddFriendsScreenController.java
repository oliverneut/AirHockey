package gui;

import app.util.Path;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFriendsScreenController {

    private transient Parent menuScreen = null;

    @FXML
    private Button goBackButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button acceptButton;

    @FXML
    private Button declineButton;

    @FXML
    private TextField friendsTextField;

    @FXML
    private TableColumn usernameList;


    @FXML
    private void goBack(ActionEvent event) {
        try {
            menuScreen = FXMLLoader.load(
                    Thread.currentThread().getContextClassLoader().getResource("menuScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(menuScreen));
    }

    @FXML
    private void searchFriends(ActionEvent event) {
        String username = friendsTextField.getText();
        String display = "";

        if (username.isEmpty()) {
            return;
        }

        String[] displayUsers = findFriendsDB(username);
    }

    private String[] findFriendsDB(String username) {
        return new String[1];
    }


    @FXML
    private void acceptFriendRequest(ActionEvent event) {
        // still to be implemented
    }

    @FXML
    private void declineFriendRequest(ActionEvent event) {
        // still to be implemented
    }

}
