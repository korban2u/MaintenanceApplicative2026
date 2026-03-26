package calendar.serialization;

import calendar.domain.*;
import calendar.domain.value.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarSerializerTest {

    private final CalendarSerializer serializer = new CalendarSerializer();

    private static final EventDateTime START = EventDateTime.of(LocalDateTime.of(2026, 3, 26, 10, 0));
    private static final EventDuration DURATION = EventDuration.ofMinutes(60);

    @Test
    void serialize_then_deserialize_personal_appointment() throws Exception {
        EventId id = EventId.generate();
        Event event = new PersonalAppointment(id, EventTitle.of("Dentist"), START, DURATION);

        String json = serializer.serialize(List.of(event));
        List<Event> result = serializer.deserialize(json);

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).id());
        assertEquals("Dentist", result.get(0).title().toString());
        assertEquals(START, result.get(0).start());
        assertEquals(DURATION, result.get(0).duration());
    }

    @Test
    void serialize_then_deserialize_meeting() throws Exception {
        EventId id = EventId.generate();
        Event event = new Meeting(
                id, EventTitle.of("Sprint Review"), START, DURATION,
                EventLocation.of("Room A"),
                Participants.of(EventTitle.of("Alice"), EventTitle.of("Bob"))
        );

        String json = serializer.serialize(List.of(event));
        List<Event> result = serializer.deserialize(json);

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).id());
        assertTrue(result.get(0).description().contains("Room A"));
        assertTrue(result.get(0).description().contains("Alice"));
    }

    @Test
    void serialize_then_deserialize_periodic_event() throws Exception {
        EventId id = EventId.generate();
        Event event = new PeriodicEvent(id, EventTitle.of("Standup"), START, DURATION, Frequency.weekly());

        String json = serializer.serialize(List.of(event));
        List<Event> result = serializer.deserialize(json);

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).id());
        assertTrue(result.get(0) instanceof PeriodicEvent);
        assertEquals(Frequency.weekly(), ((PeriodicEvent) result.get(0)).frequency());
    }

    @Test
    void serialize_multiple_event_types() throws Exception {
        List<Event> events = List.of(
                new PersonalAppointment(EventId.generate(), EventTitle.of("A"), START, DURATION),
                new Meeting(EventId.generate(), EventTitle.of("B"), START, DURATION,
                        EventLocation.of("L"), Participants.of(EventTitle.of("X"))),
                new PeriodicEvent(EventId.generate(), EventTitle.of("C"), START, DURATION, Frequency.monthly())
        );

        String json = serializer.serialize(events);
        List<Event> result = serializer.deserialize(json);

        assertEquals(3, result.size());
    }

    @Test
    void serialize_produces_valid_json_string() throws Exception {
        Event event = new PersonalAppointment(EventId.generate(), EventTitle.of("Test"), START, DURATION);
        String json = serializer.serialize(List.of(event));
        assertTrue(json.startsWith("["));
        assertTrue(json.endsWith("]"));
        assertTrue(json.contains("Test"));
    }
}
