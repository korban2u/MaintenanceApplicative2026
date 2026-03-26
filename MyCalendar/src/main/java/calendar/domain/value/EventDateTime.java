package calendar.domain.value;

import java.time.LocalDateTime;
import java.util.Objects;

public final class EventDateTime {

    private final LocalDateTime value;

    private EventDateTime(LocalDateTime value) {
        this.value = Objects.requireNonNull(value);
    }

    public static EventDateTime of(LocalDateTime value) {
        return new EventDateTime(value);
    }

    public EventDateTime plusMinutes(EventDuration duration) {
        return new EventDateTime(value.plusMinutes(duration.toMinutes()));
    }

    public EventDateTime plusDays(long days) {
        return new EventDateTime(value.plusDays(days));
    }

    public boolean isBefore(EventDateTime other) {
        return value.isBefore(other.value);
    }

    public boolean isAfter(EventDateTime other) {
        return value.isAfter(other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventDateTime)) return false;
        return Objects.equals(value, ((EventDateTime) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
