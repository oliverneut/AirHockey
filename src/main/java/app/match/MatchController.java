package app.match;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import org.eclipse.jetty.websocket.api.Session;


public class MatchController {

    transient Queue<Session> waitingPlayers;

    transient Map<Integer, UUID> playerToMatch;

    transient Map<UUID, Match> matches;

    public MatchController() {
        this.waitingPlayers = new LinkedList<>();

        this.playerToMatch = new HashMap<>();

        this.matches = new HashMap<>();
    }

    /**
     * Handle new web-socket connection.
     *
     * @param user Web-socket session.
     */
    public void handleNewPlayer(Session user) {

        Session opponent = getWaitingPlayer();

        if (opponent == null) {
            addWaitingPlayer(user);
            return;
        }

        UUID matchid = createNewMatch(user, opponent);

        assert matchid != null;
    }

    public boolean isMatchReadyToStart(UUID matchid) {
        return matches.get(matchid).readyToStart();
    }


    public UUID createNewMatch(Session player1, Session player2) {

        UUID matchid = UUID.randomUUID();
        Match match = new Match(matchid);

        this.playerToMatch.put(player1.hashCode(), matchid);
        this.playerToMatch.put(player2.hashCode(), matchid);

        match.setPlayer(player1.hashCode(), player1);
        match.setPlayer(player2.hashCode(), player2);

        this.matches.put(matchid, match);

        MatchWebSocketHandler.sendStart(player1);
        MatchWebSocketHandler.sendStart(player2);

        return matchid;
    }

    public Match getMatch(Session player) {
        return matches.get(playerToMatch.get(player.hashCode()));
    }

    public Match deleteMatch(UUID matchid) {
        Match match = this.matches.remove(matchid);

        for (int player : match.players.keySet()) {
            this.playerToMatch.remove(player);
        }

        return match;
    }

    public Session getWaitingPlayer() {
        Session player = null;
        //find the next waiting player still connected
        while (!this.waitingPlayers.isEmpty()) {
            player = this.waitingPlayers.poll();
            if (player.isOpen()) {
                break;
            }
        }
        return player;
    }

    public void addWaitingPlayer(Session player) {
        this.waitingPlayers.add(player);
    }
}
