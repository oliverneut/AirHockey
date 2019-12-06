package app.match;

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
            user.getRemote().sendString(matchid.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (matchController.isMatchReadyToStart(matchid)) {
            try {
                user.getRemote().sendString("Match is ready to start");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {

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

        System.out.println(statusCode + reason);

    }


}
