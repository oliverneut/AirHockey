package basis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameVectorTest {

    GameVector vector;

    @BeforeEach
    void setUp() {
        vector = new GameVector(2, 6);
    }

    @Test
    void testNotEquals() {
        GameVector other = new GameVector(0, 1);
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
