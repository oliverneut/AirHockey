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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;


public class LeaderboardScreenController {
    private transient Parent menuScreen = null;

    @FXML
    private transient TableView<PlayerRecord> leaderboardTable;

    @FXML
    private transient TableColumn<PlayerRecord, String> usernameColumn;

    @FXML
    private transient TableColumn<PlayerRecord, String> numGamesColumn;

    @FXML
    private transient TableColumn<PlayerRecord, String> gamesWonColumn;

    @FXML
    private transient TableColumn<PlayerRecord, String> goalsScoredColumn;

    @FXML
    private Button goBackButton;

    @FXML
    public void initialize() {
        showFirst();
    }

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
     * Method which is called in intitialize when you open the leaderboard screen.
     * and in showAll.
     */
    public void showFirst() {
        if (getPlayerRecordAll().isEmpty()) {
            return;
        }
        if (!leaderboardTable.getItems().isEmpty()) {
            leaderboardTable.setItems(getPlayerRecordAll());
            return;
        }

        usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(130);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        numGamesColumn = new TableColumn<>("#games");
        numGamesColumn.setMinWidth(90);
        numGamesColumn.setCellValueFactory(new PropertyValueFactory<>("numGames"));

        gamesWonColumn = new TableColumn<>("#wins");
        gamesWonColumn.setMinWidth(90);
        gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));

        goalsScoredColumn = new TableColumn<>("#goals");
        goalsScoredColumn.setMinWidth(90);
        goalsScoredColumn.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));

        leaderboardTable.setItems(getPlayerRecordAll());

        leaderboardTable.getColumns().addAll(usernameColumn, numGamesColumn,
                gamesWonColumn, goalsScoredColumn);
    }


    @FXML
    public void showAll(ActionEvent event) {
        showFirst();
    }

    /**
     * Shows the stats of the friends of the player.
     * @param event when the friends button is clicked.
     */
    @FXML
    public void showFriends(ActionEvent event) {
        if (!leaderboardTable.getItems().isEmpty()) {
            leaderboardTable.setItems(getPlayerRecordFriends());
            return;
        }

        usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(130);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        numGamesColumn = new TableColumn<>("#games");
        numGamesColumn.setMinWidth(90);
        numGamesColumn.setCellValueFactory(new PropertyValueFactory<>("numGames"));

        gamesWonColumn = new TableColumn<>("#wins");
        gamesWonColumn.setMinWidth(90);
        gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));

        goalsScoredColumn = new TableColumn<>("#goals");
        goalsScoredColumn.setMinWidth(90);
        goalsScoredColumn.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));

        leaderboardTable.setItems(getPlayerRecordFriends());

        leaderboardTable.getColumns().addAll(usernameColumn, numGamesColumn,
                gamesWonColumn, goalsScoredColumn);

    }

    /**
     * Gets all the data of each user.
     * Data it retrieves : (games played, games won, goals scored).
     * Then takes that data and creates a new PlayerRecord object.
     * Adds all the PlayerRecord objects to an ObservableList and returns it.
     *
     * @return ObservableList with all the data of all users.
     */
    public ObservableList<PlayerRecord> getPlayerRecordAll() {
        ObservableList<PlayerRecord> playerRecords = FXCollections.observableArrayList();

        // Need the same code here as in method below,
        // but then it has to return every player in the DB.

        return playerRecords;
    }


    /**
     * Gets all the data for each user which is a friend of the user that is playing.
     * Data it retrieves : (games played, games won, goals scored).
     * Then takes that data and creates a new PlayerRecord object for each friend.
     * Adds all the PlayerRecord objects to an ObservableList and returns it.
     *
     * @return ObservableList with all the data of all friends.
     */
    public ObservableList<PlayerRecord> getPlayerRecordFriends() {
        ObservableList<PlayerRecord> playerRecords = FXCollections.observableArrayList();

        HTTPController httpController = HTTPController.getHTTPController();
        Request request = httpController.makeGetRequest(Path.FRIENDS, new HashMap<>());
        ContentResponse response = httpController.sendRequest(request);
        JsonObject jsonObject = Jsoner.deserialize(response.getContentAsString(), new JsonObject());
        ArrayList<String> usernames = (ArrayList<String>) jsonObject.get(jsonObject.get("Head"));

        for (int i = 0; i < usernames.size(); i++) {
            playerRecords.add(new PlayerRecord(usernames.get(i), 0, 0, 0));
        }

        return playerRecords;
    }

}
