package field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

public class FrameTest {


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
    void testgetGoals() {
        try {
            Frame frame = new Frame(1);
            assertNotNull(frame.getGoals());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Test
    void testFileNotFound() {
        try {
            new Frame(999);
        } catch (FileNotFoundException e) {
            assertNotNull(e);
        }
    }

    @Test
    void testOpponentPaddle() {
        try {
            Frame frame = new Frame(1);
            assertNotNull(frame.getOpponentPaddle());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}
