package EventBookingSystem;

import io.cucumber.java.en.*;
import org.example.EventBookingSystem.*;
import org.example.EventBookingSystem.Exceptions.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EventBookingSteps {

    private BookingService bookingService;
    private Event event;
    private User user1;
    private User user2;
    private Reservation reservation;
    private String reservationId;

    @Given("an event with {int} available seats exists")
    public void an_event_with_available_seats_exists(Integer seats) {

        bookingService = new BookingService();

        event = new Event(
                "E1",
                "Glitch Festival",
                LocalDate.now().plusDays(30),
                seats
        );

        bookingService.addEvent(event);
    }

    @Given("a registered user exists")
    public void a_registered_user_exists() {

        user1 = new User(
                "U1",
                "Aidan",
                "aidan@mail.com"
        );

        bookingService.addUser(user1);
    }

    @Given("two registered users exist")
    public void two_registered_users_exist() {
        user1 = new User("U1", "Aidan", "aidan@mail.com");
        user2 = new User("U2", "John", "john@mail.com");
        bookingService.addUser(user1);
        bookingService.addUser(user2);
    }

    @When("the user reserves a ticket")
    public void the_user_reserves_a_ticket() throws Exception, ReservationAssignedToNullException {

        reservationId = bookingService.reserveTicket(
                user1.getUserId(),
                event.getEventId()
        );

        reservation = bookingService.getReservation(reservationId);
    }

    @Then("the reservation should be successful")
    public void the_reservation_should_be_successful() {

        assertNotNull(reservationId);
        assertNotNull(reservation);

        assertEquals(
                Reservation.STATUS.ACTIVE,
                reservation.getStatus()
        );

        assertEquals(
                user1,
                reservation.getUser()
        );

        assertEquals(
                event,
                reservation.getEvent()
        );
    }

    @Then("the event should have {int} available seats")
    public void the_event_should_have_available_seats(Integer seats) {

        assertEquals(
                seats,
                event.getAvailableSeats()
        );
    }

    @When("the first user reserves the last ticket")
    public void theFirstUserReservesTheLastTicket() throws Exception, ReservationAssignedToNullException {
        reservationId = bookingService.reserveTicket(user1.getUserId(), event.getEventId());
        reservation = bookingService.getReservation(reservationId);
    }

    @Then("the second user should not be able to reserve a ticket")
    public void theSecondUserShouldNotBeAbleToReserveATicket() {
        assertThrows(EventSoldOutException.class, () ->
                bookingService.reserveTicket(user2.getUserId(), event.getEventId())
        );
    }

    @Given("the user has reserved a ticket")
    public void theUserHasReservedATicket() throws Exception, ReservationAssignedToNullException {
        reservationId = bookingService.reserveTicket(user1.getUserId(), event.getEventId());
        reservation = bookingService.getReservation(reservationId);
    }

    @When("the user cancels the reservation")
    public void theUserCancelsTheReservation() throws Exception, ReservationAlreadyCancelledException {
        bookingService.cancelReservation(reservationId);
        reservation = bookingService.getReservation(reservationId);
    }

    @Then("the reservation should be cancelled")
    public void theReservationShouldBeCancelled() {
        assertEquals(Reservation.STATUS.CANCELLED, reservation.getStatus());
    }

    @Given("the first user has reserved a ticket")
    public void the_first_user_has_reserved_a_ticket() throws Exception, ReservationAssignedToNullException {
        reservationId = bookingService.reserveTicket(user1.getUserId(), event.getEventId());
        reservation = bookingService.getReservation(reservationId);
    }

    @When("the reservation is transferred to the second user")
    public void the_reservation_is_transferred_to_the_second_user() throws Exception, TransferException, ReservationAlreadyCancelledException {
        reservation = bookingService.transferReservation(reservationId, user2.getUserId());
    }

    @Then("the reservation should belong to the second user")
    public void the_reservation_should_belong_to_the_second_user() {
        assertEquals(user2, reservation.getUser());
        assertEquals(Reservation.STATUS.ACTIVE, reservation.getStatus());
    }

    @Then("the event should still have {int} available seats")
    public void the_event_should_still_have_available_seats(Integer seats) {
        assertEquals(seats, event.getAvailableSeats());
    }
}
