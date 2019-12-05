package field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class FrameTest {

    static

    @Test
    void testSetter() {
        try {
            Frame frame = new Frame(1);
            assertNotNull(frame);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Test
    void testGetPuck() {
        try {
            Frame frame = new Frame(1);
            assertNotNull(frame.getPucks());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Test
    void testGetBoundingBoxes() {
        try {
            Frame frame = new Frame(1);
            assertNotNull(frame.getBoundingBoxes());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Test
    void testgetGoals() {        try {
        Frame frame = new Frame(1);
        assertNotNull(frame.getGoals());
    } catch (FileNotFoundException e) {
        System.out.println(e);
    }

    }
}
