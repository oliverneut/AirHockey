package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.net.URL;


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
    private void playGame(){
        // needs to be linked with actual game
    }

    @FXML
    private void goLeaderBoard(ActionEvent event){
        Parent leaderBoardScreen = null;
        try {
            URL url = new File("/Users/oliverneut/Desktop/template/src/main/resources/leaderBoardScreen.fxml").toURI().toURL();
            leaderBoardScreen = FXMLLoader.load(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(leaderBoardScreen));
    }

    @FXML
    private void goFriends(ActionEvent event){
        Parent friendsScreen = null;
        try {
            URL url = new File("/Users/oliverneut/Desktop/template/src/main/resources/friendsScreen.fxml").toURI().toURL();
            friendsScreen = FXMLLoader.load(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(friendsScreen));
    }

    @FXML
    private void logout(ActionEvent event){
        Parent main = null;
        try {
            URL url = new File("/Users/oliverneut/Desktop/template/src/main/resources/main.fxml").toURI().toURL();
            main = FXMLLoader.load(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(main));
    }

}
