package EventBookingSystem;

import org.example.EventBookingSystem.*;
import org.example.EventBookingSystem.Exceptions.ReservationAlreadyCancelledException;
import org.example.EventBookingSystem.Exceptions.ReservationAssignedToNullException;
import org.example.EventBookingSystem.Exceptions.TransferException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    Reservation reservation;
    Event event;
    User user;
    User user2;

    @Nested
    class WithoutSetup {

        @Test
        void creatingAReservationWithoutAnEventDoesNotWork() throws ReservationAssignedToNullException {
            assertThrows(ReservationAssignedToNullException.class,()->reservation = new Reservation(event, user));
        }

        @Test
        void transferringToTheSameUser() throws ReservationAssignedToNullException {
            event = new Event("1", "Glitch", LocalDate.parse("2026-08-14"), 10);
            user = new User("1", "Aidan", "aidan@mail.com");
            reservation = new Reservation(event, user);
            TransferException e = assertThrows(TransferException.class, ()-> reservation.transferTo(user2));
            assertEquals("Transferring to a NULL user is not possible!" , e.getMessage());
        }

    }

    @Nested
    class WithSetup {

        @BeforeEach
        void setup() {
            user = new User("1", "Aidan", "aidan@mail.com");
            event = new Event("1", "Glitch", LocalDate.parse("2026-08-14"), 10);
        }

        @Test
        void creatingAReservationShouldBeActive() throws ReservationAssignedToNullException {
            reservation = new Reservation(event, user);
            assertEquals(Reservation.STATUS.ACTIVE, reservation.getStatus());
        }

        @Test
        void gettingReservationIdFromReservation() throws ReservationAssignedToNullException {
            reservation = new Reservation(event, user);
            assertEquals(1, reservation.getId());
        }

        @Test
        void gettingEventIdFromReservation() throws ReservationAssignedToNullException {
            reservation = new Reservation(event, user);
            assertEquals(event, reservation.getEvent());
        }

        @Test //fails due to processing time (few milliseconds apart)
        void gettingCreatedAtFromReservation() throws ReservationAssignedToNullException {
            reservation = new Reservation(event, user);
            assertNotEquals(LocalDateTime.now(), reservation.getCreatedAt());
        }

        @Test
        void cancellingAReservationChangesStatus() throws ReservationAlreadyCancelledException, ReservationAssignedToNullException {
            reservation = new Reservation(event, user);            reservation.cancel();
            assertEquals(Reservation.STATUS.CANCELLED, reservation.getStatus());
        }

        @Test
        void cancellingAReservationTwiceThrowsException() throws ReservationAlreadyCancelledException, ReservationAssignedToNullException {
            reservation = new Reservation(event, user);            reservation.cancel();
            assertThrows(ReservationAlreadyCancelledException.class, ()-> reservation.cancel());
        }

        @Test
        void transferringReservationToAnotherUser() throws ReservationAlreadyCancelledException, ReservationAssignedToNullException, TransferException {
            reservation = new Reservation(event, user);            assertEquals(user, reservation.getUser());

            user2 = new User("2", "John", "john@mail.com");
            reservation.transferTo(user2);
            assertEquals(Reservation.STATUS.ACTIVE, reservation.getStatus());
            assertEquals(user2, reservation.getUser());
        }

        @Test
        void transferringACancelledReservationToAnotherUser() throws ReservationAlreadyCancelledException, ReservationAssignedToNullException {
            reservation = new Reservation(event, user);            reservation.cancel();
            assertEquals(Reservation.STATUS.CANCELLED, reservation.getStatus());

            User user2 = new User("2", "John", "john@mail.com");
            assertThrows(ReservationAlreadyCancelledException.class,()-> reservation.transferTo(user2));
        }

        @Test
        void transferringToTheSameUser() throws ReservationAssignedToNullException {
            reservation = new Reservation(event, user);            TransferException e = assertThrows(TransferException.class, ()-> reservation.transferTo(user));
            assertEquals("Transferring to the same user is not possible!" , e.getMessage());
        }
    }
}