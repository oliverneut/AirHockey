package gamepackage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import field.Frame;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovingEntityTest {

    private static double move = 20;
    private static int size = 50;
    private static int multiplier = 1;
    private transient Puck puck;
    private transient Paddle paddle;
    private transient field.Frame frame;

    @BeforeEach
    void setupTestEnvironment() {
        GameVector pos = new GameVector(200, 200);
        GameVector vel = new GameVector(1, 0);
        puck = new Puck(pos, vel, size, multiplier);
        paddle = new Paddle(
                pos,
                new GameVector(0, 0), 1, size, size);
        try {
            frame = new Frame(1);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void testSetBackWithSpeed() {
        puck.setPosition(new GameVector(
                puck.position.getX() - 1, puck.position.getY() - 1));
        assertFalse(puck.intersects(paddle) >= 0);
        puck.setVelocity(new GameVector(1, 1));
        puck.setBack(paddle, puck.intersects(paddle));
        assertTrue(puck.intersects(paddle) == 0);
    }

    @Test
    void testSetBackWithoutSpeed() {
        puck.setPosition(new GameVector(
                puck.position.getX() - 1, puck.position.getY() - 1));
        assertFalse(puck.intersects(paddle) >= 0);
        GameVector tempPosition = puck.position;
        puck.setVelocity(new GameVector(0, 0));
        puck.setBack(paddle, puck.intersects(paddle));
        assertEquals(tempPosition, puck.position);
    }

    @Test
    void testIntersectSameRadius() {
        assertEquals(0, puck.intersects(paddle));
        paddle.setPosition(new GameVector(move, move));
        assertFalse(puck.intersects(paddle) <= 0);
    }

    @Test
    void testIntersectDifferentRadius() {
        paddle.setHeight(size - 10);
        paddle.setWidth(size - 10);
        assertTrue(puck.intersects(paddle) <= 0);
    }

    @Test
    void intersectsSamePosition() {
        assertTrue(paddle.intersects(puck) == 0);
    }

    @Test
    void intersectsDifferentPosition() {
        Puck testPuck = new Puck(
                new GameVector(paddle.getPosition().getX() - (size / 2),
                        paddle.getPosition().getY()),
                new GameVector(0, 0), size, size);
        assertTrue(paddle.intersects(testPuck) <= 0);
    }

    @Test
    void notIntersect() {
        Puck puck = new Puck(new GameVector(50, 50), new GameVector(0, 0), size, size);
        assertFalse(paddle.intersects(puck) <= 0);
    }

    @Test
    void testPosition() {
        puck.setPosition(new GameVector(move, move));
        assertEquals(move, puck.getPosition().getX());
        assertEquals(move, puck.getPosition().getY());
    }

    @Test
    void testVelocity() {
        puck.setVelocity(new GameVector(move, move));
        assertEquals(move, puck.getVelocity().getX());
        assertEquals(move, puck.getVelocity().getY());
    }

    @Test
    void testPaddleNoWallCollision() {
        GameVector tempPosition = paddle.position;
        paddle.wallCollide(frame);
        assertEquals(tempPosition, paddle.position);
    }

    @Test
    void testPuckNoWallCollision() {
        GameVector tempPosition = puck.position;
        GameVector tempVelocity = puck.velocity;
        puck.wallCollide(frame);
        assertEquals(tempPosition, puck.position);
        assertEquals(tempVelocity, puck.velocity);
    }
}
