package gamepackage;

import static gamepackage.Game.gson;

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

    @OnWebSocketMessage
    public void onMessage(String message) {
        Message msg = gson.fromJson(message, Message.class);

        switch (msg.head) {
            case "Joined":
                System.out.println("Waiting for opponent");
                break;
            case "Start":
                System.out.println("Match starting");
                frame.setOpponentPaddle(new Paddle(new GameVector(100, 100),
                        new GameVector(0, 0), 0, 70, 70));
                try {
                    this.session.getRemote().sendString(new Message("Update", frame.getPaddle()).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Update":
                Paddle paddle = (Paddle) msg.body;
                frame.setOpponentPaddle(paddle);
                try {
                    this.session.getRemote().sendString(new Message("Update", frame.getPaddle()).toString());
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
        System.out.println("Websocket error: " + cause.getMessage());
    }

}
