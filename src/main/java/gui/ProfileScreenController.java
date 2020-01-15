package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


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
        //Need player's own playerrecord

        PlayerRecord own = new PlayerRecord("Steve Jobs", 100, 80, 400);

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
