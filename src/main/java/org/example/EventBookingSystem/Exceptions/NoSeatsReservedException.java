package org.example.EventBookingSystem.Exceptions;

public class NoSeatsReservedException extends Exception {
    public NoSeatsReservedException(String message) {
        super(message);
    }
}
