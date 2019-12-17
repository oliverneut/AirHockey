package gui;

import java.io.File;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * controller class for the friendscreen.
 */
public class FriendScreenController {
    /**
     * button to go back to previous screen.
     */
    @FXML
    private Button goBackButton;

    /**
     * takes the user back to the previous
     * screen when the goBackButton is pressed.
     * @param event
     */
    @FXML
    private void goBack(ActionEvent event) {
        Parent menuScreen = null;
        try {
            URL url = new File("../template/src/main/resources/menuScreen.fxml").toURI().toURL();
            menuScreen = FXMLLoader.load(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(menuScreen));
    }

}
