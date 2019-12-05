package gamepackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaddleTest {

    private transient Paddle paddle;

    private static double move = 10;
    private static int sizex = 70;
    private static int sizey = 70;
    private static int radius = 75;

    @BeforeEach
    void setupTestEnvironment() {
        //fix to test errors hopefully
        GameVector position = new GameVector(300, 300);
        GameVector velocity = new GameVector(0, 0);
        paddle = new Paddle(position, velocity, 0, sizey, sizex);
    }
    @Test
    void intersectsSamePosition() {
        Puck puck = new Puck(new GameVector(300, 300), new GameVector(0, 0));
        GameVector position = paddle.getPosition();
        assertTrue(paddle.intersects(puck.getPosition(), radius));
    }

    @Test
    void intersectsDifferentPosition() {
        Puck puck = new Puck(new GameVector(350, 350), new GameVector(0, 0));
        GameVector position = paddle.getPosition();
        assertTrue(paddle.intersects(puck.getPosition(), radius));
    }

    @Test
    void notIntersect() {
        Puck puck = new Puck(new GameVector(50, 50), new GameVector(0, 0));
        GameVector position = paddle.getPosition();
        assertFalse(paddle.intersects(puck.getPosition(), radius));
    }

    @Test
    void getBounceDirection() {
    }

    @Test
    void testPosition() {
        paddle.setPosition(new GameVector(move, move));
        assertEquals(move, paddle.getPosition().getX());
        assertEquals(move, paddle.getPosition().getY());
    }

    @Test
    void testVelocity() {
        paddle.setVelocity(new GameVector(move, move));
        assertEquals(move, paddle.getVelocity().getX());
        assertEquals(move, paddle.getVelocity().getY());
    }

    @Test
    void testId() {
        paddle.setId(1);
        assertEquals(1, paddle.getId());
    }
}