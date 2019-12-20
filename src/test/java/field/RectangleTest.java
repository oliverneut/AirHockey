package field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testIntersectTrue() {
        Rectangle temp2 = new Rectangle(1, 2, 3, 4);
        assertTrue(temp.intersects(temp2));
    }

    @Test
    void testIntersectFalse() {
        Rectangle temp2 = new Rectangle(200, 200, 3, 4);
        assertFalse(temp.intersects(temp2));
    }
}