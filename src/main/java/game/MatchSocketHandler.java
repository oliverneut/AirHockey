package game;

import basis.GameVector;
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

@WebSocket
public class MatchSocketHandler {

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

        String head = (String) json.get("Head");
        switch (head) {
            case "PaddleUpdate":
                applyPaddleUpdate(json);
                sendPaddleUpdate();
                break;
            case "PuckUpdate":
                applyPuckUpdate(json);
                break;
            case "ScoreUpdate":
                applyScoreUpdate(json);
                break;
            case "Joined":
                System.out.println("Waiting for opponent");
                break;
            case "Initialize":
                sendFrameInfo();
                break;
            case "Start":
                System.out.println("Match starting");
                sendPaddleUpdate();
                break;
            case "MatchResult":
                System.out.println("Match Result");
                //display match won/lost
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

    void sendFrameInfo() {
        JsonObject reply = new JsonObject();
        reply.put("Head", "FieldInitialize");

        reply.put("Frame_width", frame.getWidth());
        reply.put("Frame_height", frame.getHeight());
        reply.put("Frame_boundingBoxes", frame.getBoundingBoxes());
        reply.put("Frame_goalBoxes", frame.getGoals());

        reply.put("Paddle_width", frame.getPaddle().getWidth());
        reply.put("Paddle_height", frame.getPaddle().getHeight());

        reply.put("Puck_size", frame.getPucks().get(0).size);
        reply.put("Puck_multiplier", frame.getPucks().get(0).multiplier);

        try {
            session.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendPaddleUpdate() {
        JsonObject reply = new JsonObject();
        reply.put("Head", "PaddleUpdate");
        GameVector position = frame.getPaddle().getPosition();

        reply.put("xpos", position.getX());
        reply.put("ypos", position.getY());

        GameVector velocity = frame.getPaddle().getVelocity();
        reply.put("xvel", velocity.getX());
        reply.put("yvel", velocity.getY());

        try {
            session.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void applyPaddleUpdate(JsonObject reply) {
        double xcoord = ((BigDecimal) reply.get("xpos")).doubleValue();
        double ycoord = ((BigDecimal) reply.get("ypos")).doubleValue();

        double xvel = ((BigDecimal) reply.get("xvel")).doubleValue();
        double yvel = ((BigDecimal) reply.get("yvel")).doubleValue();

        frame.getOpponentPaddle().setPosition(new GameVector(xcoord, ycoord));
        frame.getOpponentPaddle().setVelocity(new GameVector(xvel, yvel));
    }

    void applyScoreUpdate(JsonObject reply) {
        System.out.println(reply.get("goal scored").getClass());

        boolean userScored = (boolean) reply.get("goal scored");
        if (userScored) {
            frame.field.score.goal1();
        } else {
            frame.field.score.goal2();
        }
    }

    void applyPuckUpdate(JsonObject reply) {
        double xcoord = ((BigDecimal) reply.get("xpos")).doubleValue();
        double ycoord = ((BigDecimal) reply.get("ypos")).doubleValue();

        double xvel = ((BigDecimal) reply.get("xvel")).doubleValue();
        double yvel = ((BigDecimal) reply.get("yvel")).doubleValue();

        frame.getPucks().get(0).setPosition(new GameVector(xcoord, ycoord));
        frame.getPucks().get(0).setVelocity(new GameVector(xvel, yvel));
    }

}
