package calendar.domain.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public final class EventLocation {

    private final String value;

    private EventLocation(String value) {
        this.value = Objects.requireNonNull(value);
    }

    @JsonCreator
    public static EventLocation of(String value) {
        return new EventLocation(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventLocation)) return false;
        return Objects.equals(value, ((EventLocation) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }
}
