package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class leaderBoardScreenController {
    private transient Parent menuScreen = null;

    @FXML
    private Button goBackButton;

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


}
