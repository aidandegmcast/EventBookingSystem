package EventBookingSystem;

import org.example.EventBookingSystem.Event;
import org.example.EventBookingSystem.Exceptions.EventIsInPastException;
import org.example.EventBookingSystem.Exceptions.EventSoldOutException;
import org.example.EventBookingSystem.Exceptions.NoSeatsReservedException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    Event event;

    @Test
    void gettingEventId1() {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        assertEquals("1",event.getEventId(),"Getting event ID = 1");
        assertNotEquals("2",event.getEventId(),"Not getting event ID = 2");
    }

    @Test
    void gettingEventNameGlitch() {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        assertEquals("Glitch Festival",event.getEventName(),"Getting event name = Glitch Festival");
        assertNotEquals("XXL",event.getEventName(),"Not Getting event name = XXL");
    }

    @Test
    void gettingEventDate14Aug() {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        assertEquals(LocalDate.parse("2026-08-14"),event.getDate(),"Getting date = 14th Aug");
        assertNotEquals(LocalDate.parse("2026-06-03"),event.getDate(),"Not Getting date = 3rd June");
    }

    @Test
    void gettingEventTotalSeats() {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        assertEquals(10,event.getTotalSeats(),"Getting 10 total seats");
        assertNotEquals(100,event.getTotalSeats(),"Not Getting 10 total seats");
    }

    @Test
    void getting10AvailableSeats() {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        assertEquals(10,event.getAvailableSeats(),"Getting 10 available seats");
        assertNotEquals(100,event.getAvailableSeats(),"Not Getting 100 available seats");
    }

    @Test
    void reservingASeatAndGetting9AvailableSeats() throws EventSoldOutException, EventIsInPastException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        event.reserveSeat();
        assertEquals(9,event.getAvailableSeats(),"Getting 9 available seats");
        assertNotEquals(10,event.getAvailableSeats(),"Not Getting 10 available seats");
    }

    @Test
    void getting0ReservedSeats() throws EventSoldOutException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        assertEquals(0,event.getReservedSeats(),"Getting 0 reserved seats");
        assertNotEquals(1,event.getReservedSeats(),"Not Getting 1 reserved seats");
    }

    @Test
    void reservingASeatAndGetting1ReservedSeat() throws EventSoldOutException, EventIsInPastException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        event.reserveSeat();
        assertEquals(1,event.getReservedSeats(),"Getting 1 reserved seats");
        assertNotEquals(0,event.getReservedSeats(),"Not Getting 0 reserved seats");
    }

    @Test
    void reserving2SeatsAndGetting2ReservedSeatAnd8AvailableSeats() throws EventSoldOutException, EventIsInPastException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        event.reserveSeats(2);
        assertEquals(2,event.getReservedSeats(),"Getting 2 reserved seats");
        assertEquals(8,event.getAvailableSeats(),"Getting 8 available seats");
        assertNotEquals(0,event.getReservedSeats(),"Not Getting 0 reserved seats");
    }

    @Test
    void reserving11AndThrowingEventSoldOutException() throws EventSoldOutException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        Exception e = assertThrows(EventSoldOutException.class,()-> event.reserveSeats(11));
        assertEquals("Amount of seats available: 10",e.getMessage());
        assertEquals(0,event.getReservedSeats(),"Getting 0 reserved seats");
        assertNotEquals(11, event.getReservedSeats(), "Not getting 11 reserved seats");
    }

    @Test
    void reserving10Then1MoreSeatAndThrowingEventSoldOutException() throws EventSoldOutException, EventIsInPastException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        event.reserveSeats(10);
        Exception e = assertThrows(EventSoldOutException.class,()-> event.reserveSeats(11));
        assertEquals("Amount of seats available: 0",e.getMessage());
        assertEquals(10,event.getReservedSeats(),"Getting 0 reserved seats");
        assertNotEquals(11, event.getReservedSeats(), "Not getting 11 reserved seats");
    }

    @Test
    void checkingIsSoldOut() throws EventSoldOutException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        assertFalse(event.isSoldOut());
    }

    @Test
    void reserving10ThenCheckingIsSoldOut() throws EventSoldOutException, EventIsInPastException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        event.reserveSeats(10);
        assertTrue(event.isSoldOut());
    }

    @Test
    void hasCapacityFor10() {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        assertTrue(event.hasCapacityFor(10));
    }

    @Test
    void hasCapacityFor10Fails() throws EventSoldOutException, EventIsInPastException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        event.reserveSeat();
        assertFalse(event.hasCapacityFor(10));
    }

    @Test
    void releaseSeatAfterBooking() throws EventSoldOutException, NoSeatsReservedException, EventIsInPastException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        event.reserveSeat();
        event.releaseSeat();
        assertEquals(10,event.getAvailableSeats());
    }

    @Test
    void releaseSeatWithoutBooking() throws NoSeatsReservedException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        Exception e = assertThrows(NoSeatsReservedException.class ,()-> event.releaseSeat());
        assertEquals("Seat cannot be released if not reserved!", e.getMessage());
        assertEquals(10,event.getAvailableSeats());
    }

    @Test
    void release3SeatAfterBooking4() throws EventSoldOutException, NoSeatsReservedException, EventIsInPastException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        event.reserveSeats(4);
        event.releaseSeats(3);
        assertEquals(1, event.getReservedSeats(), "Only 1 reserved seat should be left");
    }

    @Test
    void release4SeatsAfterBooking3() throws NoSeatsReservedException, EventSoldOutException, EventIsInPastException {
        event = new Event("1","Glitch Festival", LocalDate.parse( "2026-08-14"), 10 );
        event.reserveSeats(3);
        Exception e = assertThrows(NoSeatsReservedException.class,()-> event.releaseSeats(4));
        assertEquals("Cannot release more seats than: 3",e.getMessage());
    }

    @Test
    void checkIfEventIsInPast() {
        //2025
        event = new Event("1","Glitch Festival", LocalDate.parse( "2025-08-14"), 10 );
        assertTrue(event.isInPast());
    }

    @Test
    void reservingASeatInAPastEventShouldNotWork() {
        //2025
        event = new Event("1","Glitch Festival", LocalDate.parse( "2025-08-14"), 10 );
        assertThrows(EventIsInPastException.class, ()-> event.reserveSeat());
        assertEquals(0, event.getReservedSeats());
    }

}
