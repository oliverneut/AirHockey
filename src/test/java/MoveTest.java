import static org.junit.jupiter.api.Assertions.assertEquals;

import field.Frame;
import gamepackage.GameVector;
import gamepackage.Puck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveTest {

    private transient field.Frame frame;
    private transient Puck puck;
    private transient double move = 10;

    @BeforeEach
    void setupTestEnvironment() {
        //fix to test errors hopefully
        System.setProperty("java.awt.headless", "true");
        frame = new Frame();
        this.puck = frame.getPuck();
    }

    @AfterEach
    void shutDownScreen() {
        //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    @Test
    void testInitialize() {
        double calculatedX = frame.getSize().getWidth() / 2 + 1;
        double calculatedY = frame.getSize().getHeight() / 2 + 1;
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
        puck.move(frame);
        assertEquals(endY, puck.getPosition().getY());
        assertEquals(endX, puck.getPosition().getX());
    }
}
