package calendar.domain.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public final class Frequency {

    private final long days;

    private Frequency(long days) {
        this.days = days;
    }

    @JsonCreator
    public static Frequency everyDays(long days) {
        return new Frequency(days);
    }

    public static Frequency weekly() {
        return new Frequency(7);
    }

    public static Frequency monthly() {
        return new Frequency(30);
    }

    public static Frequency yearly() {
        return new Frequency(365);
    }

    @JsonValue
    public long toDays() {
        return days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frequency)) return false;
        return days == ((Frequency) o).days;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days);
    }

    @Override
    public String toString() {
        return "every " + days + " days";
    }
}
