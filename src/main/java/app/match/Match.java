package app.match;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import org.eclipse.jetty.websocket.api.Session;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Match {

    protected transient Session player1 = null;
    protected transient Session player2 = null;
    protected transient boolean ended;
    private transient int player1Id;
    private transient int player2Id;
    private transient int score1;
    private transient int score2;
    private transient ScheduledExecutorService scheduledService;
    private transient ScheduledExecutorService gameScheduledExecutorService;

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

        this.ended = false;
    }

    public UUID getMatchid() {
        return matchid;
    }

    /**
     * Update score on goal.
     *
     * @param player1Scored If player1 scored goal or player2.
     */
    public void updateScore(boolean player1Scored) {
        if (player1Scored) {
            this.score1++;
        } else {
            this.score2++;
        }

        MatchWebSocketHandler.sendScoreUpdate(player1, player1Scored);
        MatchWebSocketHandler.sendScoreUpdate(player2, !player1Scored);

        if (score1 >= 10 || score2 >= 10) {
            endGame();
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

    protected void startGame() {
        MatchWebSocketHandler.sendStart(player1, true);
        MatchWebSocketHandler.sendStart(player2, false);
    }

    protected void endGame() {
        scheduledService.shutdown();
        ended = true;
        if (score1 >= 10 || score2 >= 10) {
            MatchWebSocketHandler.sendMatchResult(player1, score1 > score2);
            MatchWebSocketHandler.sendMatchResult(player2, score2 > score1);
        }
        //save score in database
    }

    /**
     * Gets the current score of player 1.
     * @return The current score of player 1.
     */
    protected int getScore1() {
        return this.score1;
    }

    /**
     * Gets the current score of player 2.
     * @return The current score of player 2.
     */
    protected int getScore2() {
        return this.score2;
    }

    /**
     * Sets the ScheduledExecutorService for the match.
     * @param service The ScheduledExecutorService to be set
     */
    protected void setScheduledService(ScheduledExecutorService service) {
        this.scheduledService = service;
    }

}
