package app.match;

import static app.Application.matches;
import static app.Application.waitingMatches;

import java.util.UUID;
import org.eclipse.jetty.websocket.api.Session;


public class MatchController {

    /**
     * Handle new web-socket connection.
     *
     * @param user Web-socket session.
     * @return Matchid.
     */
    public static UUID handleNewPlayer(Session user) {

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

    public static boolean isMatchReadyToStart(UUID matchid) {
        return matches.get(matchid).readyToStart();
    }

    /**
     * Get match for user to join.
     *
     * @return id of match.
     */
    public static UUID getWaitingMatch() {

        if (waitingMatches.isEmpty()) {
            UUID matchid = UUID.randomUUID();
            matches.put(matchid, new Match(matchid));
            waitingMatches.add(matchid);
            return matchid;
        }

        return waitingMatches.poll();
    }
}
