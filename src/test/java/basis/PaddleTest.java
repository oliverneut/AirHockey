package basis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import game.Frame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class PaddleTest {

    private static double move = 10;
    private static int sizex = 70;
    private static int sizey = 70;
    private static int radius = 75;
    private transient ArrayList<Rectangle> boxes;
    private transient game.Frame frame;
    private transient Paddle paddle;

    @Mock
    private transient MouseEvent mouseevent;

    @BeforeEach
    void setupTestEnvironment() {
        GameVector position = new GameVector(300, 300);
        GameVector velocity = new GameVector(0, 0);
        paddle = new Paddle(position, velocity, 0, sizey, sizex);
        try {
            frame = new Frame(1);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        boxes = frame.getBoundingBoxes();
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

    @Test
    void testMouseDragged() {
        mouseevent = Mockito.mock(MouseEvent.class);
        Mockito.when(mouseevent.getX()).thenReturn(1);
        Mockito.when(mouseevent.getY()).thenReturn(1);
        paddle.mouseDragged(mouseevent);
        double newVelocityX = 300 - mouseevent.getX();
        assertEquals(paddle.getPosition().getX(), 1);
        assertEquals(paddle.getPosition().getY(), 1);
        assertEquals(paddle.getVelocity().getX(), newVelocityX);
    }

    @Test
    void testPaint() {
        Graphics g = Mockito.mock(Graphics.class);
        paddle.paint(g);
        Mockito.verify(g).setColor(Color.BLUE);
    }

    @Test
    void testWallCollisionOne() {
        paddle.setPosition(new GameVector(100, 100));
        mouseevent = Mockito.mock(MouseEvent.class);
        Mockito.when(mouseevent.getX()).thenReturn(0);
        Mockito.when(mouseevent.getY()).thenReturn(0);
        Mockito.when(mouseevent.getComponent()).thenReturn(frame);
        paddle.mouseMoved(mouseevent);
        assertNotEquals(paddle.getPosition().getX(), 0);
    }

    @Test
    void testWallCollisionTwo() {
        paddle.setPosition(new GameVector(100, 100));
        mouseevent = Mockito.mock(MouseEvent.class);
        Mockito.when(mouseevent.getX()).thenReturn(315);
        Mockito.when(mouseevent.getY()).thenReturn(15);
        Mockito.when(mouseevent.getComponent()).thenReturn(frame);
        paddle.mouseMoved(mouseevent);
        assertNotEquals(paddle.getPosition().getX(), 315);
    }

    @Test
    void testWallCollisionThree() {
        paddle.setPosition(new GameVector(100, 100));
        mouseevent = Mockito.mock(MouseEvent.class);
        Mockito.when(mouseevent.getX()).thenReturn(20);
        Mockito.when(mouseevent.getY()).thenReturn(635);
        Mockito.when(mouseevent.getComponent()).thenReturn(frame);
        paddle.mouseMoved(mouseevent);
        assertNotEquals(paddle.getPosition().getY(), 635);
    }

    @Test
    void testWallCollisionFour() {
        paddle.setPosition(new GameVector(100, 100));
        mouseevent = Mockito.mock(MouseEvent.class);
        Mockito.when(mouseevent.getX()).thenReturn(100);
        Mockito.when(mouseevent.getY()).thenReturn(0);
        Mockito.when(mouseevent.getComponent()).thenReturn(frame);
        paddle.mouseMoved(mouseevent);
        assertNotEquals(paddle.getPosition().getX(), 3);
    }
}