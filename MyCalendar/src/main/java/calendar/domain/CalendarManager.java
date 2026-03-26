package calendar.domain;

import calendar.domain.value.EventDateTime;
import calendar.domain.value.EventId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarManager {

    private final List<Event> events = new ArrayList<>();

    public void add(Event event) {
        events.add(event);
    }

    public void remove(EventId id) {
        events.removeIf(e -> e.id().equals(id));
    }

    public List<Event> eventsInPeriod(EventDateTime from, EventDateTime to) {
        return events.stream()
                .filter(e -> e.occursInPeriod(from, to))
                .collect(Collectors.toList());
    }
}
