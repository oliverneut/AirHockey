package app.match;

import basis.GameVector;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.math.BigDecimal;
import java.util.UUID;
import org.eclipse.jetty.websocket.api.Session;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Match {

    protected transient Session player1 = null;
    protected transient Session player2 = null;
    protected transient Frame frame;
    protected transient boolean ended;
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

        if (score1 == 10 || score2 == 10) {
            ended = true;
            MatchWebSocketHandler.sendMatchResult(player1, score1 > score2);
            MatchWebSocketHandler.sendMatchResult(player2, score2 > score1);
        }

    }

    /**
     * Send update puck messages to both players.
     */
    public void updatePuck() {
        MatchWebSocketHandler.sendPuckUpdate(player1,
                frame.puck.position, frame.puck.velocity);
        MatchWebSocketHandler.sendPuckUpdate(player2,
                frame.puck.position, frame.mirrorVelocity(frame.puck.velocity));
    }

    /**
     * Send apply Paddle update and send it to the opponent.
     *
     * @param json   Paddle update from player.
     * @param player WS session of player.
     */
    public void updatePaddle(JsonObject json, Session player) {
        double xcoord = ((BigDecimal) json.get("xpos")).doubleValue();
        double ycoord = ((BigDecimal) json.get("ypos")).doubleValue();
        double xvel = ((BigDecimal) json.get("xvel")).doubleValue();
        double yvel = ((BigDecimal) json.get("yvel")).doubleValue();
        GameVector position = new GameVector(xcoord, ycoord);
        GameVector velocity = new GameVector(xvel, yvel);

        if (player.equals(player1)) {
            frame.paddle.position = position;
            frame.paddle.velocity = velocity;

            MatchWebSocketHandler.sendPaddleUpdate(player2,
                    frame.mirrorPosition(position, frame.paddle), frame.mirrorVelocity(velocity));
        } else {
            position = frame.mirrorPosition(position, frame.opponentPaddle);
            velocity = frame.mirrorVelocity(velocity);
            frame.opponentPaddle.position = position;
            frame.opponentPaddle.velocity = velocity;

            MatchWebSocketHandler.sendPaddleUpdate(player1, position, velocity);
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

    protected void runGame() {
        while (!ended) {
            frame.puck.move(frame);
        }
    }

}
