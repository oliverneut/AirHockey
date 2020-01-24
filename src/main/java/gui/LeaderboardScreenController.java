package gui;

import app.util.Path;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
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

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class LeaderboardScreenController {
    private transient Parent menuScreen = null;

    @FXML
    private transient TableView<LeaderBoardPlayerRecord> leaderboardTable;

    @FXML
    private transient TableColumn<LeaderBoardPlayerRecord, String> usernameColumn;

    @FXML
    private transient TableColumn<LeaderBoardPlayerRecord, String> scoreColumn;

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

        scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(90);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.setItems(getPlayerRecordAll());

        leaderboardTable.getColumns().addAll(usernameColumn, scoreColumn);
    }


    @FXML
    public void showAll(ActionEvent event) {
        showFirst();
    }

    /**
     * Shows the stats of the friends of the player.
     *
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

        scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(90);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.setItems(getPlayerRecordFriends());

        leaderboardTable.getColumns().addAll(usernameColumn, scoreColumn);
    }

    /**
     * Gets all the data of each user.
     * Data it retrieves : (games played, games won, goals scored).
     * Then takes that data and creates a new PlayerRecord object.
     * Adds all the PlayerRecord objects to an ObservableList and returns it.
     *
     * @return ObservableList with all the data of all users.
     */
    public ObservableList<LeaderBoardPlayerRecord> getPlayerRecordAll() {
        ObservableList<LeaderBoardPlayerRecord> playerRecords = FXCollections.observableArrayList();

        HttpController httpController = HttpController.getHTTPController();
        ContentResponse response = httpController.getRequest(Path.GENERALLEADERBOARD);
        JsonObject jsonObject = httpController.responseToJson(response);

        Map<String, Double> topPlayers = (Map<String, Double>)
                jsonObject.get(jsonObject.get("Head"));

        for (Map.Entry entry : topPlayers.entrySet()) {
            playerRecords.add(new LeaderBoardPlayerRecord((String) entry.getKey(),
                    ((BigDecimal) entry.getValue()).doubleValue()));
        }

        Collections.sort(playerRecords);

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
    public ObservableList<LeaderBoardPlayerRecord> getPlayerRecordFriends() {
        ObservableList<LeaderBoardPlayerRecord> playerRecords = FXCollections.observableArrayList();

        HttpController httpController = HttpController.getHTTPController();
        ContentResponse response = httpController.getRequest(Path.FRIENDLEADERBOARD);
        JsonObject jsonObject = httpController.responseToJson(response);

        Map<String, Double> topPlayers = (Map<String, Double>)
                jsonObject.get(jsonObject.get("Head"));

        for (Map.Entry entry : topPlayers.entrySet()) {
            playerRecords.add(new LeaderBoardPlayerRecord((String) entry.getKey(),
                    ((BigDecimal) entry.getValue()).doubleValue()));
        }

        Collections.sort(playerRecords);

        return playerRecords;
    }

}
