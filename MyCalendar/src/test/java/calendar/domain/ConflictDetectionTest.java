package calendar.domain;

import calendar.domain.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConflictDetectionTest {

    private CalendarManager manager;

    @BeforeEach
    void setUp() {
        manager = new CalendarManager();
    }

    private Event appointment(String title, LocalDateTime start, long durationMinutes) {
        return new PersonalAppointment(
                EventId.generate(),
                EventTitle.of(title),
                EventDateTime.of(start),
                EventDuration.ofMinutes(durationMinutes)
        );
    }

    @Test
    void two_overlapping_events_conflict() {
        Event e1 = appointment("A", LocalDateTime.of(2026, 3, 26, 10, 0), 60);
        Event e2 = appointment("B", LocalDateTime.of(2026, 3, 26, 10, 30), 60);
        assertTrue(manager.hasConflict(e1, e2));
    }

    @Test
    void two_non_overlapping_events_do_not_conflict() {
        Event e1 = appointment("A", LocalDateTime.of(2026, 3, 26, 10, 0), 60);
        Event e2 = appointment("B", LocalDateTime.of(2026, 3, 26, 11, 0), 60);
        assertFalse(manager.hasConflict(e1, e2));
    }

    @Test
    void event_contained_within_other_conflicts() {
        Event e1 = appointment("A", LocalDateTime.of(2026, 3, 26, 10, 0), 120);
        Event e2 = appointment("B", LocalDateTime.of(2026, 3, 26, 10, 30), 30);
        assertTrue(manager.hasConflict(e1, e2));
    }

    @Test
    void adjacent_events_do_not_conflict() {
        Event e1 = appointment("A", LocalDateTime.of(2026, 3, 26, 10, 0), 60);
        Event e2 = appointment("B", LocalDateTime.of(2026, 3, 26, 11, 0), 60);
        assertFalse(manager.hasConflict(e1, e2));
    }

    @Test
    void findConflicts_returns_conflicting_events() {
        Event e1 = appointment("A", LocalDateTime.of(2026, 3, 26, 10, 0), 60);
        Event e2 = appointment("B", LocalDateTime.of(2026, 3, 26, 10, 30), 60);
        Event e3 = appointment("C", LocalDateTime.of(2026, 3, 26, 14, 0), 60);
        manager.add(e1);
        manager.add(e2);
        manager.add(e3);

        List<Event> conflicts = manager.findConflicts();
        assertTrue(conflicts.contains(e1));
        assertTrue(conflicts.contains(e2));
        assertFalse(conflicts.contains(e3));
    }
}
