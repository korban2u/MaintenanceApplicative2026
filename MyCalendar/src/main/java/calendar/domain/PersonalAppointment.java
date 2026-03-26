package calendar.domain;

import calendar.domain.value.EventDateTime;
import calendar.domain.value.EventDuration;
import calendar.domain.value.EventId;
import calendar.domain.value.EventTitle;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PersonalAppointment implements Event {

    private final EventId id;
    private final EventTitle title;
    private final EventDateTime start;
    private final EventDuration duration;

    @JsonCreator
    public PersonalAppointment(
            @JsonProperty("id") EventId id,
            @JsonProperty("title") EventTitle title,
            @JsonProperty("start") EventDateTime start,
            @JsonProperty("duration") EventDuration duration) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.duration = duration;
    }

    @Override
    @JsonProperty("id")
    public EventId id() { return id; }

    @Override
    @JsonProperty("title")
    public EventTitle title() { return title; }

    @Override
    @JsonProperty("start")
    public EventDateTime start() { return start; }

    @Override
    @JsonProperty("duration")
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
