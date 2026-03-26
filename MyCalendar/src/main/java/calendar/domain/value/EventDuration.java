package calendar.domain.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public final class EventDuration {

    private final long minutes;

    private EventDuration(long minutes) {
        this.minutes = minutes;
    }

    @JsonCreator
    public static EventDuration ofMinutes(long minutes) {
        return new EventDuration(minutes);
    }

    @JsonValue
    public long toMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventDuration)) return false;
        return minutes == ((EventDuration) o).minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutes);
    }

    @Override
    public String toString() {
        return minutes + " min";
    }
}
