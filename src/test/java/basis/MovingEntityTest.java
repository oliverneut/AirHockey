package basis;

import static basis.MovingEntity.handleCollision;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import game.Frame;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MovingEntityTest {

    private static double move = 20;
    private static int size = 50;
    private static int multiplier = 1;
    private transient Puck puck;
    private transient Paddle paddle;
    private transient game.Frame frame;

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
    void testCollisionNoPuck() {
        Paddle thisEntity = Mockito.mock(Paddle.class);
        handleCollision(thisEntity, paddle);
        verifyNoMoreInteractions(thisEntity);
    }

    @Test
    void testCollisionPuck() {
        Puck otherPuck = Mockito.mock(Puck.class);
        GameVector vel = puck.getVelocity();
        handleCollision(puck, otherPuck);
        verify(otherPuck).getBounceDirection(
                puck.getPosition().getX(), puck.getPosition().getY(), vel);
    }

    @Test
    void testNoWallCollidePaddle() {
        GameVector velocity = paddle.getVelocity();
        paddle.setPosition(
                new GameVector(frame.getWidth() / 2, frame.getHeight() / 2));
        paddle.wallCollide(frame);
        assertEquals(velocity, paddle.getVelocity());
    }

    @Test
    void testWallCollideInvalidEntity() {
        MovingEntity entity = Mockito.mock(MovingEntity.class);
        entity.wallCollide(frame);
        verify(entity).wallCollide(frame);
        verifyNoMoreInteractions(entity);
    }

    @Test
    void testSetBackWithSpeed() {
        puck.setPosition(new GameVector(
                puck.getPosition().getX() - 1, puck.getPosition().getY() - 1));
        assertFalse(puck.intersects(paddle) >= 0);
        puck.setVelocity(new GameVector(1, 1));
        GameVector newPosition = puck.setBack(paddle);
        puck.setPosition(newPosition);
        assertTrue(puck.intersects(paddle) == 0);
    }

    @Test
    void testSetBackWithoutSpeed() {
        puck.setPosition(new GameVector(
                puck.getPosition().getX() - 5, puck.getPosition().getY() - 5));
        assertFalse(puck.intersects(paddle) >= 0);
        GameVector tempPosition = puck.getPosition();
        puck.setVelocity(new GameVector(0, 0));
        puck.setBack(paddle);
        assertEquals(tempPosition, puck.getPosition());
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
        GameVector tempPosition = paddle.getPosition();
        paddle.wallCollide(frame);
        assertEquals(tempPosition, paddle.getPosition());
    }

    @Test
    void testPuckNoWallCollision() {
        GameVector tempPosition = puck.getPosition();
        GameVector tempVelocity = puck.getVelocity();
        puck.wallCollide(frame);
        assertEquals(tempPosition, puck.getPosition());
        assertEquals(tempVelocity, puck.getVelocity());
    }

    @Test
    void testBounceDirection() {
        puck.setPosition(new GameVector(101, 101));
        GameVector direction =
                puck.getBounceDirection(100, 100, new GameVector(1, 1));
        assertEquals(1, direction.getX());
        assertEquals(-1, direction.getY());
    }
}
