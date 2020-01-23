package basis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

    @Test
    void testIntersectFalseSameX() {
        Rectangle temp2 = new Rectangle(1, -2, 3, 4);
        assertFalse(temp.intersects(temp2));
    }

    @Test
    void testIntersectFalseSameY() {
        Rectangle temp2 = new Rectangle(-4, 2, 3, 4);
        assertFalse(temp.intersects(temp2));
    }

    @Test
    void testJson() {
        Rectangle rectangle = new Rectangle(200, 200, 3, 4);
        String result = rectangle.toJson();
        String shouldBe = "{\"ycord\":200,\"width\":4,\"xcord\":200,\"height\":3}";
        assertEquals(shouldBe, result);
    }

    @Test
    void testWriterJson() {
        Writer writable = Mockito.mock(Writer.class);
        Rectangle rectangle = new Rectangle(200, 200, 3, 4);
        try {
            rectangle.toJson(writable);
            Mockito.verify(writable).write(rectangle.toJson());
        } catch (IOException ex) {
            assertEquals(ex, FileNotFoundException.class);
        }
    }
}