package org.example.EventBookingSystem;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class Event {

    public int id;
    public String name;
    public ChronoLocalDate date;
    public int totalSeats;
    private int reservedSeats;

    public Event(int id, String name, ChronoLocalDate date, int totalSeats) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.totalSeats = totalSeats;
    }

    public int getEventId() {
        return id;
    }
    public String getEventName() {
        return name;
    }
    public ChronoLocalDate getDate() {
        return date;
    }
    public  int getTotalSeats() {
        return totalSeats;
    }
    public int getAvailableSeats() {
        return totalSeats - reservedSeats;
    }
    public int getReservedSeats() {
        return reservedSeats;
    }

    public boolean isSoldOut() {
        if (getAvailableSeats() <= 0) return true;

        return false;
    }

    public boolean isInPast() {

        LocalDate currentDate = LocalDate.now();

        if (currentDate.isBefore(date)) return true;

        return false;
    }

    public boolean hasCapacityFor(int people) {
        if ((getAvailableSeats() - people) >= 0) return true;

        return false;
    }

    public void reserveSeat() throws EventSoldOutException {
        if (isSoldOut()) throw new EventSoldOutException("Event is Sold out!");
        reservedSeats++;
    }
    public void releaseSeat() {
        reservedSeats--;
    }
    public void reserveSeats(int people) throws  EventSoldOutException {
        if (!hasCapacityFor(people)) throw new EventSoldOutException("Amount of seats available: " + getAvailableSeats());
    }
    public void releaseSeats(int people) {
        reservedSeats = reservedSeats - people;
    }
}
