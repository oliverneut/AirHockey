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

    @FXML
    private void playGame() {
        // needs to be linked with actual game
    }

    @FXML
    private void goLeaderBoard(ActionEvent event) {
        Parent leaderBoardScreen = null;
        try {
            leaderBoardScreen = FXMLLoader.load(
                    getClass().getClassLoader().getResource("leaderBoardScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(leaderBoardScreen));
    }

    @FXML
    private void goFriends(ActionEvent event) {
        Parent friendsScreen = null;
        try {
            friendsScreen = FXMLLoader.load(
                    getClass().getClassLoader().getResource("friendsScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(friendsScreen));
    }

    @FXML
    private void logout(ActionEvent event) {
        Parent main = null;
        try {
            main = FXMLLoader.load(
                    getClass().getClassLoader().getResource("main.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(main));
    }

}
