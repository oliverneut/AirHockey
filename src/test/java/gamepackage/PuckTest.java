package gamepackage;

import field.Field;
import field.Frame;
import org.junit.jupiter.api.BeforeEach;

class PuckTest {

    private static Field field;
    private static Frame frame;
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
                new GameVector(pos.getX(), pos.getY()),
                new GameVector(0, 0), 1, size, size);
    }


    /*
     @Test void testPaddleCollision() {
     GameVector pos = new GameVector(100, 100);
     GameVector vel = new GameVector(0, 0);
     paddle = new Paddle(pos, vel, 0, 75, 75);
     }

     @Test void testGoalCollision() {
     puck.goalCollision(frame);
     }

     @Test void testNoWallCollision() {
     puck.wallCollision(frame);
     assertEquals(200, puck.getPosition().getX());
     assertEquals(200, puck.getPosition().getY());
     }

     @Test void testWallCollisionInX() {
     puck.setPosition(new GameVector(- 50, puck.getPosition().getY()));
     GameVector tempVel = puck.getVelocity();
     puck.wallCollision(frame);
     assertEquals(0, puck.getPosition().getX());
     assertEquals(puck.getVelocity().getX(), - tempVel.getX());
     assertEquals(puck.getVelocity().getY(), - tempVel.getY());
     }

     @Test void testWallCollisionInY() {
     puck.setPosition(new GameVector(puck.getPosition().getX(), - 50));
     GameVector tempVel = puck.getVelocity();
     puck.wallCollision(frame);
     assertEquals(0, puck.getPosition().getY());
     assertEquals(puck.getVelocity().getX(), - tempVel.getX());
     assertEquals(puck.getVelocity().getY(), - tempVel.getY());
     }
     */
}