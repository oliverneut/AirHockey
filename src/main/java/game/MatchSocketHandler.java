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
            case "Joined":
                System.out.println("Waiting for opponent");
                break;
            case "Start":
                System.out.println("Match starting");

                double xvel = ((BigDecimal) json.get("x_vel")).doubleValue();
                double yvel = ((BigDecimal) json.get("y_vel")).doubleValue();
                frame.resetMovingEntities(new GameVector(xvel, yvel));

                try {
                    JsonObject reply = createUpdateReply();
                    this.session.getRemote().sendString(reply.toJson());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Update":
                applyUpdate(json);
                try {
                    JsonObject reply = createUpdateReply();
                    this.session.getRemote().sendString(reply.toJson());
                } catch (IOException e) {
                    e.printStackTrace();
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

    JsonObject createUpdateReply() {
        JsonObject reply = new JsonObject();
        reply.put("Head", "Update");
        GameVector mirrorCoord = frame.mirrorCoordinates(
                frame.getPaddle().getPosition(), frame.getPaddle());

        reply.put("x_coord", mirrorCoord.getX());
        reply.put("y_coord", mirrorCoord.getY());

        GameVector mirrorVel = frame.mirrorCoordinates(frame.getPaddle().getVelocity());
        reply.put("x_vel", mirrorVel.getX());
        reply.put("y_vel", mirrorVel.getY());

        return reply;
    }

    void applyUpdate(JsonObject reply) {
        double xcoord = ((BigDecimal) reply.get("x_coord")).doubleValue();
        double ycoord = ((BigDecimal) reply.get("y_coord")).doubleValue();

        double xvel = ((BigDecimal) reply.get("x_vel")).doubleValue();
        double yvel = ((BigDecimal) reply.get("y_vel")).doubleValue();

        frame.getOpponentPaddle().setPosition(new GameVector(xcoord, ycoord));
        frame.getOpponentPaddle().setVelocity(new GameVector(xvel, yvel));
    }

}
