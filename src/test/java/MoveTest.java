import static org.junit.jupiter.api.Assertions.assertEquals;

import gamepackage.Board;
import gamepackage.Game;
import gamepackage.GameVector;
import gamepackage.Puck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveTest {

    private transient Game.GameFrame frame;
    private transient Puck puck;
    private transient Board board;
    private transient double move = 10;

    @BeforeEach
    void setupTestEnvironment() {
        frame = new Game.GameFrame();
        frame.setSize(500, 800);
        GameVector position = new GameVector((frame.getSize().getWidth()) / 2 + 1,
                (frame.getSize().getHeight() / 2) + 1);
        GameVector zeroVector = new GameVector(0, 0);
        puck = new Puck(position, zeroVector);
        board = new Board(puck);
        frame.setContentPane(board);
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

    //@Test
    //void testMove() {
    //    double endX = puck.getPosition().getX() + move;
    //    double endY = puck.getPosition().getY() + move;
    //    puck.setVelocity(new GameVector(move, move));
    //    puck.move(frame);
    //    assertEquals(endY, puck.getPosition().getY());
    //    assertEquals(endX, puck.getPosition().getX());
    //}
}
