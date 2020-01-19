package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import basis.GameVector;
import basis.Paddle;
import basis.Puck;

import java.awt.*;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PuckTest {

    private static Frame frame;
    private static double move = 20;
    private static int size = 50;
    private static int multiplier = 1;
    private transient Paddle paddle;
    private transient Puck puck;

    @BeforeEach
    void setupTestEnvironment() {
        GameVector pos = new GameVector(200, 200);
        GameVector vel = new GameVector(1, 0);
        puck = new Puck(pos, vel, size, multiplier);
        paddle = new Paddle(
                new GameVector(pos.getX(), pos.getY()),
                new GameVector(0, 0), 1, size, size);
        try {
            frame = new Frame(1);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void testMoveNoCollision() {
        double endX = puck.getPosition().getX() + move;
        double endY = puck.getPosition().getY() + move;
        puck.setVelocity(new GameVector(move, move));
        puck.move(frame);
        assertEquals(endY, puck.getPosition().getY());
        assertEquals(endX, puck.getPosition().getX());
    }

    @Test
    void testMoveCollision() {
        GameVector currVelocity = puck.getVelocity();
        frame.getPaddle().setPosition(puck.getPosition());
        puck.move(frame);
        assertTrue(!puck.getVelocity().equals(currVelocity));
    }

    @Test
    void testPaint() {
        Graphics g = Mockito.mock(Graphics.class);
        puck.paint(g);
        Mockito.verify(g).fillOval((int) puck.getPosition().getX(),
                (int) puck.getPosition().getY(), puck.size, puck.size);
    }
}