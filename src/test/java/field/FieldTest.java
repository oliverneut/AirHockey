package field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import gamepackage.GameVector;
import gamepackage.Paddle;
import gamepackage.Puck;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class FieldTest {

    @Test
    void testSetter() {
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, 1);
        assertNotNull(field);
    }

    @Test
    void testGetBoundBoxes() {
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, 1);
        assertNotNull(field.getBoundBoxes());
    }

    @Test
    void testGetGoals() {
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, 1);
        assertNotNull(field.getGoals());
    }
}
