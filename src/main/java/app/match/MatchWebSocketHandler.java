package app.match;

import app.user.UserController;
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

    transient UserController userController;
    transient MatchController matchController;

    public MatchWebSocketHandler(MatchController matchController, UserController userController) {
        this.matchController = matchController;
        this.userController = userController;
    }

    protected static void getFieldInfo(Session user) {
        System.out.println("WSHandler - getFieldInfo : " + user.hashCode());

        JsonObject jsonObject = new JsonObject();
        jsonObject.put(HEAD, "Initialize");
        try {
            user.getRemote().sendString(jsonObject.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send the Start message to the player.
     *
     * @param user    The WS session of the player.
     * @param player1 if the user is player1 in the match or not.
     */
    protected static void sendStart(Session user, boolean player1) {
        System.out.println("WSHandler : sendStart " + user.hashCode());

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Start");
        reply.put("Player1", player1);

        try {
            user.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void sendScoreUpdate(Session user, boolean userScored) {
        System.out.println("WSHandler : sendScoreUpdate " + user.hashCode());

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "ScoreUpdate");
        reply.put("goal scored", userScored);

        try {
            user.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void sendMatchResult(Session user, boolean won) {
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "MatchResult");
        reply.put("MatchResult", won);

        try {
            user.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.close();
        System.out.println("Match result sent");
    }

    /**
     * Set match and user in websocket.
     *
     * @param user Session user.
     */
    @OnWebSocketConnect
    public void onConnect(Session user) {
        System.out.println("WSHandler : new connection " + user.hashCode());

        // extremely ghetto solution because spark doesn't support
        // getting http session on ws upgrade request
        String username = user.getUpgradeRequest().getParameterMap().get("user").get(0);

        int userid = userController.getUser(username).getUserid();

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Joined");
        try {
            user.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }

        matchController.handleNewPlayer(user, userid);
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

        String head = (String) json.get(HEAD);

        Match match = matchController.getMatch(user);

        if (match == null) {
            return;
        }

        switch (head) {
            case "PuckUpdate":
            case "PaddleUpdate":
                try {
                    match.getOpponent(user).getRemote().sendString(message);
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

        match.endGame();

        matchController.deleteMatch(match.getMatchid());

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Ended");
        reply.put("Ended", "Opponent has left.");

        try {
            match.getOpponent(user).getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            System.out.println("Couldn't send close message to opponent of player who left");
        }
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        System.out.println("WSHandler : websocket error ");
        cause.printStackTrace(System.out);
    }
}
