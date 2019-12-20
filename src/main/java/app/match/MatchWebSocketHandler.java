package app.match;

import app.util.Message;
import java.io.IOException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class MatchWebSocketHandler {

    transient MatchController matchController;

    public MatchWebSocketHandler(MatchController matchController) {
        this.matchController = matchController;
    }

    public static void sendStart(Session user) {
        try {
            System.out.println("WSHandler : match starting " + user.hashCode());
            user.getRemote().sendString(new Message("Start").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set match and user in websocket.
     *
     * @param user Session user.
     */
    @OnWebSocketConnect
    public void onConnect(Session user) {

        System.out.println("WSHandler : new connection " + user.hashCode());

        matchController.handleNewPlayer(user);

        try {
            user.getRemote().sendString(new Message("Joined").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {

        Message msg = Message.parse(message);

        System.out.println("WSHandler : message from " + user.hashCode() + " " + msg.getHead());

        switch (msg.getHead()) {
            case "Update":
                Match match = matchController.getMatch(user);
                Session opponent = match.getOpponent(user);
                try {
                    opponent.getRemote().sendString(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println(message);
        }
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

        System.out.println("WSHandler : player left " + user.hashCode());

        Match match = matchController.getMatch(user);
        if (match == null) {
            return;
        }

        matchController.deleteMatch(match.getMatchid());

        try {
            match.getOpponent(user).getRemote().sendString(new Message("Ended").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        System.out.println("WSHandler : websocket error ");
        cause.printStackTrace(System.out);
    }
}
