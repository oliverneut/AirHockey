package app.match;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import javafx.util.Pair;
import org.eclipse.jetty.websocket.api.Session;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class MatchController {

    transient Queue<Pair<Session, Integer>> waitingPlayers;

    transient Map<Integer, UUID> playerToMatch;

    transient Map<UUID, Match> matches;

    /**
     * Constructor for match controller. Initializes data-structures.
     */
    public MatchController() {
        this.waitingPlayers = new LinkedList<>();

        this.playerToMatch = new HashMap<>();

        this.matches = new HashMap<>();
    }

    /**
     * Handle new web-socket connection.
     *
     * @param user   Web-socket session.
     * @param userid id associated with user.
     */
    public void handleNewPlayer(Session user, int userid) {

        Pair<Session, Integer> opponent = getWaitingPlayer();

        if (opponent == null) {
            addWaitingPlayer(user, userid);
            return;
        }

        UUID matchid = createNewMatch(user, userid, opponent.getKey(), opponent.getValue());

        assert matchid != null;
    }


    /**
     * Create new Match instance with the two players.
     *
     * @param player1 WS session of player1.
     * @param player2 WS session of player2.
     * @return The id for the match created.
     */
    public UUID createNewMatch(Session player1, int player1Id, Session player2, int player2Id) {
        System.out.println("MatchController - createNewMatch : " + player1Id + " " + player2Id);

        UUID matchid = UUID.randomUUID();
        Match match = new Match(matchid, player1Id, player2Id);

        this.playerToMatch.put(player1.hashCode(), matchid);
        this.playerToMatch.put(player2.hashCode(), matchid);

        match.setPlayer(player1, player1.hashCode());
        match.setPlayer(player2, player2.hashCode());

        this.matches.put(matchid, match);

        match.startGame();

        return matchid;
    }

    public Match getMatch(Session player) {
        return matches.get(playerToMatch.get(player.hashCode()));
    }

    /**
     * Return and remove instance of match.
     *
     * @param matchid ID of match to be deleted.
     * @return The match instance that is removed.
     */
    public Match deleteMatch(UUID matchid) {
        Match match = this.matches.remove(matchid);

        this.playerToMatch.remove(match.player1.hashCode());
        this.playerToMatch.remove(match.player2.hashCode());

        this.matches.remove(matchid);

        return match;
    }

    /**
     * Get the next player waiting to join the match.
     *
     * @return The WS session of the player.
     */
    public Pair<Session, Integer> getWaitingPlayer() {
        Pair<Session, Integer> player = null;
        //find the next waiting player still connected
        while (!this.waitingPlayers.isEmpty()) {
            player = this.waitingPlayers.poll();
            if (player.getKey().isOpen()) {
                return player;
            }
        }
        return null;
    }

    public void addWaitingPlayer(Session player, int playerid) {
        this.waitingPlayers.add(new Pair<>(player, playerid));
    }
}
