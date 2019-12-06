package app.match;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import org.eclipse.jetty.websocket.api.Session;


public class MatchController {

    //list of matches to be started
    transient Queue<UUID> waitingMatches;
    //set of all matches
    transient Map<UUID, Match> matches;

    public MatchController() {
        this.waitingMatches = new LinkedList<>();
        this.matches = new HashMap<>();
    }

    /**
     * Handle new web-socket connection.
     *
     * @param user Web-socket session.
     * @return Matchid.
     */
    public UUID handleNewPlayer(Session user) {

        if (!user.isOpen()) {
            return null;
        }

        UUID matchid = getWaitingMatch();

        assert matchid != null;

        Match match = matches.get(matchid);

        assert match != null;

        match.setPlayer(user);

        return matchid;

    }

    public boolean isMatchReadyToStart(UUID matchid) {
        return matches.get(matchid).readyToStart();
    }

    /**
     * Get match for user to join.
     *
     * @return id of match.
     */
    public UUID getWaitingMatch() {

        if (waitingMatches.isEmpty()) {
            UUID matchid = UUID.randomUUID();
            matches.put(matchid, new Match(matchid));
            waitingMatches.add(matchid);
            return matchid;
        }

        return waitingMatches.poll();
    }
}
