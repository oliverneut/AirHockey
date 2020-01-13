package gui;

import app.util.Path;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.util.ArrayList;
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
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;

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
    private transient ListView<String> addFriendList;

    private ObservableList<String> friends = FXCollections.observableArrayList();

    @FXML
    private transient ListView<String> requestList;

    @FXML
    public void intitialize(){
        displayRequests();
    }

    @FXML
    void goBack(ActionEvent event) {
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

    @FXML
    private void searchFriends(ActionEvent event) {
        String username = friendsTextField.getText();

        if (username.isEmpty()) {
            return;
        }
        ArrayList<String> displayUsers = findFriendsDB(username);
        addFriendList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addFriendList.getItems().addAll(displayUsers);
    }

    @FXML
    private String addFriend(ActionEvent event) {
        String selected = addFriendList.getSelectionModel().getSelectedItem();

        Map<String, String> params = new HashMap<>();
        params.put("to", selected);
        HTTPController httpController = HTTPController.getHTTPController();
        Request request = httpController.makeGetRequest(Path.SENDREQUEST, params);
        ContentResponse response = httpController.sendRequest(request);

        if (response.getStatus() != HttpStatus.OK_200) {
            System.out.println("Error : " + response.getContentAsString());
        }
        return selected;
    }

    private ArrayList<String> findFriendsDB(String username) {
        Map<String, String> params = new HashMap<>();
        params.put("search", username);

        HTTPController httpController = HTTPController.getHTTPController();
        Request request = httpController.makeGetRequest(Path.SEARCHUSERNAME, params);

        ContentResponse response = httpController.sendRequest(request);
        JsonObject jsonObject = Jsoner.deserialize(response.getContentAsString(), new JsonObject());

        ArrayList<String> usernames = (ArrayList<String>) jsonObject.get("Usernames");

        return usernames;
    }

    /**
     * Reload the received friend requests.
     *
     * @param event Button click event.
     */
    @FXML
    public void refreshRequests(ActionEvent event) {
        displayRequests();
    }

    /**
     * Load the received friend requests.
     */
    public void displayRequests(){
        ArrayList<String> displayRequests = findRequestsDB();
        requestList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        requestList.getItems().addAll(displayRequests);
    }

    private ArrayList<String> findRequestsDB() {

        HTTPController httpController = HTTPController.getHTTPController();
        Request request = httpController.makeGetRequest(
                Path.RECEIVEDREQUESTS, new HashMap<>());

        ContentResponse response = httpController.sendRequest(request);
        JsonObject jsonObject = Jsoner.deserialize(response.getContentAsString(), new JsonObject());

        ArrayList<String> friendRequests = (ArrayList<String>)
                jsonObject.get(jsonObject.get("Head"));

        return friendRequests;
    }

    @FXML
    private String acceptFriendRequest(ActionEvent event) {
        String selected = requestList.getSelectionModel().getSelectedItem();
        int removeIdx = requestList.getSelectionModel().getSelectedIndex();
        requestList.getItems().remove(removeIdx);
        System.out.println(selected); //Just for debugging purposes

        Map<String, String> params = new HashMap<>();
        params.put("from", selected);
        HTTPController httpController = HTTPController.getHTTPController();
        Request request = httpController.makeGetRequest(Path.ACCEPTREQUEST, params);
        ContentResponse response = httpController.sendRequest(request);

        if (response.getStatus() != HttpStatus.OK_200) {
            System.out.println("Error : " + response.getContentAsString());
        }

        return selected;
    }

    @FXML
    private String declineFriendRequest(ActionEvent event) {
        String selected = requestList.getSelectionModel().getSelectedItem();
        int removeIdx = requestList.getSelectionModel().getSelectedIndex();
        requestList.getItems().remove(removeIdx);
        System.out.println(selected); //Just for debugging purposes

        Map<String, String> params = new HashMap<>();
        params.put("from", selected);
        HTTPController httpController = HTTPController.getHTTPController();
        Request request = httpController.makeGetRequest(Path.DECLINEREQUEST, params);
        ContentResponse response = httpController.sendRequest(request);

        if (response.getStatus() != HttpStatus.OK_200) {
            System.out.println("Error : " + response.getContentAsString());
        }

        return selected;
    }

}
