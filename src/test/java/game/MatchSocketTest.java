package game;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class MatchSocketTest {

    private static String HEAD = "Head";

    @Test
    void testInitialization() {
        assertNotNull(MatchSocketHandler.initialize(Mockito.mock(Frame.class)));
    }

    @Test
    void testOnConnection() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        Session mock = Mockito.mock(Session.class);
        temp.onConnect(mock);
        assertNotNull(temp);
    }

    @Test
    void testOnClose() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        temp.onClose(69, "AAAAAAAAAA");
        assertNotNull(temp);
    }

    @Test
    void testOnMessagePuck() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "PuckUpdate");
        temp.onMessage(reply.toJson());
        assertNotNull(temp);
    }

    //    @Test
    //    void testOnMessagePaddle() {
    //        try {
    //            MatchSocketHandler temp = MatchSocketHandler.initialize(new Frame(1));
    //            JsonObject reply = new JsonObject();
    //            reply.put(HEAD, "PaddleUpdate");
    //            reply.put("xpos", 0.0);
    //            reply.put("ypos", 0.0);
    //            reply.put("xvel", 0.0);
    //            reply.put("yvel", 0.0);
    //            temp.onMessage(reply.toJson());
    //            assertNotNull(temp);
    //        } catch (FileNotFoundException e) {
    //            System.out.println(1);
    //        }
    //    }

    @Test
    void testOnMessageScore() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "ScoreUpdate");
        reply.put("Player", 1);
        temp.onMessage(reply.toJson());
        assertNotNull(temp);
    }

    @Test
    void testOnMessageScoreTwo() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "ScoreUpdate");
        reply.put("Player", 2);
        temp.onMessage(reply.toJson());
        assertNotNull(temp);
    }

    @Test
    void testOnMessageJoined() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Joined");
        temp.onMessage(reply.toJson());
        assertNotNull(temp);
    }

    //    @Test
    //    void testOnMessageStartOne() {
    //        try {
    //            MatchSocketHandler temp = MatchSocketHandler.initialize(new Frame(1));
    //            JsonObject reply = new JsonObject();
    //            reply.put(HEAD, "Start");
    //            reply.put("Player1", true);
    //            temp.onMessage(reply.toJson());
    //            assertNotNull(temp);
    //        } catch (FileNotFoundException e) {
    //            System.out.println(e);
    //        }
    //    }

    @Test
    void testOnMessageStartTwo() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Start");
        reply.put("Player1", false);
        temp.onMessage(reply.toJson());
        assertNotNull(temp);
    }

    @Test
    void testOnMessageMatchResult() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "MatchResult");
        reply.put("Result", true);
        temp.onMessage(reply.toJson());
        assertNotNull(temp);
    }

    @Test
    void testOnMessageMatchResultTwo() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "MatchResult");
        reply.put("Result", false);
        temp.onMessage(reply.toJson());
        assertNotNull(temp);
    }

    @Test
    void testOnMessageDefault() {
        MatchSocketHandler temp = MatchSocketHandler.initialize(Mockito.mock(Frame.class));
        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Oh no");
        temp.onMessage(reply.toJson());
        assertNotNull(temp);
    }
}
