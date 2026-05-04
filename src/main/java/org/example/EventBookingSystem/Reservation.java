package org.example.EventBookingSystem;

import java.time.LocalDate;

public class Reservation {

    public int id;
    public Event event;
    public User user;
    public enum status {ACTIVE, CANCELLED, TRANSFERRED};
    public LocalDate createdAt;

    public Reservation() {

    }

    public void cancel() {}
    public void transferTo(User newUser) {}
    public boolean isActive() {
        return  false;
    }
}
