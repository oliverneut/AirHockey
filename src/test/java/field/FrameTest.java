package field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class FrameTest {

    static Frame frame = new Frame();

    @Test
    void testSetter() {
        assertNotNull(frame);
    }

    @Test
    void testGetPuck() {
        assertNotNull(frame.getPuck());
    }

    @Test
    void testGetBoundingBoxes() {
        assertNotNull(frame.getBoundingBoxes());
    }
}
