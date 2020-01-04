package gamepackage;

import app.util.Message;
import field.Frame;
import java.io.IOException;
import java.net.URI;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;

@WebSocket
public class MatchSocketHandler {

    private Frame frame;
    private Session session;

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
        Message msg = Message.parse(message);

        switch (msg.getHead()) {
            case "Joined":
                System.out.println("Waiting for opponent");
                break;
            case "Start":
                System.out.println("Match starting");
                frame.setOpponentPaddle(new Paddle(new GameVector(100, 100),
                        new GameVector(0, 0), 0, 70, 70));
                try {
                    Message reply = new Message("Update")
                            .put("x_coord",
                                    Double.toString(frame.getPaddle().getPosition().getX()))
                            .put("y_coord",
                                    Double.toString(frame.getPaddle().getPosition().getY()));
                    this.session.getRemote().sendString(reply.toString());
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Update":
                GameVector opponentPosition = frame.getOpponentPaddle().position;
                opponentPosition.setX(Double.parseDouble(msg.getValue("x_coord")));
                opponentPosition.setY(Double.parseDouble(msg.getValue("y_coord")));
                try {
                    Message reply = new Message("Update")
                            .put("x_coord",
                                    Double.toString(frame.getPaddle().getPosition().getX()))
                            .put("y_coord",
                                    Double.toString(frame.getPaddle().getPosition().getY()));
                    this.session.getRemote().sendString(reply.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println(message);
        }
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        System.out.print("Websocket error: ");
        cause.printStackTrace(System.out);
    }

}
