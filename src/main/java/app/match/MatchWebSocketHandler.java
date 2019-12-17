package app.match;

import static app.Application.gson;

import java.io.IOException;
import java.util.UUID;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class MatchWebSocketHandler {

    transient MatchController matchController;

    public MatchWebSocketHandler(MatchController matchController) {
        this.matchController = matchController;
    }

    /**
     * Set match and user in websocket.
     *
     * @param user Session user.
     */
    @OnWebSocketConnect
    public void onConnect(Session user) {

        UUID matchid = matchController.handleNewPlayer(user);

        assert matchid != null;

        try {
            user.getRemote().sendString(matchid.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (matchController.isMatchReadyToStart(matchid)) {
            try {
                user.getRemote().sendString("Start");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {

        MatchInfo matchInfo = gson.fromJson(message, MatchInfo.class);
        UUID matchid = matchInfo.getMatchid();

        Match match = matchController.matches.get(matchid);
        Session opponent = match.getOpponent(user.hashCode());

        try {
            opponent.getRemote().sendString(gson.toJson(matchInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(message);
    }

    /**
     * Handle when web-socket disconnects.
     *
     * @param user       Web-socket session.
     * @param statusCode .
     * @param reason     .
     */
    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        int id = user.hashCode();

        final int properClose = 1000;
        if (statusCode == properClose) {
            MatchInfo matchInfo = gson.fromJson(reason, MatchInfo.class);
            UUID matchid = matchInfo.getMatchid();

            Match match = matchController.matches.get(matchid);
            Session opponent = match.getOpponent(user.hashCode());

            try {
                opponent.getRemote().sendString("Opponent Left");
            } catch (IOException e) {
                e.printStackTrace();
            }

            matchController.matches.remove(matchid);
        }
    }
}
