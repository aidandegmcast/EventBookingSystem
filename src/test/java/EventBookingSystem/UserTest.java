package EventBookingSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.example.EventBookingSystem.User;
import org.junit.jupiter.api.Test;

public class UserTest {
    User user;

    @Test
    void gettingUserId1() {
        user = new User(1,"Aidan","aidan@mail.com");
        assertEquals(1,user.getUserId());
        assertNotEquals(2, user.getUserId());
    }

    @Test
    void gettingUserNameAidan() {
        user = new User(1, "Aidan", "aidan@mail.com");
        assertEquals("Aidan",user.getUserName());
        assertNotEquals("John",user.getUserName());
    }

    @Test
    void gettingUserEmailSuccesfully() {
        user = new User(1, "Aidan", "aidan@mail.com");
        assertEquals("aidan@mail.com",user.getUserEmail());
        assertNotEquals("john@mail.com",user.getUserEmail());
    }
}
