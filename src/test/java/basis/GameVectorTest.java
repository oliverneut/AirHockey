package basis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameVectorTest {

    private transient GameVector vector;

    @BeforeEach
    void setUp() {
        vector = new GameVector(2, 6);
    }

    @Test
    void testNotEqualsDifferentY() {
        GameVector other = new GameVector(2, 1);
        assertNotEquals(vector, other);
    }

    @Test
    void testNotEqualsDifferentX() {
        GameVector other = new GameVector(4, 6);
        assertNotEquals(vector, other);
    }


    @Test
    void testNotEqualsNoGameVector() {
        Object other = new Object();
        assertNotEquals(vector, other);
    }

    @Test
    void testEqualsSameVector() {
        assertEquals(vector, vector);
    }

    @Test
    void testEqualsOtherVector() {
        GameVector otherVector = new GameVector(2, 6);
        assertEquals(vector, otherVector);
    }

    @Test
    void testHashCodeEquals() {
        GameVector other = new GameVector(2, 6);
        assertEquals(vector.hashCode(), other.hashCode());
    }

    @Test
    void testHashCodeNotEquals() {
        GameVector other = new GameVector(6, 2);
        assertNotEquals(vector.hashCode(), other.hashCode());
    }

    @Test
    void testAddVector() {
        GameVector other = new GameVector(5, 8);
        GameVector result = new GameVector(7, 14);
        other.addVector(vector);
        assertEquals(result, other);
    }
}
