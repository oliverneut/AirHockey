package game;

import basis.GameVector;
import basis.ScoreCount;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import gui.HttpController;
import java.io.IOException;
import java.math.BigDecimal;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@WebSocket
public class MatchSocketHandler {

    private static final String HEAD = "Head";
    private static final String XPOS = "xpos";
    private static final String YPOS = "ypos";
    private static final String XVEL = "xvel";
    private static final String YVEL = "yvel";

    public static int sendScoreUpdate = 0;
    public static boolean player1 = false;
    private transient Frame frame;
    private transient Session session;

    /**
     * Constructor for local endpoint of websocket connection.
     *
     * @param frame Frame object of current game.
     */
    private MatchSocketHandler(Frame frame) {
        this.frame = frame;
    }


    /**
     * Instantiate a new WS client and connect to server.
     *
     * @param frame The frame of the match.
     * @return The initialized instance of WebSocketClient.
     */
    public static MatchSocketHandler initialize(Frame frame) {

        MatchSocketHandler handler = new MatchSocketHandler(frame);
        try {
            HttpController.initializeWebSocket(handler);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return handler;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        System.out.println("Socket connected.");
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Socket connection closed.");
    }

    /**
     * The method to handle new WS messages.
     *
     * @param message The received message.
     */
    @OnWebSocketMessage
    public void onMessage(String message) {
        JsonObject json = Jsoner.deserialize(message, new JsonObject());

        String head = (String) json.get(HEAD);

        switch (head) {
            case "PuckUpdate":
                applyPuckUpdate(json);
                break;

            case "PaddleUpdate":
                applyPaddleUpdate(json);

                sendScoreUpdate();
                sendPuckUpdate();
                sendPaddleUpdate();
                break;
            case "ScoreUpdate":
                applyScoreUpdate(json);
                break;

            case "Joined":
                System.out.println("Waiting for opponent");
                break;

            case "Start":
                player1 = (Boolean) json.get("Player1");

                System.out.println("Match starting - Player1 : " + player1);

                if (player1) {
                    frame.resetMovingEntities(new GameVector(1, 1));
                    sendPuckUpdate();
                    sendPaddleUpdate();
                }
                break;

            case "MatchResult":
                System.out.println("Match Result");

                boolean wonMatch = (Boolean) json.get("Result");

                if (wonMatch) {
                    ScoreCount.getInstance().winOne();
                } else {
                    ScoreCount.getInstance().winTwo();
                }

                break;

            default:
                System.out.println(message);
                break;
        }
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        System.out.print("Websocket error: ");
        cause.printStackTrace(System.out);
    }

    void sendPaddleUpdate() {
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "PaddleUpdate");
        GameVector position = frame.mirrorPosition(
                frame.getPaddle().getPosition(), frame.getPaddle());

        reply.put(XPOS, position.getX());
        reply.put(YPOS, position.getY());

        GameVector velocity = frame.mirrorVelocity(
                frame.getPaddle().getVelocity());
        reply.put(XVEL, velocity.getX());
        reply.put(YVEL, velocity.getY());

        try {
            session.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void applyPaddleUpdate(JsonObject reply) {
        double xcoord = ((BigDecimal) reply.get(XPOS)).doubleValue();
        double ycoord = ((BigDecimal) reply.get(YPOS)).doubleValue();

        double xvel = ((BigDecimal) reply.get(XVEL)).doubleValue();
        double yvel = ((BigDecimal) reply.get(YVEL)).doubleValue();

        frame.getOpponentPaddle().setPosition(new GameVector(xcoord, ycoord));
        frame.getOpponentPaddle().setVelocity(new GameVector(xvel, yvel));
    }

    void sendScoreUpdate() {
        if (!player1 || (sendScoreUpdate == 0)) {
            return;
        }

        System.out.println("MatchSocketHandler : sent score update");

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "ScoreUpdate");
        reply.put("Player", sendScoreUpdate);

        sendScoreUpdate = 0;

        try {
            session.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void applyScoreUpdate(JsonObject reply) {
        System.out.println("MatchSocketHandler : received score update");

        int playerScored = ((BigDecimal) reply.get("Player")).intValue();

        if (playerScored == 1) {
            frame.field.score.goal2();
        } else {
            frame.field.score.goal1();
        }
    }

    void sendPuckUpdate() {
        if (!player1) {
            return;
        }
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "PuckUpdate");
        GameVector position = frame.mirrorPosition(
                frame.getPucks().get(0).getPosition(), frame.getPucks().get(0));

        reply.put(XPOS, position.getX());
        reply.put(YPOS, position.getY());

        GameVector velocity = frame.mirrorVelocity(
                frame.getPucks().get(0).getVelocity());
        reply.put(XVEL, velocity.getX());
        reply.put(YVEL, velocity.getY());

        try {
            session.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void applyPuckUpdate(JsonObject reply) {

        BigDecimal temp = (BigDecimal) reply.get(XPOS);
        if (temp == null) {
            System.out.println("Puck update null");
            return;
        }

        double xcoord = (temp).doubleValue();
        double ycoord = ((BigDecimal) reply.get(YPOS)).doubleValue();

        double xvel = ((BigDecimal) reply.get(XVEL)).doubleValue();
        double yvel = ((BigDecimal) reply.get(YVEL)).doubleValue();

        frame.getPucks().get(0).setPosition(new GameVector(xcoord, ycoord));
        frame.getPucks().get(0).setVelocity(new GameVector(xvel, yvel));
    }

}
