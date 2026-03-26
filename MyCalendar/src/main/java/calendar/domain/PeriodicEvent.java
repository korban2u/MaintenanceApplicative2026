package calendar.domain;

import calendar.domain.value.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.stream.Stream;

public final class PeriodicEvent implements Event {

    private final EventId id;
    private final EventTitle title;
    private final EventDateTime start;
    private final EventDuration duration;
    private final Frequency frequency;

    @JsonCreator
    public PeriodicEvent(
            @JsonProperty("id") EventId id,
            @JsonProperty("title") EventTitle title,
            @JsonProperty("start") EventDateTime start,
            @JsonProperty("duration") EventDuration duration,
            @JsonProperty("frequency") Frequency frequency) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.duration = duration;
        this.frequency = frequency;
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

    @JsonProperty("frequency")
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
