package gui;

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


/**
 * controller class for the friendscreen.
 */
public class FriendScreenController {
    private transient Parent menuScreen = null;

    @FXML
    private TableView<PlayerRecord> friendsTable;

    @FXML
    private TableColumn<PlayerRecord, String> usernameColumn;

    @FXML
    private TableColumn<PlayerRecord, String> numGamesColumn;

    @FXML
    private TableColumn<PlayerRecord, String> gamesWonColumn;

    @FXML
    private TableColumn<PlayerRecord, String> goalsScoredColumn;



    /**
     * button to go back to previous screen.
     */
    @FXML
    private Button goBackButton;

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

    @FXML
    private void refreshTable(ActionEvent event){

        usernameColumn = new TableColumn<>("Usename");
        usernameColumn.setMinWidth(112);
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

        friendsTable.setItems(getPlayerRecord());

        friendsTable.getColumns().addAll(usernameColumn, numGamesColumn, gamesWonColumn, goalsScoredColumn);

    }


    /**
     * Gets all the data for each user which is a friend of the user that is playing.
     * Data it retrieves : (games played, games won, goals scored).
     * Then takes that data and creates a new PlayerRecord object for each friend.
     * Adds all the PlayerRecord objects to an ObservableList and returns it.
     * @return ObservableList with all the data of all friends.
     */
    public ObservableList<PlayerRecord> getPlayerRecord(){
        ObservableList<PlayerRecord> playerRecords = FXCollections.observableArrayList();

        //This is sample data , I need the DB data.
        playerRecords.add(new PlayerRecord("Oliver", 5, 4, 7));
        playerRecords.add(new PlayerRecord("Dixit", 10, 9, 100));
        playerRecords.add(new PlayerRecord("Jean", 18, 3, 17));

        return playerRecords;
    }







}
