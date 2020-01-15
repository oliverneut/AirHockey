package gui;

import app.util.Path;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.math.BigDecimal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.eclipse.jetty.client.api.ContentResponse;


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
    private transient Label score;

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

        HttpController httpController = HttpController.getHTTPController();
        ContentResponse response = httpController.getRequest(Path.USERSTATS);
        JsonObject jsonObject = httpController.responseToJson(response);

        PlayerRecord own = new PlayerRecord(Main.username,
                ((BigDecimal) jsonObject.get("Matches played")).intValue(),
                ((BigDecimal) jsonObject.get("Matches won")).intValue(),
                ((BigDecimal) jsonObject.get("Goals scored")).intValue());

        username.setText(own.getUsername());
        int num = own.getNumGames();
        numGames.setText(Integer.toString(num));
        int won = own.getGamesWon();
        gamesWon.setText(Integer.toString(won));
        goalsScored.setText(Integer.toString(own.getGoalsScored()));
        double ratio = num == 0 ? 0 : (double) won * won / num;
        score.setText(Double.toString(ratio));
    }

}
