package calendar.domain.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public final class EventTitle {

    private final String value;

    private EventTitle(String value) {
        this.value = Objects.requireNonNull(value);
    }

    @JsonCreator
    public static EventTitle of(String value) {
        return new EventTitle(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventTitle)) return false;
        return Objects.equals(value, ((EventTitle) o).value);
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
