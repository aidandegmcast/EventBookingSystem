package org.example.EventBookingSystem;

public class EventSoldOutException extends Exception {
    public EventSoldOutException(String message) {
        super(message);
    }
}
