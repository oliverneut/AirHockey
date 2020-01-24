package game;

import basis.GameVector;
import basis.ScoreCount;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import gui.HttpController;
import java.io.IOException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@WebSocket
public class MatchSocketHandler {

    public static final int PLAYER_ONE = 1;

    private static final String HEAD = "Head";
    public static int sendScoreUpdateFlag = 0;
    public static boolean player1 = true;
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
                frame.applyPuckUpdate(json);
                break;

            case "PaddleUpdate":
                frame.applyPaddleUpdate(json);

                sendScoreUpdate();
                sendPuckUpdate();
                sendPaddleUpdate();
                break;
            case "ScoreUpdate":
                frame.applyScoreUpdate(json);
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

        JsonObject reply = frame.generatePaddleUpdate();

        try {
            session.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void sendScoreUpdate() {
        if (!player1 || (sendScoreUpdateFlag == 0)) {
            return;
        }

        System.out.println("MatchSocketHandler : sent score update");

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "ScoreUpdate");
        reply.put("Player", sendScoreUpdateFlag);

        sendScoreUpdateFlag = 0;

        try {
            session.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendPuckUpdate() {
        if (!player1) {
            return;
        }

        JsonObject reply = frame.generatePuckUpdate();

        try {
            session.getRemote().sendString(reply.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
