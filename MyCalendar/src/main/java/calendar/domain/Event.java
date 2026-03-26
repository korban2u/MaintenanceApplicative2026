package calendar.domain;

import calendar.domain.value.EventDateTime;
import calendar.domain.value.EventDuration;
import calendar.domain.value.EventId;
import calendar.domain.value.EventTitle;

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
