package game;

import basis.GameVector;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;

@WebSocket
public class MatchSocketHandler {

    transient Frame frame;
    transient Session session;

    MatchSocketHandler(Frame frame) {
        this.frame = frame;
    }

    /**
     * Instantiate a new WS client and connect to server.
     *
     * @param serverUrl The url of the WS server.
     * @param frame     The frame of the match.
     * @return The initialized instance of WebSocketClient.
     */
    public static WebSocketClient initialize(String serverUrl, Frame frame) {

        WebSocketClient client = new WebSocketClient();
        try {
            client.start();
            client.connect(new MatchSocketHandler(frame), new URI(serverUrl));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
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
                frame.getOpponentPaddle().setPosition(new GameVector(70, 70));
                try {
                    JsonObject reply = new JsonObject();
                    reply.put("Head", "Update");
                    reply.put("x_coord",
                            Double.toString(frame.getPaddle().getPosition().getX()));
                    reply.put("y_coord",
                            Double.toString(frame.getPaddle().getPosition().getY()));
                    this.session.getRemote().sendString(reply.toJson());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Update":
                double xcoord = (double) json.get("x_coord");
                double ycoord = (double) json.get("y_coord");
                frame.getOpponentPaddle().setPosition(new GameVector(xcoord, ycoord));
                try {
                    JsonObject reply = new JsonObject();
                    reply.put("Head", "Update");
                    reply.put("x_coord",
                            Double.toString(frame.getPaddle().getPosition().getX()));
                    reply.put("y_coord",
                            Double.toString(frame.getPaddle().getPosition().getY()));
                    this.session.getRemote().sendString(reply.toString());
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

}
