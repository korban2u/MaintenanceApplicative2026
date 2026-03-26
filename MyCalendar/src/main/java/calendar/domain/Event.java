package calendar.domain;

import calendar.domain.value.EventDateTime;
import calendar.domain.value.EventDuration;
import calendar.domain.value.EventId;
import calendar.domain.value.EventTitle;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonalAppointment.class, name = "appointment"),
        @JsonSubTypes.Type(value = Meeting.class, name = "meeting"),
        @JsonSubTypes.Type(value = PeriodicEvent.class, name = "periodic")
})
public interface Event {

    EventId id();

    EventTitle title();

    EventDateTime start();

    EventDuration duration();

    default EventDateTime end() {
        return start().plusMinutes(duration());
    }

    String description();

    boolean occursInPeriod(EventDateTime from, EventDateTime to);
}
