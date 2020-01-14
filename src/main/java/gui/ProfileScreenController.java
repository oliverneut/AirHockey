package gui;

import app.util.Path;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;


/**
 * controller class for the friendscreen.
 */
public class ProfileScreenController {
    private transient Parent menuScreen = null;



    /**
     * button to go back to previous screen.
     */
    @FXML
    private Button goBackButton;

    @FXML
    private transient Label username;

    @FXML
    private transient Label numGames;

    @FXML
    private transient Label gamesWon;

    @FXML
    private transient Label goalsScored;

    @FXML
    private transient Label winLosRatio;

    /**
     * takes the user back to the previous
     * screen when the goBackButton is pressed.
     *
     * @param event .
     */
    @FXML
    private void goBack(ActionEvent event) {
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

    /**
     * Starts when the FXML file is loaded.
     * by outputting the user data to the screen in the form of labels.
     */
    @FXML
    public void initialize() {
        PlayerRecord own = new PlayerRecord("Oliver", 10, 9, 8);
        //Need player's own playerrecord

        username.setText(own.getUsername());
        int num = own.getNumGames();
        numGames.setText(Integer.toString(num));
        int won = own.getGamesWon();
        gamesWon.setText(Integer.toString(won));
        goalsScored.setText(Integer.toString(own.getGoalsScored()));
        double ratio = won / (num - won);
        winLosRatio.setText(Double.toString(ratio));
    }

}
