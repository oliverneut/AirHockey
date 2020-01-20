package game;

import basis.GameVector;
import basis.ScoreCount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void testRunGameOne() {
        ScoreCount.getInstance().resetScore();
        try {
            for (int i = 0; i < 5; i++) {
                ScoreCount.getInstance().goal1();
            }
            Frame frame = Game.runGame(1);
            assertNotNull(frame);
        } catch (InterruptedException e) {
            assertNull(e);
        }
    }

    @Test
    void testRunGameTwo() {
        ScoreCount.getInstance().resetScore();
        try {
            for (int i = 0; i < 5; i++) {
                ScoreCount.getInstance().goal2();
            }
            Frame frame = Game.runGame(1);
            assertNotNull(frame);
        } catch (InterruptedException e) {
            assertNull(e);
        }
    }
}
