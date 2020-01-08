package gamepackage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaddleTest {

    private static double move = 10;
    private static int sizex = 70;
    private static int sizey = 70;
    private static int radius = 75;
    private transient Paddle paddle;

    @BeforeEach
    void setupTestEnvironment() {
        //fix to test errors hopefully
        GameVector position = new GameVector(300, 300);
        GameVector velocity = new GameVector(0, 0);
        paddle = new Paddle(position, velocity, 0, sizey, sizex);
    }

    @Test
    void intersectsSamePosition() {
        Puck puck = new Puck(new GameVector(300, 300), new GameVector(0, 0), sizex, sizey);
        assertTrue(paddle.intersects(puck) <= 0);
    }

    @Test
    void intersectsDifferentPosition() {
        Puck puck = new Puck(new GameVector(320, 320), new GameVector(0, 0), sizex, sizey);
        assertTrue(paddle.intersects(puck) < 0);
    }

    @Test
    void notIntersect() {
        Puck puck = new Puck(new GameVector(50, 50), new GameVector(0, 0), sizex, sizey);
        assertFalse(paddle.intersects(puck) <= 0);
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