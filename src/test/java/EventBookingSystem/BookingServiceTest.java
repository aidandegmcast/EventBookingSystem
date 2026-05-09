package EventBookingSystem;

import org.example.EventBookingSystem.BookingService;

import org.example.EventBookingSystem.Event;
import org.example.EventBookingSystem.Exceptions.*;
import org.example.EventBookingSystem.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookingServiceTest {

    BookingService bookingService = new BookingService();

    Event event0 = null;
    Event event1 = new Event("1", "DALMA FESTIVAL", LocalDate.parse("2025-12-01"), 10);
    Event event2 = new Event("2", "Glitch Club Series: Cloudy.Mp3", LocalDate.parse("2026-07-01"), 10);
    Event event3 = new Event("3", "Neolitika: Kobosil", LocalDate.parse("2026-07-01"), 10);


    User user0 = null;
    User user1 = new User("1", "aidan", "aidan@mail.com");
    User user2 = new User("2", "yanik", "yanik@mail.com");
    User user3 = new User("3", "raiden", "raiden@mail.com");
    User user4 = new User("4", "dion", "dion@mail.com");
    User user5 = new User("5", "nayden", "nayden@mail.com");
    User user6 = new User("6", "zven", "zven@mail.com");
    User user7 = new User("7", "luke", "luke@mail.com");
    User user8 = new User("8", "kurt", "kurt@mail.com");
    User user9 = new User("9", "ellezia", "ellezia@mail.com");
    User user10 = new User("10", "maria", "maria@mail.com");
    User user11 = new User("11", "david", "david@mail.com");

    @BeforeEach
    void setup() {

    }

    @Test
    void addingAnEvent() {
        bookingService.addEvent(event2);
        assertEquals(event2 ,bookingService.getEvent("2"), "addEvent, getEvent, and events HashMap work");
    }

    @Test
    void addingMultipleAnEvents() {
        bookingService.addEvent(event2);
        bookingService.addEvent(event3);

        assertEquals(event2 ,bookingService.getEvent("2"));
        assertEquals(event3 ,bookingService.getEvent("3"));
    }

    @Test
    void addingANullEvent() {
        assertThrows(IllegalArgumentException.class, ()-> bookingService.addEvent(event0));
    }

    @Test
    void addingADuplicateEvent() {
        bookingService.addEvent(event2);
        assertThrows(DuplicateEventException.class, ()-> bookingService.addEvent(event2));
    }

    @Test
    void addingAUser() {
        bookingService.addUser(user1);
        assertEquals(user1, bookingService.getUser("1"));
    }

    @Test
    void addingMultipleUsers() {
        bookingService.addUser(user1);
        bookingService.addUser(user2);
        bookingService.addUser(user3);

        assertEquals(user1, bookingService.getUser("1"));
        assertEquals(user2, bookingService.getUser("2"));
        assertEquals(user3, bookingService.getUser("3"));

    }

    @Test
    void addingANullUser() {
        assertThrows(IllegalArgumentException.class, ()-> bookingService.addUser(user0));
    }

    @Test
    void addingADuplicateUser() {
        bookingService.addUser(user1);
        assertThrows(DuplicateUserException.class, ()-> bookingService.addUser(user1));
    }

    @Nested
    class WithSetup {

        @BeforeEach
        void setup() {
            bookingService.addEvent(event1);
            bookingService.addEvent(event2);
            bookingService.addEvent(event3);
            bookingService.addUser(user1);
            bookingService.addUser(user2);
            bookingService.addUser(user3);
            bookingService.addUser(user4);
            bookingService.addUser(user5);
            bookingService.addUser(user6);
            bookingService.addUser(user7);
            bookingService.addUser(user8);
            bookingService.addUser(user9);
            bookingService.addUser(user10);
            bookingService.addUser(user11);
        }

        @Test
        void reservingATicket() throws ReservationAssignedToNullException, EventIsInPastException, EventSoldOutException {
            bookingService.reserveTicket("1","2");
            assertEquals(1, event2.getReservedSeats());
        }

        @Test
        void reservingATicketForUser1InEvent2() throws ReservationAssignedToNullException, EventIsInPastException, EventSoldOutException {
            bookingService.reserveTicket(user1.getUserId(), event2.getEventId());
            assertEquals(9 , event2.getAvailableSeats());
            assertEquals(1 , event2.getReservedSeats());
        }

        @Test
        void reservingATicketForUnknownUser() {
            assertThrows(UserNotFoundException.class, ()-> bookingService.reserveTicket("100", event2.getEventId()));
        }

        @Test
        void reservingATicketForUnknownEvent() {
            assertThrows(EventNotFoundException.class, ()-> bookingService.reserveTicket(user1.getUserId(), "100"));
        }

        @Test
        void reservingForASoldOutEvent() throws ReservationAssignedToNullException, EventIsInPastException, EventSoldOutException {
            //selling out the event
            bookingService.reserveTicket(user1.getUserId(), event2.getEventId());
            bookingService.reserveTicket(user2.getUserId(), event2.getEventId());
            bookingService.reserveTicket(user3.getUserId(), event2.getEventId());
            bookingService.reserveTicket(user4.getUserId(), event2.getEventId());
            bookingService.reserveTicket(user5.getUserId(), event2.getEventId());
            bookingService.reserveTicket(user6.getUserId(), event2.getEventId());
            bookingService.reserveTicket(user7.getUserId(), event2.getEventId());
            bookingService.reserveTicket(user8.getUserId(), event2.getEventId());
            bookingService.reserveTicket(user9.getUserId(), event2.getEventId());
            bookingService.reserveTicket(user10.getUserId(), event2.getEventId());
            assertEquals(0, event2.getAvailableSeats());

            assertThrows(EventSoldOutException.class, ()-> bookingService.reserveTicket(user11.getUserId(), event2.getEventId()));
        }

        @Test
        void reservingATicketForAPastEvent() {
            assertThrows(EventIsInPastException.class, ()-> bookingService.reserveTicket(user1.getUserId(), event1.getEventId()));
        }
    }
}
