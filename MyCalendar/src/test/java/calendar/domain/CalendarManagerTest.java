package calendar.domain;

import calendar.domain.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarManagerTest {

    private CalendarManager manager;
    private EventDateTime monday9h;
    private EventDateTime monday10h;
    private EventDateTime monday11h;
    private EventDateTime tuesday9h;

    @BeforeEach
    void setUp() {
        manager = new CalendarManager();
        monday9h = EventDateTime.of(LocalDateTime.of(2026, 3, 23, 9, 0));
        monday10h = EventDateTime.of(LocalDateTime.of(2026, 3, 23, 10, 0));
        monday11h = EventDateTime.of(LocalDateTime.of(2026, 3, 23, 11, 0));
        tuesday9h = EventDateTime.of(LocalDateTime.of(2026, 3, 24, 9, 0));
    }

    @Test
    void add_event_then_retrieve_it() {
        Event event = new PersonalAppointment(
                EventId.generate(), EventTitle.of("Dentist"), monday9h, EventDuration.ofMinutes(30)
        );
        manager.add(event);
        assertEquals(1, manager.eventsInPeriod(monday9h, monday11h).size());
    }

    @Test
    void eventsInPeriod_returns_only_events_in_range() {
        Event inRange = new PersonalAppointment(
                EventId.generate(), EventTitle.of("In"), monday10h, EventDuration.ofMinutes(30)
        );
        Event outOfRange = new PersonalAppointment(
                EventId.generate(), EventTitle.of("Out"), tuesday9h, EventDuration.ofMinutes(30)
        );
        manager.add(inRange);
        manager.add(outOfRange);

        List<Event> result = manager.eventsInPeriod(monday9h, monday11h);
        assertEquals(1, result.size());
        assertEquals(inRange, result.get(0));
    }

    @Test
    void remove_event_by_id() {
        EventId id = EventId.generate();
        Event event = new PersonalAppointment(
                id, EventTitle.of("Dentist"), monday9h, EventDuration.ofMinutes(30)
        );
        manager.add(event);
        manager.remove(id);
        assertTrue(manager.eventsInPeriod(monday9h, monday11h).isEmpty());
    }

    @Test
    void remove_nonexistent_id_does_nothing() {
        manager.add(new PersonalAppointment(
                EventId.generate(), EventTitle.of("Dentist"), monday9h, EventDuration.ofMinutes(30)
        ));
        manager.remove(EventId.generate());
        assertEquals(1, manager.eventsInPeriod(monday9h, monday11h).size());
    }

    @Test
    void periodic_event_appears_in_period_when_occurrence_matches() {
        Event weekly = new PeriodicEvent(
                EventId.generate(), EventTitle.of("Standup"),
                EventDateTime.of(LocalDateTime.of(2026, 3, 2, 9, 0)),
                EventDuration.ofMinutes(15),
                Frequency.weekly()
        );
        manager.add(weekly);
        EventDateTime weekStart = EventDateTime.of(LocalDateTime.of(2026, 3, 23, 0, 0));
        EventDateTime weekEnd = EventDateTime.of(LocalDateTime.of(2026, 3, 29, 23, 59));
        assertEquals(1, manager.eventsInPeriod(weekStart, weekEnd).size());
    }
}
