package gamepackage;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import field.Frame;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;

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

                //reset the pucks and paddles when match starts
                frame.getOpponentPaddle().setPosition(new GameVector(70, 100));
                frame.getPaddle().setPosition(
                        frame.mirrorCoordinates(new GameVector(70, 100)));
                ArrayList<Puck> pucks = frame.getPucks();
                for (int i = 0; i < pucks.size(); i++) {
                    pucks.get(i).setPosition(new GameVector(50, 50));
                }
                //

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
        GameVector mirrorCoord = frame.mirrorCoordinates(frame.getPaddle().getPosition());
        reply.put("x_coord", Double.toString(mirrorCoord.getX()));
        reply.put("y_coord", Double.toString(mirrorCoord.getY()));
        return reply;
    }

    void applyUpdate(JsonObject reply) {
        double xcoord = Double.parseDouble((String) reply.get("x_coord"));
        double ycoord = Double.parseDouble((String) reply.get("y_coord"));
        frame.getOpponentPaddle().setPosition(new GameVector(xcoord, ycoord));
    }

}
