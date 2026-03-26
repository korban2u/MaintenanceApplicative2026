package calendar.domain.value;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {

    @Test
    void eventId_equality() {
        EventId id1 = EventId.generate();
        EventId id2 = EventId.of(id1.toString());
        assertEquals(id1, id2);
    }

    @Test
    void eventId_uniqueness() {
        assertNotEquals(EventId.generate(), EventId.generate());
    }

    @Test
    void eventTitle_equality() {
        assertEquals(EventTitle.of("Meeting"), EventTitle.of("Meeting"));
        assertNotEquals(EventTitle.of("Meeting"), EventTitle.of("Other"));
    }

    @Test
    void eventTitle_toString() {
        assertEquals("Meeting", EventTitle.of("Meeting").toString());
    }

    @Test
    void eventDateTime_equality() {
        LocalDateTime now = LocalDateTime.of(2026, 3, 26, 10, 0);
        assertEquals(EventDateTime.of(now), EventDateTime.of(now));
    }

    @Test
    void eventDateTime_plusMinutes() {
        EventDateTime start = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 10, 0));
        EventDuration duration = EventDuration.ofMinutes(30);
        EventDateTime end = start.plusMinutes(duration);
        assertEquals(EventDateTime.of(LocalDateTime.of(2026, 3, 26, 10, 30)), end);
    }

    @Test
    void eventDateTime_comparison() {
        EventDateTime earlier = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 9, 0));
        EventDateTime later = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 11, 0));
        assertTrue(earlier.isBefore(later));
        assertFalse(later.isBefore(earlier));
    }

    @Test
    void eventDuration_equality() {
        assertEquals(EventDuration.ofMinutes(60), EventDuration.ofMinutes(60));
        assertNotEquals(EventDuration.ofMinutes(60), EventDuration.ofMinutes(30));
    }

    @Test
    void eventDuration_toMinutes() {
        assertEquals(90L, EventDuration.ofMinutes(90).toMinutes());
    }

    @Test
    void eventLocation_equality() {
        assertEquals(EventLocation.of("Room A"), EventLocation.of("Room A"));
        assertNotEquals(EventLocation.of("Room A"), EventLocation.of("Room B"));
    }

    @Test
    void eventLocation_toString() {
        assertEquals("Room A", EventLocation.of("Room A").toString());
    }

    @Test
    void participants_toString() {
        Participants p = Participants.of(EventTitle.of("Alice"), EventTitle.of("Bob"));
        assertEquals("Alice, Bob", p.toString());
    }

    @Test
    void participants_equality() {
        Participants p1 = Participants.of(EventTitle.of("Alice"), EventTitle.of("Bob"));
        Participants p2 = Participants.of(EventTitle.of("Alice"), EventTitle.of("Bob"));
        assertEquals(p1, p2);
    }

    @Test
    void frequency_weekly() {
        assertEquals(7L, Frequency.weekly().toDays());
    }

    @Test
    void frequency_monthly() {
        assertEquals(30L, Frequency.monthly().toDays());
    }

    @Test
    void frequency_yearly() {
        assertEquals(365L, Frequency.yearly().toDays());
    }

    @Test
    void frequency_custom() {
        assertEquals(14L, Frequency.everyDays(14).toDays());
    }
}
