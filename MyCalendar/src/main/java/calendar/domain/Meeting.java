package calendar.domain;

import calendar.domain.value.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Meeting implements Event {

    private final EventId id;
    private final EventTitle title;
    private final EventDateTime start;
    private final EventDuration duration;
    private final EventLocation location;
    private final Participants participants;

    @JsonCreator
    public Meeting(
            @JsonProperty("id") EventId id,
            @JsonProperty("title") EventTitle title,
            @JsonProperty("start") EventDateTime start,
            @JsonProperty("duration") EventDuration duration,
            @JsonProperty("location") EventLocation location,
            @JsonProperty("participants") Participants participants) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.duration = duration;
        this.location = location;
        this.participants = participants;
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

    @JsonProperty("location")
    public EventLocation location() { return location; }

    @JsonProperty("participants")
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
