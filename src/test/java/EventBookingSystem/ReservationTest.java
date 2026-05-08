package EventBookingSystem;

import org.example.EventBookingSystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    Reservation reservation;
    Event event;
    User user;

    @Nested
    class WithoutSetup {

        @Test
        void creatingAReservationWithoutAnEventDoesNotWork() throws ReservationAssignedToNullException {
            assertThrows(ReservationAssignedToNullException.class,()->reservation = new Reservation(event, user));
        }

    }

    @Nested
    class WithSetup {

        @BeforeEach
        void setup() {
            user = new User(1, "Aidan", "aidan@mail.com");
            event = new Event(1, "Glitch", LocalDate.parse("2026-08-14"), 10);
        }

        @Test
        void creatingAReservationShouldBeActive() throws ReservationAssignedToNullException {
            reservation = new Reservation(event, user);
            assertEquals(Reservation.STATUS.ACTIVE, reservation.getStatus());
        }

        @Test
        void cancellingAReservationChangesStatus() throws ReservationAlreadyCancelledException, ReservationAssignedToNullException {
            reservation = new Reservation(event, user);
            reservation.cancel();
            assertEquals(Reservation.STATUS.CANCELLED, reservation.getStatus());
        }

        @Test
        void cancellingAReservationTwiceThrowsException() throws ReservationAlreadyCancelledException, ReservationAssignedToNullException {
            reservation = new Reservation(event, user);
            reservation.cancel();
            assertThrows(ReservationAlreadyCancelledException.class, ()-> reservation.cancel());
        }

        @Test
        void transferringReservationToAnotherUser() throws ReservationAlreadyCancelledException, ReservationAssignedToNullException, TransferException {
            reservation = new Reservation(event, user);
            assertEquals(user, reservation.getUser());

            User user2 = new User(2, "John", "john@mail.com");
            reservation.transferTo(user2);
            assertEquals(Reservation.STATUS.ACTIVE, reservation.getStatus());
            assertEquals(user2, reservation.getUser());
        }

        @Test
        void transferringACancelledReservationToAnotherUser() throws ReservationAlreadyCancelledException, ReservationAssignedToNullException {
            reservation = new Reservation(event, user);
            reservation.cancel();
            assertEquals(Reservation.STATUS.CANCELLED, reservation.getStatus());

            User user2 = new User(2, "John", "john@mail.com");
            assertThrows(ReservationAlreadyCancelledException.class,()-> reservation.transferTo(user2));
        }
    }
}