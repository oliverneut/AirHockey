package basis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.Frame;
import java.awt.Graphics;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PuckTest {

    private static game.Frame frame;
    private static double move = 20;
    private static int size = 50;
    private static int multiplier = 1;
    private transient basis.Paddle paddle;
    private transient basis.Puck puck;

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

    @Test
    void testWallCollisionOne() {
        puck.setPosition(new GameVector(0, 0));
        puck.move(frame);
        assertTrue(puck.getVelocity().getX() < 0.1);
    }

    @Test
    void testWallCollisionTwo() {
        puck.setPosition(new GameVector(315, 15));
        puck.move(frame);
        assertTrue(puck.getVelocity().getX() < 0.1);
    }

    @Test
    void testWallCollisionThree() {
        puck.setPosition(new GameVector(20, 635));
        puck.move(frame);
        assertTrue(puck.getVelocity().getX() < 1.1);
    }

    @Test
    void testWallCollisionFour() {
        puck.setPosition(new GameVector(3, 100));
        puck.move(frame);
        assertTrue(puck.getVelocity().getX() < 1.1);
    }

    @Test
    void testGoalCollisionOne() {
        puck.setPosition(new GameVector(110, 0));
        puck.setVelocity(new GameVector(0, 0));
        puck.move(frame);
        assertTrue(puck.getVelocity().getX() < 0.1);
    }

    @Test
    void testGoalCollisionTwo() {
        puck.setPosition(new GameVector(110, 590));
        puck.setVelocity(new GameVector(0, 0));
        puck.move(frame);
        assertTrue(puck.getVelocity().getX() < 1.1);
    }
}