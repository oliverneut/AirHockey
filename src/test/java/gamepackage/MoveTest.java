package gamepackage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveTest {

    private transient Puck puck;

    private static double move = 10;
    private static double width = 500;
    private static double height = 800;

    @BeforeEach
    void setupTestEnvironment() {
        //fix to test errors hopefully
        puck = new Puck(new GameVector((this.width) / 2 + 1,
                (this.height / 2) + 1), new GameVector(0, 0),50, 50);
    }

    @AfterEach
    void shutDownScreen() {
        //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    @Test
    void testInitialize() {
        double calculatedX = this.width / 2 + 1;
        double calculatedY = this.height / 2 + 1;
        assertEquals(calculatedX, puck.getPosition().getX());
        assertEquals(calculatedY, puck.getPosition().getY());
    }

    @Test
    void testVelocity() {
        puck.setVelocity(new GameVector(move, move));
        assertEquals(move, puck.getVelocity().getX());
        assertEquals(move, puck.getVelocity().getY());
    }

    @Test
    void testMove() {
        double endX = puck.getPosition().getX() + move;
        double endY = puck.getPosition().getY() + move;
        puck.setVelocity(new GameVector(move, move));
        puck.move(null);
        assertEquals(endY, puck.getPosition().getY());
        assertEquals(endX, puck.getPosition().getX());
    }
}
