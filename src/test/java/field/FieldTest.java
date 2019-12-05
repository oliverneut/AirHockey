package field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import gamepackage.GameVector;
import gamepackage.Puck;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class FieldTest {


    @Test
    void testSetter() {
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, 1);
        assertNotNull(field);
    }

    @Test
    void testGetBoundBoxes() {
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, 1);
        assertNotNull(field.getBoundBoxes());
    }

    @Test
    void testGetGoals() {
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, 1);
        assertNotNull(field.getGoals());
    }
}
