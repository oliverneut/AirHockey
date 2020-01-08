package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class menuScreenController {

    @FXML
    private Button playButton;

    @FXML
    private Button leaderBoardButton;

    @FXML
    private Button friendsButton;

    @FXML
    private Button logoutButton;

    private transient Parent leaderBoardScreen = null;

    private transient Parent friendsScreen = null;

    private transient Parent main = null;

    private transient Parent addFriendsScreen = null;


    @FXML
    private void playGame() {
        // needs to be linked with actual game
    }

    @FXML
    private void goLeaderBoard(ActionEvent event) {
        try {
            leaderBoardScreen = FXMLLoader.load(
                    Thread.currentThread().getContextClassLoader().getResource("leaderBoardScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(leaderBoardScreen));
    }

    @FXML
    private void goFriends(ActionEvent event) {
        try {
            friendsScreen = FXMLLoader.load(
                    Thread.currentThread().getContextClassLoader().getResource("friendsScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(friendsScreen));
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            main = FXMLLoader.load(
                    Thread.currentThread().getContextClassLoader().getResource("main.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(main));
    }

    @FXML
    private void goAddFriends(ActionEvent event){
        try {
            addFriendsScreen = FXMLLoader.load(
                    Thread.currentThread().getContextClassLoader().getResource("addFriendsScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(addFriendsScreen));
    }

}
