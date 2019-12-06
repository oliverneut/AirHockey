package app.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private transient User user;

    @BeforeEach
    void setUp() {
        user = new User(42, "username", "password");
    }

    @Test
    void getUserid() {
        assertEquals(42, user.getUserid());
    }

    @Test
    void testEqualsSameUser() {
        User user2 = new User(42, "changedusername", "changedpass");

        assertTrue(user.equals(user2));
    }

    @Test
    void testEqualsDiffUser() {
        User user2 = new User(69, "diffusername", "password");

        assertFalse(user.equals(user2));
    }

    @Test
    void testHashCodeSameUsers() {
        User user2 = new User(42, "changedusername", "changedpass");

        assertEquals(user.hashCode(), user2.hashCode());
    }

    @Test
    void testHashCodeDiffUsers() {
        User user2 = new User(69, "diffusername", "password");

        assertNotEquals(user.hashCode(), user2.hashCode());
    }
}