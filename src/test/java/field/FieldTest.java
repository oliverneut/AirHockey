package field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import gamepackage.GameVector;
import gamepackage.Puck;
import java.awt.Dimension;
import org.junit.jupiter.api.Test;

public class FieldTest {

    static Field field = new Field(new Dimension(200, 430),
        new Puck(new GameVector(12, 12), new GameVector(12, 12)));

    @Test
    void testSetter() {
        assertNotNull(field);
    }

    @Test
    void testGetBoundBoxes() {
        assertNotNull(field.getBoundBoxes());
    }
}
