package calendar.domain.value;

import java.util.Objects;

public final class EventLocation {

    private final String value;

    private EventLocation(String value) {
        this.value = Objects.requireNonNull(value);
    }

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

    @Override
    public String toString() {
        return value;
    }
}
