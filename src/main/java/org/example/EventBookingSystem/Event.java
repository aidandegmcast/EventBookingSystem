package org.example.EventBookingSystem;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class Event {

    public int id;
    public String name;
    public LocalDate date;
    public int totalSeats;
    private int reservedSeats;

    public Event(int id, String name, LocalDate date, int totalSeats) {
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
    public LocalDate getDate() {
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

        if (currentDate.isAfter(date)) return true;

        return false;
    }

    public boolean hasCapacityFor(int people) {
        if ((getAvailableSeats() - people) >= 0) return true;

        return false;
    }

    public void reserveSeat() throws EventSoldOutException, EventIsInPastException {
        if (isSoldOut()) throw new EventSoldOutException("Event is Sold out!");
        if (isInPast()) throw new EventIsInPastException();
        reservedSeats++;
    }
    public void releaseSeat() throws NoSeatsReservedException {
        if (getAvailableSeats() == totalSeats) throw new NoSeatsReservedException("Seat cannot be released if not reserved!");
        reservedSeats--;
    }
    public void reserveSeats(int people) throws EventSoldOutException, EventIsInPastException {
        if (!hasCapacityFor(people)) throw new EventSoldOutException("Amount of seats available: " + getAvailableSeats());
        if (isInPast()) throw new EventIsInPastException();
        reservedSeats = reservedSeats + people;
    }
    public void releaseSeats(int people) throws NoSeatsReservedException {
        if (getAvailableSeats() == totalSeats) throw new NoSeatsReservedException("Seat cannot be released if not reserved!");
        if (people > getReservedSeats()) throw new NoSeatsReservedException("Cannot release more seats than: " + getReservedSeats());
        reservedSeats = reservedSeats - people;
    }
}
