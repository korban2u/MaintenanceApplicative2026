package calendar.domain;

import calendar.domain.value.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MeetingTest {

    private static final EventId ID = EventId.generate();
    private static final EventTitle TITLE = EventTitle.of("Sprint Review");
    private static final EventDateTime START = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 14, 0));
    private static final EventDuration DURATION = EventDuration.ofMinutes(60);
    private static final EventLocation LOCATION = EventLocation.of("Room B");
    private static final Participants PARTICIPANTS = Participants.of(
            EventTitle.of("Alice"), EventTitle.of("Bob")
    );

    @Test
    void description_contains_title_location_and_participants() {
        Event event = new Meeting(ID, TITLE, START, DURATION, LOCATION, PARTICIPANTS);
        String desc = event.description();
        assertTrue(desc.contains("Sprint Review"));
        assertTrue(desc.contains("Room B"));
        assertTrue(desc.contains("Alice"));
        assertTrue(desc.contains("Bob"));
    }

    @Test
    void occursInPeriod_when_start_is_within_range() {
        Event event = new Meeting(ID, TITLE, START, DURATION, LOCATION, PARTICIPANTS);
        EventDateTime from = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 0, 0));
        EventDateTime to = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 23, 59));
        assertTrue(event.occursInPeriod(from, to));
    }

    @Test
    void location_and_participants_are_accessible() {
        Meeting event = new Meeting(ID, TITLE, START, DURATION, LOCATION, PARTICIPANTS);
        assertEquals(LOCATION, event.location());
        assertEquals(PARTICIPANTS, event.participants());
    }
}
