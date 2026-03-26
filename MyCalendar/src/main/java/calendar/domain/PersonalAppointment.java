package calendar.domain;

import calendar.domain.value.EventDateTime;
import calendar.domain.value.EventDuration;
import calendar.domain.value.EventId;
import calendar.domain.value.EventTitle;

public final class PersonalAppointment implements Event {

    private final EventId id;
    private final EventTitle title;
    private final EventDateTime start;
    private final EventDuration duration;

    public PersonalAppointment(EventId id, EventTitle title, EventDateTime start, EventDuration duration) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.duration = duration;
    }

    @Override
    public EventId id() { return id; }

    @Override
    public EventTitle title() { return title; }

    @Override
    public EventDateTime start() { return start; }

    @Override
    public EventDuration duration() { return duration; }

    @Override
    public String description() {
        return "Appointment: " + title + " at " + start;
    }

    @Override
    public boolean occursInPeriod(EventDateTime from, EventDateTime to) {
        return !start.isBefore(from) && !start.isAfter(to);
    }
}
