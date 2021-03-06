package basis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ScoreCountTest {

    @Test
    public void testSetter() {
        assertNotNull(ScoreCount.getInstance().getPlayer1());
        assertNotNull(ScoreCount.getInstance().getPlayer2());
    }

    @Test
    public void testGoalUpdate1() {
        ScoreCount.getInstance().resetScore();
        ScoreCount.getInstance().goal1();

        assertEquals(1, ScoreCount.getInstance().getPlayer1());
    }

    @Test
    public void testGoalUpdate2() {
        ScoreCount.getInstance().resetScore();
        ScoreCount.getInstance().goal2();
        assertEquals(1, ScoreCount.getInstance().getPlayer2());
    }

    @Test
    public void testResetGoals() {
        ScoreCount.getInstance().resetScore();
        assertEquals(0, ScoreCount.getInstance().getPlayer1());
        assertEquals(0, ScoreCount.getInstance().getPlayer2());
    }
}

