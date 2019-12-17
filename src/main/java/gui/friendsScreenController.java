package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class friendsScreenController {


    @FXML
    private Button goBackButton;

    @FXML
    private void goBack(ActionEvent event) {
        Parent menuScreen = null;
        try {
            menuScreen = FXMLLoader.load(
                    getClass().getClassLoader().getResource("menuScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(menuScreen));
    }


}
