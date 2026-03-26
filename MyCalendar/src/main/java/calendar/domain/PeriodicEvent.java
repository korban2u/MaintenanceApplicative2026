package calendar.domain;

import calendar.domain.value.*;

import java.util.stream.Stream;

public final class PeriodicEvent implements Event {

    private final EventId id;
    private final EventTitle title;
    private final EventDateTime start;
    private final EventDuration duration;
    private final Frequency frequency;

    public PeriodicEvent(EventId id, EventTitle title, EventDateTime start, EventDuration duration,
                         Frequency frequency) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.duration = duration;
        this.frequency = frequency;
    }

    @Override
    public EventId id() { return id; }

    @Override
    public EventTitle title() { return title; }

    @Override
    public EventDateTime start() { return start; }

    @Override
    public EventDuration duration() { return duration; }

    public Frequency frequency() { return frequency; }

    @Override
    public String description() {
        return "Periodic: " + title + " every " + frequency.toDays() + " days starting " + start;
    }

    @Override
    public boolean occursInPeriod(EventDateTime from, EventDateTime to) {
        return Stream.iterate(start, s -> s.isBefore(to), s -> s.plusDays(frequency.toDays()))
                .anyMatch(s -> !s.isBefore(from));
    }
}
