package calendar.domain;

import calendar.domain.value.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PersonalAppointmentTest {

    private static final EventId ID = EventId.generate();
    private static final EventTitle TITLE = EventTitle.of("Doctor");
    private static final EventDateTime START = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 10, 0));
    private static final EventDuration DURATION = EventDuration.ofMinutes(30);

    @Test
    void description_contains_title_and_start() {
        Event event = new PersonalAppointment(ID, TITLE, START, DURATION);
        String desc = event.description();
        assertTrue(desc.contains("Doctor"));
        assertTrue(desc.contains(START.toString()));
    }

    @Test
    void occursInPeriod_when_start_is_within_range() {
        Event event = new PersonalAppointment(ID, TITLE, START, DURATION);
        EventDateTime from = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 0, 0));
        EventDateTime to = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 23, 59));
        assertTrue(event.occursInPeriod(from, to));
    }

    @Test
    void doesNotOccurInPeriod_when_start_is_outside_range() {
        Event event = new PersonalAppointment(ID, TITLE, START, DURATION);
        EventDateTime from = EventDateTime.of(LocalDateTime.of(2026, 3, 27, 0, 0));
        EventDateTime to = EventDateTime.of(LocalDateTime.of(2026, 3, 27, 23, 59));
        assertFalse(event.occursInPeriod(from, to));
    }

    @Test
    void id_title_start_duration_are_accessible() {
        Event event = new PersonalAppointment(ID, TITLE, START, DURATION);
        assertEquals(ID, event.id());
        assertEquals(TITLE, event.title());
        assertEquals(START, event.start());
        assertEquals(DURATION, event.duration());
    }

    @Test
    void end_equals_start_plus_duration() {
        Event event = new PersonalAppointment(ID, TITLE, START, DURATION);
        assertEquals(START.plusMinutes(DURATION), event.end());
    }
}
