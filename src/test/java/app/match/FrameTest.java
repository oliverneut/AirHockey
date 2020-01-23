package app.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import basis.GameVector;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class FrameTest {

    private static int height = 640;
    private static int width = 320;

    private transient Frame frame;


    @Mock
    private transient Match match;

    @BeforeEach
    void setUp() {
        match = Mockito.mock(Match.class);
        frame = new Frame(match, width, height,
                new ArrayList(), new ArrayList());
    }

    @Test
    void testCreatePaddleSelf() {
        frame.createPaddle(height, width, false);
        GameVector wantedPosition = new GameVector(width / 2, height / 4);
        assertNotNull(frame.paddle);
        assertEquals(wantedPosition, frame.paddle.getPosition());
        assertEquals(new GameVector(0, 0), frame.paddle.getVelocity());
    }

    @Test
    void testCreatePaddleOpponent() {
        frame.createPaddle(height, width, true);
        GameVector wantedPosition = new GameVector(width / 2, height / 4 * 3);
        assertNotNull(frame.opponentPaddle);
        assertEquals(wantedPosition, frame.opponentPaddle.getPosition());
        assertEquals(new GameVector(0, 0), frame.opponentPaddle.getVelocity());
    }
}
