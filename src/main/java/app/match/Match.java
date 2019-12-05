package app.match;

import java.util.UUID;
import org.eclipse.jetty.websocket.api.Session;

public class Match {

    public transient Status status;
    /**
     * ID of match.
     */
    private transient UUID matchid;
    private transient Session player1;
    private transient Session player2;
    private transient int[] score;

    /**
     * Match constructor.
     *
     * @param matchid UUID for match.
     */
    public Match(UUID matchid) {
        this.matchid = matchid;
        score = new int[]{0, 0};
        status = Status.PLAYER1READY;
    }

    public UUID getMatchid() {
        return matchid;
    }

    public int[] getScore() {
        return score;
    }

    /**
     * Set player 1 or 2 in match.
     *
     * @param user .
     */
    public void setPlayer(Session user) {
        if (player1 == null || !player1.isOpen()) {
            player1 = user;
            status = Status.PLAYER1READY;
        } else if (player2 == null) {
            player2 = user;
        }
    }

    /**
     * Checks if both players are connected and match is ready to start.
     *
     * @return If match is ready to start.
     */
    public boolean readyToStart() {
        if (player1 != null && player1.isOpen()) {
            if (player2 != null && player2.isOpen()) {
                return true;
            }
        }
        return false;
    }

    enum Status {
        EMPTY,
        PLAYER1READY,
        BOTHPLAYERSREADY,
        INPROGRESS,
        ENDED
    }

}
