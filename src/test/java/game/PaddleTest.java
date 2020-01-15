package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import basis.GameVector;
import basis.Paddle;
import java.awt.event.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class PaddleTest {

    private static double move = 10;
    private static int sizex = 70;
    private static int sizey = 70;
    private static int radius = 75;
    private transient Paddle paddle;

    @Mock
    private transient MouseEvent mouseevent;

    @BeforeEach
    void setupTestEnvironment() {
        //fix to test errors hopefully
        GameVector position = new GameVector(300, 300);
        GameVector velocity = new GameVector(0, 0);
        paddle = new Paddle(position, velocity, 0, sizey, sizex);
    }

    @Test
    void testId() {
        paddle.setId(1);
        assertEquals(1, paddle.getId());
    }

    @Test
    void testMouseMoved() {
        mouseevent = Mockito.mock(MouseEvent.class);
        Mockito.when(mouseevent.getX()).thenReturn(1);
        Mockito.when(mouseevent.getY()).thenReturn(1);
        paddle.mouseMoved(mouseevent);
        double newVelocityX = 300 - mouseevent.getX();
        assertEquals(paddle.getPosition().getX(), 1);
        assertEquals(paddle.getPosition().getY(), 1);
        assertEquals(paddle.getVelocity().getX(), newVelocityX);
    }
}