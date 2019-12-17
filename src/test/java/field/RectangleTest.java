package field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class RectangleTest {


    static Rectangle temp = new Rectangle(1, 2, 3, 4);

    @Test
    void testSetter() {
        assertNotNull(temp);
    }

    @Test
    void testGetX() {
        assertEquals(1, temp.getXcord());
    }

    @Test
    void testGetY() {
        assertEquals(2, temp.getYcord());
    }

    @Test
    void testGetWidth() {
        assertEquals(4, temp.getWidth());
    }

    @Test
    void testGetHeight() {
        assertEquals(3, temp.getHeight());
    }
}