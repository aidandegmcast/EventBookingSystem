package org.example.EventBookingSystem;

import org.example.EventBookingSystem.Exceptions.ReservationAlreadyCancelledException;
import org.example.EventBookingSystem.Exceptions.ReservationAssignedToNullException;
import org.example.EventBookingSystem.Exceptions.TransferException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {

    public enum STATUS {ACTIVE, CANCELLED};

    public String id;
    public Event event;
    public User user;
    public STATUS status;
    public LocalDateTime createdAt;

    public Reservation(Event event, User user) throws ReservationAssignedToNullException {
        if (event == null) throw new ReservationAssignedToNullException();

        this.id = UUID.randomUUID().toString();
        this.event = event;
        this.user = user;
        this.status = STATUS.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public User getUser() {
        return user;
    }

    public STATUS getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void cancel() throws ReservationAlreadyCancelledException {
        if (!isActive()) throw new ReservationAlreadyCancelledException();

        status = STATUS.CANCELLED;
    }

    public void transferTo(User newUser) throws ReservationAlreadyCancelledException, TransferException {
        if (!isActive()) throw new ReservationAlreadyCancelledException();
        if (newUser == null) throw new TransferException("Transferring to a NULL user is not possible!");
        if (newUser == user) throw new TransferException("Transferring to the same user is not possible!");
        user = newUser;
    }

    public boolean isActive() {
        if (status == STATUS.ACTIVE) return true;

        return  false;
    }
}
