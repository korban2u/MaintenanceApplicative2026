package calendar.domain;

import calendar.domain.value.*;

public final class Meeting implements Event {

    private final EventId id;
    private final EventTitle title;
    private final EventDateTime start;
    private final EventDuration duration;
    private final EventLocation location;
    private final Participants participants;

    public Meeting(EventId id, EventTitle title, EventDateTime start, EventDuration duration,
                   EventLocation location, Participants participants) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.duration = duration;
        this.location = location;
        this.participants = participants;
    }

    @Override
    public EventId id() { return id; }

    @Override
    public EventTitle title() { return title; }

    @Override
    public EventDateTime start() { return start; }

    @Override
    public EventDuration duration() { return duration; }

    public EventLocation location() { return location; }

    public Participants participants() { return participants; }

    @Override
    public String description() {
        return "Meeting: " + title + " at " + location + " with " + participants;
    }

    @Override
    public boolean occursInPeriod(EventDateTime from, EventDateTime to) {
        return !start.isBefore(from) && !start.isAfter(to);
    }
}
