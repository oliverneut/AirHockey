package gui;

import app.util.Path;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
    private transient TextField friendsTextField;

    @FXML
    private ListView<String> addFriendList;

    private ObservableList<String> friends = FXCollections.observableArrayList();

    @FXML
    private ListView<String> requestList;




    @FXML
    void goBack(ActionEvent event) {
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

        if (username.isEmpty()) {
            return;
        }
        String[] displayUsers = findFriendsDB(username);
        addFriendList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addFriendList.getItems().addAll(displayUsers);
    }

    @FXML
    private String addFriend(ActionEvent event){
        String selected = addFriendList.getSelectionModel().getSelectedItem();
        return selected;
    }

    private String[] findFriendsDB(String username) {
        Map<String, String> params = new HashMap<>();
        params.put("search", username);

        HTTPController httpController = HTTPController.getHTTPController();
        HttpRequest httpRequest = httpController.makeGetRequest(Path.SEARCHUSERNAME, params);

        HttpResponse<String> httpResponse = httpController.sendRequest(httpRequest);

        JsonObject response = Jsoner.deserialize(httpResponse.body(), new JsonObject());

        String[] usernames = (String[]) response.get("Usernames");
        return usernames;
    }

    @FXML
    public void refreshRequests(ActionEvent event){
        //String[] displayRequests = findRequestsDB();
        String[] displayRequests = {"Jean", "Dixit","Calvin", "Cas", "Oliver"}; //Just for debugging purposes
        requestList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        requestList.getItems().addAll(displayRequests);
    }

    private String[] findRequestsDB(){

        // Find the friend requests for the user in the DB and return them in a String[] array.
        return new String[1];
    }

    @FXML
    private String acceptFriendRequest(ActionEvent event) {
        String selected = requestList.getSelectionModel().getSelectedItem();
        int removeIdx = requestList.getSelectionModel().getSelectedIndex();
        requestList.getItems().remove(removeIdx);
        System.out.println(selected); //Just for debugging purposes
        return selected;
    }

    @FXML
    private String declineFriendRequest(ActionEvent event) {
        String selected = requestList.getSelectionModel().getSelectedItem();
        int removeIdx = requestList.getSelectionModel().getSelectedIndex();
        requestList.getItems().remove(removeIdx);
        System.out.println(selected); //Just for debugging purposes
        return selected;
    }

}
