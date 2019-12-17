package app.match;

import static app.Application.gson;

import app.util.Message;
import java.io.IOException;
import java.util.UUID;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
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
            user.getRemote().sendString(new Message("Joined", matchid.toString()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (matchController.isMatchReadyToStart(matchid)) {
            try {
                user.getRemote().sendString(new Message("Start", null).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {

        Message msg = new Message(message);
        if (msg.head.equals("Update")) {
            MatchInfo matchInfo = (MatchInfo) msg.body;
            UUID matchid = matchInfo.getMatchid();
            Match match = matchController.matches.get(matchid);
            Session opponent = match.getOpponent(user.hashCode());

            try {
                opponent.getRemote().sendString(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        if (statusCode == 1000) {
            MatchInfo matchInfo = gson.fromJson(reason, MatchInfo.class);
            UUID matchid = matchInfo.getMatchid();

            Match match = matchController.matches.get(matchid);
            Session opponent = match.getOpponent(user.hashCode());

            try {
                opponent.getRemote().sendString(new Message("Ended", "Opponent Left").toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            matchController.matches.remove(matchid);
        }

        System.out.println(statusCode + " " + reason);
    }
}
