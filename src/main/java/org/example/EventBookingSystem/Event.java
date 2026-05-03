package org.example.EventBookingSystem;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

class Event {

    private int id;
    private String name;
    public ChronoLocalDate date;
    private int totalSeats;
    public int availableSeats;

    public Event(int id, String name, ChronoLocalDate date, int totalSeats) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean isSoldOut() {
        if (availableSeats <= 0) {
            return true;
        }
        return false;
    }

    public boolean isInPast() {

        LocalDate currentDate = LocalDate.now();

        if (currentDate.isAfter(date)) {
            return true;
        }

        return false;
    }

    public boolean hasCapacityFor(int people) {
        if ((availableSeats - people) >= 0) {
            return true;
        }
        return false;
    }
}
