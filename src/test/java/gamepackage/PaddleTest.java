package gamepackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testId() {
        paddle.setId(1);
        assertEquals(1, paddle.getId());
    }
}