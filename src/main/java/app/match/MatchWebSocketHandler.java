package app.match;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
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

    public static String HEAD = "Head";

    transient MatchController matchController;

    public MatchWebSocketHandler(MatchController matchController) {
        this.matchController = matchController;
    }

    /**
     * Send the Start message to the player.
     *
     * @param user The WS session of the player.
     */
    public static void sendStart(Session user) {
        System.out.println("WSHandler : match starting " + user.hashCode());
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Start");

        try {
            user.getRemote().sendString(reply.toJson());
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

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Joined");

        try {
            user.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method to handle received WS messages.
     *
     * @param user    The WS session of the user.
     * @param message The received message.
     */
    @OnWebSocketMessage
    public void onMessage(Session user, String message) {

        JsonObject json = Jsoner.deserialize(message, new JsonObject());
        String head = (String) json.get("Head");

        System.out.println("WSHandler : message from " + user.hashCode() + " " + head);

        switch (head) {
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
                break;
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

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Ended");

        try {
            match.getOpponent(user).getRemote().sendString(reply.toJson());
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
