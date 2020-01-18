package app.match;

import java.util.UUID;
import org.eclipse.jetty.websocket.api.Session;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Match {

    protected transient Session player1 = null;
    protected transient Session player2 = null;
    private transient int player1Id;
    private transient int player2Id;
    private transient int score1;
    private transient int score2;
    /**
     * ID of match.
     */
    private transient UUID matchid;

    /**
     * Match constructor.
     *
     * @param matchid UUID for match.
     */
    public Match(UUID matchid, int player1Id, int player2Id) {
        this.matchid = matchid;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.score1 = 0;
        this.score2 = 0;
    }

    public UUID getMatchid() {
        return matchid;
    }

    /**
     * Update score on goal.
     *
     * @param player1 If player1 scored goal or player2.
     */
    public void updateScore(boolean player1) {
        if (player1) {
            this.score1++;
        } else {
            this.score2++;
        }
    }

    Session getOpponent(Session player) {
        if (player1.equals(player)) {
            return player2;
        } else {
            return player1;
        }
    }

    void setPlayer(Session player, int id) {
        if (player1 == null) {
            player1 = player;
            player1Id = id;
        } else {
            player2 = player;
            player2Id = id;
        }
    }

    /**
     * Checks if both players are connected and match is ready to start.
     *
     * @return If match is ready to start.
     */
    public boolean readyToStart() {
        return player1.isOpen() && player2.isOpen();
    }

}
