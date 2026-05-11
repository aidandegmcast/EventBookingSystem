package org.example.EventBookingSystem;

import java.util.HashMap;
import java.util.Map;

import org.example.EventBookingSystem.Exceptions.*;

public class BookingService {

    private final Map<String, Event> events = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Reservation> reservations = new HashMap<>();

    public void addEvent(Event event) {
        if (event == null) throw new IllegalArgumentException("Event cannot be null");
        if (events.containsKey(event.getEventId())) throw new DuplicateEventException("Event ID " + event.getEventId() + " already exists");
        events.put(event.getEventId(), event);
    }
    public void addUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        if (users.containsKey(user.getUserId())) throw new DuplicateUserException("User ID " + user.getUserId() + " already exists");
        users.put(user.getUserId(), user);
    }

    public Event getEvent(String eventId) {
        Event event = events.get(eventId);
        if (event == null) throw new EventNotFoundException();
        return event;
    }

    public User getUser(String userId) {
        User user = users.get(userId);
        if (user == null) throw new UserNotFoundException();
        return user;
    }

    public Reservation getReservation(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) throw new ReservationNotFoundException();
        return reservation;
    }

    public String reserveTicket(String userId, String eventId) throws EventIsInPastException, EventSoldOutException, ReservationAssignedToNullException {
        User user = getUser(userId);
        Event event = getEvent(eventId);

        if (event.isInPast()) throw new EventIsInPastException();

        event.reserveSeat();

        Reservation reservation = new Reservation(event, user);
        reservations.put(reservation.getId(), reservation);

        return reservation.getId();
    }

    public void cancelReservation(String reservationId) throws ReservationAlreadyCancelledException, NoSeatsReservedException {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) throw new ReservationNotFoundException();

        reservation.cancel();
        reservation.getEvent().releaseSeat();
    }

    public Reservation transferReservation(String reservationId, String newUserId) throws TransferException, ReservationAlreadyCancelledException {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) throw new ReservationNotFoundException();
        if (newUserId.equals(reservations.get(reservationId).toString())) throw new TransferException("Cannot transfer to the current user.");

        reservation.transferTo(getUser(newUserId));
        return reservation;
    }
}
