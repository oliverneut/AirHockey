package game;

import basis.GameVector;
import basis.Paddle;
import basis.Puck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovingEntityTest {

    private static double move = 20;
    private static int size = 50;
    private static int multiplier = 1;
    private transient Puck puck;
    private transient Paddle paddle;

    @BeforeEach
    void setupTestEnvironment() {
        GameVector pos = new GameVector(200, 200);
        GameVector vel = new GameVector(1, 0);
        puck = new Puck(pos, vel, size, multiplier);
        paddle = new Paddle(
                pos,
                new GameVector(0, 0), 1, size, size);
    }

    @Test
    void testSetBack() {
        assertFalse(puck.intersects(paddle) > 0);
        puck.setBack(paddle, puck.intersects(paddle));
        assertTrue(puck.intersects(paddle) >= 0);
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
}
