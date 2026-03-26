package calendar.domain;

import calendar.domain.value.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicEventTest {

    private static final EventId ID = EventId.generate();
    private static final EventTitle TITLE = EventTitle.of("Weekly Standup");
    private static final EventDateTime START = EventDateTime.of(LocalDateTime.of(2026, 3, 2, 9, 0));
    private static final EventDuration DURATION = EventDuration.ofMinutes(15);
    private static final Frequency FREQUENCY = Frequency.weekly();

    @Test
    void description_contains_title_and_frequency() {
        Event event = new PeriodicEvent(ID, TITLE, START, DURATION, FREQUENCY);
        String desc = event.description();
        assertTrue(desc.contains("Weekly Standup"));
        assertTrue(desc.contains("7"));
    }

    @Test
    void occursInPeriod_when_first_occurrence_is_in_range() {
        Event event = new PeriodicEvent(ID, TITLE, START, DURATION, FREQUENCY);
        EventDateTime from = EventDateTime.of(LocalDateTime.of(2026, 3, 1, 0, 0));
        EventDateTime to = EventDateTime.of(LocalDateTime.of(2026, 3, 7, 23, 59));
        assertTrue(event.occursInPeriod(from, to));
    }

    @Test
    void occursInPeriod_when_later_occurrence_is_in_range() {
        Event event = new PeriodicEvent(ID, TITLE, START, DURATION, FREQUENCY);
        EventDateTime from = EventDateTime.of(LocalDateTime.of(2026, 3, 23, 0, 0));
        EventDateTime to = EventDateTime.of(LocalDateTime.of(2026, 3, 29, 23, 59));
        assertTrue(event.occursInPeriod(from, to));
    }

    @Test
    void doesNotOccurInPeriod_when_no_occurrence_in_range() {
        Event event = new PeriodicEvent(ID, TITLE, START, DURATION, FREQUENCY);
        EventDateTime from = EventDateTime.of(LocalDateTime.of(2026, 3, 3, 0, 0));
        EventDateTime to = EventDateTime.of(LocalDateTime.of(2026, 3, 8, 23, 59));
        assertFalse(event.occursInPeriod(from, to));
    }

    @Test
    void frequency_is_accessible() {
        PeriodicEvent event = new PeriodicEvent(ID, TITLE, START, DURATION, FREQUENCY);
        assertEquals(FREQUENCY, event.frequency());
    }
}
