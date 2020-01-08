package gui;

import javafx.beans.property.ReadOnlyStringWrapper;
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

        if(username.isEmpty()){
            return;
        }

        String displayFriend = findFriendsDB(username);
    }

    private String findFriendsDB(String username){
        // Go through database and find username that corresponds with entered username.
        // For now I assume that you enter "oliver" as friend, and it is found in DB.
        return "Oliver";
    }


    @FXML
    private void acceptFriendRequest(ActionEvent event){
        // still to be implemented
    }

    @FXML
    private void declineFriendRequest(ActionEvent event){
        // still to be implemented
    }

}
