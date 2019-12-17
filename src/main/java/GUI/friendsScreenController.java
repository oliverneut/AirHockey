package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;


public class friendsScreenController {



    @FXML
    private Button goBackButton;

    @FXML
    private void goBack(ActionEvent event){
        Parent menuScreen = null;
        try {
            URL url = new File("/Users/oliverneut/Desktop/template/src/main/resources/menuScreen.fxml").toURI().toURL();
            menuScreen = FXMLLoader.load(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(menuScreen));
    }


}
