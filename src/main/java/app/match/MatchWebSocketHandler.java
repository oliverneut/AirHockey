package app.match;

import app.user.UserController;
import basis.GameVector;
import basis.Rectangle;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
     * @param user The WS session of the player.
     */
    protected static void sendStart(Session user, boolean player1) {
        System.out.println("WSHandler : sendStart " + user.hashCode());

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Start");

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

    protected static void sendPuckUpdate(Session user, GameVector position, GameVector velocity) {
        System.out.println("WSHandler : sendPuckUpdate " + user.hashCode());

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "PuckUpdate");

        reply.put("xpos", position.getX());
        reply.put("ypos", position.getY());

        reply.put("xvel", velocity.getX());
        reply.put("yvel", velocity.getY());

        try {
            user.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void sendPaddleUpdate(Session user, GameVector position, GameVector velocity) {
        System.out.println("WSHandler : sendPaddleUpdate " + user.hashCode());

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "PaddleUpdate");

        reply.put("xpos", position.getX());
        reply.put("ypos", position.getY());

        reply.put("xvel", velocity.getX());
        reply.put("yvel", velocity.getY());

        try {
            user.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void sendMatchResult(Session user, boolean won) {
        System.out.println("WSHandler : sendMatchResult " + user.hashCode());

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "MatchResult");
        reply.put("MatchResult", won);

        try {
            user.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.close();

    }

    /**
     * Set match and user in websocket.
     *
     * @param user Session user.
     */
    @OnWebSocketConnect
    public void onConnect(Session user) {
        System.out.println("WSHandler : new connection " + user.hashCode());

        //extremely ghetto solution because spark doesn't support
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

        System.out.println("WSHandler : message from " + user.hashCode() + " " + head);

        Match match = matchController.getMatch(user);

        switch (head) {
            case "FieldInitialize":
                System.out.println("Field Initialize");

                int height = ((BigDecimal) json.get("Frame_height")).intValue();
                int width = ((BigDecimal) json.get("Frame_width")).intValue();

                ArrayList<Rectangle> boundingBoxes = new ArrayList<>();
                for (JsonObject box : (ArrayList<JsonObject>) json.get("Frame_boundingBoxes")) {
                    int xcord = ((BigDecimal) box.get("xcord")).intValue();
                    int ycord = ((BigDecimal) box.get("ycord")).intValue();
                    int boxHeight = ((BigDecimal) box.get("height")).intValue();
                    int boxWidth = ((BigDecimal) box.get("width")).intValue();
                    boundingBoxes.add(new Rectangle(xcord, ycord, boxHeight, boxWidth));
                }

                ArrayList<Rectangle> goalBoxes = new ArrayList<>();
                for (JsonObject box : (ArrayList<JsonObject>) json.get("Frame_goalBoxes")) {
                    int xcord = ((BigDecimal) box.get("xcord")).intValue();
                    int ycord = ((BigDecimal) box.get("ycord")).intValue();
                    int boxHeight = ((BigDecimal) box.get("height")).intValue();
                    int boxWidth = ((BigDecimal) box.get("width")).intValue();
                    goalBoxes.add(new Rectangle(xcord, ycord, boxHeight, boxWidth));
                }

                match.frame = new Frame(match, width, height, boundingBoxes, goalBoxes);

                height = ((BigDecimal) json.get("Paddle_height")).intValue();
                width = ((BigDecimal) json.get("Paddle_width")).intValue();
                match.frame.createPaddle(height, width, false);
                match.frame.createPaddle(height, width, true);

                int size = ((BigDecimal) json.get("Puck_size")).intValue();
                int multiplier = ((BigDecimal) json.get("Puck_multiplier")).intValue();
                match.frame.createPuck(size, multiplier);

                sendStart(user, true);
                sendStart(match.getOpponent(user), false);

                match.runGame();
                match.updatePuck();
                break;
            case "PaddleUpdate":
                match.updatePaddle(json, user);
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
        reply.put("Ended", "Opponent has left.");

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
