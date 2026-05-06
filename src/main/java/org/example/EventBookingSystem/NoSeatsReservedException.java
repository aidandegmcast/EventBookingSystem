package org.example.EventBookingSystem;

public class NoSeatsReservedException extends Exception {
    public NoSeatsReservedException(String message) {
        super(message);
    }
}
