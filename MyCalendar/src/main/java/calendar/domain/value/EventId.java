package calendar.domain.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.UUID;

public final class EventId {

    private final UUID value;

    private EventId(UUID value) {
        this.value = Objects.requireNonNull(value);
    }

    public static EventId generate() {
        return new EventId(UUID.randomUUID());
    }

    @JsonCreator
    public static EventId of(String value) {
        return new EventId(UUID.fromString(value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventId)) return false;
        return Objects.equals(value, ((EventId) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @JsonValue
    @Override
    public String toString() {
        return value.toString();
    }
}
