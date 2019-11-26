import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {
    private transient Student student1;
    private transient Student student2;
    private transient Student student3;

    @BeforeEach
    void setupTestEnvironment() {
        student1 = new Student("Black Widow", 1234567);
        student2 = new Student("Thor", 7654321);
        student3 = new Student("Loki", 1234567);
    }

    @Test
    void testConstructor() {
        assertNotNull(student1);
    }

    @Test
    void testGetName() {
        assertEquals(student1.getName(), "Black Widow");
    }

    @Test
    void testGetStudentNumber() {
        assertEquals(student1.getStudentNumber(), 1234567);
    }

    @Test
    void testEqualsPositive() {
        assertEquals(student1, student3);
    }

    @Test
    void testEqualsSelfPositive() {
        assertEquals(student1, student1);
    }

    @Test
    void testEqualsNegative() {
        assertNotEquals(student1, student2);
    }
}
