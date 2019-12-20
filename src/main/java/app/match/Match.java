package app.match;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.eclipse.jetty.websocket.api.Session;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Match {

    transient Map<Integer, Session> players;
    /**
     * ID of match.
     */
    private transient UUID matchid;
    private transient int[] score;

    /**
     * Match constructor.
     *
     * @param matchid UUID for match.
     */
    public Match(UUID matchid) {
        this.matchid = matchid;
        players = new HashMap<Integer, Session>();
        score = new int[]{0, 0};
    }

    public UUID getMatchid() {
        return matchid;
    }

    public int[] getScore() {
        return score;
    }

    Session getOpponent(Session thisPlayer) {
        int id = thisPlayer.hashCode();
        assert players.keySet().size() <= 2;
        for (Integer player : players.keySet()) {
            if (player != id) {
                return players.get(player);
            }
        }
        return null;
    }

    void setPlayer(int id, Session player) {
        players.put(id, player);
    }

    void removeOpponent(int id) {
        for (Integer player : players.keySet()) {
            if (player != id) {
                players.remove(player);
            }
        }
    }

    /**
     * Checks if both players are connected and match is ready to start.
     *
     * @return If match is ready to start.
     */
    public boolean readyToStart() {
        for (Session player : players.values()) {
            if (player == null || !player.isOpen()) {
                return false;
            }
        }
        return true;
    }

}
