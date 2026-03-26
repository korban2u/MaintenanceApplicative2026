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

    public boolean hasConflict(Event e1, Event e2) {
        return e1.start().isBefore(e2.end()) && e1.end().isAfter(e2.start());
    }

    public List<Event> findConflicts() {
        return events.stream()
                .filter(e1 -> events.stream()
                        .filter(e2 -> !e2.id().equals(e1.id()))
                        .anyMatch(e2 -> hasConflict(e1, e2)))
                .collect(Collectors.toList());
    }
}
